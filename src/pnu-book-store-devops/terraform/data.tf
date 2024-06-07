data external env {
  program = ["bash", "${path.module}/read_env.sh"]
}

output db_host {
    value = data.external.env.result["db_host"]
}

output db_user {
    value = data.external.env.result["db_user"]
}

output db_password {
    value = data.external.env.result["db_password"]
}

output db_name {
    value = data.external.env.result["db_name"]
}