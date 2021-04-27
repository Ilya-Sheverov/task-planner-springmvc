#!/bin/bash

dbtestdatafilename="pgsql-fill-db-test-data-script.sql"

if [ -f "$dbtestdatafilename" ]; then
  read -p "Enter the user to connect to the server[postgres]:" pguser
  read -p "Enter the host to connect to the server[127.0.0.1]:" pghost
  read -p "Enter the port to connect to the server[5432]:" pgport

  if [ "$pguser" = "" ]; then
    pguser="postgres"
  fi

  if [ "$pghost" = "" ]; then
    pghost="127.0.0.1"
  fi

  if [ "$pgport" = "" ]; then
    pgport="5432"
  fi

  psql -U "$pguser" -h "$pghost" -p "$pgport" -c '\c task_planner' -f "$dbtestdatafilename" -e
else
  echo "File $dbtestdatafilename not found!"
fi
