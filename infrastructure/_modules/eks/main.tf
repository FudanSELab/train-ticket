# Thin wrapper over the upstream community EKS module (v20).
# v20 manages cluster access via EKS Access Entries (no aws-auth ConfigMap, so no
# kubernetes provider is required here). IRSA is enabled so add-ons the platform
# layer installs later (e.g. AWS Load Balancer Controller) can assume scoped roles.
module "eks" {
  source  = "terraform-aws-modules/eks/aws"
  version = "~> 20.24"

  cluster_name    = var.cluster_name
  cluster_version = var.cluster_version

  cluster_endpoint_public_access = var.endpoint_public_access

  vpc_id     = var.vpc_id
  subnet_ids = var.subnet_ids

  enable_irsa = true

  # Core add-ons kept current. The EBS CSI driver backs the per-service MySQL PVCs.
  cluster_addons = {
    coredns            = { most_recent = true }
    kube-proxy         = { most_recent = true }
    vpc-cni            = { most_recent = true }
    aws-ebs-csi-driver = { most_recent = true }
  }

  eks_managed_node_group_defaults = {
    ami_type = "AL2_x86_64"

    # Grant nodes the EBS CSI permissions so the driver works without a separate
    # IRSA role. (Other controllers still use IRSA via the iam-irsa module.)
    iam_role_additional_policies = {
      AmazonEBSCSIDriverPolicy = "arn:aws:iam::aws:policy/service-role/AmazonEBSCSIDriverPolicy"
    }
  }

  eks_managed_node_groups = {
    default = {
      instance_types = var.node_instance_types
      capacity_type  = var.node_capacity_type

      min_size     = var.node_min_size
      max_size     = var.node_max_size
      desired_size = var.node_desired_size
    }
  }

  # Give the Terraform/Terragrunt principal cluster-admin via an access entry so
  # `kubectl`/Argo CD bootstrap works immediately after apply.
  enable_cluster_creator_admin_permissions = true

  tags = var.tags
}
