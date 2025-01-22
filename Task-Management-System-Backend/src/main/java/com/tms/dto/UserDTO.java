package com.tms.dto;
 
import com.tms.model.User;
 
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
 
public class UserDTO {
 
    private Integer userID;
 
    @NotEmpty(message = "Username is required")
    private String username;
 
    @NotEmpty(message = "Password is required")
    private String password;
 
    @Email(message = "Email should be valid")
    @NotEmpty(message = "Email is required")
    private String email;
 
    @NotEmpty(message = "Full name is required")
    private String fullName;
 
    // Default constructor
    public UserDTO() {}
 
    // Constructor for creating a UserDTO from a User
    public UserDTO(Integer userID, String username, String password, String email, String fullName) {
        this.userID = userID;
        this.username = username;
        this.password = password;
        this.email = email;
        this.fullName = fullName;
    }
 
    // Getters and Setters
    public Integer getUserID() {
        return userID;
    }
 
    public void setUserID(Integer userID) {
        this.userID = userID;
    }
 
    public String getUsername() {
        return username;
    }
 
    public void setUsername(String username) {
        this.username = username;
    }
 
    public String getPassword() {
        return password;
    }
 
    public void setPassword(String password) {
        this.password = password;
    }
 
    public String getEmail() {
        return email;
    }
 
    public void setEmail(String email) {
        this.email = email;
    }
 
    public String getFullName() {
        return fullName;
    }
 
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
 
    // Optional: A method to convert a User to a UserDTO
    public static UserDTO fromUser(User user) {
        return new UserDTO(user.getUserID(), user.getUsername(), user.getPassword(), user.getEmail(), user.getFullName());
    }
}