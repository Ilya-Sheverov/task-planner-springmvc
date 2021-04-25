package ilya.sheverov.projectstask.database.mapper;

import ilya.sheverov.projectstask.entity.Task;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;

public class TaskWithInitialsAssigneeResultSetExtractor implements ResultSetExtractor<Map<Task, String>> {
    @Override
    public Map<Task, String> extractData(ResultSet rs) throws SQLException, DataAccessException {
        Map<Task, String> tasksWithInitialsAssignee = new LinkedHashMap<>();
        TaskMapper taskMapper = new TaskMapper();
        while (rs.next()) {
            Task task = taskMapper.mapRow(rs, rs.getRow());
            String initials = rs.getString("initials");
            tasksWithInitialsAssignee.put(task, initials);
        }
        return tasksWithInitialsAssignee;
    }
}
