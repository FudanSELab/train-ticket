# TrainTicket Infrastructure — Terraform + Terragrunt

Cloud foundation for the TrainTicket platform on AWS: **VPC → EKS → IRSA roles**,
built with reusable Terraform modules driven by a DRY Terragrunt tree.

> **Division of labor:** this directory provisions the *cloud foundation* only —
> Terraform stops at a ready, empty, IRSA-enabled EKS cluster. Everything *on* the
> cluster (Kustomize, Argo CD, service mesh, observability, alerting) is built by hand
> under `apps/` and `platform/` — see [`ROADMAP.md`](./ROADMAP.md).

## Layout

```
infrastructure/
├── root.hcl                      # remote state (S3 native locking) + provider + default tags
├── _modules/                     # reusable Terraform modules (thin wrappers over community modules)
│   ├── vpc/                       # terraform-aws-modules/vpc/aws    (~> 5.8)
│   ├── eks/                       # terraform-aws-modules/eks/aws    (~> 20.24)
│   └── iam-irsa/                  # IRSA roles for cluster controllers
└── environments/
    ├── _common.hcl                # project + region (shared by all envs)
    ├── staging/
    │   ├── account.hcl            # account_id + environment
    │   ├── vpc/         · eks/         · iam-irsa/      # one terragrunt.hcl each
    └── production/
        ├── account.hcl
        ├── vpc/         · eks/         · iam-irsa/
```

Conventions mirror the [`clouds-outline`](https://github.com/HDAli00/clouds-outline)
infrastructure: `root.hcl` at the root, a flat `_modules/`, and `environments/<env>/<module>`
units that pull shared values from `_common.hcl` + per-env `account.hcl`.

## State & providers (set once in `root.hcl`)

- **Remote state:** S3 bucket `trainticket-tfstate-<account_id>`, key per unit, **native S3
  locking** (`use_lockfile = true`) — no DynamoDB table. Requires Terraform **>= 1.10**.
- **Provider:** generated per unit with `default_tags` (Project/Environment/ManagedBy/Repository).

## What gets created

| Module     | Resources |
|------------|-----------|
| `vpc`      | 3-AZ VPC, public + private subnets, NAT gateway, EKS/ELB subnet tags |
| `eks`      | EKS control plane (v1.30), managed node group, core add-ons (coredns, kube-proxy, vpc-cni, **EBS CSI**), IRSA/OIDC, creator-admin access entry |
| `iam-irsa` | IRSA role for the **AWS Load Balancer Controller** (+ optional external-dns) |

Dependencies are wired with Terragrunt `dependency` blocks, so apply order is
`vpc → eks → iam-irsa` automatically.

## Environments

| Env          | NAT       | Capacity  | Nodes (min/desired/max) | CIDR         |
|--------------|-----------|-----------|--------------------------|--------------|
| `staging`    | single    | SPOT      | 2 / 3 / 5                | 10.0.0.0/16  |
| `production` | per-AZ HA | ON_DEMAND | 4 / 5 / 8                | 10.1.0.0/16  |

## Prerequisites

- Terraform **>= 1.10**, Terragrunt **>= 0.55**
- AWS credentials for the target account with VPC/EKS/IAM/S3 permissions
- Set the real `account_id` in `environments/<env>/account.hcl`

## Usage

```bash
cd infrastructure/environments/staging

# Plan/apply everything in dependency order:
terragrunt run-all plan
terragrunt run-all apply

# …or one unit at a time:
cd vpc && terragrunt apply
cd ../eks && terragrunt apply
cd ../iam-irsa && terragrunt apply
```

Get a kubeconfig and check the Layer-0 gate:

```bash
aws eks update-kubeconfig --name trainticket-staging --region us-east-1
kubectl get nodes
```

Read the LB controller role ARN to annotate your ServiceAccount later:

```bash
cd infrastructure/environments/staging/iam-irsa
terragrunt output aws_load_balancer_controller_role_arn
```

## Cost & teardown

EKS + EC2 nodes + NAT are **not free**. Tear an environment down when idle:

```bash
cd infrastructure/environments/staging
terragrunt run-all destroy
```

Trim cost without destroying: lower node counts in the env's `eks/terragrunt.hcl`, or keep
`capacity_type = "SPOT"`.

## Adding an environment

Copy `environments/staging/` to a new folder, edit `account.hcl` (`account_id`,
`environment`) and the per-unit CIDRs/sizing. Remote-state bucket/key/lock derive
automatically from project + account_id, so nothing else changes.
