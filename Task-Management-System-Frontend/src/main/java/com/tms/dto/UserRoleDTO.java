package com.tms.dto;

public class UserRoleDTO {

    private int userRoleId;
    private String roleName;

    // Default constructor
    public UserRoleDTO() {}

    // Parameterized constructor
    public UserRoleDTO(int userRoleId, String roleName) {
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
