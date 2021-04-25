package ilya.sheverov.projectstask.database.mapper;

import ilya.sheverov.projectstask.entity.Person;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PersonResultSetExtractor implements ResultSetExtractor<Person> {
    @Override
    public Person extractData(ResultSet rs) throws SQLException, DataAccessException {
        Person person = null;
        PersonMapper personMapper = new PersonMapper();
        while (rs.next()) {
            person = personMapper.mapRow(rs, rs.getRow());
        }
        return person;
    }
}
