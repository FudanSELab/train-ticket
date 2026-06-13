# TrainTicket Infrastructure — Terraform + Terragrunt (Layer 0)

Cloud foundation for the TrainTicket platform: **VPC → EKS → IRSA roles**, provisioned
with Terraform modules driven by a DRY Terragrunt `live/` tree.

> **Division of labor:** this `terraform/` + `../live/` tree (the cloud foundation) is
> maintained here. Everything *on* the cluster — Kustomize bases/overlays, Argo CD, the
> service mesh, observability, alerting — is built by hand under `infrastructure/apps/`
> and `infrastructure/platform/` (see `../ROADMAP.md`). Terraform stops at a ready,
> empty, IRSA-enabled EKS cluster.

## Layout

```
infrastructure/
├── terraform/modules/          # thin wrappers over community modules
│   ├── vpc/                     # terraform-aws-modules/vpc/aws  (~> 5.8)
│   ├── eks/                     # terraform-aws-modules/eks/aws  (~> 20.24)
│   └── iam-irsa/                # IRSA roles for cluster controllers
└── live/                        # Terragrunt — state, provider, per-env wiring
    ├── terragrunt.hcl           # root: S3+DynamoDB remote state, provider gen, tags
    ├── common.hcl               # project-wide constants
    └── dev/
        ├── env.hcl              # region, CIDRs, cluster version, node sizing
        ├── vpc/terragrunt.hcl
        ├── eks/terragrunt.hcl       # depends on vpc
        └── iam-irsa/terragrunt.hcl  # depends on eks (OIDC provider)
```

Dependencies are wired with Terragrunt `dependency` blocks, so apply order is
`vpc → eks → iam-irsa` automatically.

## What gets created

| Module    | Resources |
|-----------|-----------|
| `vpc`     | 3-AZ VPC, public + private subnets, NAT gateway, EKS/ELB subnet tags |
| `eks`     | EKS control plane (v1.30), managed node group (4× m5.2xlarge by default), core add-ons (coredns, kube-proxy, vpc-cni, **EBS CSI**), IRSA/OIDC enabled, creator admin access entry |
| `iam-irsa`| IRSA role for the **AWS Load Balancer Controller** (and optional external-dns) — consumed later by your ingress layer |

## Prerequisites

- Terraform `>= 1.5`, Terragrunt `>= 0.55`
- AWS credentials with permission to create VPC/EKS/IAM/S3/DynamoDB
- An S3 bucket + DynamoDB table are **auto-created** by Terragrunt on first run
  (`trainticket-tfstate-dev` / `trainticket-tflock-dev`)

## Usage

```bash
cd infrastructure/live/dev

# Provision everything in dependency order:
terragrunt run-all plan
terragrunt run-all apply

# Or one unit at a time:
cd vpc && terragrunt apply
cd ../eks && terragrunt apply
cd ../iam-irsa && terragrunt apply
```

Get a kubeconfig once the cluster is up:

```bash
aws eks update-kubeconfig --name trainticket-dev --region us-east-1
kubectl get nodes        # Layer 0 acceptance gate
```

Read an output (e.g. the LB controller role ARN to annotate your ServiceAccount):

```bash
cd infrastructure/live/dev/iam-irsa
terragrunt output aws_load_balancer_controller_role_arn
```

## Cost & teardown

The `dev` env (4× m5.2xlarge + EKS control plane + NAT) is **not free** — on the order of
tens of USD/day. Tear it down when idle:

```bash
cd infrastructure/live/dev
terragrunt run-all destroy
```

To trim cost without destroying: lower `node_desired_size`/`node_min_size` in `env.hcl`,
or set `node_capacity_type = "SPOT"`.

## Adding an environment

Copy `live/dev/` to `live/staging/`, edit `env.hcl` (region, CIDRs, `environment`,
sizing). Remote-state bucket/key/lock are derived automatically, so no other changes
are needed.
