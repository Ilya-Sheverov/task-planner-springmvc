package ilya.sheverov.projectstask.database.dao;

import ilya.sheverov.projectstask.entity.Person;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

public interface PersonDaoI {

    Person findPersonByIdAndVersion(Integer personId, Timestamp personVersion);

    Map<Person, Integer> findPartOfTheListOfPersons(String orderByColumnName, int offSet, int limit);

    int getTheNumberOfPersons();

    int createPerson(Person newPerson);

    int editPerson(Person editedPerson);

    int deletePerson(Person personToDelete);

    int deletePerson(Integer personId, Timestamp personVersion);

}