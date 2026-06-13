output "vpc_id" {
  description = "ID of the VPC."
  value       = module.vpc.vpc_id
}

output "vpc_cidr_block" {
  description = "CIDR block of the VPC."
  value       = module.vpc.vpc_cidr_block
}

output "private_subnets" {
  description = "List of private subnet IDs (where EKS nodes/pods run)."
  value       = module.vpc.private_subnets
}

output "public_subnets" {
  description = "List of public subnet IDs (where internet-facing load balancers run)."
  value       = module.vpc.public_subnets
}

output "nat_public_ips" {
  description = "Public IPs of the NAT gateway(s) — useful for egress allow-listing."
  value       = module.vpc.nat_public_ips
}
