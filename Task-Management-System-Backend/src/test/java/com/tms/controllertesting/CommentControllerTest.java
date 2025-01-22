//package com.tms.testing;
//
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.eq;
//import static org.mockito.Mockito.when;
//import static org.mockito.Mockito.doNothing;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//import java.time.LocalDateTime;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.stubbing.OngoingStubbing;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//
//import com.tms.controller.CommentController;
//import com.tms.dto.CommentDTO;
//import com.tms.exception.CommentAlreadyExistException;
//import com.tms.exception.CommentNotFoundException;
//import com.tms.service.CommentService;
//
//public class CommentControllerTest {
//
//    private MockMvc mockMvc;
//
//    @InjectMocks
//    private CommentController commentController;
//
//    @Mock
//    private CommentService commentService;
//
//    @BeforeEach
//    void setUp() {
//        mockMvc = MockMvcBuilders.standaloneSetup(commentController).build();
//    }
//
//    @Test
//    void testCreateComment_Success() throws Exception {
//        // Prepare the CommentDTO object
//        CommentDTO commentDTO = new CommentDTO(1, "New Comment", LocalDateTime.now(), 100, 200, false);
//
//        // Mock the service method (service does not return anything, so no return value)
//        doNothing().when(commentService).createComment(any(CommentDTO.class));
//
//        // Perform the request and assert the response
//        mockMvc.perform(post("/api/comments/post")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content("{\"text\": \"New Comment\", \"createdAt\": \"2025-01-01T12:00:00\", \"taskId\": 100, \"userId\": 200}"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.code").value("200"))
//                .andExpect(jsonPath("$.message").value("Comment added successfully"));
//    }
//
//    @Test
//    void testCreateComment_Fails_CommentAlreadyExist() throws Exception {
//        // Mock the service method to throw exception
//        Mockito.when(commentService.createComment(any(CommentDTO.class)))
//                .thenThrow(new CommentAlreadyExistException("Comment with this text already exists."));
//
//        // Perform the request and assert the response
//        mockMvc.perform(post("/api/comments/post")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content("{\"text\": \"Duplicate Comment\", \"createdAt\": \"2025-01-01T12:00:00\", \"taskId\": 100, \"userId\": 200}"))
//                .andExpect(status().isBadRequest())
//                .andExpect(jsonPath("$.code").value("400"))
//                .andExpect(jsonPath("$.message").value("Comment with this text already exists."));
//    }
//
//    private OngoingStubbing<CommentDTO> when(Object comment) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Test
//    void testUpdateComment_Success() throws Exception {
//        // Prepare the updated CommentDTO object
//        CommentDTO updatedCommentDTO = new CommentDTO(1, "Updated Comment", LocalDateTime.now(), 100, 200, false);
//
//        // Mock the service method to simulate update
//        when(commentService.updateComment(eq(1), any(CommentDTO.class)))
//                .thenReturn(updatedCommentDTO); // Return updated comment
//
//        // Perform the request and assert the response
//        mockMvc.perform(put("/api/comments/update/1")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content("{\"text\": \"Updated Comment\", \"createdAt\": \"2025-01-01T12:00:00\", \"taskId\": 100, \"userId\": 200}"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.code").value("200"))
//                .andExpect(jsonPath("$.message").value("Comment updated successfully"));
//    }
//
//    @Test
//    void testUpdateComment_Fails_CommentNotFound() throws Exception {
//        // Mock the service method to throw exception
//        when(commentService.updateComment(eq(1), any(CommentDTO.class)))
//                .thenThrow(new CommentNotFoundException("Comment not found for update"));
//
//        // Perform the request and assert the response
//        mockMvc.perform(put("/api/comments/update/1")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content("{\"text\": \"Updated Comment\", \"createdAt\": \"2025-01-01T12:00:00\", \"taskId\": 100, \"userId\": 200}"))
//                .andExpect(status().isNotFound())
//                .andExpect(jsonPath("$.code").value("404"))
//                .andExpect(jsonPath("$.message").value("Comment not found for update"));
//    }
//
//    @Test
//    void testDeleteComment_Success() throws Exception {
//        // Mock the service method to simulate successful delete
//        doNothing().when(commentService).softDeleteComment(1);
//
//        // Perform the request and assert the response
//        mockMvc.perform(delete("/api/comments/delete/1"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.code").value("200"))
//                .andExpect(jsonPath("$.message").value("Comment deleted successfully"));
//    }
//
//    @Test
//    void testDeleteComment_Fails_CommentNotFound() throws Exception {
//        // Mock the service method to throw exception
//        when(commentService.softDeleteComment(1))
//                .thenThrow(new CommentNotFoundException("Comment not found for deletion"));
//
//        // Perform the request and assert the response
//        mockMvc.perform(delete("/api/comments/delete/1"))
//                .andExpect(status().isNotFound())
//                .andExpect(jsonPath("$.code").value("404"))
//                .andExpect(jsonPath("$.message").value("Comment not found for deletion"));
//    }
//
//    @Test
//    void testGetCommentById_Success() throws Exception {
//        // Prepare the CommentDTO object
//        CommentDTO commentDTO = new CommentDTO(1, "Test Comment", LocalDateTime.now(), 100, 200, false);
//
//        // Mock the service method to simulate fetching comment by ID
//        when(commentService.getCommentById(1))
//                .thenReturn(commentDTO);
//
//        // Perform the request and assert the response
//        mockMvc.perform(get("/api/comments/1"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.commentId").value(1))
//                .andExpect(jsonPath("$.text").value("Test Comment"))
//                .andExpect(jsonPath("$.createdAt").value("2025-01-01T12:00:00"))
//                .andExpect(jsonPath("$.taskId").value(100))
//                .andExpect(jsonPath("$.userId").value(200))
//                .andExpect(jsonPath("$.deleted").value(false));
//    }
//
//    @Test
//    void testGetCommentById_Fails_CommentNotFound() throws Exception {
//        // Mock the service method to throw exception
//        when(commentService.getCommentById(1))
//                .thenThrow(new CommentNotFoundException("Comment not found"));
//
//        // Perform the request and assert the response
//        mockMvc.perform(get("/api/comments/1"))
//                .andExpect(status().isNotFound())
//                .andExpect(jsonPath("$.code").value("404"))
//                .andExpect(jsonPath("$.message").value("Comment not found"));
//    }
//}
package com.tms.controllertesting;

import com.tms.controller.CommentController;
import com.tms.dto.ApiResponse;
import com.tms.dto.CommentDTO;
import com.tms.exception.CommentAlreadyExistException;
import com.tms.exception.CommentNotFoundException;
import com.tms.service.CommentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class CommentControllerTest {

    @InjectMocks
    private CommentController commentController;

    @Mock
    private CommentService commentService;

    @Mock
    private BindingResult bindingResult;

    private CommentDTO commentDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        commentDTO = new CommentDTO();
        commentDTO.setCommentId(1);
        commentDTO.setText("This is a test comment");
    }

    @Test
    void createComment_Success() {
        when(bindingResult.hasErrors()).thenReturn(false);
        doNothing().when(commentService).createComment(any(CommentDTO.class));

        ResponseEntity<ApiResponse> response = commentController.createComment(commentDTO, bindingResult);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("POSTSUCCESS", response.getBody().getCode());
        assertEquals("Comment added successfully", response.getBody().getMessage());
    }

    @Test
    void createComment_ValidationFails() {
        // Mocking BindingResult to simulate validation errors
        when(bindingResult.hasErrors()).thenReturn(true);
        
        // Create a mock error object
        ObjectError error = new ObjectError("commentDTO", "Content must not be empty");
        
        // Return a list with one error
        when(bindingResult.getAllErrors()).thenReturn(List.of(error));

        ResponseEntity<ApiResponse> response = commentController.createComment(commentDTO, bindingResult);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("VALIDATIONFAILS", response.getBody().getCode());
        assertEquals("Content must not be empty", response.getBody().getMessage());
    }

    @Test
    void getAllComments_Success() {
        List<CommentDTO> comments = new ArrayList<>();
        comments.add(commentDTO);
        when(commentService.getAllComments()).thenReturn(comments);

        ResponseEntity<Object> response = commentController.getAllComments();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(comments, response.getBody());
    }

    @Test
    void getAllComments_EmptyList() {
        when(commentService.getAllComments()).thenReturn(new ArrayList<>());

        ResponseEntity<Object> response = commentController.getAllComments();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("GETALLFAILS", ((ApiResponse) response.getBody()).getCode());
        assertEquals("Comment list is empty", ((ApiResponse) response.getBody()).getMessage());
    }

    @Test
    void getCommentById_Success() {
        when(commentService.getCommentById(1)).thenReturn(commentDTO);

        ResponseEntity<Object> response = commentController.getCommentById(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(commentDTO, response.getBody());
    }

    @Test
    void getCommentById_NotFound() {
        when(commentService.getCommentById(1)).thenThrow(new CommentNotFoundException("Comment not found"));

        ResponseEntity<Object> response = commentController.getCommentById(1);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("GETFAILS", ((ApiResponse) response.getBody()).getCode());
    }

    @Test
    void updateComment_Success() {
        when(bindingResult.hasErrors()).thenReturn(false);
        doNothing().when(commentService).updateComment(anyInt(), any(CommentDTO.class));

        ResponseEntity<ApiResponse> response = commentController.updateComment(1, commentDTO, bindingResult);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("UPDATESUCCESS", response.getBody().getCode());
        assertEquals("Comment updated successfully", response.getBody().getMessage());
    }

    @Test
    void updateComment_NotFound() {
        when(bindingResult.hasErrors()).thenReturn(false);
        doThrow(new CommentNotFoundException("Comment not found")).when(commentService).updateComment(anyInt(), any(CommentDTO.class));

        ResponseEntity<ApiResponse> response = commentController.updateComment(1, commentDTO, bindingResult);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("UPDATEFAILS", ((ApiResponse) response.getBody()).getCode());
    }

    @Test
    void deleteComment_Success() {
        doNothing().when(commentService).softDeleteComment(1);

        ResponseEntity<ApiResponse> response = commentController.deleteComment(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("DELETESUCCESS", response.getBody().getCode());
        assertEquals("Comment deleted successfully", response.getBody().getMessage());
    }

    @Test
    void deleteComment_NotFound() {
        doThrow(new CommentNotFoundException("Comment not found for deletion")).when(commentService).softDeleteComment(1);

        ResponseEntity<ApiResponse> response = commentController.deleteComment(1);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("DLTFAILS", ((ApiResponse) response.getBody()).getCode());
    }
}
