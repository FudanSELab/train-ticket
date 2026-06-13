include "root" {
  path = find_in_parent_folders()
}

locals {
  env = read_terragrunt_config(find_in_parent_folders("env.hcl"))
}

terraform {
  source = "${get_repo_root()}/infrastructure/terraform/modules/eks"
}

# Pull VPC network details from the vpc unit's state.
dependency "vpc" {
  config_path = "../vpc"

  mock_outputs = {
    vpc_id          = "vpc-00000000000000000"
    private_subnets = ["subnet-00000000000000000", "subnet-11111111111111111", "subnet-22222222222222222"]
  }
  mock_outputs_allowed_terraform_commands = ["validate", "plan"]
}

inputs = {
  cluster_name    = local.env.locals.cluster_name
  cluster_version = local.env.locals.cluster_version

  vpc_id     = dependency.vpc.outputs.vpc_id
  subnet_ids = dependency.vpc.outputs.private_subnets

  node_instance_types = local.env.locals.node_instance_types
  node_capacity_type  = local.env.locals.node_capacity_type
  node_min_size       = local.env.locals.node_min_size
  node_desired_size   = local.env.locals.node_desired_size
  node_max_size       = local.env.locals.node_max_size
}
