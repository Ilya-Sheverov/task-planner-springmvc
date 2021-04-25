package ilya.sheverov.projectstask.entity;


import java.sql.Timestamp;
import java.util.Objects;

public class Task {

    private Integer id;
    private String name;
    private Integer volumeOfWorkInHours;
    private Timestamp startDate;
    private Timestamp dueDate;
    private String status;
    private Integer personId;
    private Timestamp version;

    public Task() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getVolumeOfWorkInHours() {
        return volumeOfWorkInHours;
    }

    public void setVolumeOfWorkInHours(Integer volumeOfWorkInHours) {
        this.volumeOfWorkInHours = volumeOfWorkInHours;
    }

    public Timestamp getStartDate() {
        return startDate;
    }

    public void setStartDate(Timestamp startDate) {
        this.startDate = startDate;
    }

    public Timestamp getDueDate() {
        return dueDate;
    }

    public void setDueDate(Timestamp dueDate) {
        this.dueDate = dueDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getPersonId() {
        return personId;
    }

    public void setPersonId(Integer personId) {
        this.personId = personId;
    }

    public Timestamp getVersion() {
        return version;
    }

    public void setVersion(Timestamp version) {
        this.version = version;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Task)) return false;
        Task task = (Task) o;
        return id.equals(task.id) &&
            version.equals(task.version);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, version);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Task{\n");
        sb.append("id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append(", volumeOfWorkInHours=").append(volumeOfWorkInHours);
        sb.append(", startDate=").append(startDate);
        sb.append(", dueDate=").append(dueDate);
        sb.append(", status='").append(status).append('\'');
        sb.append(", personId=").append(personId);
        sb.append(", version=").append(version);
        sb.append('}');
        return sb.toString();
    }
}
