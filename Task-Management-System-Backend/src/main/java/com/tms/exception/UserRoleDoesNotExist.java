package com.tms.exception;

public class UserRoleDoesNotExist extends RuntimeException {
	private String action;
	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public UserRoleDoesNotExist(String msg) {
		super(msg);
	}
	
	public UserRoleDoesNotExist(String action,String msg) {
		super(msg);
		this.action=action;
	}
}
