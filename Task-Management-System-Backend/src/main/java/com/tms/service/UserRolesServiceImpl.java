package com.tms.service;

import java.util.List;

import com.tms.dto.UserRolesDTO;

public interface UserRolesServiceImpl {
	
	void assignRoleToUser(Integer userId, Integer userRoleId);

    List<UserRolesDTO> getAllUserRoles();

    List<UserRolesDTO> getUserRolesByUserId(Integer userId);

    void revokeUserRole(Integer userRoleId, Integer userId);

}
