package com.tms.controllertesting;

import com.tms.controller.UserRolesController;
import com.tms.dto.ApiResponse;
import com.tms.dto.UserRolesDTO;
import com.tms.service.UserRolesService;
import com.tms.exception.UserNotFoundException;
import com.tms.exception.UserRoleAlreadyExistException;
import com.tms.exception.UserRoleDoesNotExist;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class UserRolesControllerTest {

    @InjectMocks
    private UserRolesController userRolesController;

    @Mock
    private UserRolesService userRolesService;

    private UserRolesDTO userRolesDTO;

    @BeforeEach
    public void setUp() {
        userRolesDTO = new UserRolesDTO(1, 2, "testUser", "ADMIN");
    }

    @Test
    public void testAssignRoleToUser_Success() {
        // Arrange
        doNothing().when(userRolesService).assignRoleToUser(anyInt(), anyInt());

        // Act
        ResponseEntity<ApiResponse> response = userRolesController.assignRoleToUser(userRolesDTO);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("POSTSUCCESS", response.getBody().getCode());
        assertEquals("User role assigned successfully", response.getBody().getMessage());

        // Verify service method is called
        verify(userRolesService, times(1)).assignRoleToUser(userRolesDTO.getUserId(), userRolesDTO.getUserRoleId());
    }

    @Test
    public void testAssignRoleToUser_Failure_UserIdOrUserRoleIdNull() {
        // Arrange
        userRolesDTO.setUserId(null);
        userRolesDTO.setUserRoleId(null);

        // Act
        ResponseEntity<ApiResponse> response = userRolesController.assignRoleToUser(userRolesDTO);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("ERROR", response.getBody().getCode());
        assertEquals("User ID and User Role ID must not be null", response.getBody().getMessage());
    }

    @Test
    public void testGetAllUserRoles_Success() {
        // Arrange
        List<UserRolesDTO> userRolesDTOList = Arrays.asList(userRolesDTO);
        when(userRolesService.getAllUserRoles()).thenReturn(userRolesDTOList);

        // Act
        List<UserRolesDTO> result = userRolesController.getAllUserRoles();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(userRolesDTO.getUsername(), result.get(0).getUsername());
        assertEquals(userRolesDTO.getRoleName(), result.get(0).getRoleName());
        
        // Verify service method is called
        verify(userRolesService, times(1)).getAllUserRoles();
    }

    @Test
    public void testGetUserRolesByUserId_Success() {
        // Arrange
        List<UserRolesDTO> userRolesDTOList = Arrays.asList(userRolesDTO);
        when(userRolesService.getUserRolesByUserId(anyInt())).thenReturn(userRolesDTOList);

        // Act
        List<UserRolesDTO> result = userRolesController.getUserRolesByUserId(1);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(userRolesDTO.getUsername(), result.get(0).getUsername());
        assertEquals(userRolesDTO.getRoleName(), result.get(0).getRoleName());
        
        // Verify service method is called
        verify(userRolesService, times(1)).getUserRolesByUserId(1);
    }

//    @Test
//    public void testRevokeUserRole_Failure_UserRoleDoesNotExist() {
//        // Arrange: Mock the service to throw UserRoleDoesNotExist exception
//        doThrow(new UserNotFoundException("DLTFAILS", "UserRole doesn't exist"))
//                .when(userRolesService).revokeUserRole(anyInt(), anyInt());
//
//        // Act: Call the controller method
//        ResponseEntity<ApiResponse> response = userRolesController.revokeUserRole(2, 1);
//
//        // Assert: Verify the response returned by the global exception handler
//        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
//        assertEquals("DLTFAILS", response.getBody().getAction());
//        assertEquals("UserRole doesn't exist", response.getBody().getMessage());
//
//        // Verify that the service method was invoked
//        verify(userRolesService, times(1)).revokeUserRole(2, 1);
//    }


}
