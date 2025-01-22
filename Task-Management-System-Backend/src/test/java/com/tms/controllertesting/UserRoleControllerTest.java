package com.tms.controllertesting;

import com.tms.controller.UserRoleController;
import com.tms.dto.ApiResponse;
import com.tms.dto.UserRoleDTO;
import com.tms.service.UserRoleService;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserRoleControllerTest {

    @Mock
    private UserRoleService userRoleService;

    @InjectMocks
    private UserRoleController userRoleController;

    private UserRoleDTO validUserRoleDTO;
    private UserRoleDTO invalidUserRoleDTO;

    // Validator for checking DTO validation constraints
    private Validator validator;

    @BeforeEach
    public void setUp() {
        // Initialize DTOs for valid and invalid scenarios
        validUserRoleDTO = new UserRoleDTO(1, "Admin");
        invalidUserRoleDTO = new UserRoleDTO(0, "A"); // Invalid role name

        // Initialize the validator to check annotations
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testCreateUserRoleValid() {
        // Mock service call for saving user role
        doNothing().when(userRoleService).saveUserRole(any(UserRoleDTO.class));

        // Call controller method
        ResponseEntity<ApiResponse> response = userRoleController.createUserRole(validUserRoleDTO);

        // Assert the response
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("UserRole added successfully", response.getBody().getMessage());
    }


    @Test
    public void testGetAllUserRoles() {
        List<UserRoleDTO> userRoles = Arrays.asList(validUserRoleDTO);

        // Mock service call
        when(userRoleService.getAllUserRoles()).thenReturn(userRoles);

        // Call controller method
        ResponseEntity<List<UserRoleDTO>> response = userRoleController.getAllUserRoles();

        // Assert the response
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
    }

    @Test
    public void testGetUserRoleById() {
        // Mock the service call
        when(userRoleService.getUserRoleById(1)).thenReturn(validUserRoleDTO);

        // Call controller method
        ResponseEntity<UserRoleDTO> response = userRoleController.getUserRoleById(1);

        // Assert the response
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Admin", response.getBody().getRoleName());
    }

    @Test
    public void testUpdateUserRole() {
        // Mock service call
        doNothing().when(userRoleService).updateUserRole(anyInt(), any(UserRoleDTO.class));

        // Call controller method
        ResponseEntity<ApiResponse> response = userRoleController.updateUserRole(1, validUserRoleDTO);

        // Assert the response
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("UserRole updated successfully", response.getBody().getMessage());
    }

    @Test
    public void testDeleteUserRole() {
        // Mock service call
        doNothing().when(userRoleService).deleteUserRole(1);

        // Call controller method
        ResponseEntity<ApiResponse> response = userRoleController.deleteUserRole(1);

        // Assert the response
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("UserRole deleted successfully", response.getBody().getMessage());
    }
}

