package com.tms.controller;

import com.tms.dto.UserRoleDTO;
import com.tms.service.UserRoleFrontendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/dashboard/userrole")
public class UserRoleFrontendController {

    @Autowired
    private UserRoleFrontendService userRoleService;

    // List all user roles
    @GetMapping("/list")
    public String getAllUserRoles(Model model) {
        List<UserRoleDTO> userRoles = userRoleService.getAllUserRoles();
        model.addAttribute("userRoles", userRoles);
        return "UserRoleList";  // Points to the Thymeleaf userRoleList.html page
    }

    // Show form for adding a new user role
    @GetMapping("/add")
    public String showAddUserRoleForm(Model model) {
        model.addAttribute("userRole", new UserRoleDTO());
        return "UserRoleCreate";  // Points to the Thymeleaf userRoleAdd.html page
    }

    // Save a new user role
    @PostMapping("/save")
    public String saveUserRole(@ModelAttribute("userRole") UserRoleDTO userRoleDTO) {
        userRoleService.saveUserRole(userRoleDTO);
        return "redirect:/admin/dashboard/userrole/list";  // Redirects to user roles list after saving
    }

    // Show form for editing an existing user role
    @GetMapping("/edit/{id}")
    public String editUserRole(@PathVariable("id") int userRoleId, Model model) {
        UserRoleDTO userRoleDTO = userRoleService.getUserRoleById(userRoleId);
        model.addAttribute("userRole", userRoleDTO);
        return "UserRoleEdit";  // Points to the Thymeleaf userRoleEdit.html page
    }

    // Update an existing user role
    @PostMapping("/update/{id}")
    public String updateUserRole(@PathVariable("id") int userRoleId, @ModelAttribute("userRole") UserRoleDTO updatedUserRoleDTO) {
        userRoleService.updateUserRole(userRoleId, updatedUserRoleDTO);
        return "redirect:/admin/dashboard/userrole/list";  // Redirects to user roles list after updating
    }
    
 // Search for a user role by ID
    @GetMapping("/search")
    public String searchUserRoleById(@RequestParam("query") int query, Model model) {
        try {
            UserRoleDTO userRole = userRoleService.getUserRoleById(query);
            model.addAttribute("userRole", userRole);
            return "UserRoleDetails"; // Show the details page for the user role
        } catch (Exception e) {
            model.addAttribute("error", "User Role not found for ID: " + query);
            return "UserRoleList"; // Return to the list page with an error message
        }
    }


    // Perform the deletion of a user role
    @GetMapping("/delete/{id}")
  public String deleteUserRole(@PathVariable("id") int id) {
      try {
          userRoleService.deleteUserRole(id); // Delete user role
          return "redirect:/admin/dashboard/userrole/list"; // Redirect to list page after deletion
      } catch (Exception e) {
          return "ErrorPage"; // Show error page if deletion fails
      }
  }
}
