package ilya.sheverov.projectstask.entity.presenter.converter;

import ilya.sheverov.projectstask.entity.Task;
import ilya.sheverov.projectstask.entity.presenter.TaskPresenter;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Component
@Scope("prototype")
public class TaskViewConverter {

    char dateTimeSeparator = ' ';

    public TaskViewConverter() {

    }

    public TaskViewConverter(char dateTimeSeparator) {
        this.dateTimeSeparator = dateTimeSeparator;
    }

    public TaskPresenter convertTaskToTaskView(Task task) {
        TaskPresenter taskView = new TaskPresenter();
        Integer id = task.getId();
        if (id != null) {
            String taskViewId = String.valueOf(id);
            taskView.setId(taskViewId);
        }
        String name = task.getName();
        if (name != null) {
            taskView.setName(name);
        }
        Integer volumeOfWorkInHours = task.getVolumeOfWorkInHours();
        if (volumeOfWorkInHours != null) {
            String taskViewVolumeOfWorkInHours = String.valueOf(volumeOfWorkInHours);
            taskView.setVolumeOfWorkInHours(taskViewVolumeOfWorkInHours);
        } else {
            taskView.setVolumeOfWorkInHours("-");
        }
        Timestamp startDate = task.getStartDate();
        if (startDate != null) {
            String taskViewStartDate = timestampToString(startDate, 16);
            taskView.setStartDate(taskViewStartDate);
        }
        Timestamp dueDate = task.getDueDate();
        if (dueDate != null) {
            String taskViewDueDate = timestampToString(dueDate, 16);
            taskView.setDueDate(taskViewDueDate);
        }
        String status = task.getStatus();
        taskView.setStatus(status);

        Integer personId = task.getPersonId();
        if (personId != null) {
            String taskViewPersonId = String.valueOf(personId);
            taskView.setPersonId(taskViewPersonId);
        }
        Timestamp version = task.getVersion();
        if (version != null) {
            String taskViewVersion = String.valueOf(version);
            taskView.setVersion(taskViewVersion);
        }
        return taskView;
    }

    public List<TaskPresenter> convertTaskToTaskView(List<Task> tasks) {
        LinkedList<TaskPresenter> taskViews = new LinkedList<>();
        tasks.forEach(task -> {
            TaskPresenter taskView = convertTaskToTaskView(task);
            taskViews.add(taskView);
        });
        return taskViews;
    }

    public Map<TaskPresenter, String> convertTaskToTaskView(Map<Task, String> tasks) {
        Map<TaskPresenter, String> taskViews = new LinkedHashMap<>();
        tasks.forEach((task, initials) -> {
            TaskPresenter taskView = convertTaskToTaskView(task);
            if (initials == null || initials.equals("  ")) {
                initials = "Не назначен";
            }
            taskViews.put(taskView, initials);
        });
        return taskViews;
    }

    private String timestampToString(Timestamp timestamp, int length) {
        String timestampAsString = timestamp.toString().substring(0, length);
        if (!(dateTimeSeparator == ' ')) {
            timestampAsString = timestampAsString.replace(' ', dateTimeSeparator);
        }
        return timestampAsString;
    }
}
