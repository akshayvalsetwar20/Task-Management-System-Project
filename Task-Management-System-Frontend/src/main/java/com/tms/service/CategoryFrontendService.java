package com.tms.service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import com.tms.dto.CategoryDTO;

@Service
public class CategoryFrontendService {

    @Autowired
    RestTemplate restTemplate;
    
    private static final String BASE_URL = "http://localhost:9091/api/categories";

    
    public List<CategoryDTO> getAllCategories() {
        CategoryDTO[] categoryList = restTemplate.getForObject(BASE_URL + "/all", CategoryDTO[].class);
        if (categoryList != null) {
            return Arrays.asList(categoryList);
        }
        return Collections.emptyList(); 
    }


    
    public CategoryDTO getCategoryById(Integer categoryId) {
        try {
            
            System.out.println("Fetching category with ID: " + categoryId);
            
            // Send request to the API
            CategoryDTO category = restTemplate.getForObject(BASE_URL + "/" + categoryId, CategoryDTO.class);
            
            if (category == null) {
                System.out.println("Category not found for ID: " + categoryId);
            }
            return category; 
        } catch (Exception e) {
            e.printStackTrace();  
            return null;  
        }
    
    
    }  
    
    public String createCategory(CategoryDTO categoryDTO) {
        try {
            restTemplate.postForObject(BASE_URL + "/post", categoryDTO, CategoryDTO.class);
            return "{\"code\": \"POSTSUCCESS\", \"message\": \"Category added successfully\"}";
        } catch (Exception e) {
            return "{\"code\": \"ADDFAILS\", \"message\": \"Category already exists or error occurred\"}";
        }
    }

    
    public String updateCategory(Integer categoryId, CategoryDTO categoryDTO) {
        try {
            restTemplate.put(BASE_URL + "/update/" + categoryId, categoryDTO);
            return "{\"code\": \"UPDATESUCCESS\", \"message\": \"Category updated successfully\"}";
        } catch (Exception e) {
            return "{\"code\": \"UPDTFAILS\", \"message\": \"Category not found or error occurred\"}";
        }
    }

   
    public String deleteCategory(Integer categoryId) {
        try {
            restTemplate.delete(BASE_URL + "/delete/" + categoryId);
            return "{\"code\": \"DELETESUCCESS\", \"message\": \"Category deleted successfully\"}";
        } catch (Exception e) {
            return "{\"code\": \"DLTFAILS\", \"message\": \"Category not found or error occurred\"}";
        }
    }

    
    public List<CategoryDTO> getCategoriesWithTaskCount() {
        try {
            CategoryDTO[] categoryList = restTemplate.getForObject(BASE_URL + "/task-count", CategoryDTO[].class);
            if (categoryList != null) {
                return Arrays.asList(categoryList);
            }
        } catch (Exception e) {
            return Collections.emptyList(); 
        }
        return Collections.emptyList();
    }


    public CategoryDTO searchCategoryById(Integer categoryId) {
        try {
            
            String url = BASE_URL + "/{categoryId}"; 
            CategoryDTO category = restTemplate.getForObject(url, CategoryDTO.class, categoryId);
            
            
            System.out.println("Fetched Category: " + category);
            
            return category; 
        } catch (Exception e) {
            
            System.out.println("Error fetching category by ID: " + e.getMessage());
            return null; 
        }
    }

  
}