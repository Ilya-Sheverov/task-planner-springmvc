package ilya.sheverov.projectstask.validator;

import ilya.sheverov.projectstask.entity.Person;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class PersonValidator implements Validator {

    private static final Integer MAX_VALUE_ID = 2147483647;
    private static final Integer MAX_LENGTH_LAST_NAME = 35;
    private static final Integer MAX_LENGTH_FIRST_NAME = 35;
    private static final Integer MAX_LENGTH_MIDDLE_NAME = 35;

    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Person person = (Person) target;
        if (person.getId() != null) {
            if (person.getId() < 0) {
                errors.rejectValue("id", "id.negative");
            } else if (person.getId() > MAX_VALUE_ID) {
                errors.rejectValue("id", "id.too.big");
            }
        }
        ValidationUtils.rejectIfEmpty(errors, "lastName", "lastName.empty");
        if (person.getLastName().length() > MAX_LENGTH_LAST_NAME) {
            errors.rejectValue("lastName", "lastName.too.big");
        }
        ValidationUtils.rejectIfEmpty(errors, "firstName", "firstName.empty");
        if (person.getFirstName().length() > MAX_LENGTH_FIRST_NAME) {
            errors.rejectValue("firstName", "firstName.too.big");
        }
        ValidationUtils.rejectIfEmpty(errors, "middleName", "middleName.empty");
        if (person.getMiddleName().length() > MAX_LENGTH_MIDDLE_NAME) {
            errors.rejectValue("middleName", "middleName.too.big");
        }
    }

}
