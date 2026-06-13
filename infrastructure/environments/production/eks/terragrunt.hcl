include "root" {
  path = find_in_parent_folders("root.hcl")
}

locals {
  common  = read_terragrunt_config(find_in_parent_folders("_common.hcl"))
  account = read_terragrunt_config(find_in_parent_folders("account.hcl"))
  name    = "${local.common.locals.project}-${local.account.locals.environment}"
}

terraform {
  source = "${get_repo_root()}/infrastructure/_modules/eks"
}

dependency "vpc" {
  config_path = "../vpc"

  mock_outputs = {
    vpc_id          = "vpc-00000000000000000"
    private_subnets = ["subnet-00000000000000000", "subnet-11111111111111111", "subnet-22222222222222222"]
  }
  mock_outputs_allowed_terraform_commands = ["validate", "plan"]
}

inputs = {
  cluster_name    = local.name
  cluster_version = "1.30"

  vpc_id     = dependency.vpc.outputs.vpc_id
  subnet_ids = dependency.vpc.outputs.private_subnets

  # Production: on-demand capacity with headroom for the full workload + bursts.
  node_instance_types = ["m5.2xlarge"]
  node_capacity_type  = "ON_DEMAND"
  node_min_size       = 4
  node_desired_size   = 5
  node_max_size       = 8
}
