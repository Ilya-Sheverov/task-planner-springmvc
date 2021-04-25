package ilya.sheverov.projectstask.database.mapper;

import ilya.sheverov.projectstask.entity.Person;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PersonMapper implements RowMapper<Person> {
    @Override
    public Person mapRow(ResultSet resultSet, int i) throws SQLException {
        Person person = new Person();
        person.setId(resultSet.getInt("id"));
        person.setLastName(resultSet.getString("last_name"));
        person.setFirstName(resultSet.getString("first_name"));
        person.setMiddleName(resultSet.getString("middle_name"));
        person.setVersion(resultSet.getTimestamp("version"));
        return person;
    }
}
