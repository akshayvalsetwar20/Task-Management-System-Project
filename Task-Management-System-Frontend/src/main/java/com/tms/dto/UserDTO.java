package com.tms.dto;

import java.util.Objects;

public class UserDTO {

	private Integer userID;
	private String username;
	private String password;
	private String email;
	private String fullName;

	// Default constructor
	public UserDTO() {
	}

	// Constructor for creating a UserDTO from a User
	public UserDTO(Integer userID, String username, String password, String email, String fullName) {
		this.userID = userID;
		this.username = username;
		this.password = password;
		this.email = email;
		this.fullName = fullName;
	}

	// Getters and Setters
	public Integer getUserID() {
		return userID;
	}

	public void setUserID(Integer userID) {
		this.userID = userID;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	@Override
	public int hashCode() {
		return Objects.hash(email, fullName, password, userID, username);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserDTO other = (UserDTO) obj;
		return Objects.equals(email, other.email) && Objects.equals(fullName, other.fullName)
				&& Objects.equals(password, other.password) && Objects.equals(userID, other.userID)
				&& Objects.equals(username, other.username);
	}

	
}
