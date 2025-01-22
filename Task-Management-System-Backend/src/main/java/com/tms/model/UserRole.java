package com.tms.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "userrole")
public class UserRole {
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "userroleid")
    private int userRoleId;

    @Column(nullable = false, name = "rolename")
    private String roleName;

    @Column(nullable = false, name = "is_deleted")
    private boolean isDeleted = false;


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
    @JsonIgnore
    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

}
