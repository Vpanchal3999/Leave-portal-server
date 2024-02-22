package com.synoverge.leave.portal.Exceptions;

public class AlreadyExistUserException extends RuntimeException {

    public AlreadyExistUserException() {
    }

    public AlreadyExistUserException(String message) {
        super(message);
    }

    public AlreadyExistUserException(String message, Throwable cause) {
        super(message, cause);
    }

    public AlreadyExistUserException(Throwable cause) {
        super(cause);
    }

    public AlreadyExistUserException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
