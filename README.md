# Task-planner

Тестовое задание, которое заключается в написание простейшего Web-приложения, с базовой функциональностью, необходимой для для регистрации выполняемых на проекте задач.

## Table of contents

* [description](README.md#description)
* [technologies](README.md#technologies)
* [quick build](README.md#Quick-Build)
* [illustrations](README.md#illustrations)



### Description
[to the table of contents](README.md#table-of-contents)

Приложение является тестовым заданием, для применения навыков полученных в ходе обучения.

Задание заключается в том, что бы разработать приложение для регистрации выполняемы на проекте задач. Должна быть предусмотрена возможность добавления, редактирования, удаления *персон*  и *задач*, которые они выполняют. Основные функциональные требования можно прочитать [здесь](FUNCTIONAL_REQUIREMENTS.md).

### Technologies
[to the table of contents](README.md#table-of-contents)

В проекте используется:

1. Java 8;
2. Spring MVC, JSP, Jstl;
3. PostgresSQL;
4. JUnit 5;
5. Maven
6. Git
7. Bash

## Quick Build

[to the table of contents](README.md#table-of-contents)

##### Для того, что бы развернуть у себя проект у вас должно быть установлено:

1. Java 8 или выше;
2. PostgresSQL 9 или выше, работать интерактивный терминал *[psql](https://postgrespro.ru/docs/postgresql/9.6/app-psql)*.
3. Tomcat 9.0 или выше;
4. Maven 3 или выше;
5. Git.
6. Для пользователей Windows 10 для запуска bash-скриптов требуется [wsl](https://docs.microsoft.com/en-us/windows/wsl/install-win10).

##### Инструкция по установке:

1. Зайдите в консоль.

2. Скачайте репозиторий при помощи команды :

   `$ git clone -b onePeronPerTask https://github.com/Ilya-Sheverov/task-planner-springmvc.git`

   После чего у вас появится папка *task-planner-springmvc*.

1. После этого, Вам необходимо создать базу данных.

   1. Для этого, перейдите в директорию  *[task-planner-springmvc/scripts/database](scripts/database)*

   2. Там находится файл *[psql-server.config](scripts/database/psql-server.config)*. В нем необходимо изменить  значения свойств *pg_user*, *pg_host*, *pg_port* на те, что у вас. Эти данные будут использоваться в ходе работы скриптов.

   3. Теперь, что бы создать базу данных, запустите скрипт [task-planner-db-create.sh](scripts/database/task-planner-db-create.sh) при помощи команды:

      Для Linux:

      ​	`$ sh task-planner-db-create.sh`

      ​	Для Windows в PowerShell

      ​	`bash ./task-planner-db-create.sh`

   4. **Необязательный.** Если хотите заполнить таблицы тестовыми данными, это можно сделать используя [task-planner-fill-db-test-data-script.sh](scripts/database/task-planner-fill-db-test-data-script.sh). Для очистки таблиц от данных используйте [task-planner-tables-clear-script.sh](scripts/database/task-planner-tables-clear-script.sh). Для удаления базы данных используйте скрипт [task-planner-db-drop.sh](scripts/database/task-planner-db-drop.sh).

2. Далее необходимо собрать **.war**.  

   1. Для этого зайдите в корень проекта (директория где хранится pom.xml) и запустить в консоли  `mvn package`. 
   2. После чего у вас появится директория  *target*, в конторой будет наш архив *taskplanner.war*.

3. Добавьте полученный архив (taskplanner.war) в папку webapps вашего TomCat. Перезапустите его.

4. Зайдите в браузер по http://localhost:8080/taskplanner/mainmenu.

### Style Guide

[to the table of contents](README.md#table-of-contents)

В проекте используется  [Google Java Style Guide](https://google.github.io/styleguide/javaguide.html), за исключением пункта [4.2 Block indentation](https://google.github.io/styleguide/javaguide.html#s4.2-block-indentation). Использует **block indentation**= +4 пробела, вместо 2.

### Illustrations

[to the table of contents](README.md#table-of-contents)

Скрины приложения можно посмотреть [тут](images).

