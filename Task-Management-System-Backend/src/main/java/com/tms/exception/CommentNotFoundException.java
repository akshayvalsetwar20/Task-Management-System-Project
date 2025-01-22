package com.tms.exception;

public class CommentNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    // Constructor that accepts an error message
    public CommentNotFoundException(String message) {
        super(message);
    }

    // Optional: Constructor that accepts an error message and cause (another Throwable)
    public CommentNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
