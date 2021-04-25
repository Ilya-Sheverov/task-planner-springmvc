#!/bin/bash
dbcreatefilename="pgsql-db-creation-script.sql"
tbcreatefilename="pgsql-tables-creation-script.sql"

if [ -f "$dbcreatefilename" ]; then
  if [ -f "$tbcreatefilename" ]; then
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

    echo "Connection to server with $pguser + $pghost + $pgport"

    psql -U "$pguser" -h "$pghost" -p "$pgport" -f "$dbcreatefilename" -c '\c task_planner' -f "$tbcreatefilename" -e
  else
    echo "File $tbcreatefilename not found!"
  fi
else
  echo "File $dbcreatefilename not found!"
fi
