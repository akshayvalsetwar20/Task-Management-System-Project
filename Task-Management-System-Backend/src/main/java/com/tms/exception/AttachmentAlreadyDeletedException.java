package com.tms.exception;

public class AttachmentAlreadyDeletedException extends RuntimeException {
    public AttachmentAlreadyDeletedException(String message) {
        super(message);
    }
}

