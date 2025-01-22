package com.tms.exception;

public class TaskException extends RuntimeException {

    private final String errorCode;

    public TaskException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }
}

