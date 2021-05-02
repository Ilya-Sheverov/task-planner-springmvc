#!/bin/bash

psql_server_config_filename="psql-server.config"
db_drop_script_filiname="psql-db-drop-script.sql"

if [ -f "$db_drop_script_filiname" ]; then

  if [ -f "$psql_server_config_filename" ]; then

    while IFS='=' read -r key value; do
      if [ -n "${key}" ]; then
        eval "${key}=\${value}"
      fi
    done <"$psql_server_config_filename"

    if [ -n "${pg_user}" ]; then

      if [ -n "${pg_host}" ]; then

        if [ -n "${pg_port}" ]; then

          echo "Connection to server with port: $pg_port host: $pg_host user: $pg_user"
          psql -U "$pg_user" -h "$pg_host" -p "$pg_port" -f "$db_drop_script_filiname" -e
        else
          echo "pg_port property is empty.Check your ${psql_server_config_filename} file."
          exit
        fi

      else
        echo "pg_host property is empty.Check your ${psql_server_config_filename} file."
        exit
      fi
    else
      echo "pg_user property is empty.Check your ${psql_server_config_filename} file."
      exit
    fi
  else
    echo "$psql_server_config_filename not found."
    exit
  fi

else
  echo "File $db_drop_script_filiname not found!"
fi
