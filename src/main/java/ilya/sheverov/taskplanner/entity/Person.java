package ilya.sheverov.taskplanner.entity;

import java.sql.Timestamp;
import java.util.Objects;

public class Person {

  private Integer personId;
  private String lastName;
  private String firstName;
  private String middleName;
  private Timestamp version;

  public Integer getPersonId() {
    return personId;
  }

  public void setPersonId(Integer personId) {
    this.personId = personId;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getMiddleName() {
    return middleName;
  }

  public void setMiddleName(String middleName) {
    this.middleName = middleName;
  }

  public Timestamp getVersion() {
    return version;
  }

  public void setVersion(Timestamp version) {
    this.version = version;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Person)) return false;
    Person person = (Person) o;
    return personId.equals(person.personId) &&
        Objects.equals(lastName, person.lastName) &&
        Objects.equals(firstName, person.firstName) &&
        Objects.equals(middleName, person.middleName) &&
        version.equals(person.version);
  }

  @Override
  public int hashCode() {
    return Objects.hash(personId, lastName, firstName, middleName, version);
  }
}
