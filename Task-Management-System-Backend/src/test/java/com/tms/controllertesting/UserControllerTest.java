package com.tms.controllertesting;
 
import com.tms.controller.UserController;
import com.tms.dto.ApiResponse;
import com.tms.dto.UserDTO;
import com.tms.service.UserService;
import com.tms.exception.UserAlreadyExistsException;
import com.tms.exception.UserNotFoundException;
import com.tms.model.User;
import com.tms.exception.UserListEmptyException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
 
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
 
import java.util.Collections;
import java.util.List;
import java.util.Optional;
 
import static org.junit.jupiter.api.Assertions.*;
 
@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
public class UserControllerTest {
 
	@InjectMocks
	private UserController userController;
 
	@Mock
	private UserService userService;
 
	private UserDTO validUserDTO;
 
	@BeforeEach
	public void setUp() {
		validUserDTO = new UserDTO(1, "john_doe", "password123", "john@example.com", "John Doe");
	}
 
	@Test
	public void createUser_ShouldReturnCreatedResponse_WhenUserIsValid() {
		// Arrange
		doNothing().when(userService).createUser(any(UserDTO.class));
 
		// Act
		ResponseEntity<ApiResponse> response = userController.createUser(validUserDTO);
 
		// Assert
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertEquals("POSTSUCCESS", response.getBody().getCode());
		assertEquals("User added successfully", response.getBody().getMessage());
	}
 
	@Test
	public void createUser_ShouldReturnConflict_WhenUserAlreadyExists() {
		// Arrange
		doThrow(new UserAlreadyExistsException("User already exists")).when(userService).createUser(any(UserDTO.class));
 
		// Act & Assert
		assertThrows(UserAlreadyExistsException.class, () -> userController.createUser(validUserDTO));
	}
 
	@Test
	public void getAllUsers_ShouldReturnUsersList_WhenUsersExist() {
		// Arrange
		List<UserDTO> userList = Collections.singletonList(validUserDTO);
		when(userService.getAllUsers()).thenReturn(userList);
 
		// Act
		List<UserDTO> users = userController.getAllUsers();
 
		// Assert
		assertNotNull(users);
		assertEquals(1, users.size());
	}
 
	@Test
	public void getAllUsers_ShouldReturnNotFound_WhenUserListIsEmpty() {
		// Arrange
		when(userService.getAllUsers()).thenThrow(new UserListEmptyException("User list is empty"));
 
		// Act & Assert
		assertThrows(UserListEmptyException.class, () -> userController.getAllUsers());
	}
 
	@Test
	public void getUserById_ShouldReturnUser_WhenUserExists() {
		// Arrange
		when(userService.getUserById(anyInt())).thenReturn(validUserDTO);
 
		// Act
		UserDTO user = userController.getUserById(1);
 
		// Assert
		assertNotNull(user);
		assertEquals("john_doe", user.getUsername());
	}
 
	@Test
	public void getUserById_ShouldReturnNotFound_WhenUserDoesNotExist() {
		// Arrange
		when(userService.getUserById(anyInt())).thenThrow(new UserNotFoundException("User doesn't exist"));
 
		// Act & Assert
		assertThrows(UserNotFoundException.class, () -> userController.getUserById(1));
	}
 
	@Test
	public void getUsersByEmailDomain_ShouldReturnUsers_WhenUsersExistWithDomain() {
		// Arrange
		List<UserDTO> userList = Collections.singletonList(validUserDTO);
		when(userService.getUsersByEmailDomain(any(String.class))).thenReturn(userList);
 
		// Act
		List<UserDTO> users = userController.getUsersByEmailDomain("example.com");
 
		// Assert
		assertNotNull(users);
		assertEquals(1, users.size());
	}
 
	@Test
	public void getUsersByEmailDomain_ShouldReturnNotFound_WhenNoUsersExistWithDomain() {
		// Arrange
		when(userService.getUsersByEmailDomain(any(String.class)))
				.thenThrow(new UserNotFoundException("User doesn't exist"));
 
		// Act & Assert
		assertThrows(UserNotFoundException.class, () -> userController.getUsersByEmailDomain("example.com"));
	}
 
	@Test
	public void getUsersByFullName_ShouldReturnUsers_WhenUsersExist() {
		// Arrange
		List<UserDTO> userList = Collections.singletonList(validUserDTO);
		when(userService.searchUserByFullName(any(String.class))).thenReturn(userList);
 
		// Act
		ResponseEntity<Object> response = userController.getUsersByFullName("John Doe");
 
		// Assert
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}
 
	@Test
	public void getUsersByFullName_ShouldReturnNotFound_WhenNoUsersExist() {
		// Arrange
		when(userService.searchUserByFullName(any(String.class)))
				.thenThrow(new UserNotFoundException("User doesn't exist"));
 
		// Act & Assert
		assertThrows(UserNotFoundException.class, () -> userController.getUsersByFullName("Unknown Name"));
	}
 
	@Test
	public void authenticateUser_ShouldReturnOk_WhenUserIsAuthenticated() {
	    // Arrange
	    User mockUser = new User();
	    mockUser.setUsername("john_doe");
	    mockUser.setPassword("password123");
	    
	    when(userService.authenticateUser(any(String.class), any(String.class))).thenReturn(mockUser);

	    // Act
	    ResponseEntity<User> response = userController.authenticateUser("john_doe", "password123");

	    // Assert
	    assertEquals(HttpStatus.OK, response.getStatusCode());
	    assertNotNull(response.getBody());
	    assertEquals("john_doe", response.getBody().getUsername());
	}

 
	@Test
	public void authenticateUser_ShouldReturnNotFound_WhenUserIsNotAuthenticated() {
		// Arrange
		doThrow(new UserNotFoundException("User doesn't exist")).when(userService).authenticateUser(any(String.class),
				any(String.class));
 
		// Act & Assert
		assertThrows(UserNotFoundException.class,
				() -> userController.authenticateUser("invalid_user", "wrong_password"));
	}
 
	@Test
	public void updateUser_ShouldReturnOk_WhenUserIsUpdated() {
		// Arrange
		doNothing().when(userService).updateUser(anyInt(), any(UserDTO.class));
 
		// Act
		ResponseEntity<ApiResponse> response = userController.updateUser(1, validUserDTO);
 
		// Assert
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals("UPDATESUCCESS", response.getBody().getCode());
		assertEquals("User updated successfully", response.getBody().getMessage());
	}
 
	@Test
	public void updateUser_ShouldReturnNotFound_WhenUserDoesNotExist() {
		// Arrange
		doThrow(new UserNotFoundException("UPDTFAILS", "User doesn't exist")).when(userService).updateUser(anyInt(),
				any(UserDTO.class));
 
		// Act & Assert
		assertThrows(UserNotFoundException.class, () -> userController.updateUser(1, validUserDTO));
	}
 
	@Test
	public void deleteUser_ShouldReturnOk_WhenUserIsDeleted() {
		// Arrange
		doNothing().when(userService).deleteUser(anyInt());
 
		// Act
		ResponseEntity<ApiResponse> response = userController.deleteUser(1);
 
		// Assert
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals("DELETESUCCESS", response.getBody().getCode());
		assertEquals("User deleted successfully", response.getBody().getMessage());
	}
 
	@Test
	public void deleteUser_ShouldReturnNotFound_WhenUserDoesNotExist() {
		// Arrange
		doThrow(new UserNotFoundException("DLTFAILS", "User doesn't exist")).when(userService).deleteUser(anyInt());
 
		// Act & Assert
		assertThrows(UserNotFoundException.class, () -> userController.deleteUser(1));
	}
}