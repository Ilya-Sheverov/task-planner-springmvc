package ilya.sheverov.projectstask.service;

import ilya.sheverov.projectstask.entity.Task;
import ilya.sheverov.projectstask.exception.OptimisticLockException;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

public interface TaskServiceI {

    Task findTaskByIdAndVersion(Integer personId, Timestamp personVersion);

    Map<Task, String> findSortedPartOfTheListOfTask(String orderByColumnName, int offSet, int limit);

    int getTheNumberOfTasks();

    Map<String, String> getPersonsIdAndInitials();

    void createTask(Task task) throws OptimisticLockException;

    void editTask(Task task) throws OptimisticLockException;

    void deleteTask(Integer personId, Timestamp personVersion) throws OptimisticLockException;

    void deleteTasks(List<Task> tasks) throws OptimisticLockException;
}
