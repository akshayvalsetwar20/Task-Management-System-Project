package com.tms.service;
 
import com.tms.dto.ApiResponse;
import com.tms.dto.UserDTO;
import com.tms.dto.UserRolesDTO;
import com.tms.exception.UserListEmptyException;
import com.tms.exception.UserNotFoundException;
import com.tms.exception.UserRoleAlreadyExistException;
import com.tms.exception.UserRoleDoesNotExist;
import com.tms.exception.UserRoleListEmptyException;
import com.tms.model.User;
import com.tms.model.UserRole;
import com.tms.model.UserRoles;
import com.tms.model.UserRolesId;
import com.tms.repository.UserRepository;
import com.tms.repository.UserRolesRepository;
import com.tms.repository.UserRoleRepository;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
 
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
 
@Service
public class UserRolesService implements UserRolesServiceImpl{
 
	@Autowired
	private UserRepository userRepository;
 
	@Autowired
	private UserRoleRepository userRoleRepository;
 
	@Autowired
	private UserRolesRepository userRolesRepository;
 
	public void assignRoleToUser(Integer userId, Integer userRoleId) {
 
		// Check if the user exists
		User user = userRepository.findById(userId).orElse(null);
		if (user == null || user.isDeleted()) {
			throw new UserNotFoundException("User doesn't exist");
		}
 
		// Check if the user role exists
		if (userRoleRepository.findById(userRoleId).isEmpty()) {
			throw new UserRoleDoesNotExist("UserRole doesn't exist");
		}
 
		// Check if the user already has this role
		UserRolesId userRolesId = new UserRolesId(userId, userRoleId);
		if (userRolesRepository.existsById(userRolesId)) {
			throw new UserRoleAlreadyExistException("User role already exists");
		}
 
		// Create a new UserRoles object and save it
		UserRoles userRoles = new UserRoles(userRolesId);
		userRoles.setUser(user);
		userRoles.setUserrole(userRoleRepository.findById(userRoleId).get());
		userRolesRepository.save(userRoles);
	}
 
	// Get all UserRoles that are not deleted
	public List<UserRolesDTO> getAllUserRoles() {
		List<UserRoles> userRolesList = userRolesRepository.findAll();
 
		List<UserRoles> allExistingUserRoles = userRolesList.stream().filter(userRole -> !userRole.isDeleted())
				.filter(userRole -> userRole.getUser() != null && !userRole.getUser().isDeleted())
				.collect(Collectors.toList());
 
		if (allExistingUserRoles.isEmpty()) {
			throw new UserRoleListEmptyException("UserRole list is empty");
		}
 
		return allExistingUserRoles.stream().map(userRole -> {
			User user = userRole.getUser();
			UserRole role = userRole.getUserrole();
 
			if (user != null && !user.isDeleted()) {
				return UserRolesDTO.fromUserRoles(user, role);
			}
			return null;
		}).filter(Objects::nonNull).collect(Collectors.toList());
	}
 
	public List<UserRolesDTO> getUserRolesByUserId(Integer userId) {
		List<UserRoles> userRoles = userRolesRepository.findByUserRolesId_UserId(userId);
 
		if (userRoles == null || userRoles.isEmpty()) {
			throw new UserRoleDoesNotExist("User role doesn't exist");
		}
 
		// Convert UserRoles to UserRolesDTO
		return userRoles.stream().map(userRolesObj -> {
			User user = userRolesObj.getUser();
			UserRole userRole = userRolesObj.getUserrole();
			return UserRolesDTO.fromUserRoles(user, userRole);
		}).collect(Collectors.toList());
	}
 
	public void revokeUserRole(Integer userRoleId, Integer userId) {
		Optional<UserRoles> userRoleOptional = userRolesRepository
				.findByUserRolesId_UserIdAndUserRolesId_UserRoleIdAndIsDeletedFalse(userId, userRoleId);
 
		if (userRoleOptional.isEmpty() || userRoleOptional.get().isDeleted()) {
			throw new UserNotFoundException("DLTFAILS", "UserRole doesn't exist");
		}
 
		UserRoles userRole = userRoleOptional.get();
		userRole.setDeleted(true);
		userRolesRepository.save(userRole);
	}
 
}