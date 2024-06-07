
# Resource : Subnet(Public)
resource "ncloud_subnet" "public_sbn" {
    name            = "cloud-termp-sbn-public"
    vpc_no          = ncloud_vpc.main.id
    subnet          = cidrsubnet(ncloud_vpc.main.ipv4_cidr_block, 8, 0) // 172.20.0.0/24
    zone            = "KR-1"
    network_acl_no  = ncloud_vpc.main.default_network_acl_no
    subnet_type     = "PUBLIC" 
    #usage_type     = "GEN" -> default
}

# Resource : Subnet(private)
resource "ncloud_subnet" "private_sbn1" {
    name            = "cloud-termp-sbn-private-1"
    vpc_no          = ncloud_vpc.main.id
    subnet          = cidrsubnet(ncloud_vpc.main.ipv4_cidr_block, 8, 1) // 172.20.1.0/24
    zone            = "KR-1"
    network_acl_no  = ncloud_vpc.main.default_network_acl_no
    subnet_type     = "PRIVATE" 
}

# Resource : Subnet(private)
resource "ncloud_subnet" "private_sbn2" {
    name            = "cloud-termp-sbn-private-2"
    vpc_no          = ncloud_vpc.main.id
    subnet          = cidrsubnet(ncloud_vpc.main.ipv4_cidr_block, 8, 2) // 172.20.2.0/24
    zone            = "KR-1"
    network_acl_no  = ncloud_vpc.main.default_network_acl_no
    subnet_type     = "PRIVATE"
}

# Resource : Subnet(private)
resource "ncloud_subnet" "private_sbn3" {
    name            = "cloud-termp-sbn-private-3"
    vpc_no          = ncloud_vpc.main.id
    subnet          = cidrsubnet(ncloud_vpc.main.ipv4_cidr_block, 8, 3) // 172.20.3.0/24
    zone            = "KR-1"
    network_acl_no  = ncloud_vpc.main.default_network_acl_no
    subnet_type     = "PRIVATE" 
}

resource "ncloud_subnet" "public_lb_subnet" {
    name            = "cloud-termp-sbn-ld-public"
    vpc_no          = ncloud_vpc.main.id
    subnet          = cidrsubnet(ncloud_vpc.main.ipv4_cidr_block, 8, 4)
    zone            = "KR-1"
    network_acl_no  = ncloud_vpc.main.default_network_acl_no
    subnet_type     = "PUBLIC"
    usage_type      = "LOADB" 
}

resource "ncloud_subnet" "private_lb_subnet" {
    name            = "cloud-termp-sbn-ld-private"
    vpc_no          = ncloud_vpc.main.id
    subnet          = cidrsubnet(ncloud_vpc.main.ipv4_cidr_block, 8, 5)
    zone            = "KR-1"
    network_acl_no  = ncloud_vpc.main.default_network_acl_no
    subnet_type     = "PRIVATE"
    usage_type      = "LOADB" 
}

resource "ncloud_subnet" "public_nat_subnet" {
    name            = "cloud-termp-sbn-nat"
    vpc_no          = ncloud_vpc.main.id
    subnet          = cidrsubnet(ncloud_vpc.main.ipv4_cidr_block, 8, 6)
    zone            = "KR-1"
    network_acl_no  = ncloud_vpc.main.default_network_acl_no
    subnet_type     = "PUBLIC"
    usage_type      = "NATGW" 
}