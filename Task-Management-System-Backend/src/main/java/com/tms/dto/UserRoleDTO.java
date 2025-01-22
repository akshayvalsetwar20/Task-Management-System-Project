package com.tms.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class UserRoleDTO {
	
	@NotNull(message = "UserRole ID cannot be null")
	private int userRoleId;

	@NotBlank(message = "Role name is mandatory")
	@Size(min = 3, max = 50, message = "Role name must be between 3 and 50 characters")
	private String roleName;

	public UserRoleDTO() {
		super();
	}
	
    public UserRoleDTO(int userRoleId, String roleName) {
		super();
		this.userRoleId = userRoleId;
		this.roleName = roleName;
	}
    
	// Getters and setters
    
    public int getUserRoleId() {
        return userRoleId;
    }

    public void setUserRoleId(int userRoleId) {
        this.userRoleId = userRoleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

}
