package com.synoverge.leave.portal.Exceptions;

public class InActiveEmployeeException extends RuntimeException {
    public InActiveEmployeeException() {
    }

    public InActiveEmployeeException(String message) {
        super(message);
    }

    public InActiveEmployeeException(String message, Throwable cause) {
        super(message, cause);
    }

    public InActiveEmployeeException(Throwable cause) {
        super(cause);
    }

    public InActiveEmployeeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
