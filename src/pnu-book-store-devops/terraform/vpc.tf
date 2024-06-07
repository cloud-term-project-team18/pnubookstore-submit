# Resource : VPC
resource "ncloud_vpc" "main" {
    name            = "cloud-termp-vpc"
    ipv4_cidr_block = "172.20.0.0/16" 
}


