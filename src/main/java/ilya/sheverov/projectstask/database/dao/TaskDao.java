package ilya.sheverov.projectstask.database.dao;

import ilya.sheverov.projectstask.database.mapper.PersonsIdAndInitialsResultSetExtractor;
import ilya.sheverov.projectstask.database.mapper.TaskResultSetExtractor;
import ilya.sheverov.projectstask.database.mapper.TaskWithInitialsAssigneeResultSetExtractor;
import ilya.sheverov.projectstask.entity.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.Map;

@Repository("taskDao")
public class TaskDao implements TaskDaoI {

    private static final String GET_THE_NUMBER_OF_TASKS_SQL = "SELECT count(*) FROM tasks;";

    private static final String FIND_TASk_BY_ID_AND_VERSION_SQL =
        "SELECT *\n" +
            "FROM tasks\n" +
            "WHERE id = ? AND version = ?;";
    private static final String FIND_PERSONS_ID_AND_INITIAL =
        "SELECT persons.\"id\", concat(\"last_name\", ' ', \"first_name\", ' ', \"middle_name\") AS initial\n" +
            "FROM persons ORDER BY initial;";

    private static final String CREATE_TASK_SQL =
        "INSERT INTO tasks (\"name\", \"volume_of_work_in_hours\",\n" +
            "                   \"start_date\", \"due_date\",\n" +
            "                   \"status\", \"person_id\")\n" +
            "VALUES (?, ?, ?, ?, ?, ?);";

    private static final String EDIT_TASK_SQL =
        "UPDATE tasks\n" +
            "SET \"name\" = ?,\n" +
            "    \"volume_of_work_in_hours\" = ?,\n" +
            "    \"start_date\" = ?,\n" +
            "    \"due_date\" = ?,\n" +
            "    \"status\" = ?,\n" +
            "    \"person_id\" = ?,\n" +
            "    \"version\" = current_timestamp\n" +
            "WHERE id = ? AND version = ?;";

    private static final String DELETE_TASK_SQL =
        "DELETE\n" +
            "FROM tasks\n" +
            "WHERE id = ?\n" +
            "  AND version = ?;";

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public TaskDao(@Qualifier("jdbcTemplate") JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Task findTaskByIdAndVersion(Integer personId, Timestamp personVersion) {
        return jdbcTemplate.query(FIND_TASk_BY_ID_AND_VERSION_SQL, new TaskResultSetExtractor(), personId, personVersion);
    }

    @Override
    public Map<Task, String> findSortedPartOfTheListOfTask(String orderByColumnName, int offSet, int limit) {
        return jdbcTemplate.query(
            generateAnSqlQueryToSearchForTaskWithAVariableColumnForSorting(orderByColumnName),
            new TaskWithInitialsAssigneeResultSetExtractor(), offSet, limit);
    }

    @Override
    public int getTheNumberOfTasks() {
        Integer count = jdbcTemplate.query(GET_THE_NUMBER_OF_TASKS_SQL, rs -> {
            if (rs.next()) {
                return rs.getInt("count");
            }
            return 0;
        });
        return count;
    }

    @Override
    public Map<String, String> getPersonsIdAndInitials() {
        return jdbcTemplate.query(FIND_PERSONS_ID_AND_INITIAL,
            new PersonsIdAndInitialsResultSetExtractor());
    }

    @Override
    public int createTask(Task task) {
        int updatedRecords;
        updatedRecords = jdbcTemplate.update(CREATE_TASK_SQL, task.getName(), task.getVolumeOfWorkInHours(),
            task.getStartDate(), task.getDueDate(), task.getStatus(), task.getPersonId());
        return updatedRecords;
    }

    @Override
    public int editTask(Task editedTask) {
        int updatedRecords;
        updatedRecords = jdbcTemplate.update(EDIT_TASK_SQL, editedTask.getName(), editedTask.getVolumeOfWorkInHours(),
            editedTask.getStartDate(), editedTask.getDueDate(), editedTask.getStatus(), editedTask.getPersonId(),
            editedTask.getId(), editedTask.getVersion());
        return updatedRecords;
    }

    @Override
    public int deleteTask(Integer taskId, Timestamp taskVersion) {
        int deletedRecord;
        deletedRecord = jdbcTemplate.update(DELETE_TASK_SQL, taskId, taskVersion);
        return deletedRecord;
    }

    private String generateAnSqlQueryToSearchForTaskWithAVariableColumnForSorting(final String columnName) {
        StringBuilder sqlStringBuilder = new StringBuilder();
        sqlStringBuilder.append("SELECT t.\"id\",\n" +
            "       t.\"name\",\n" +
            "       t.\"volume_of_work_in_hours\",\n" +
            "       t.\"start_date\",\n" +
            "       t.\"due_date\",\n" +
            "       t.\"status\",\n" +
            "       t.\"person_id\",\n" +
            "       t.\"version\",\n" +
            "       concat(p.\"last_name\", ' ', p.\"first_name\", ' ', p.\"middle_name\") AS initials\n" +
            "FROM tasks AS t\n" +
            "         LEFT JOIN persons AS p ON t.\"person_id\" = p.\"id\" ORDER BY ")
            .append(columnName);
        if (columnName.equals("start_date") || columnName.equals("due_date")) {
            sqlStringBuilder.append(" DESC NULLS LAST");
        }
        sqlStringBuilder.append(" OFFSET ? LIMIT ?;");
        return sqlStringBuilder.toString();
    }
}
