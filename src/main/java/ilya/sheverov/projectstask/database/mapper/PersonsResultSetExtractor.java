package ilya.sheverov.projectstask.database.mapper;

import ilya.sheverov.projectstask.entity.Person;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;

public class PersonsResultSetExtractor implements ResultSetExtractor<Map<Person, Integer>> {
    @Override
    public Map<Person, Integer> extractData(ResultSet rs) throws SQLException, DataAccessException {
        Map<Person, Integer> personsWithNumberOfTasks = new LinkedHashMap<>();
        PersonMapper personMapper = new PersonMapper();
        while (rs.next()) {
            Person person = personMapper.mapRow(rs, rs.getRow());
            Integer numberOfTasks = rs.getInt("number_of_tasks");
            personsWithNumberOfTasks.put(person, numberOfTasks);
        }
        return personsWithNumberOfTasks;
    }
}
