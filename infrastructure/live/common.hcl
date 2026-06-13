# Project-wide constants shared across all environments.
locals {
  project = "trainticket"

  tags = {
    Project   = "trainticket-rca"
    ManagedBy = "terragrunt"
    Repo      = "HDAli00/train-ticket"
  }
}
