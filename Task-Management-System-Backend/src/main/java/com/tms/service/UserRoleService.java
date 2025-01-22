package com.tms.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import com.tms.dto.UserRoleDTO;
import com.tms.exception.UserRoleNotFoundException;
import com.tms.model.UserRole;
import com.tms.repository.UserRoleRepository;
import org.springframework.stereotype.Service;

@Service
public class UserRoleService implements UserRoleServiceImpl{
	
	@Autowired
    private UserRoleRepository userRoleRepository;

    // Helper method to convert DTO to Entity
    private UserRole convertToEntity(UserRoleDTO dto) {
        UserRole userRole = new UserRole();
        userRole.setUserRoleId(dto.getUserRoleId());
        userRole.setRoleName(dto.getRoleName());
        return userRole;
    }

    // Helper method to convert Entity to DTO
    private UserRoleDTO convertToDTO(UserRole userRole) {
        return new UserRoleDTO(userRole.getUserRoleId(), userRole.getRoleName());
    }

    // Create a new UserRole
    public void saveUserRole(UserRoleDTO userRoleDTO) {
        // Check for duplicate role name
        userRoleRepository.findByRoleNameAndIsDeletedFalse(userRoleDTO.getRoleName())
                .ifPresent(existingRole -> {
                    throw new IllegalArgumentException("UserRole already exists");
                });

        UserRole userRole = convertToEntity(userRoleDTO);
        userRole.setDeleted(false); // Ensure the role is not marked as deleted
        userRoleRepository.save(userRole);
    }

    // Get all UserRoles excluding deleted ones
    public List<UserRoleDTO> getAllUserRoles() {
        List<UserRole> roles = userRoleRepository.findByIsDeletedFalse();
        if (roles.isEmpty()) {
            throw new UserRoleNotFoundException("No UserRoles found");
        }
        return roles.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    // Get a specific UserRole by ID
    public UserRoleDTO getUserRoleById(int userRoleId) {
        UserRole userRole = userRoleRepository.findByUserRoleIdAndIsDeletedFalse(userRoleId)
                .orElseThrow(() -> new UserRoleNotFoundException("UserRole not found or has been deleted"));
        return convertToDTO(userRole);
    }

    // Update an existing UserRole
    public void updateUserRole(int userRoleId, UserRoleDTO updatedRoleDTO) {
        UserRole existingRole = userRoleRepository.findByUserRoleIdAndIsDeletedFalse(userRoleId)
                .orElseThrow(() -> new UserRoleNotFoundException("UserRole not found or has been deleted"));

        existingRole.setRoleName(updatedRoleDTO.getRoleName());
        userRoleRepository.save(existingRole);
    }

    // Soft delete a UserRole
    public void deleteUserRole(int userRoleId) {
        UserRole existingRole = userRoleRepository.findByUserRoleIdAndIsDeletedFalse(userRoleId)
                .orElseThrow(() -> new UserRoleNotFoundException("UserRole not found or has been deleted"));

        existingRole.setDeleted(true); // Mark as deleted
        userRoleRepository.save(existingRole);
    }

}
