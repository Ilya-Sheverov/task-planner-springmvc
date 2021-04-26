package ilya.sheverov.projectstask.entity.converter;

import ilya.sheverov.projectstask.entity.Task;
import ilya.sheverov.projectstask.entity.presenter.TaskPresenter;
import ilya.sheverov.projectstask.entity.validator.TaskObjectValidator;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

public class TaskTaskPresenterConverter {

    public final Set<String> invalidFieldsNames = new HashSet<>();
    private Task task;
    private TaskObjectValidator taskObjectValidator;
    private int invalidFieldsCount = 0;

    public TaskTaskPresenterConverter(TaskObjectValidator taskObjectValidator) {
        this.taskObjectValidator = taskObjectValidator;
    }

    public static void main(String[] args) {
        try {
            Timestamp timestamp = Timestamp.valueOf("2020-13-13 25:23:23.0");
            System.out.println(timestamp);
        } catch (IllegalArgumentException e) {
            System.out.println("aaa");
        }
        System.out.println("qqq");
    }

    public void getTaskByTaskPresenter(TaskPresenter taskPresenter) {
        task = new Task();
        Integer taskId = convertTaskPresenterIdByTaskId(taskPresenter.getId());
        if (taskId != null) {
            task.setId(taskId);
        }
        String taskName = convertTaskPresenterNameByTaskName(taskPresenter.getName());
        if (taskName != null) {
            task.setName(taskName);
        }
        Integer volumeOfWork = convertTaskPresenterVolumeOfWork(taskPresenter.getVolumeOfWorkInHours());
        if (volumeOfWork != null) {
            task.setVolumeOfWorkInHours(volumeOfWork);
        }
        Timestamp startDate = convertTaskPresenterStartDate(taskPresenter.getStartDate());
        if (startDate != null) {
            task.setStartDate(startDate);
        }
        Timestamp dueDate = convertTaskPresenterDueDate(taskPresenter.getDueDate());
        if (dueDate != null) {
            task.setDueDate(dueDate);
        }
        String status = convertTaskPresenterStatus(taskPresenter.getStatus());
        if (status != null) {
            task.setStatus(status);
        }
        Integer personId = convertTaskPresenterPersonId(taskPresenter.getPersonId());
        if (personId != null) {
            task.setPersonId(personId);
        }
        Timestamp version = convertTaskPresenterVersion(taskPresenter.getVersion());
        if (version != null) {
            task.setVersion(version);
        }

        taskObjectValidator.validateTask(task);
        if (taskObjectValidator.getInvalidFieldsCount() != 0) {
            invalidFieldsNames.addAll(taskObjectValidator.getInvalidFieldsNames());
            invalidFieldsCount = invalidFieldsNames.size();
        }
    }

    private Integer convertTaskPresenterIdByTaskId(String taskPresenterId) {
        if (taskPresenterId.isEmpty() || taskPresenterId == null) {
            return null;
        }
        try {
            Integer taskId = Integer.valueOf(taskPresenterId);
            return taskId;
        } catch (NumberFormatException e) {
            setInvalidFieldName("id");
            return null;
        }
    }

    private String convertTaskPresenterNameByTaskName(String name) {
        if (name.isEmpty() || name == null) {
            return null;
        }
        return name;
    }

    private Integer convertTaskPresenterVolumeOfWork(String volumeOfWorkInHours) {
        if (volumeOfWorkInHours.isEmpty() || volumeOfWorkInHours == null) {
            return null;
        }
        try {
            Integer taskId = Integer.valueOf(volumeOfWorkInHours);
            return taskId;
        } catch (NumberFormatException e) {
            setInvalidFieldName("volumeOfWorkInHours");
        }
        return null;
    }

    private Timestamp convertTaskPresenterStartDate(String startDate) {
        try {
            Timestamp startDateTimestamp = stringToTimestamp(startDate);
            return startDateTimestamp;
        } catch (IllegalArgumentException e) {
            setInvalidFieldName("startDate");
        }
        return null;
    }

    private Timestamp convertTaskPresenterDueDate(String dueDate) {
        try {
            Timestamp dueDateTimestamp = stringToTimestamp(dueDate);
            return dueDateTimestamp;
        } catch (IllegalArgumentException e) {
            setInvalidFieldName("dueDate");
        }
        return null;
    }

    private String convertTaskPresenterStatus(String status) {
        if (status.isEmpty() || status == null) {
            return null;
        }
        return status;
    }

    private Integer convertTaskPresenterPersonId(String personId) {
        if (personId.isEmpty() || personId == null) {
            return null;
        }
        try {
            Integer taskPersonId = Integer.valueOf(personId);
            return taskPersonId;
        } catch (NumberFormatException e) {
            setInvalidFieldName("personId");
            return null;
        }
    }

    private Timestamp convertTaskPresenterVersion(String version) {
        try {
            Timestamp taskVersion = stringToTimestamp(version);
            return taskVersion;
        } catch (IllegalArgumentException e) {
            setInvalidFieldName("version");
        }
        return null;
    }

    private Timestamp stringToTimestamp(String timestamp) throws IllegalArgumentException {
        if (timestamp.isEmpty() || timestamp == null) {
            return null;
        }
        if (timestamp.matches("^((\\d{4}-\\d{2}-\\d{2})([ T])((\\d{2}:\\d{2})(:\\d{2}(.(\\d){1,9})?)?))|$")) {
            if (timestamp.contains("T")) {
                timestamp = timestamp.replace('T', ' ');
            }
            int dateLength = timestamp.length();
            if (dateLength == 16) {
                timestamp = timestamp + ":00.0";
            }
            if (dateLength == 19) {
                timestamp = timestamp + ".0";
            }
            return Timestamp.valueOf(timestamp);
        } else {
            throw new IllegalArgumentException();
        }
    }

    private void setInvalidFieldName(String invalidFieldName) {
        invalidFieldsNames.add(invalidFieldName);
    }

    public Task getTask() {
        return task;
    }

    public int getInvalidFieldsCount() {
        return invalidFieldsNames.size();
    }

    public Set<String> getInvalidFieldsNames() {
        return invalidFieldsNames;
    }
}
