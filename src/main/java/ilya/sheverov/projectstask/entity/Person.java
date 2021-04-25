package ilya.sheverov.projectstask.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

public class Person implements Serializable {

    private Integer id;
    private String lastName;
    private String firstName;
    private String middleName;
    private Timestamp version;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
        return id.equals(person.id) &&
            Objects.equals(lastName, person.lastName) &&
            Objects.equals(firstName, person.firstName) &&
            Objects.equals(middleName, person.middleName) &&
            version.equals(person.version);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, lastName, firstName, middleName, version);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Person{");
        sb.append("id=").append(id);
        sb.append(", lastName='").append(lastName).append('\'');
        sb.append(", firstName='").append(firstName).append('\'');
        sb.append(", middleName='").append(middleName).append('\'');
        sb.append(", version=").append(version);
        sb.append('}');
        return sb.toString();
    }
}
