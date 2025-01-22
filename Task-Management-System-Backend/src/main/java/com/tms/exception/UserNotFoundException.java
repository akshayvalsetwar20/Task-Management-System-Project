package com.tms.exception;

public class UserNotFoundException extends RuntimeException{
	
private String action; 
	
	public UserNotFoundException(String msg) {
		super(msg);
	}

	public UserNotFoundException(String action, String message) {
		super(message);
		this.action = action;
	}

	public String getAction() {
		return action;
	}

}
