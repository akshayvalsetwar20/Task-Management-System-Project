package com.tms.service;

import java.util.List;

import com.tms.dto.UserRoleDTO;

public interface UserRoleServiceImpl {
	
	void saveUserRole(UserRoleDTO userRoleDTO);

    List<UserRoleDTO> getAllUserRoles();

    UserRoleDTO getUserRoleById(int userRoleId);

    void updateUserRole(int userRoleId, UserRoleDTO updatedRoleDTO);

    void deleteUserRole(int userRoleId);

}
