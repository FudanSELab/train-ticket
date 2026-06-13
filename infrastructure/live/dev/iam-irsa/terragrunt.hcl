include "root" {
  path = find_in_parent_folders()
}

locals {
  env = read_terragrunt_config(find_in_parent_folders("env.hcl"))
}

terraform {
  source = "${get_repo_root()}/infrastructure/terraform/modules/iam-irsa"
}

# Needs the cluster OIDC provider from the eks unit.
dependency "eks" {
  config_path = "../eks"

  mock_outputs = {
    oidc_provider_arn = "arn:aws:iam::000000000000:oidc-provider/oidc.eks.us-east-1.amazonaws.com/id/MOCK"
  }
  mock_outputs_allowed_terraform_commands = ["validate", "plan"]
}

inputs = {
  cluster_name      = local.env.locals.cluster_name
  oidc_provider_arn = dependency.eks.outputs.oidc_provider_arn

  # Flip on once a Route53 hosted zone exists.
  enable_external_dns = false
}
