resource "ncloud_mysql" "mysql" {
  subnet_no             = ncloud_subnet.private_sbn3.id
  service_name          = "cloud-mysql"
  server_name_prefix    = "cl-m"
  user_name             = data.external.env.result["db_user"]
  user_password         = data.external.env.result["db_password"]
  database_name         = data.external.env.result["db_name"]
  host_ip               = data.external.env.result["db_host"]
}

resource "ncloud_redis_config_group" "redis_config" {
    name                = "cloud-rcg"
    redis_version       = "7.0.13-simple"
}

output "redis_config_id" {
  value                 = ncloud_redis_config_group.redis_config.id 
}

resource "ncloud_redis" "redis" {
    service_name        = "cloud-redis"
    server_name_prefix  = "cl-r"
    image_product_code  = "SW.VDBAS.VRDS.LNX64.CNTOS.0708.REDIS.7013.B050"
    vpc_no              = ncloud_vpc.main.vpc_no
    subnet_no           = ncloud_subnet.private_sbn3.id
    config_group_no     = ncloud_redis_config_group.redis_config.id
    mode                = "SIMPLE"
}