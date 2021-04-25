package ilya.sheverov.projectstask.database.dao;

import ilya.sheverov.projectstask.database.mapper.PersonMapper;
import ilya.sheverov.projectstask.database.mapper.PersonResultSetExtractor;
import ilya.sheverov.projectstask.database.mapper.PersonsResultSetExtractor;
import ilya.sheverov.projectstask.entity.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

@Repository("personDao")
public class PersonDao implements PersonDaoI {

    private static final String FIND_PERSON_BY_ID_AND_VERSION_SQL =
        "SELECT *\n" +
            "FROM persons\n" +
            "WHERE id = ? AND version = ?;";
    private static final String FIND_ALL_PERSONS_SQL =
        "SELECT * FROM persons ORDER BY \"last_name\", \"first_name\", \"middle_name\",\"version\";";
    private static final String FIND_ALL_PERSONS_WITH_THE_NUMBER_OF_TASKS =
        "SELECT persons.id," +
            "       persons.last_name," +
            "       persons.first_name," +
            "       persons.middle_name," +
            "       persons.version," +
            "       count(tasks.id) AS \"number_of_tasks\"" +
            "FROM persons" +
            "         LEFT JOIN tasks" +
            "                   ON persons.id = tasks.person_id " +
            "GROUP BY persons.id;";
    private static final String GET_THE_NUMBER_OF_USERS_SQL = "SELECT count(*) FROM persons;";
    private static final String CREATE_PERSON_SQL =
        "INSERT INTO persons (last_name, first_name, middle_name) VALUES (?, ?, ?);";
    private static final String EDIT_PERSON_SQL =
        "UPDATE persons SET last_name  = ?, first_name = ?, middle_name = ?,version = current_timestamp\n" +
            "WHERE id = ? AND version = ?;";
    private static final String DELETE_PERSON_SQL =
        "DELETE\n" +
            "FROM persons\n" +
            "WHERE \"id\" = ? AND  \"version\" = ?\n" +
            "  AND (SELECT count(tasks.person_id)\n" +
            "       FROM tasks\n" +
            "       WHERE tasks.person_id = persons.id) = 0;";
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonDao(@Qualifier("jdbcTemplate") JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public int getTheNumberOfPersons() {
        return jdbcTemplate.query(GET_THE_NUMBER_OF_USERS_SQL, rs -> {
            if (rs.next()) {
                return rs.getInt("count");
            }
            return 0;
        });
    }

    @Override
    public Person findPersonByIdAndVersion(Integer personId, Timestamp personVersion) {
        return jdbcTemplate.query(FIND_PERSON_BY_ID_AND_VERSION_SQL, new PersonResultSetExtractor(), personId,
            personVersion);
    }

    public List<Person> findAllPerson() {
        return jdbcTemplate.query(FIND_ALL_PERSONS_SQL, new PersonMapper());
    }

    public Map<Person, Integer> findAllPersonsWithTheNumberOfTasks() {
        return jdbcTemplate.query(FIND_ALL_PERSONS_WITH_THE_NUMBER_OF_TASKS,
            new PersonsResultSetExtractor());
    }

    public Map<Person, Integer> findPartOfTheListOfPersons(String orderByColumnName, int offSet, int limit) {
        return jdbcTemplate.query(
            generateAnSqlQueryToSearchForPeopleWithAVariableColumnForSorting(orderByColumnName),
            new PersonsResultSetExtractor(), offSet, limit);
    }

    public int createPerson(Person newPerson) {
        int updatedRecords;
        updatedRecords = jdbcTemplate.update(CREATE_PERSON_SQL, newPerson.getLastName(), newPerson.getFirstName(),
            newPerson.getMiddleName());
        return updatedRecords;
    }

    public int editPerson(Person editedPerson) {
        int updatedRecords;
        updatedRecords = jdbcTemplate.update(EDIT_PERSON_SQL, editedPerson.getLastName(), editedPerson.getFirstName(),
            editedPerson.getMiddleName(), editedPerson.getId(), editedPerson.getVersion());
        return updatedRecords;
    }

    public int deletePerson(Person personToDelete) {
        int deletedRecord;
        deletedRecord = deletePerson(personToDelete.getId(), personToDelete.getVersion());
        return deletedRecord;
    }

    public int deletePerson(Integer personId, Timestamp personVersion) {
        int deletedRecord;
        deletedRecord = jdbcTemplate.update(DELETE_PERSON_SQL, personId, personVersion);
        return deletedRecord;
    }

    private String generateAnSqlQueryToSearchForPeopleWithAVariableColumnForSorting(final String columnName) {
        StringBuilder sqlStringBuilder = new StringBuilder();
        sqlStringBuilder.append("SELECT p.id,\n" +
            "       p.last_name,\n" +
            "       p.first_name,\n" +
            "       p.middle_name,\n" +
            "       p.version,\n" +
            "       count(tasks.id) AS number_of_tasks\n" +
            "FROM persons AS p\n" +
            "         LEFT JOIN tasks\n" +
            "                   ON p.id = tasks.person_id\n" +
            "GROUP BY p.id, p.")
            .append(columnName)
            .append(" ORDER BY ")
            .append(columnName)
            .append("  OFFSET ?\n" +
                "LIMIT ?;");
        return sqlStringBuilder.toString();
    }
}