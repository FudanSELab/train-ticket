include "root" {
  path = find_in_parent_folders("root.hcl")
}

locals {
  common  = read_terragrunt_config(find_in_parent_folders("_common.hcl"))
  account = read_terragrunt_config(find_in_parent_folders("account.hcl"))
  name    = "${local.common.locals.project}-${local.account.locals.environment}"
}

terraform {
  source = "${get_repo_root()}/infrastructure/_modules/vpc"
}

inputs = {
  name         = "${local.name}-vpc"
  cluster_name = local.name

  # /16 split into six /19s: three private (nodes/pods) + three public (LBs).
  cidr            = "10.0.0.0/16"
  azs             = ["us-east-1a", "us-east-1b", "us-east-1c"]
  private_subnets = ["10.0.0.0/19", "10.0.32.0/19", "10.0.64.0/19"]
  public_subnets  = ["10.0.96.0/19", "10.0.128.0/19", "10.0.160.0/19"]

  single_nat_gateway = true
}
