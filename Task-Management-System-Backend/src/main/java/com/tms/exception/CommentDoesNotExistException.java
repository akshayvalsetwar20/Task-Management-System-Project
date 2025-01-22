package com.tms.exception;


public class CommentDoesNotExistException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public CommentDoesNotExistException(String message) {
        super(message);
    }
}

