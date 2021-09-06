package indi.mat.work.project.exception;

public class ErrorInputException extends RuntimeException {
    public ErrorInputException() {
        super();
    }

    public ErrorInputException(String message) {
        super(message);
    }

    public ErrorInputException(String message, Throwable cause) {
        super(message, cause);
    }

    public ErrorInputException(Throwable cause) {
        super(cause);
    }

    protected ErrorInputException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
