# Root Terragrunt config. Included by every unit via `include "root"`.
# All find_in_parent_folders()/path_relative_to_include() calls resolve relative
# to the *including* unit, so this file is never applied on its own.

locals {
  common = read_terragrunt_config(find_in_parent_folders("common.hcl"))
  env    = read_terragrunt_config(find_in_parent_folders("env.hcl"))

  project     = local.common.locals.project
  region      = local.env.locals.region
  environment = local.env.locals.environment

  tags = merge(local.common.locals.tags, {
    Environment = local.environment
  })
}

# Remote state in S3 with DynamoDB locking. Terragrunt creates the bucket and
# lock table on first run if they don't exist.
remote_state {
  backend = "s3"

  generate = {
    path      = "backend.tf"
    if_exists = "overwrite_terragrunt"
  }

  config = {
    bucket         = "${local.project}-tfstate-${local.environment}"
    key            = "${path_relative_to_include()}/terraform.tfstate"
    region         = local.region
    encrypt        = true
    dynamodb_table = "${local.project}-tflock-${local.environment}"
  }
}

# Generate the AWS provider for every unit so module code stays provider-agnostic.
generate "provider" {
  path      = "provider.tf"
  if_exists = "overwrite_terragrunt"
  contents  = <<EOF
provider "aws" {
  region = "${local.region}"
}
EOF
}

# Tags are merged into every unit's inputs (units override per-key as needed).
inputs = {
  tags = local.tags
}
