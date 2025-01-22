package com.tms.exception;


public class CategoryDoesNotExistException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public CategoryDoesNotExistException(String message) {
        super(message);
    }
}

