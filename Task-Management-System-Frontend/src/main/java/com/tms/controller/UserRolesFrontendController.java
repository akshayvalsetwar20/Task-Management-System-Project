//package com.tms.controller;
//
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//
//import com.tms.dto.UserRolesDTO;
//import com.tms.service.UserRolesAPIService;
//
//import java.util.List;
//
//@Controller
//@RequestMapping("/admin/dashboard/userroles")
//public class UserRolesController {
//
//    @Autowired
//    private UserRolesAPIService userRolesService;
//
//    // Get the list of all user roles and display them in a Thymeleaf page
//    @GetMapping("/all")
//    public String getAllUserRoles(Model model) {
//        List<UserRolesDTO> userRoles = userRolesService.getAllUserRoles();
//        model.addAttribute("userRoles", userRoles);
//        return "user-roles"; // Thymeleaf template name
//    }
//    
// // Search user roles by user ID
//    @GetMapping("/user")
//    public String getUserRolesByUserId(@RequestParam Integer userId, Model model) {
//        List<UserRolesDTO> userRoles = userRolesService.getUserRolesByUserId(userId);
//        model.addAttribute("userRoles", userRoles);
//        return "user-roles"; // Return the same template for displaying results
//    }
//
//    // Display the form to assign a role to a user
//    @GetMapping("/assign")
//    public String showAssignRoleForm(Model model) {
//        model.addAttribute("userRolesDTO", new UserRolesDTO());
//        return "assign-role"; // Thymeleaf template name for form
//    }
//
////     Handle the role assignment form submission
//    @PostMapping("/assign")
//    public String assignRoleToUser(@ModelAttribute UserRolesDTO userRolesDTO, Model model) {
//    	userRolesService.assignRoleToUser(userRolesDTO);
//        return "redirect:/admin/dashboard/userroles/all";
//    }
//
//    // Handle revoking a role
//    @GetMapping("/revoke/{userRoleId}/{userId}")
//    public String revokeUserRole(@PathVariable Integer userRoleId, @PathVariable Integer userId, Model model) {
//        userRolesService.revokeUserRole(userRoleId, userId);
////        model.addAttribute("apiResponse", apiResponse);
//        return "redirect:/admin/dashboard/userroles/all";
//    }
//}
package com.tms.controller;

import com.tms.dto.UserRolesDTO;
import com.tms.service.UserRolesFrontendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/dashboard/userroles")
public class UserRolesFrontendController {

    @Autowired
    private UserRolesFrontendService userRolesService;

    // Get the list of all user roles and display them in a Thymeleaf page
    @GetMapping("/all")
    public String getAllUserRoles(Model model) {
        try {
            List<UserRolesDTO> userRoles = userRolesService.getAllUserRoles();
            model.addAttribute("userRoles", userRoles);
            return "user-roles"; // Thymeleaf template name
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            return "user-roles"; // Return to the same page with the error message
        }
    }

    // Search user roles by user ID
    @GetMapping("/user")
    public String getUserRolesByUserId(@RequestParam Integer userId, Model model) {
        try {
            List<UserRolesDTO> userRoles = userRolesService.getUserRolesByUserId(userId);
            if (userRoles.isEmpty()) {
                model.addAttribute("error",  userId);
                return "user-roles"; // Return the same page with the error message
            }
            model.addAttribute("userRoles", userRoles);
            return "user-roles"; // Return the same template for displaying results
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            return "user-roles"; // Return the same page with the error message
        }
    }

    // Display the form to assign a role to a user
    @GetMapping("/assign")
    public String showAssignRoleForm(Model model) {
        model.addAttribute("userRolesDTO", new UserRolesDTO());
        return "assign-role"; // Thymeleaf template name for form
    }

    // Handle the role assignment form submission
    @PostMapping("/assign")
    public String assignRoleToUser(@ModelAttribute UserRolesDTO userRolesDTO, Model model) {
        try {
            userRolesService.assignRoleToUser(userRolesDTO);
            return "redirect:/admin/dashboard/userroles/all";
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            return "assign-role"; // Return to the same page with the error message
        }
    }

    // Handle revoking a role
    @GetMapping("/revoke/{userRoleId}/{userId}")
    public String revokeUserRole(@PathVariable Integer userRoleId, @PathVariable Integer userId, Model model) {
        try {
            userRolesService.revokeUserRole(userRoleId, userId);
            return "redirect:/admin/dashboard/userroles/all";
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            return "user-roles"; // Return to the same page with the error message
        }
    }
}
