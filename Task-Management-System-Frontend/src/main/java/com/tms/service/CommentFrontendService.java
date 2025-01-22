package com.tms.service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.tms.dto.CommentDTO;

@Service
public class CommentFrontendService {

    @Autowired
    RestTemplate restTemplate;

    private static final String BASE_URL = "http://localhost:9091/api/comments"; 

   
    public List<CommentDTO> getAllComments() {
        try {
            CommentDTO[] commentList = restTemplate.getForObject(BASE_URL + "/all", CommentDTO[].class);
            if (commentList != null) {
                return Arrays.asList(commentList);
            }
        } catch (Exception e) {
            e.printStackTrace(); 
        }
        return Collections.emptyList(); 
    }

   
    public CommentDTO getCommentById(Integer commentId) {
        try {
            return restTemplate.getForObject(BASE_URL + "/{id}" , CommentDTO.class, commentId);
        } catch (Exception e) {
        	e.printStackTrace();
            return null; 
        }
    }


    public String createComment(CommentDTO commentDTO) {
        try {
            
            CommentDTO createdComment = restTemplate.postForObject(BASE_URL + "/post", commentDTO, CommentDTO.class);
            if (createdComment != null) {
                return "{\"code\": \"POSTSUCCESS\", \"message\": \"Comment added successfully\"}";
            } else {
                return "{\"code\": \"ADDFAILS\", \"message\": \"Failed to add comment (no response from API)\"}";
            }
        } catch (Exception e) {
            e.printStackTrace(); // Print the stack trace to log
            return "{\"code\": \"ADDFAILS\", \"message\": \"Error occurred: " + e.getMessage() + "\"}";
        }
    }
    
    public String updateComment(Integer commentId, CommentDTO commentDTO) {
        try {
            restTemplate.put(BASE_URL + "/update/" + commentId, commentDTO);
            return "{\"code\": \"UPDATESUCCESS\", \"message\": \"Comment updated successfully\"}";
        } catch (Exception e) {
            return "{\"code\": \"UPDTFAILS\", \"message\": \"Comment does not exist or error occurred\"}" + e.getMessage();
        }
    }

    
    public String deleteComment(Integer commentId) {
        try {
            restTemplate.delete(BASE_URL + "/delete/" + commentId);
            return "{\"code\": \"DELETESUCCESS\", \"message\": \"Comment deleted successfully\"}";
        } catch (Exception e) {
            return "{\"code\": \"DLTFAILS\", \"message\": \"Comment does not exist or error occurred\"}";
        }
    }
}
