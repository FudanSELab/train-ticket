variable "cluster_name" {
  description = "Name of the EKS cluster."
  type        = string
}

variable "cluster_version" {
  description = "Kubernetes control-plane version."
  type        = string
  default     = "1.30"
}

variable "vpc_id" {
  description = "VPC the cluster is deployed into."
  type        = string
}

variable "subnet_ids" {
  description = "Subnet IDs for the cluster and node groups (use private subnets)."
  type        = list(string)
}

variable "endpoint_public_access" {
  description = "Whether the EKS API endpoint is reachable from the public internet."
  type        = bool
  default     = true
}

variable "node_instance_types" {
  description = "Instance types for the default managed node group. Sized for the full TrainTicket workload (~71 pods incl. per-service databases)."
  type        = list(string)
  default     = ["m5.2xlarge"]
}

variable "node_min_size" {
  description = "Minimum number of worker nodes."
  type        = number
  default     = 3
}

variable "node_desired_size" {
  description = "Desired number of worker nodes."
  type        = number
  default     = 4
}

variable "node_max_size" {
  description = "Maximum number of worker nodes."
  type        = number
  default     = 6
}

variable "node_capacity_type" {
  description = "ON_DEMAND or SPOT for the default node group."
  type        = string
  default     = "ON_DEMAND"
}

variable "tags" {
  description = "Tags applied to all resources."
  type        = map(string)
  default     = {}
}
