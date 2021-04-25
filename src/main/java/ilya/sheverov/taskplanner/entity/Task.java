package ilya.sheverov.taskplanner.entity;

import java.sql.Timestamp;
import java.util.Objects;

public class Task {

  private Integer taskId;
  private String description;
  private Integer workVolume;
  private Timestamp startDate;
  private Timestamp dueDate;
  private String statusId;
  private Timestamp version;

  public Integer getTaskId() {
    return taskId;
  }

  public void setTaskId(Integer taskId) {
    this.taskId = taskId;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Integer getWorkVolume() {
    return workVolume;
  }

  public void setWorkVolume(Integer workVolume) {
    this.workVolume = workVolume;
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

  public String getStatusId() {
    return statusId;
  }

  public void setStatusId(String statusId) {
    this.statusId = statusId;
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
    return taskId.equals(task.taskId) &&
        Objects.equals(description, task.description) &&
        Objects.equals(workVolume, task.workVolume) &&
        Objects.equals(startDate, task.startDate) &&
        Objects.equals(dueDate, task.dueDate) &&
        statusId.equals(task.statusId) &&
        version.equals(task.version);
  }

  @Override
  public int hashCode() {
    return Objects.hash(taskId, description, workVolume, startDate, dueDate, statusId, version);
  }
}

