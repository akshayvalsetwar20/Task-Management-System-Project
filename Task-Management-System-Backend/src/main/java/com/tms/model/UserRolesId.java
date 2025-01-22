package com.tms.model;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class UserRolesId implements Serializable{
	
	@Column(name = "userid")
    private Integer userId;

    @Column(name = "userroleid")
    private Integer userRoleId;

    // Default constructor
    public UserRolesId() {}

    // Constructor with fields
    public UserRolesId(Integer userId, Integer userRoleId) {
        this.userId = userId;
        this.userRoleId = userRoleId;
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

    // Override equals and hashCode for correct behavior in collections and databases
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserRolesId that = (UserRolesId) o;
        return Objects.equals(userId, that.userId) && Objects.equals(userRoleId, that.userRoleId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, userRoleId);
    }

}
