package ilya.sheverov.projectstask.exception;

public class OptimisticLockException extends Exception {
    public OptimisticLockException() {
    }

    public OptimisticLockException(String message) {
        super(message);
    }

    public OptimisticLockException(String message, Throwable cause) {
        super(message, cause);
    }

    public OptimisticLockException(Throwable cause) {
        super(cause);
    }
}
