package com.tms.controller;

import com.tms.dto.ApiResponse;
import com.tms.dto.AttachmentDTO;
import com.tms.service.AttachmentFrontendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;

@Controller
@RequestMapping("/admin/dashboard/attachments")
public class AttachmentFrontendController {

    @Autowired
    private AttachmentFrontendService attachmentFrontendService;

    // Display the list of attachments
    @GetMapping("/all")
    public String getAllAttachments(Model model) {
        List<AttachmentDTO> attachments = attachmentFrontendService.getAllAttachments();
        if (attachments != null && !attachments.isEmpty()) {
            // Sort the attachments by attachmentID
            attachments.sort(Comparator.comparing(AttachmentDTO::getAttachmentID));
            model.addAttribute("attachments", attachments);
        } else {
            model.addAttribute("message", "No attachments found.");
        }
        return "attachment-list"; // Return the view for the attachment list
    }
    
    @GetMapping("/")
    public String getHomePgae() {
    	return "layout";
    }



    // View an individual attachment (this is now disabled)
    @GetMapping("/{attachmentId}")
    public String getAttachmentById(@PathVariable("attachmentId") Integer attachmentId, Model model) {
        model.addAttribute("message", "Attachment details view is disabled.");
        return "attachment-list"; // Redirect to the list page instead of showing attachment details
    }

    // Add an attachment (GET form for creating a new attachment)
    @GetMapping("/add")
    public String showAddAttachmentForm(Model model) {
        model.addAttribute("attachmentDTO", new AttachmentDTO());
        return "add-attachment";
    }

    // Handle form submission to add an attachment (POST request)
    @PostMapping("/add")
    public String addAttachment(@ModelAttribute AttachmentDTO attachmentDTO, Model model) {
        ApiResponse response = attachmentFrontendService.addAttachment(attachmentDTO);
        if ("POSTSUCCESS".equals(response.getCode())) {
            model.addAttribute("message", "Attachment added successfully.");
            return "redirect:/admin/dashboard/attachments/all"; // Redirect back to the list
        } else {
            model.addAttribute("message", response.getMessage());
            return "add-attachment"; // Return to the form if adding failed
        }
    }

    // Edit an attachment
    @GetMapping("/edit/{attachmentId}")
    public String editAttachment(@PathVariable("attachmentId") Integer attachmentId, Model model) {
        AttachmentDTO attachment = attachmentFrontendService.getAttachmentById(attachmentId);
        if (attachment != null) {
            model.addAttribute("attachmentDTO", attachment); // Ensure this line is executed
            return "edit-attachment";
        } else {
            model.addAttribute("message", "Attachment not found or already deleted.");
            return "error"; // Use a proper error page if needed
        }
    }

    // Update an attachment
    @PostMapping("/edit/{attachmentId}")
    public String updateAttachment(@PathVariable("attachmentId") Integer attachmentId,
                                   @ModelAttribute AttachmentDTO attachmentDTO, Model model) {
        ApiResponse response = attachmentFrontendService.updateAttachment(attachmentId, attachmentDTO);
        model.addAttribute("message", response.getMessage());
        return "redirect:/admin/dashboard/attachments/all"; // Redirect to the list after update
    }

    // Soft delete an attachment
    @GetMapping("/delete/{attachmentId}")
    public String deleteAttachment(@PathVariable("attachmentId") Integer attachmentId, Model model) {
        ApiResponse response = attachmentFrontendService.deleteAttachment(attachmentId);
        model.addAttribute("message", response.getMessage());
        return "redirect:/admin/dashboard/attachments/all"; // Redirect to the list after deletion
    }
}



