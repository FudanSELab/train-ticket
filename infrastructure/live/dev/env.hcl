# Per-environment settings for `dev`. Copy this folder to add another environment
# (e.g. staging/prod) and adjust sizing/CIDRs.
locals {
  environment = "dev"
  region      = "us-east-1"

  cluster_name    = "trainticket-dev"
  cluster_version = "1.30"

  # /16 split into six /19s: three private (nodes/pods) + three public (LBs).
  vpc_cidr        = "10.0.0.0/16"
  azs             = ["us-east-1a", "us-east-1b", "us-east-1c"]
  private_subnets = ["10.0.0.0/19", "10.0.32.0/19", "10.0.64.0/19"]
  public_subnets  = ["10.0.96.0/19", "10.0.128.0/19", "10.0.160.0/19"]

  # Sized for the full TrainTicket workload (~71 pods incl. per-service MySQL).
  # 4x m5.2xlarge = 32 vCPU / 128 GiB at desired capacity. Scale down or use SPOT
  # to cut cost; tear the env down when idle.
  node_instance_types = ["m5.2xlarge"]
  node_capacity_type  = "ON_DEMAND"
  node_min_size       = 3
  node_desired_size   = 4
  node_max_size       = 6
}
