# IRSA roles for cluster controllers the platform layer (Kustomize/Helm) installs.
# Each role is scoped to a specific namespace:serviceaccount via the cluster OIDC
# provider, so the controller assumes it through a projected service-account token
# rather than node-wide credentials.

locals {
  lb_sa  = split(":", var.lb_controller_service_account)
  dns_sa = split(":", var.external_dns_service_account)
}

# AWS Load Balancer Controller — needed for ALB/NLB ingress in the ingress layer.
module "lb_controller_irsa" {
  source  = "terraform-aws-modules/iam/aws//modules/iam-role-for-service-accounts-eks"
  version = "~> 5.39"

  role_name                              = "${var.cluster_name}-aws-lb-controller"
  attach_load_balancer_controller_policy = true

  oidc_providers = {
    main = {
      provider_arn               = var.oidc_provider_arn
      namespace_service_accounts = ["${local.lb_sa[0]}:${local.lb_sa[1]}"]
    }
  }

  tags = var.tags
}

# external-dns — optional, only useful once a Route53 hosted zone exists.
module "external_dns_irsa" {
  count = var.enable_external_dns ? 1 : 0

  source  = "terraform-aws-modules/iam/aws//modules/iam-role-for-service-accounts-eks"
  version = "~> 5.39"

  role_name                  = "${var.cluster_name}-external-dns"
  attach_external_dns_policy = true

  oidc_providers = {
    main = {
      provider_arn               = var.oidc_provider_arn
      namespace_service_accounts = ["${local.dns_sa[0]}:${local.dns_sa[1]}"]
    }
  }

  tags = var.tags
}
