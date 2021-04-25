package ilya.sheverov.projectstask.database.mapper;

import ilya.sheverov.projectstask.entity.Task;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class TaskMapper implements RowMapper<Task> {

    @Override
    public Task mapRow(ResultSet rs, int rowNum) throws SQLException {
        Task task = new Task();
        task.setId((Integer) rs.getObject("id"));
        task.setName((String) rs.getObject("name"));
        task.setVolumeOfWorkInHours((Integer) rs.getObject("volume_of_work_in_hours"));
        task.setStartDate((Timestamp) rs.getObject("start_date"));
        task.setDueDate((Timestamp) rs.getObject("due_date"));
        task.setStatus((String) rs.getObject("status"));
        task.setPersonId((Integer) rs.getObject("person_id"));
        task.setVersion((Timestamp) rs.getObject("version"));
        return task;
    }
}
