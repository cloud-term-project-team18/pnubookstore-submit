provider "ncloud" {
    region      = "KR"
    site        = "public"
    support_vpc = "true"
}

terraform {
  required_providers {
    ncloud = {
        source = "NaverCloudPlatform/ncloud"
        version = "3.1.0"
    }
  }
  required_version = ">= 0.13"
}
