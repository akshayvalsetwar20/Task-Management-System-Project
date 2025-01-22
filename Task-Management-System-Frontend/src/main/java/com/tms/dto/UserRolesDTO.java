package com.tms.dto;

public class UserRolesDTO {

	private Integer userId;
	private Integer userRoleId;
	private String username;
	private String roleName;

	// Default constructor
	public UserRolesDTO() {
	}

	// Constructor using fields
	public UserRolesDTO(Integer userId, Integer userRoleId, String username, String roleName) {
		this.userId = userId;
		this.userRoleId = userRoleId;
		this.username = username;
		this.roleName = roleName;
	}



	// Getters and setters
	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getUserRoleId() {
		return userRoleId;
	}

	public void setUserRoleId(Integer userRoleId) {
		this.userRoleId = userRoleId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
}
