package ilya.sheverov.projectstask.exception;

import java.util.HashMap;
import java.util.Map;

public class EntityHandlerException extends RuntimeException {
    private Map<String, String[]> illegalArgument = new HashMap<>();

    public EntityHandlerException() {
    }

    public EntityHandlerException(String message) {
        super(message);
    }

    public EntityHandlerException(String message, Throwable cause) {
        super(message, cause);
    }

    public EntityHandlerException(Throwable cause) {
        super(cause);
    }

    public EntityHandlerException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public Map<String, String[]> getIllegalArgument() {

        return illegalArgument;
    }

    public String getIllegalArgumentName() {
        return illegalArgument.keySet().iterator().next();
    }

    public String[] getIllegalArgumentValue() {
        return illegalArgument.values().iterator().next();
    }

    public void setIllegalArgument(String argumentName, String[] argumentValue) {
        illegalArgument.put(argumentName, argumentValue);
    }
}
