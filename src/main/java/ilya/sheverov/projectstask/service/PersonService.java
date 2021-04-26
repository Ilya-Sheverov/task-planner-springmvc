package ilya.sheverov.projectstask.service;

import ilya.sheverov.projectstask.database.dao.PersonDaoI;
import ilya.sheverov.projectstask.entity.Person;
import ilya.sheverov.projectstask.exception.OptimisticLockException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service("personService")
@Scope("singleton")
public class PersonService implements PersonServiceI {

    @Autowired
    @Qualifier("personDao")
    private PersonDaoI personDao;

    public Person findPersonByIdAndVersion(Integer personId, Timestamp personVersion) {
        Person person = personDao.findPersonByIdAndVersion(personId, personVersion);
        return person;
    }

    @Override
    public List<Person> findAllPerson() {
        return null;
    }

    @Override
    public Map<Person, Integer> findAllPersonsWithTheNumberOfTasks() {
        return null;
    }

    @Override
    public Map<Person, Integer> findPartOfTheListOfPersons(String orderByColumnName, int offSet, int limit) {
        String[] validColumnNames = {"id", "last_name", "first_name", "middle_name"};
        String finalOrderByColumnName = orderByColumnName;
        boolean isValid = Arrays.stream(validColumnNames).anyMatch(s -> {
            return s.equals(finalOrderByColumnName);
        });
        if (!isValid) {
            orderByColumnName = "id";
        }
        if (offSet < 1) {
            offSet = 1;
        }
        if (limit < 0) {
            limit = 0;
        }
        Map<Person, Integer> persons = personDao.findPartOfTheListOfPersons(orderByColumnName, offSet, limit);
        return persons;
    }

    @Override
    public int getTheNumberOfPersons() {
        int numberOfUsers = personDao.getTheNumberOfPersons();
        return numberOfUsers;
    }

    @Override
    public void createPerson(Person newPerson) throws OptimisticLockException {
        int newRecords = 0;
        newRecords = personDao.createPerson(newPerson);
        if (newRecords == 0) {
            throw new OptimisticLockException();
        }

    }

    @Override
    public void editPerson(Person editedPerson) throws OptimisticLockException {
        int updatedRecords = personDao.editPerson(editedPerson);
        if (updatedRecords == 0) {
            throw new OptimisticLockException();
        }
    }

    @Override
    public void deletePerson(Person personToDelete) throws OptimisticLockException {

    }

    @Transactional(rollbackFor = OptimisticLockException.class)
    public void deletePerson(Integer personId, Timestamp personVersion) throws OptimisticLockException {
        int deletedRecords = personDao.deletePerson(personId, personVersion);
        if (deletedRecords == 0) {
            throw new OptimisticLockException();
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = OptimisticLockException.class)
    @Override
    public void deletePersons(List<Person> listPersons) throws OptimisticLockException {
        for (Person person : listPersons) {
            deletePerson(person.getId(), person.getVersion());
        }
    }
}
