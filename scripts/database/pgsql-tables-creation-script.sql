SET CLIENT_ENCODING TO 'UTF8';

CREATE TABLE persons
(
    "id"   serial,
    "last_name"   CHARACTER VARYING(35) NOT NULL,
    "first_name"  CHARACTER VARYING(35) NOT NULL,
    "middle_name" CHARACTER VARYING(35) NOT NULL,
    "version"     TIMESTAMP DEFAULT current_timestamp NOT NULL,
    PRIMARY KEY ("id")
);

CREATE INDEX person_version_idx ON persons ("version");

ALTER TABLE persons
    OWNER to task_planner_admin;

CREATE TABLE tasks
(
    "id"                      SERIAL NOT NULL,
    "name"                    CHARACTER VARYING(120) NOT NULL,
    "volume_of_work_in_hours" SMALLINT,
    "start_date"              TIMESTAMP(0),
    "due_date"                TIMESTAMP(0),
    -- Нельзя окончить задачу раньше ее начала и закончить задачу не начав.
    CONSTRAINT timestamp_check CHECK (
            (("due_date" >= "start_date") AND ("start_date" IS NOT NULL) AND ("due_date" IS NOT NULL))
            OR (("start_date" IS NULL) AND ("due_date" IS NULL))
            OR ((start_date IS NOT NULL) AND ("due_date" IS NULL))),
    "status"                  CHARACTER VARYING(14)  NOT NULL,
    "person_id"               INTEGER,
    "version"                 TIMESTAMP DEFAULT current_timestamp NOT NULL,
    PRIMARY KEY ("id"),
    FOREIGN KEY ("person_id") REFERENCES persons ("id") ON DELETE RESTRICT
);

CREATE INDEX person_id_idx ON tasks ("person_id");
CREATE INDEX task_version_idx ON tasks ("version");

ALTER TABLE tasks
    OWNER to task_planner_admin;