package com.tms.controller;
 
import java.util.List;
import java.util.Map;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
 
import com.tms.dto.CategoryDTO;
import com.tms.exception.CategoryAlreadyExistException;
import com.tms.exception.CategoryDoesNotExistException;
import com.tms.service.CategoryService;
 
import jakarta.validation.Valid;
 
@RestController
@RequestMapping("/api/categories")
@Validated
public class CategoryController {
 
    @Autowired
    private CategoryService categoryService;
 
   
    @GetMapping("/all")
    public ResponseEntity<Object> getAllCategories() {
        try {
            List<CategoryDTO> categories = categoryService.getAllCategories();
            return new ResponseEntity<>(categories, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("{ \"code\": \"GETALLFAILS\", \"message\": \"" + e.getMessage() + "\" }", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
 
    @GetMapping("/{categoryId}")
    public ResponseEntity<Object> getCategoryById(@PathVariable Integer categoryId) {
        try {
            CategoryDTO categoryDTO = categoryService.getCategoryById(categoryId);
            return new ResponseEntity<>(categoryDTO, HttpStatus.OK);
        } catch (CategoryDoesNotExistException e) {
            return new ResponseEntity<>("{ \"code\": \"GETFAILS\", \"message\": \"" + e.getMessage() + "\" }", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("{ \"code\": \"GETFAILS\", \"message\": \"" + e.getMessage() + "\" }", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
 
    @PostMapping("/post")
    public ResponseEntity<Object> createCategory(@Valid @RequestBody CategoryDTO categoryDTO) {
        try {
            if (categoryService.categoryExists(categoryDTO.getCategoryName())) {
                return new ResponseEntity<>("{ \"code\": \"ADDFAILS\", \"message\": \"Category already exists\" }", HttpStatus.CONFLICT);
            }
 
            categoryService.createCategory(categoryDTO);
            return new ResponseEntity<>("{ \"code\": \"POSTSUCCESS\", \"message\": \"Category created successfully\" }", HttpStatus.CREATED);
 
        } catch (CategoryAlreadyExistException e) {
            return new ResponseEntity<>("{ \"code\": \"ADDFAILS\", \"message\": \"" + e.getMessage() + "\" }", HttpStatus.CONFLICT);
        } catch (Exception e) {
            return new ResponseEntity<>("{ \"code\": \"ADDFAILS\", \"message\": \"" + e.getMessage() + "\" }", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
 
    @PutMapping("/update/{categoryId}")
    public ResponseEntity<Object> updateCategory(@PathVariable Integer categoryId, @Valid @RequestBody CategoryDTO categoryDTO) {
        try {
            categoryDTO.setCategoryId(categoryId);
            CategoryDTO updatedCategoryDTO = categoryService.updateCategory(categoryId, categoryDTO);
            return new ResponseEntity<>("{ \"code\": \"UPDATESUCCESS\", \"message\": \"Category updated successfully\", \"data\": " + updatedCategoryDTO + " }", HttpStatus.OK);
        } catch (CategoryDoesNotExistException e) {
            return new ResponseEntity<>("{ \"code\": \"UPDATEFAILS\", \"message\": \"" + e.getMessage() + "\" }", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("{ \"code\": \"UPDATEFAILS\", \"message\": \"" + e.getMessage() + "\" }", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
 
    @DeleteMapping("/delete/{categoryId}")
    public ResponseEntity<Object> deleteCategory(@PathVariable Integer categoryId) {
        try {
            categoryService.softDeleteCategory(categoryId);
            return new ResponseEntity<>("{ \"code\": \"DELETESUCCESS\", \"message\": \"Category marked as deleted successfully\" }", HttpStatus.OK);
        } catch (CategoryDoesNotExistException e) {
            return new ResponseEntity<>("{ \"code\": \"DLTFAILS\", \"message\": \"" + e.getMessage() + "\" }", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("{ \"code\": \"DLTFAILS\", \"message\": \"" + e.getMessage() + "\" }", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
 
    @GetMapping("/task-count")
    public ResponseEntity<Object> getCategoriesWithTaskCount() {
        try {
            Map<String, Integer> taskCount = categoryService.getCategoriesWithTaskCount();
            return new ResponseEntity<>(taskCount, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("{ \"code\": \"TASKCOUNTFAILS\", \"message\": \"" + e.getMessage() + "\" }", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
