package com.tms.exception;

public class UserRoleAlreadyExistException extends RuntimeException {
	
	public UserRoleAlreadyExistException(String msg) {
		super(msg);
	}

}
