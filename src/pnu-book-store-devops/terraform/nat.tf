resource "ncloud_nat_gateway" "nat_gateway" {
  vpc_no            = ncloud_vpc.main.id
  subnet_no         = ncloud_subnet.public_nat_subnet.id
  zone              = "KR-1"
  name              = "cloud-termp-nat-gw"
  description       = "nat-gw for k8s"
}