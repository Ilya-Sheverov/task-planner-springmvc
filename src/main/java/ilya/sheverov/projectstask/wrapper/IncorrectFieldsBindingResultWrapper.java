package ilya.sheverov.projectstask.wrapper;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class IncorrectFieldsBindingResultWrapper {
    private BindingResult bindingResult;

    public IncorrectFieldsBindingResultWrapper(BindingResult bindingResult) {
        this.bindingResult = bindingResult;
    }

    public Set<String> getNamesOfTheIncorrectFields() {
        Set<String> namesOfTheIncorrectFields = new HashSet<>();
        List<ObjectError> allErrors = bindingResult.getAllErrors();
        for (ObjectError allError : allErrors) {
            FieldError fieldError = ((FieldError) allError);
            String fieldName = fieldError.getField();
            namesOfTheIncorrectFields.add(fieldName);
        }
        return namesOfTheIncorrectFields;
    }
}
