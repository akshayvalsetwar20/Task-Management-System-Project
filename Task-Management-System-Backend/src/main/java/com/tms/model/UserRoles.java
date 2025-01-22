package com.tms.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "userroles")
public class UserRoles {
	
	@EmbeddedId
	private UserRolesId userRolesId;
	@ManyToOne
	@JoinColumn(name = "userid", insertable = false, updatable = false)
	@JsonIgnore
	private User user;

	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}

	@ManyToOne
	@JoinColumn(name = "userroleid", insertable = false, updatable = false)
	@JsonIgnore
	private UserRole userrole;
	
	private boolean isDeleted=false;

	
	public UserRole getUserrole() {
		return userrole;
	}

	public void setUserrole(UserRole userrole) {
		this.userrole = userrole;
	}

	// Default constructor
	public UserRoles() {
	}

	// Constructor with fields
	public UserRoles(UserRolesId userRolesId) {
		this.userRolesId = userRolesId;
	}

	// Getters and setters
	public UserRolesId getUserRolesId() {
		return userRolesId;
	}

	public void setUserRolesId(UserRolesId userRolesId) {
		this.userRolesId = userRolesId;
	}
	
	public boolean isDeleted() {
		return isDeleted;
	}

	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

}
