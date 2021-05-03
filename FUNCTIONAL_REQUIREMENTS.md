### Functional requirements

##### Приложение должно позволять создавать, редактировать и удалять следующие сущности:

1. Задача

   - Название
   - Работа (часы)
   - Дата начала
   - Дата окончания
   - Статус (Не начата | В процессе | Завершена | Отложена)

2. Персона

   - Фамилия

   - Имя

   - Отчество

   - Должность


##### Между перечисленными выше объектами необходимо реализовать следующие связи:

* У задачи может быть только одна персона, исполняющая её.
* Одина персона может быть назначена на множество задач.

##### Приложение должно содержать следующие основные элементы:

1. ###### Главное Меню

   - Задачи: Отображается форма “Список задач”
   - Персоны: Отображается форма “Список сотрудников”

2. ###### Форма “Список задач”

   - Колонки:
     - Идентификатор
     - Название
     - Работа (количество времени необходимого для выполнения задачи, часы)
     - Дата начала
     - Дата окончания
     - Статус (Не начата | В процессе | Завершена | Отложена)
     - Исполнитель (ФИО)
   - Команды уровня формы
     - Добавить: Отображается форма ввода задачи в режиме добавления
   - Команды уровня записи
     - Изменить: Отображается форма ввода задачи в режиме редактирования
     - Удалить: Текущая запись удаляется

3. ###### Форма “Список персон”

   - Колонки:
     - Идентификатор
     - Фамилия
     - Имя
     - Отчество
     - Должность
   - Команды уровня формы
     - Добавить: Отображается форма ввода сотрудника в режиме добавления
   - Команды уровня записи
     - Изменить: Отображается форма ввода сотрудника в режиме редактирования
     - Удалить: Текущая запись удаляется

4. ###### Форма ввода задачи

   - Поля:
     - Идентификатор: порядковый номер задачи; формируется автоматически; недоступно для изменения
     - Название
     - Работа (количество времени необходимого для выполнения задачи, часы)
     - Дата начала
     - Дата окончания
     - Статус (Не начата | В процессе | Завершена | Отложена)
     - Исполнитель: выбирается из списка персон
   - Команды:
     - Сохранить: введенные данные сохраняются в базе; управление передается в форму [“Список задач”](FUNCTIONAL_REQUIREMENTS.md#форма-список-задач)
     - Отмена: управление передается в форму [“Список задач”](FUNCTIONAL_REQUIREMENTS.md#форма-список-задач)

5. ###### Форма ввода персоны

   - Поля:
     - Идентификатор
     - Фамилия
     - Имя
     - Отчество
   - Команды:
     - Сохранить: введенные данные сохраняются в базе; управление передается в форму [“Список персон”](FUNCTIONAL_REQUIREMENTS.md#форма-список-сотрудников)
     - Отмена: управление передается в форму  [“Список персон”](FUNCTIONAL_REQUIREMENTS.md#форма-список-сотрудников)
