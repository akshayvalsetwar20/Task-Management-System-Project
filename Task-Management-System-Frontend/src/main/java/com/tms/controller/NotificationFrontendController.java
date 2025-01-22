package com.tms.controller;

import com.tms.dto.ApiResponse;
import com.tms.dto.NotificationDTO;
import com.tms.service.NotificationFrontendService;

import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/dashboard/notifications")
public class NotificationFrontendController {

    @Autowired
    private NotificationFrontendService ns;

    @GetMapping("/all")
    public String getAllNotifications(Model model) {
        List<NotificationDTO> notifications = ns.getAllNotifications();
        if (notifications != null && !notifications.isEmpty()) {
            // Sort the notifications by notificationID
            notifications.sort(Comparator.comparing(NotificationDTO::getNotificationId));
            model.addAttribute("notifications", notifications);
        } else {
            model.addAttribute("message", "No notifications found.");
        }
        return "notifications"; // Return the view for the notification list
    }


    @GetMapping("/add")
    public String showAddNotificationForm(Model model) {
        // Add an empty NotificationDTO object to the model for form binding
        model.addAttribute("notificationDTO", new NotificationDTO());
        return "add-notification"; // Return the view for the "Add Notification" form
    }

    @PostMapping("/add")
    public String addNotification(@ModelAttribute NotificationDTO notificationDTO, Model model) {
        ApiResponse response = ns.addNotification(notificationDTO);
        if ("POSTSUCCESS".equals(response.getCode())) {
            // If the notification is successfully added, redirect to the notification list
            model.addAttribute("message", "Notification added successfully.");
            return "redirect:/notifications/all";
        } else {
            // If there was an error, show the form again with an error message
            model.addAttribute("message", response.getMessage());
            return "add-notification";
        }
    }


    @GetMapping("/update/{id}")
    public String showUpdateNotificationForm(@PathVariable Integer id, Model model) {
        model.addAttribute("notification", ns.getNotificationById(id).orElse(null));
        return "update-notification";
    }

    @PostMapping("/update/{id}")
    public String updateNotification(@PathVariable Integer id, NotificationDTO notificationDTO) {
        ns.updateNotification(id, notificationDTO);
        return "redirect:/admin/dashboard/notifications/all";
    }

    @GetMapping("/delete/{id}")
    public String deleteNotification(@PathVariable Integer id) {
        ns.deleteNotification(id);
        return "redirect:/admin/dashboard/notifications/all";
    }
}
