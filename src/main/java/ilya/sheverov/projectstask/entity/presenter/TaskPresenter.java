package ilya.sheverov.projectstask.entity.presenter;

import ilya.sheverov.projectstask.entity.StatusOfATask;
import java.util.Objects;

public class TaskPresenter {

    public static final String[] statusValues = {"Не начата", "В процессе", "Завершена",
        "Отложена"};

    private String id = "";
    private String name = "";
    private String volumeOfWorkInHours = "";
    private String startDate = "";
    private String dueDate = "";
    private StatusOfATask status = StatusOfATask.NOT_STARTED;
    private String personId = "";
    private String version = "";

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVolumeOfWorkInHours() {
        return volumeOfWorkInHours;
    }

    public void setVolumeOfWorkInHours(String volumeOfWorkInHours) {
        this.volumeOfWorkInHours = volumeOfWorkInHours;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public StatusOfATask getStatus() {
        return status;
    }

    public void setStatus(StatusOfATask status) {
        this.status = status;
    }

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String[] getStatusValues() {
        return statusValues;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TaskPresenter)) {
            return false;
        }
        TaskPresenter taskView = (TaskPresenter) o;
        return id.equals(taskView.id) &&
            name.equals(taskView.name) &&
            volumeOfWorkInHours.equals(taskView.volumeOfWorkInHours) &&
            startDate.equals(taskView.startDate) &&
            dueDate.equals(taskView.dueDate) &&
            status.equals(taskView.status) &&
            personId.equals(taskView.personId) &&
            version.equals(taskView.version);
    }

    @Override
    public int hashCode() {
        return Objects
            .hash(id, name, volumeOfWorkInHours, startDate, dueDate, status, personId, version);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("TaskPresenter{");
        sb.append("id='").append(id).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append(", volumeOfWorkInHours='").append(volumeOfWorkInHours).append('\'');
        sb.append(", startDate='").append(startDate).append('\'');
        sb.append(", dueDate='").append(dueDate).append('\'');
        sb.append(", status='").append(status).append('\'');
        sb.append(", personId='").append(personId).append('\'');
        sb.append(", version='").append(version).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
