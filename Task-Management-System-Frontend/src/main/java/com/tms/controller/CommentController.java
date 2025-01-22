package com.tms.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tms.dto.CommentDTO;
import com.tms.service.CommentFrontendService;

@Controller
@RequestMapping("/admin/dashboard/comments")
public class CommentController {

    @Autowired
    CommentFrontendService commentService;

    
    @GetMapping("/all")
    public String getAllComments(Model model) {
        List<CommentDTO> comments = commentService.getAllComments();
        if (comments.isEmpty()) {
            model.addAttribute("message", "Comment list is empty");
        }
        model.addAttribute("comments", comments);
        return "comments"; // Return comments.html
    }

    
    @GetMapping("/create")
    public String showCreateCommentPage(Model model) {
        model.addAttribute("comment", new CommentDTO()); 
        return "comment-create"; 
    }

    
    @PostMapping("/save")
    public String createComment(@ModelAttribute("comment") CommentDTO commentDTO, Model model) {
        try {
           
            String response = commentService.createComment(commentDTO);
            model.addAttribute("message", response); 
            return "redirect:/admin/dashboard/comments/all"; 
        } catch (Exception e) {
            model.addAttribute("message", "Failed to add comment"); 
            return "comment-create"; 
        }
    }

    
    @GetMapping("/searchById")
    public String searchCommentById(@RequestParam("commentId") Integer commentId, Model model) {
        CommentDTO comment = commentService.getCommentById(commentId);
        if (comment != null) {
            model.addAttribute("comment", comment);
        } else {
            model.addAttribute("message", "Comment not found with ID: " + commentId);
        }
        return "comment-search"; 
    }

    
    @GetMapping("/edit/{commentId}")
    public String showEditCommentPage(@PathVariable("commentId") Integer commentId, Model model) {
        
        CommentDTO comment = commentService.getCommentById(commentId);

        
        if (comment == null) {
            model.addAttribute("message", "Invalid Comment ID! The comment does not exist.");
            return "comment-message"; // Show error message
        }

        
        model.addAttribute("comment", comment);
        return "comment-form"; 
    }

    
    @PostMapping("/update/{commentId}")
    public String updateComment(@PathVariable("commentId") Integer commentId, 
                                 @ModelAttribute("comment") CommentDTO commentDTO, 
                                 Model model) {
      
    	 try {
             
             String response = commentService.updateComment(commentId, commentDTO);
             model.addAttribute("message", response); 
             return "redirect:/admin/dashboard/comments/all"; 
         } catch (Exception e) {
             model.addAttribute("message", "Failed to update comment");
             return "comment-form"; 
         }
    }

    
    @GetMapping("/delete/{commentId}")
    public String deleteComment(@PathVariable("commentId") Integer commentId, Model model) {
        try {
            
            String response = commentService.deleteComment(commentId);
            model.addAttribute("message", response); 
            return "redirect:/admin/dashboard/comments/all"; 
        } catch (Exception e) {
            model.addAttribute("message", "Failed to delete comment");
            return "comments"; 
        }
    }
}
