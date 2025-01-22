//package com.tms.testing;
//
//
//import static org.hamcrest.CoreMatchers.any;
//import static org.mockito.Mockito.doNothing;
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//import java.util.List;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//
//import com.tms.controller.CategoryController;
//import com.tms.dto.CategoryDTO;
//import com.tms.exception.CategoryDoesNotExistException;
//import com.tms.service.CategoryService;
//
//class CategoryControllerTest {
//
//    @Mock
//    private CategoryService categoryService;
//
//    @InjectMocks
//    @Autowired
//    private CategoryController categoryController;
//
//    private MockMvc mockMvc;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//        mockMvc = MockMvcBuilders.standaloneSetup(categoryController).build();
//    }
//
//    @Test
//    void testGetAllCategories_Success() throws Exception {
//        // Arrange
//        CategoryDTO categoryDTO = new CategoryDTO(1, "Test Category");
//        when(categoryService.getAllCategories()).thenReturn(List.of(categoryDTO));
//
//        // Act & Assert
//        mockMvc.perform(get("/api/categories/all"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$[0].categoryName").value("Test Category"));
//    }
//
//    @Test
//    void testGetCategoryById_Success() throws Exception {
//        // Arrange
//        CategoryDTO categoryDTO = new CategoryDTO(1, "Test Category");
//        when(categoryService.getCategoryById(1)).thenReturn(categoryDTO);
//
//        // Act & Assert
//        mockMvc.perform(get("/api/categories/1"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.categoryName").value("Test Category"));
//    }
//
//    @Test
//    void testGetCategoryById_NotFound() throws Exception {
//        // Arrange
//        when(categoryService.getCategoryById(1)).thenThrow(new CategoryDoesNotExistException("Category does not exist"));
//
//        // Act & Assert
//        mockMvc.perform(get("/api/categories/1"))
//                .andExpect(status().isNotFound())
//                .andExpect(jsonPath("$.message").value("Category does not exist"));
//    }
//
////    @Test
////    void testCreateCategory_Success() throws Exception {
////        // Arrange
////        CategoryDTO categoryDTO = new CategoryDTO(null, "New Category");
////        doNothing().when(categoryService).createCategory(categoryDTO);
////
////        // Act & Assert
////        mockMvc.perform(post("/api/categories/post")
////                        .contentType("application/json")
////                        .content("{\"categoryName\": \"New Category\"}"))
////                .andExpect(status().isCreated())
////                .andExpect(jsonPath("$.message").value("Category created successfully"));
////    }
//    
//    @Test
//    void testCreateCategory_Success() throws Exception {
//        // Arrange: Prepare the CategoryDTO without categoryId
//        CategoryDTO categoryDTO = new CategoryDTO(null, "New Category");
//        doNothing().when(categoryService).createCategory((CategoryDTO) any(CategoryDTO.class));  // Mocking service call
//
//        // Act & Assert
//        mockMvc.perform(post("/api/categories/post")
//                        .contentType("application/json")
//                        .content("{\"categoryName\": \"New Category\"}"))
//                .andExpect(status().isCreated())
//                .andExpect(jsonPath("$.message").value("Category created successfully"));
//    }
//
//
//    @Test
//    void testCreateCategory_Conflict() throws Exception {
//        // Arrange
//        CategoryDTO categoryDTO = new CategoryDTO(null, "Existing Category");
//        when(categoryService.categoryExists("Existing Category")).thenReturn(true);
//
//        // Act & Assert
//        mockMvc.perform(post("/api/categories/post")
//                        .contentType("application/json")
//                        .content("{\"categoryName\": \"Existing Category\"}"))
//                .andExpect(status().isConflict())
//                .andExpect(jsonPath("$.message").value("Category already exists"));
//    }
//
////    @Test
////    void testUpdateCategory_Success() throws Exception {
////        // Arrange
////        CategoryDTO categoryDTO = new CategoryDTO(1, "Updated Category");
////        when(categoryService.updateCategory(1, categoryDTO)).thenReturn(categoryDTO);
////
////        // Act & Assert
////        mockMvc.perform(put("/api/categories/update/1")
////                        .contentType("application/json")
////                        .content("{\"categoryName\": \"Updated Category\"}"))
////                .andExpect(status().isOk())
////                .andExpect(jsonPath("$.message").value("Category updated successfully"));
////    }
//
//    @Test
//    void testUpdateCategory_Success() throws Exception {
//        // Arrange: Prepare the CategoryDTO with a valid categoryId
//        CategoryDTO categoryDTO = new CategoryDTO(1, "Updated Category");
//        when(categoryService.updateCategory(eq(1), (CategoryDTO) any(CategoryDTO.class))).thenReturn(categoryDTO);
//
//        // Act & Assert
//        mockMvc.perform(put("/api/categories/update/1")
//                        .contentType("application/json")
//                        .content("{\"categoryName\": \"Updated Category\"}"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.message").value("Category updated successfully"));
//    }
//
//    
//    private Integer eq(int i) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Test
//    void testDeleteCategory_Success() throws Exception {
//        // Arrange
//        doNothing().when(categoryService).softDeleteCategory(1);
//
//        // Act & Assert
//        mockMvc.perform(delete("/api/categories/delete/1"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.message").value("Category marked as deleted successfully"));
//    }
//}
//
package com.tms.controllertesting;

import com.tms.controller.CategoryController;
import com.tms.dto.ApiResponse;
import com.tms.dto.CategoryDTO;
import com.tms.exception.CategoryAlreadyExistException;
import com.tms.exception.CategoryDoesNotExistException;
import com.tms.service.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

class CategoryControllerTest {

    @InjectMocks
    private CategoryController categoryController;

    @Mock
    private CategoryService categoryService;

    private CategoryDTO categoryDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        categoryDTO = new CategoryDTO();
        categoryDTO.setCategoryId(1);
        categoryDTO.setCategoryName("New Category");
    }

    @Test
    void getAllCategories_Success() {
        List<CategoryDTO> categories = Collections.singletonList(categoryDTO);
        when(categoryService.getAllCategories()).thenReturn(categories);

        ResponseEntity<Object> response = categoryController.getAllCategories();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(categories, response.getBody());
    }

    @Test
    void getAllCategories_Error() {
        when(categoryService.getAllCategories()).thenThrow(new RuntimeException("Database error"));

        ResponseEntity<Object> response = categoryController.getAllCategories();

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("{ \"code\": \"GETALLFAILS\", \"message\": \"Database error\" }", response.getBody());
    }

    @Test
    void getCategoryById_Success() {
        when(categoryService.getCategoryById(1)).thenReturn(categoryDTO);

        ResponseEntity<Object> response = categoryController.getCategoryById(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(categoryDTO, response.getBody());
    }

    @Test
    void getCategoryById_NotFound() {
        when(categoryService.getCategoryById(1)).thenThrow(new CategoryDoesNotExistException("Category not found"));

        ResponseEntity<Object> response = categoryController.getCategoryById(1);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("{ \"code\": \"GETFAILS\", \"message\": \"Category not found\" }", response.getBody());
    }

    @Test
    void createCategory_AlreadyExists() {
        when(categoryService.categoryExists("New Category")).thenReturn(true);

        ResponseEntity<Object> response = categoryController.createCategory(categoryDTO);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("{ \"code\": \"ADDFAILS\", \"message\": \"Category already exists\" }", response.getBody());
    }


    void updateCategory_Success() {
        when(categoryService.updateCategory(anyInt(), any(CategoryDTO.class))).thenReturn(categoryDTO);

        ResponseEntity<Object> response = categoryController.updateCategory(1, categoryDTO);

        String expectedResponse = "{ \"code\": \"UPDATESUCCESS\", \"message\": \"Category updated successfully\", " +
                "\"data\": { \"categoryId\": 1, \"categoryName\": \"" + categoryDTO.getCategoryName() + "\" } }";

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedResponse, response.getBody());
    }





    @Test
    void updateCategory_NotFound() {
         when(categoryService.updateCategory(anyInt(), any(CategoryDTO.class))).thenThrow(new CategoryDoesNotExistException("Category not found"));

         ResponseEntity<Object> response = categoryController.updateCategory(1, categoryDTO);

         assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
         assertEquals("{ \"code\": \"UPDATEFAILS\", \"message\": \"Category not found\" }", response.getBody());
     }

     @Test
     void deleteCategory_Success() {
         doNothing().when(categoryService).softDeleteCategory(1);

         ResponseEntity<Object> response = categoryController.deleteCategory(1);

         assertEquals(HttpStatus.OK, response.getStatusCode());
         assertEquals("{ \"code\": \"DELETESUCCESS\", \"message\": \"Category marked as deleted successfully\" }", response.getBody());
     }

     @Test
     void deleteCategory_NotFound() {
         doThrow(new CategoryDoesNotExistException("Category not found")).when(categoryService).softDeleteCategory(1);

         ResponseEntity<Object> response = categoryController.deleteCategory(1);

         assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
         assertEquals("{ \"code\": \"DLTFAILS\", \"message\": \"Category not found\" }", response.getBody());
     }
}
