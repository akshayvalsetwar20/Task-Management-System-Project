package com.tms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.tms.dto.UserDTO;
import com.tms.service.AdminLoginService;

@Controller
public class AdminLoginController {

    @Autowired
    private AdminLoginService adminUserService;

    // Redirect to Admin Login Page
    @GetMapping("/")
    public String redirectToLogin() {
        return "redirect:/admin/login";
    }

    // Show Admin Login Page
    @GetMapping("/admin/login")
    public String showLoginPage() {
        return "admin-login";
    }

    @PostMapping("/admin/login")
    public String handleLogin(String username, String password, Model model) {
        UserDTO authenticatedUser = adminUserService.authenticateUser(username, password);

        if (authenticatedUser != null) {
            String roleResponse = adminUserService.getUserRoles(authenticatedUser.getUserID());
            if ("admin".equals(roleResponse)) {
                return "redirect:/admin/dashboard?admin=" + authenticatedUser.getFullName(); 
            } else {
                model.addAttribute("error", "Access Denied: You are not an Admin.");
                return "admin-login"; 
            }
        } else {
            model.addAttribute("error", "Invalid username or password");
            return "admin-login"; 
        }
    }

    // Show Admin Dashboard
    @GetMapping("/admin/dashboard")
    public String showDashboard(Model model) {
        return "admin-dashboard";
    }

    // if needed i will add this
    @GetMapping("/admin/logout")
    public String logout(Model model) {
        return "redirect:/admin/login";
    }
}
