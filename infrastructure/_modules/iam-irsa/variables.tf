variable "cluster_name" {
  description = "Name of the EKS cluster these roles serve (used for role naming)."
  type        = string
}

variable "oidc_provider_arn" {
  description = "ARN of the cluster's IAM OIDC provider (from the eks module)."
  type        = string
}

variable "lb_controller_service_account" {
  description = "namespace:serviceaccount the AWS Load Balancer Controller runs as."
  type        = string
  default     = "kube-system:aws-load-balancer-controller"
}

variable "enable_external_dns" {
  description = "Create an IRSA role for external-dns (needs a Route53 hosted zone)."
  type        = bool
  default     = false
}

variable "external_dns_service_account" {
  description = "namespace:serviceaccount external-dns runs as."
  type        = string
  default     = "kube-system:external-dns"
}

variable "tags" {
  description = "Tags applied to all resources."
  type        = map(string)
  default     = {}
}
