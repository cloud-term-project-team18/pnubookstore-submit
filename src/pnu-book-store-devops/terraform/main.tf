data "ncloud_nks_versions" "version" {
  filter {
    name = "value"
    values = [var.nks_version]
    regex = true
  }
}

resource "ncloud_nks_cluster" "cluster" {
    hypervisor_code         = "XEN"
    cluster_type            = "SVR.VNKS.STAND.C002.M008.NET.SSD.B050.G002"
    k8s_version             = data.ncloud_nks_versions.version.versions.0.value
    login_key_name          = var.login_key
    name                    = "cloud-termp-cluster"
    lb_private_subnet_no    = ncloud_subnet.private_lb_subnet.id
    lb_public_subnet_no     = ncloud_subnet.public_lb_subnet.id
    subnet_no_list          = [ncloud_subnet.private_sbn1.id, ncloud_subnet.private_sbn2.id, ncloud_subnet.private_sbn3.id]
    kube_network_plugin     = "cilium"
    vpc_no                  = ncloud_vpc.main.id
    zone                    = "KR-1"
    log {
        audit = true
    }
}