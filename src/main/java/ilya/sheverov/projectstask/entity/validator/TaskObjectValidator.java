package ilya.sheverov.projectstask.entity.validator;


import ilya.sheverov.projectstask.database.utils.TableRulesValidator;
import ilya.sheverov.projectstask.entity.Task;
import ilya.sheverov.projectstask.exception.ObjectValidatorException;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Component("taskObjectValidator")
@Scope("singleton")
public final class TaskObjectValidator {

    public static final Integer MAX_VALUE_ID = 2147483647;
    public static final Integer MAX_LENGTH_NAME = 120;
    public static final Integer MAX_VALUE_VOLUME_OF_WORK = 32767;
    public static final Integer MAX_VALUE_PERSON_ID = 2147483647;

    public static final String[] STATUS_VALUES = {"Не начата", "В процессе", "Завершена", "Отложена"};

    private Set<String> invalidFieldsNames;

    private int invalidFieldsCount = 0;

    public void validateTask(Task task) {
        validateId(task.getId());
        validateName(task.getName());
        validateVolumeOfWork(task.getVolumeOfWorkInHours());
        validateTimeInterval(task);
        validateStatus(task.getStatus());
        validatePersonId(task.getPersonId());
    }

    public void validateId(Integer taskId) {

        if (taskId != null) {
            if (!TableRulesValidator.compareWithTheMaximumNumber(MAX_VALUE_ID, taskId)) {
                setInvalidFieldName("id");
            }
        }
    }


    public void validateName(String taskName) {
        if (taskName == null) {
            setInvalidFieldName("name");
        }
        if (!TableRulesValidator.checkTheStringLength(MAX_LENGTH_NAME, taskName)) {
            setInvalidFieldName("name");
        }
    }

    /**
     * Проверяет, что поле volumeOfWorkInHours имеет допустимое значение.
     *
     * @param volumeOfWork поле, которое проверяют.
     * @throws ObjectValidatorException если поле имеет недопустимое значение.
     */
    public void validateVolumeOfWork(Integer volumeOfWork) {
        if (volumeOfWork != null) {
            if (!TableRulesValidator.compareWithTheMaximumNumber(MAX_VALUE_VOLUME_OF_WORK, volumeOfWork)) {
                setInvalidFieldName("id");
            }
        }
    }

    /**
     * Проверяет что дата и время начала и конца задачи соответствуют правилам приложения.
     *
     * @param task задача, поля которой проверяются.
     * @throws ObjectValidatorException если поля задачи {@code startDate} и {@code DueDate} не соответствуют условиям.
     */
    public void validateTimeInterval(Task task) {
        Timestamp startDate = task.getStartDate();
        Timestamp dueDate = task.getDueDate();
        if (startDate == null && dueDate != null) {
            setInvalidFieldName("dueDate");
        }
        if (startDate != null && dueDate != null) {
            if (startDate.after(dueDate)) {
                setInvalidFieldName("dueDate");
            }
        }
    }

    /**
     * Проверяет, что статус задачи является допустимым.
     *
     * @param status статус, который проходит проверку.
     * @throws ObjectValidatorException если такого статуса не существует.
     */
    public void validateStatus(String status) {
        Boolean b = Arrays.stream(STATUS_VALUES).noneMatch(s -> s.equals(status));
        if (b) {
            setInvalidFieldName("status");
        }
    }

    /**
     * Проверяет что поле personId имеет допустимое значение.
     *
     * @param personId поле для проверки.
     * @throws ObjectValidatorException если поле personId содержит недопустимое значение.
     */
    public void validatePersonId(Integer personId) {
        if (personId != null) {
            if (!TableRulesValidator.compareWithTheMaximumNumber(MAX_VALUE_PERSON_ID, personId)) {
                setInvalidFieldName("personId");
            }
        }
    }

    private void setInvalidFieldName(String invalidFieldName) {
        if (invalidFieldsNames == null) {
            invalidFieldsNames = new HashSet<>();
        }
        invalidFieldsNames.add(invalidFieldName);
        invalidFieldsCount++;
    }

    public Set<String> getInvalidFieldsNames() {
        return invalidFieldsNames;
    }

    public int getInvalidFieldsCount() {
        return invalidFieldsCount;
    }
}
