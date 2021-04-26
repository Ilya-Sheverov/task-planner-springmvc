package ilya.sheverov.projectstask.entity.validator;

import ilya.sheverov.projectstask.database.utils.TableRulesValidator;
import ilya.sheverov.projectstask.entity.Person;
import ilya.sheverov.projectstask.exception.ObjectValidatorException;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("personObjectValidator")
@Scope("singleton")
public final class PersonObjectValidator {

    private static final Integer MAX_VALUE_ID = 2147483647;
    private static final Integer MAX_LENGTH_LAST_NAME = 35;
    private static final Integer MAX_LENGTH_FIRST_NAME = 35;
    private static final Integer MAX_LENGTH_MIDDLE_NAME = 35;

    private static final String REGEX_VERSION_PATTERN =
        "^(\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}(:\\d{2})?(.(\\d)*)?)";


    public void validatePerson(Person person) throws ObjectValidatorException {
        try {
            validatePersonId(person.getId());
            validatePersonLastName(person.getLastName());
            validatePersonFirstName(person.getFirstName());
            validatePersonMiddleName(person.getMiddleName());
        } catch (ObjectValidatorException e) {
            throw e;
        }
    }

    private void validatePersonId(Integer personId) throws ObjectValidatorException {
        if (personId != null) {
            if (!TableRulesValidator.compareWithTheMaximumNumber(MAX_VALUE_ID, personId)) {
                throw new ObjectValidatorException("id", personId);
            }
        }
    }

    private void validatePersonLastName(String personLastName) throws ObjectValidatorException {
        if (personLastName == null) {
            throw new ObjectValidatorException("lastName", null);
        }
        if (!TableRulesValidator.checkTheStringLength(MAX_LENGTH_LAST_NAME, personLastName)) {
            throw new ObjectValidatorException("lastName", personLastName);
        }
    }

    private void validatePersonFirstName(String personFirstName) throws ObjectValidatorException {
        if (personFirstName == null) {
            throw new ObjectValidatorException("firstName", null);
        }
        if (!TableRulesValidator.checkTheStringLength(MAX_LENGTH_FIRST_NAME, personFirstName)) {
            throw new ObjectValidatorException("firstName", personFirstName);
        }
    }

    private void validatePersonMiddleName(String personMiddleName) throws ObjectValidatorException {
        if (personMiddleName == null) {
            throw new ObjectValidatorException("middleName", null);
        }
        if (!TableRulesValidator.checkTheStringLength(MAX_LENGTH_MIDDLE_NAME, personMiddleName)) {
            throw new ObjectValidatorException("middleName", personMiddleName);
        }
    }
}
