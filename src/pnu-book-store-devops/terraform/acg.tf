resource "ncloud_access_control_group" "acg" {
    name        = "cloudteam18"
    vpc_no      = ncloud_vpc.main.id
}

resource "ncloud_access_control_group_rule" "acg-rule" {
    access_control_group_no    = ncloud_access_control_group.acg.id

    inbound {
        protocol        = "TCP"
        ip_block        = "0.0.0.0/0"
        port_range      = "443"
    }

    inbound {
        protocol        = "TCP"
        ip_block        = "0.0.0.0/0"
        port_range      = "80"
    }

    outbound { 
        protocol        = "TCP"
        ip_block        = "0.0.0.0/0"
        port_range      = "1-65535"
    }
}

resource "ncloud_access_control_group_rule" "mysql-acg-rule" {
    depends_on                  = [ncloud_mysql.mysql]
    # for_each                    = toset(ncloud_mysql.mysql.access_control_group_no_list)
    access_control_group_no     = ncloud_mysql.mysql.access_control_group_no_list[0]

    inbound {
        protocol        = "TCP"
        ip_block        = "172.20.0.0/16"
        port_range      = "3306"
    }
}

resource "ncloud_access_control_group_rule" "redis-acg-rule" {
    depends_on                  = [ncloud_redis.redis]
    access_control_group_no     = ncloud_redis.redis.access_control_group_no_list[0]

    inbound {
        protocol        = "TCP"
        ip_block        = "172.20.0.0/16"
        port_range      = "6379"
    }
}