#!/bin/bash

psql_server_config_filename="psql-server.config"
db_creation_script_filename="pgsql-db-creation-script.sql"
tb_db_creation_script_filename="pgsql-tables-creation-script.sql"

if [ -f "$db_creation_script_filename" ]; then
  if [ -f "$tb_db_creation_script_filename" ]; then
    if [ -f "$psql_server_config_filename" ]; then

      while IFS='=' read -r key value; do
        if [ -n "${key}" ]; then
          eval "${key}=\${value}"
        fi
      done <"$psql_server_config_filename"

      echo "Connection to server with port: $pg_port host: $pg_host user: $pg_user"
      psql -U "$pg_user" -h "$pg_host" -p "$pg_port" -f "$db_creation_script_filename" -c '\c task_planner' -f "$tb_db_creation_script_filename" -e

    else
      echo "$psql_server_config_filename not found."
    fi
  else
    echo "File $tb_db_creation_script_filename not found!"
  fi
else
  echo "File $db_creation_script_filename not found!"
fi
