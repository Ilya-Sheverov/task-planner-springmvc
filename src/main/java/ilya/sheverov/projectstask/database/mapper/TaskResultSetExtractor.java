package ilya.sheverov.projectstask.database.mapper;

import ilya.sheverov.projectstask.entity.Task;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TaskResultSetExtractor implements ResultSetExtractor<Task> {

    @Override
    public Task extractData(ResultSet rs) throws SQLException, DataAccessException {
        TaskMapper taskMapper = new TaskMapper();
        rs.next();
        Task task = taskMapper.mapRow(rs, rs.getRow());
        return task;
    }
}

