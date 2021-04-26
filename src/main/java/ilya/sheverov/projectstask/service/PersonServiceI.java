package ilya.sheverov.projectstask.service;

import ilya.sheverov.projectstask.entity.Person;
import ilya.sheverov.projectstask.exception.OptimisticLockException;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

public interface PersonServiceI {

    Person findPersonByIdAndVersion(Integer personId, Timestamp personVersion);

    List<Person> findAllPerson();

    Map<Person, Integer> findAllPersonsWithTheNumberOfTasks();

    Map<Person, Integer> findPartOfTheListOfPersons(String orderByColumnName, int offSet, int limit);

    int getTheNumberOfPersons();

    void createPerson(Person newPerson) throws OptimisticLockException;

    void editPerson(Person editedPerson) throws OptimisticLockException;

    void deletePerson(Person personToDelete) throws OptimisticLockException;

    void deletePerson(Integer personId, Timestamp personVersion) throws OptimisticLockException;

    void deletePersons(List<Person> listPersons) throws OptimisticLockException;
}
