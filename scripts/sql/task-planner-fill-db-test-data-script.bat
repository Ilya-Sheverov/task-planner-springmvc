ECHO OFF
chcp 65001
SET PGCLIENTENCODING=utf-8

SET /P PGUSER="Enter the user to connect to the database[postgres]:"
IF "%PGUSER%"=="" (
SET PGUSER=postgres
)
SET /P PGHOST="Enter the host to connect to the database[127.0.0.1]:"
IF "%PGHOST%"=="" (
SET PGHOST=127.0.0.1
)
SET /P PGPORT="Enter the port to connect to the database[5432]:"
IF "%PGPORT%"=="" (
SET PGPORT=5432
)
psql -U %PGUSER% -h %PGHOST% -p %PGPORT% -d task_planner -f "%CD%/pgsql-fill-db-test-data-script.sql" -e
pause