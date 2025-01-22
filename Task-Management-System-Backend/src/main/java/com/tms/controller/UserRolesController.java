package com.tms.controller;

import com.tms.dto.ApiResponse;
import com.tms.dto.UserRolesDTO;
import com.tms.service.UserRolesService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/userroles")
public class UserRolesController {

    @Autowired
    private UserRolesService userRolesService;

    @PostMapping("/assign")
    public ResponseEntity<ApiResponse> assignRoleToUser(@Valid @RequestBody UserRolesDTO userRolesDTO) {
        // Check for validation errors explicitly
        if (userRolesDTO.getUserId() == null || userRolesDTO.getUserRoleId() == null) {
            ApiResponse apiResponse = new ApiResponse("ERROR", "User ID and User Role ID must not be null");
            return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
        }

        userRolesService.assignRoleToUser(userRolesDTO.getUserId(), userRolesDTO.getUserRoleId());

        ApiResponse apiResponse = new ApiResponse("POSTSUCCESS", "User role assigned successfully");
        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }


    @GetMapping("/all")
    public List<UserRolesDTO> getAllUserRoles() {
        return userRolesService.getAllUserRoles();
    }

    @GetMapping("/user/{userId}")
    public List<UserRolesDTO> getUserRolesByUserId(@PathVariable Integer userId) {
        return userRolesService.getUserRolesByUserId(userId);
    }

    @DeleteMapping("/revoke/{userRoleId}/{userId}")
    public ResponseEntity<ApiResponse> revokeUserRole(@PathVariable("userRoleId") Integer userRoleId,
            @PathVariable("userId") Integer userId) {
        userRolesService.revokeUserRole(userRoleId, userId);
        ApiResponse apiResponse = new ApiResponse("DELETESUCCESS", "UserRole deleted successfully");
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
}