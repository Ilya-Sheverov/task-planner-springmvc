#!/bin/bash

psql_server_config_filename="psql-server.config"
fill_db_test_data_filename="pgsql-fill-db-test-data-script.sql"

if [ -f "$fill_db_test_data_filename" ]; then

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
          psql -U "$pg_user" -h "$pg_host" -p "$pg_port" -c '\c task_planner' -f "$fill_db_test_data_filename" -e

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
  echo "File $fill_db_test_data_filename not found!"
fi
