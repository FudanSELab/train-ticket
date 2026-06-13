include "root" {
  path = find_in_parent_folders()
}

locals {
  env = read_terragrunt_config(find_in_parent_folders("env.hcl"))
}

terraform {
  source = "${get_repo_root()}/infrastructure/terraform/modules/vpc"
}

inputs = {
  name         = "${local.env.locals.cluster_name}-vpc"
  cluster_name = local.env.locals.cluster_name

  cidr            = local.env.locals.vpc_cidr
  azs             = local.env.locals.azs
  private_subnets = local.env.locals.private_subnets
  public_subnets  = local.env.locals.public_subnets

  single_nat_gateway = true
}
