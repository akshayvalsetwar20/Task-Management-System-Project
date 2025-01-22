package com.tms.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tms.dto.ApiResponse;
import com.tms.dto.UserRoleDTO;
import com.tms.service.UserRoleService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/userrole")
public class UserRoleController {
	
	@Autowired
    private UserRoleService userRoleService;

    // Create a new UserRole
    @PostMapping("/post")
    public ResponseEntity<ApiResponse> createUserRole(@Valid @RequestBody UserRoleDTO userRoleDTO) {
        // Call the service to save the user role
        userRoleService.saveUserRole(userRoleDTO);
        ApiResponse apiResponse = new ApiResponse("POSTSUCCESS", "UserRole added successfully");
        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }

    // Get all UserRoles
    @GetMapping("/all")
    public ResponseEntity<List<UserRoleDTO>> getAllUserRoles() {
        List<UserRoleDTO> userRoles = userRoleService.getAllUserRoles();
        return new ResponseEntity<>(userRoles, HttpStatus.OK);
    }

    // Get UserRole by ID
    @GetMapping("/{userRoleId}")
    public ResponseEntity<UserRoleDTO> getUserRoleById(@PathVariable int userRoleId) {
        UserRoleDTO userRoleDTO = userRoleService.getUserRoleById(userRoleId);
        return new ResponseEntity<>(userRoleDTO, HttpStatus.OK);
    }

    // Update UserRole
    @PutMapping("/update/{userRoleId}")
    public ResponseEntity<ApiResponse> updateUserRole(@PathVariable int userRoleId, @Valid @RequestBody UserRoleDTO updatedRoleDTO) {
        userRoleService.updateUserRole(userRoleId, updatedRoleDTO);
        ApiResponse apiResponse = new ApiResponse("UPDATESUCCESS", "UserRole updated successfully");
        return ResponseEntity.ok(apiResponse);
    }

    // Delete UserRole
    @DeleteMapping("/delete/{userRoleId}")
    public ResponseEntity<ApiResponse> deleteUserRole(@PathVariable int userRoleId) {
        userRoleService.deleteUserRole(userRoleId);
        ApiResponse apiResponse = new ApiResponse("DELETESUCCESS", "UserRole deleted successfully");
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

}
