SET CLIENT_ENCODING TO 'UTF8';

CREATE TABLE persons
(
    person_id   SERIAL,
    last_name   CHARACTER VARYING(35)               NOT NULL,
    first_name  CHARACTER VARYING(35)               NOT NULL,
    middle_name CHARACTER VARYING(35)               NOT NULL,
    version     TIMESTAMP DEFAULT current_timestamp NOT NULL,
    PRIMARY KEY (person_id)
);

CREATE INDEX person_version_idx ON persons (version);

ALTER TABLE persons
    OWNER to task_planner_admin;

CREATE TABLE statuses
(
    status_id CHARACTER VARYING(11) NOT NULL,
    PRIMARY KEY (status_id)
);

INSERT INTO statuses (status_id)
VALUES ('not_started'),
       ('started'),
       ('paused'),
       ('completed');

CREATE TABLE statuses_localized
(
    statuses_localized_id SERIAL,
    status_id             CHARACTER VARYING(11) NOT NULL,
    locale                CHARACTER VARYING(2)  NOT NULL,
    value                 CHARACTER VARYING(11) NOT NULL,
    UNIQUE (status_id, locale),
    PRIMARY KEY (statuses_localized_id),
    FOREIGN KEY (status_id) REFERENCES statuses (status_id) ON DELETE CASCADE
);

CREATE INDEX statuses_localized_status_id_idx ON statuses_localized (status_id);
CREATE INDEX statuses_localized_locale_idx ON statuses_localized (locale);

INSERT INTO statuses_localized (status_id, locale, value)
VALUES ('not_started', 'ru', 'Не начата'),
       ('not_started', 'en', 'Not started'),
       ('started', 'ru', 'Начата'),
       ('started', 'en', 'Started'),
       ('paused', 'ru', 'Отложена'),
       ('paused', 'en', 'Paused'),
       ('completed', 'ru', 'Завершена'),
       ('completed', 'en', 'Completed');

ALTER TABLE statuses_localized
    OWNER to task_planner_admin;

-- Создание таблицы "Задач"
CREATE TABLE tasks
(
    task_id     SERIAL                                          NOT NULL,
    description CHARACTER VARYING(120)                          NOT NULL,
    work_volume SMALLINT,
    start_date  TIMESTAMP(0),
    due_date    TIMESTAMP(0),
    -- Нельзя окончить задачу раньше ее начала и закончить задачу не начав.
    CONSTRAINT timestamp_check CHECK (
            ((due_date >= start_date) AND (start_date IS NOT NULL) AND
             (due_date IS NOT NULL))
            OR ((start_date IS NULL) AND (due_date IS NULL))
            OR ((start_date IS NOT NULL) AND (due_date IS NULL))),
    status_id   CHARACTER VARYING(11) DEFAULT 'not_started'     NOT NULL,
    version     TIMESTAMP             DEFAULT current_timestamp NOT NULL,
    PRIMARY KEY (task_id),
    FOREIGN KEY (status_id) REFERENCES statuses (status_id) ON DELETE RESTRICT
);

CREATE INDEX task_version_idx ON tasks (version);
CREATE INDEX tasks_status_id_idx ON tasks (status_id);

ALTER TABLE tasks
    OWNER to task_planner_admin;

CREATE TABLE assignee_groups
(
    assignee_group_id SERIAL,
    task_id           INTEGER NOT NULL,
    person_id         INTEGER NOT NULL,
    PRIMARY KEY (assignee_group_id),
    FOREIGN KEY (task_id) REFERENCES tasks (task_id) ON DELETE CASCADE,
    FOREIGN KEY (person_id) REFERENCES persons (person_id) ON DELETE RESTRICT,
    unique (task_id, person_id)
);

CREATE INDEX assignee_groups_task_id_idx ON assignee_groups (task_id);
CREATE INDEX assignee_groups_person_id_idx ON assignee_groups (person_id);


ALTER TABLE assignee_groups
    OWNER to task_planner_admin;