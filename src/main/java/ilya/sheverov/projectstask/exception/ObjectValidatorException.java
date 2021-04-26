package ilya.sheverov.projectstask.exception;

import java.util.HashMap;
import java.util.Map;

/**
 * Позваляет хранить в себе информацию о поле объекта, которое хранит не допустимое значение.
 */
public class ObjectValidatorException extends Exception {

    /**
     * Хранит в качестве ключа имя поля, значение которого недопустимо и само значение.
     */
    private Map<String, Object> invalidFieldValueInfo;

    /**
     * Получает название поля, в которое имеет недопустимое значение, и его значение.
     *
     * @param fieldName  название поля.
     * @param fieldValue значение поля.
     */
    public ObjectValidatorException(String fieldName, Object fieldValue) {
        invalidFieldValueInfo = new HashMap<>(1);
        invalidFieldValueInfo.put(fieldName, fieldValue);
    }

    public Map<String, Object> getIllegalArgumentInfo() {
        return invalidFieldValueInfo;
    }

    /**
     * Позволяет получить название поля в котором хранится недопустимое значение.
     *
     * @return название поля в котором хранится недопустимое значение.
     */
    public String getTheNameOfAFieldWithAnInvalidValue() {
        return invalidFieldValueInfo.keySet().iterator().next();
    }

    /**
     * Позволяет получить недопустимое значение поля.
     *
     * @return недопустимое значение поля.
     */
    public Object getInvalidValue() {
        return invalidFieldValueInfo.values().iterator().next();
    }
}
