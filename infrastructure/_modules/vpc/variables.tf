variable "name" {
  description = "Name of the VPC."
  type        = string
}

variable "cluster_name" {
  description = "Name of the EKS cluster this VPC serves. Used to tag subnets for EKS/load-balancer discovery."
  type        = string
}

variable "cidr" {
  description = "IPv4 CIDR block for the VPC."
  type        = string
}

variable "azs" {
  description = "Availability zones to spread subnets across."
  type        = list(string)
}

variable "private_subnets" {
  description = "CIDR blocks for the private subnets (one per AZ). Worker nodes and pods live here."
  type        = list(string)
}

variable "public_subnets" {
  description = "CIDR blocks for the public subnets (one per AZ). Internet-facing load balancers live here."
  type        = list(string)
}

variable "single_nat_gateway" {
  description = "Use a single shared NAT gateway (cheaper) instead of one per AZ (HA)."
  type        = bool
  default     = true
}

variable "tags" {
  description = "Tags applied to all resources."
  type        = map(string)
  default     = {}
}
