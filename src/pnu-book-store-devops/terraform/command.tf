resource "null_resource" "kubeconfig" {
  depends_on = [ncloud_nks_cluster.cluster]

  provisioner "local-exec" {
    command = "ncp-iam-authenticator create-kubeconfig --region KR --clusterUuid ${ncloud_nks_cluster.cluster.uuid} --output ${path.module}/k8s/kubeconfig.yaml"
  }

  triggers = {
    always_run = "${timestamp()}"
  }
}


resource "null_resource" "export_db_host" {
  depends_on  = [ncloud_mysql.mysql]

  provisioner "local-exec" {
    command   = "export DB_HOST=jdbc:mysql://${ncloud_mysql.mysql.mysql_server_list[0].private_domain}/cloud?allowPublicKeyRetrieval=true&useSSL=false&rewriteBatchedStatements=true&serverTimezone=Asia/Seoul&characterEncoding=UTF-8"
  }
}

resource "null_resource" "export_redis_host" {
  depends_on  = [ncloud_redis.redis]
  
  provisioner "local-exec" {
    command   = "export REDIS_HOST=${ncloud_redis.redis.redis_server_list[0].private_domain}"
  }
}