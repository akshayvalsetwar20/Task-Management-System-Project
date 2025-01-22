package com.tms.service;

import java.util.List;

import com.tms.dto.UserDTO;
import com.tms.dto.UserRolesDTO;
import com.tms.model.User;

public interface UserServiceImpl {
	
	void createUser(UserDTO userDTO);

    List<UserDTO> getAllUsers();

    UserDTO getUserById(int userId);

    List<UserDTO> getUsersByEmailDomain(String domain);

    Object searchUserByFullName(String name);

    List<UserDTO> getUsersWithMostTasks();

    User authenticateUser(String username, String password);

    List<UserDTO> getUsersWithCompletedTasks();

    void updateUser(int userId, UserDTO userDTO);

    void deleteUser(int userId);

}
