package ilya.sheverov.projectstask.database.dao;

import ilya.sheverov.projectstask.entity.Task;

import java.sql.Timestamp;
import java.util.Map;

public interface TaskDaoI {

    Task findTaskByIdAndVersion(Integer personId, Timestamp personVersion);

    Map<Task, String> findSortedPartOfTheListOfTask(String orderByColumnName, int offSet, int limit);

    int getTheNumberOfTasks();

    Map<String, String> getPersonsIdAndInitials();

    int createTask(Task task);

    int editTask(Task task);

    int deleteTask(Integer taskId, Timestamp taskVersion);
}
