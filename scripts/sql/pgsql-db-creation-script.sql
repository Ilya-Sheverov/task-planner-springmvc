SET CLIENT_ENCODING TO 'UTF8';

CREATE ROLE task_planner_admin WITH
    LOGIN
    SUPERUSER
    CREATEDB
    CREATEROLE
    INHERIT
    NOREPLICATION
    CONNECTION LIMIT -1
    PASSWORD 'G@h@kkm123';

CREATE  DATABASE  task_planner
    WITH
    OWNER = task_planner_admin
    ENCODING = 'UTF8'
    CONNECTION LIMIT = -1;