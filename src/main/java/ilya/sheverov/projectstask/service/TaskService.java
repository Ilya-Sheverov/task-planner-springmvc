package ilya.sheverov.projectstask.service;

import ilya.sheverov.projectstask.database.dao.TaskDaoI;
import ilya.sheverov.projectstask.entity.Task;
import ilya.sheverov.projectstask.exception.OptimisticLockException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service("taskService")
public class TaskService implements TaskServiceI {

    @Autowired
    @Qualifier("taskDao")
    private TaskDaoI taskDao;

    @Override
    public Task findTaskByIdAndVersion(Integer personId, Timestamp personVersion) {
        return taskDao.findTaskByIdAndVersion(personId, personVersion);
    }

    @Override
    public Map<Task, String> findSortedPartOfTheListOfTask(String orderByColumnName, int offSet, int limit) {
        String[] validColumnNames =
            {"id", "name", "volume_of_work_in_hours", "start_date", "due_date", "status", "person_id", "version"};
        String finalOrderByColumnName = orderByColumnName;
        boolean isValid = Arrays.stream(validColumnNames).anyMatch(s -> {
            return s.equals(finalOrderByColumnName);
        });
        if (!isValid) {
            orderByColumnName = "id";
        }
        Map<Task, String> tasksWithPersonsInitials = taskDao.findSortedPartOfTheListOfTask(orderByColumnName, offSet, limit);
        return tasksWithPersonsInitials;
    }

    @Override
    public int getTheNumberOfTasks() {
        return taskDao.getTheNumberOfTasks();
    }

    @Override
    public Map<String, String> getPersonsIdAndInitials() {
        return taskDao.getPersonsIdAndInitials();
    }

    @Override
    public void createTask(Task task) throws OptimisticLockException {
        taskDao.createTask(task);
    }

    @Override
    public void editTask(Task task) throws OptimisticLockException {
        int updatedRecords = taskDao.editTask(task);
        if (updatedRecords == 0) {
            throw new OptimisticLockException();
        }
    }

    public void deleteTask(Integer taskId, Timestamp taskVersion) throws OptimisticLockException {
        int deletedRecords = taskDao.deleteTask(taskId, taskVersion);
        if (deletedRecords == 0) {
            throw new OptimisticLockException();
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {OptimisticLockException.class, Exception.class})
    @Override
    public void deleteTasks(List<Task> tasks) throws OptimisticLockException {
        int deletedRecords = 0;
        for (Task task : tasks) {
            deletedRecords += taskDao.deleteTask(task.getId(), task.getVersion());
            if (deletedRecords == 0) {
                break;
            }
        }
        if (deletedRecords != tasks.size()) {
            throw new OptimisticLockException();
        }
    }
}
