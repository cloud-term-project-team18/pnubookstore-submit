#!/bin/bash

set -o allexport
source ".env"
set +o allexport

jq -n \
    --arg db_host "$DB_HOST" \
    --arg db_user "$DB_USER" \
    --arg db_password "$DB_PASSWORD" \
    --arg db_name "$DB_NAME" \
    --arg redis_user "$REDIS_USER" \
    '{"db_host":$db_host,"db_name":$db_name,"db_user":$db_user,"db_password":$db_password,"redis_user":$redis_user}'
