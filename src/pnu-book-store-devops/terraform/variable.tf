variable login_key {
  type        = string
  default     = "cloud-termp-key"
}

variable nks_version {
    type        = string
    default     = "1.27"
}

variable db_env_path {
  description = "database_env_file"
  type        = string
  default     = "db.env"
}