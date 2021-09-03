package indi.mat.work.project.exception;

public class NewEmployeeException extends RuntimeException {

    public NewEmployeeException(String message) {
        super(message);
    }

    public NewEmployeeException(String message, Throwable cause) {
        super(message, cause);
    }

    public NewEmployeeException(Throwable cause) {
        super(cause);
    }

    protected NewEmployeeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
