output "cluster_name" {
  description = "Name of the EKS cluster."
  value       = module.eks.cluster_name
}

output "cluster_endpoint" {
  description = "Endpoint of the EKS API server."
  value       = module.eks.cluster_endpoint
}

output "cluster_certificate_authority_data" {
  description = "Base64-encoded CA cert for the cluster (for kubeconfig)."
  value       = module.eks.cluster_certificate_authority_data
}

output "cluster_security_group_id" {
  description = "Security group ID attached to the EKS control plane."
  value       = module.eks.cluster_security_group_id
}

output "node_security_group_id" {
  description = "Security group ID attached to the managed node groups."
  value       = module.eks.node_security_group_id
}

output "oidc_provider_arn" {
  description = "ARN of the IAM OIDC provider — consumed by IRSA roles."
  value       = module.eks.oidc_provider_arn
}

output "oidc_provider" {
  description = "OIDC issuer URL (without https://) for the cluster."
  value       = module.eks.oidc_provider
}

output "region" {
  description = "AWS region of the cluster (from the default provider)."
  value       = data.aws_region.current.name
}

data "aws_region" "current" {}
