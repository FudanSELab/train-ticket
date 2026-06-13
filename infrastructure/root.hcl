# Root Terragrunt config — included by every unit via:
#   include "root" { path = find_in_parent_folders("root.hcl") }
# All find_in_parent_folders()/path_relative_to_include() calls resolve relative
# to the *including* unit, so this file is never applied on its own.

locals {
  common  = read_terragrunt_config(find_in_parent_folders("_common.hcl"))
  account = read_terragrunt_config(find_in_parent_folders("account.hcl"))

  project     = local.common.locals.project
  region      = local.common.locals.aws_region
  account_id  = local.account.locals.account_id
  environment = local.account.locals.environment
}

# Remote state in S3 with native S3 locking (use_lockfile) — no DynamoDB table.
# Requires Terraform >= 1.10 and AWS provider >= 5.x. Terragrunt creates the
# bucket on first run if it doesn't exist.
remote_state {
  backend = "s3"

  generate = {
    path      = "backend.tf"
    if_exists = "overwrite_terragrunt"
  }

  config = {
    bucket       = "${local.project}-tfstate-${local.account_id}"
    key          = "${path_relative_to_include()}/terraform.tfstate"
    region       = local.region
    encrypt      = true
    use_lockfile = true
  }
}

# Generate the AWS provider (with default tags) into every unit.
generate "provider" {
  path      = "provider.tf"
  if_exists = "overwrite_terragrunt"
  contents  = <<EOF
provider "aws" {
  region = "${local.region}"

  default_tags {
    tags = {
      Project     = "${local.project}"
      Environment = "${local.environment}"
      ManagedBy   = "terragrunt"
      Repository  = "HDAli00/train-ticket"
    }
  }
}
EOF
}
