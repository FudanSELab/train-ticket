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

  # Distinct /16 from staging so the environments can peer/coexist if needed.
  cidr            = "10.1.0.0/16"
  azs             = ["us-east-1a", "us-east-1b", "us-east-1c"]
  private_subnets = ["10.1.0.0/19", "10.1.32.0/19", "10.1.64.0/19"]
  public_subnets  = ["10.1.96.0/19", "10.1.128.0/19", "10.1.160.0/19"]

  # One NAT per AZ for HA in production.
  single_nat_gateway = false
}
