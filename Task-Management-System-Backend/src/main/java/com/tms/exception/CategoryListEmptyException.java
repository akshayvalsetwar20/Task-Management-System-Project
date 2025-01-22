package com.tms.exception;

public class CategoryListEmptyException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public CategoryListEmptyException(String message) {
        super(message);
    }
}
