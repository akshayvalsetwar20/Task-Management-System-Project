package com.tms.exception;


public class CommentListEmptyException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public CommentListEmptyException(String message) {
        super(message);
    }
}
