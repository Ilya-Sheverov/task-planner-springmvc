package ilya.sheverov.projectstask.exception;

public class DataAccessObjectException extends RuntimeException {
    public DataAccessObjectException() {
    }

    public DataAccessObjectException(String message) {
        super(message);
    }

    public DataAccessObjectException(String message, Throwable cause) {
        super(message, cause);
    }

    public DataAccessObjectException(Throwable cause) {
        super(cause);
    }
}
