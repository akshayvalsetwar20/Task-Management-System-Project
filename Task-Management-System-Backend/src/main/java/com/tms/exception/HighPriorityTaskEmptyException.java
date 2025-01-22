package com.tms.exception;

public class HighPriorityTaskEmptyException extends RuntimeException {
	public HighPriorityTaskEmptyException(String msg) {
		super(msg);
	}
}
