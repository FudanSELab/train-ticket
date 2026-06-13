include "root" {
  path = find_in_parent_folders("root.hcl")
}

locals {
  common  = read_terragrunt_config(find_in_parent_folders("_common.hcl"))
  account = read_terragrunt_config(find_in_parent_folders("account.hcl"))
  name    = "${local.common.locals.project}-${local.account.locals.environment}"
}

terraform {
  source = "${get_repo_root()}/infrastructure/_modules/iam-irsa"
}

dependency "eks" {
  config_path = "../eks"

  mock_outputs = {
    oidc_provider_arn = "arn:aws:iam::000000000000:oidc-provider/oidc.eks.us-east-1.amazonaws.com/id/MOCK"
  }
  mock_outputs_allowed_terraform_commands = ["validate", "plan"]
}

inputs = {
  cluster_name      = local.name
  oidc_provider_arn = dependency.eks.outputs.oidc_provider_arn

  enable_external_dns = false
}
