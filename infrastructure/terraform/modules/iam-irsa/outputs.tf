output "aws_load_balancer_controller_role_arn" {
  description = "IRSA role ARN to annotate on the aws-load-balancer-controller ServiceAccount."
  value       = module.lb_controller_irsa.iam_role_arn
}

output "external_dns_role_arn" {
  description = "IRSA role ARN for external-dns (null when disabled)."
  value       = var.enable_external_dns ? module.external_dns_irsa[0].iam_role_arn : null
}
