package ilya.sheverov.projectstask.validator;

import ilya.sheverov.projectstask.entity.Task;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.sql.Timestamp;
import java.util.Arrays;

public class TaskValidator implements Validator {

    public static final Integer MAX_VALUE_ID = 2147483647;
    public static final Integer MAX_LENGTH_NAME = 120;
    public static final Integer MAX_VALUE_VOLUME_OF_WORK = 32767;
    public static final Integer MAX_VALUE_PERSON_ID = 2147483647;

    public static final String[] STATUS_VALUES = {"Не начата", "В процессе", "Завершена", "Отложена"};

    @Override
    public boolean supports(Class<?> clazz) {
        return Task.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Task task = (Task) target;
        if (task.getId() != null) {
            if (task.getId() < 0) {
                errors.rejectValue("id", "id.negative");
            } else if (task.getId() > MAX_VALUE_ID) {
                errors.rejectValue("id", "id.too.big");
            }
        }
        ValidationUtils.rejectIfEmpty(errors, "name", "name.empty");
        if (task.getName() != null) {
            if (task.getName().length() > MAX_LENGTH_NAME) {
                errors.rejectValue("name", "name.too.big");
            }
        }
        if (task.getVolumeOfWorkInHours() != null) {
            if (task.getVolumeOfWorkInHours() < 0) {
                errors.rejectValue("volumeOfWorkInHours", "volumeOfWorkInHours.negative");
            } else if (task.getVolumeOfWorkInHours() > MAX_VALUE_VOLUME_OF_WORK) {
                errors.rejectValue("volumeOfWorkInHours", "volumeOfWorkInHours.too.big");
            }
        }
        Timestamp startDate = task.getStartDate();
        Timestamp dueDate = task.getDueDate();
        if (startDate == null && dueDate != null) {
            errors.rejectValue("dueDate", "dueDate.notAfter.startDate");
        }
        if (startDate != null && dueDate != null) {
            if (startDate.after(dueDate)) {
                errors.rejectValue("dueDate", "dueDate.notAfter.startDate");
            }
        }
        /*Boolean statusIsIncorrect = Arrays.stream(STATUS_VALUES).noneMatch(s -> s.equals(task.getStatus()));
        if (statusIsIncorrect) {
            errors.rejectValue("status", "status.notFound");
        }*/
        if (task.getPersonId() != null) {
            if (task.getPersonId() < 0) {
                errors.rejectValue("personId", "personId.negative");
            } else if (task.getPersonId() > MAX_VALUE_PERSON_ID) {
                errors.rejectValue("personId", "personId.too.big");
            }
        }
    }
}
