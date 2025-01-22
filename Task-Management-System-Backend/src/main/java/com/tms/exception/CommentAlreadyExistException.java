package com.tms.exception;


public class CommentAlreadyExistException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public CommentAlreadyExistException(String message) {
        super(message);
    }
}

