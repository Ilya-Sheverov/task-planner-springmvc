package ilya.sheverov.projectstask.entity.converter;

import ilya.sheverov.projectstask.entity.Person;
import ilya.sheverov.projectstask.entity.presenter.PersonPresenter;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Component
@Scope("prototype")
public class PersonToPersonPresenterConverter {

  private String defaultIdValue = "";
  private String defaultLastNameValue = "";
  private String defaultFirstNameValue = "";
  private String defaultMiddleNameValue = "";
  private String defaultVersionValue = "";

  public PersonPresenter convert(Person person) {
    //todo проверка на null
    PersonPresenter personPresenter = new PersonPresenter();
    Integer personId = person.getId();
    if (personId == null) {
      personPresenter.setId(defaultIdValue);
    } else {
      personPresenter.setId(String.valueOf(personId));
    }
    String lastName = person.getLastName();
    if (lastName == null || lastName.isEmpty()) {
      personPresenter.setLastName(defaultLastNameValue);
    } else {
      personPresenter.setLastName(lastName);
    }
    String firstName = person.getFirstName();
    if (firstName == null || firstName.isEmpty()) {
      personPresenter.setFirstName(defaultFirstNameValue);
    } else {
      personPresenter.setFirstName(firstName);
    }
    String middleName = person.getMiddleName();
    if (middleName == null || middleName.isEmpty()) {
      personPresenter.setMiddleName(defaultMiddleNameValue);
    } else {
      personPresenter.setMiddleName(middleName);
    }
    Timestamp personVersion = person.getVersion();
    if (personVersion == null) {
      personPresenter.setVersion(defaultVersionValue);
    } else {
      personPresenter.setVersion(String.valueOf(personVersion));
    }
    return personPresenter;
  }

  public List<PersonPresenter> convert(List<Person> personList) {
    List<PersonPresenter> personPresenterList = new ArrayList<>();
    for (Person person : personList) {
      PersonPresenter personPresenter = convert(person);
      personPresenterList.add(personPresenter);
    }
    return personPresenterList;
  }

  public void setDefaultMiddleNameValue(String defaultMiddleNameValue) {
    this.defaultMiddleNameValue = defaultMiddleNameValue;
  }

  public <T> Map<PersonPresenter, T> convert(Map<Person, T> personsWithTasksCount) {
    Map<PersonPresenter, T> personPresenters = new LinkedHashMap<>();
    for (Map.Entry<Person, T> personTEntry : personsWithTasksCount.entrySet()) {
      PersonPresenter personPresenter = convert(personTEntry.getKey());
      personPresenters.put(personPresenter, personTEntry.getValue());
    }
    return personPresenters;
  }
}
