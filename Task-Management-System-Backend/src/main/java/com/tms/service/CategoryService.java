package com.tms.service;
 
import com.tms.dto.CategoryDTO;
import com.tms.exception.CategoryAlreadyExistException;
import com.tms.exception.CategoryDoesNotExistException;
import com.tms.model.Category;
import com.tms.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
 
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;
 
@Service
public class CategoryService implements CategoryServiceImpl{
 
    @Autowired
    private CategoryRepository categoryRepository;
 
    // Convert Category entity to CategoryDTO
    private CategoryDTO convertToDTO(Category category) {
        return new CategoryDTO(category.getCategoryId(), category.getCategoryName());
    }
 
    // Fetch all non-deleted categories and return as CategoryDTO list
    public List<CategoryDTO> getAllCategories() {
        List<Category> categories = categoryRepository.findAllByIsDeletedFalse();
        if (categories.isEmpty()) {
            throw new CategoryDoesNotExistException("No categories found");
        }
        // Convert the list of Category entities to CategoryDTOs
        return categories.stream()
                         .map(this::convertToDTO)
                         .collect(Collectors.toList());
    }
 
    // Fetch a category by ID and return as CategoryDTO
    public CategoryDTO getCategoryById(Integer categoryId) {
        Category category = categoryRepository.findByCategoryIdAndIsDeletedFalse(categoryId);
        if (category == null) {
            throw new CategoryDoesNotExistException("Category does not exist");
        }
        return convertToDTO(category);
    }
 
    // Create a new category from CategoryDTO
    public void createCategory(CategoryDTO categoryDTO) {
        if (categoryRepository.existsByCategoryNameAndIsDeletedFalse(categoryDTO.getCategoryName())) {
            throw new CategoryAlreadyExistException("Category already exists");
        }
 
        // Create Category entity from DTO
        Category category = new Category(categoryDTO.getCategoryName());
        categoryRepository.save(category);
    }
 
    // Update an existing category from CategoryDTO
    public CategoryDTO updateCategory(Integer categoryId, CategoryDTO categoryDTO) {
        Category existingCategory = categoryRepository.findByCategoryIdAndIsDeletedFalse(categoryId);
        if (existingCategory == null) {
            throw new CategoryDoesNotExistException("Category does not exist");
        }
 
        // Update the category details
        existingCategory.setCategoryName(categoryDTO.getCategoryName());
        Category updatedCategory = categoryRepository.save(existingCategory);
 
        return convertToDTO(updatedCategory);
    }
 
    // Soft delete a category
    public void softDeleteCategory(Integer categoryId) {
        Category category = categoryRepository.findByCategoryIdAndIsDeletedFalse(categoryId);
        if (category == null) {
            throw new CategoryDoesNotExistException("Category does not exist or already deleted");
        }
 
        // Mark the category as deleted
        category.setDeleted(true);
        categoryRepository.save(category);
    }
 
    // Fetch categories along with task count
    public Map<String, Integer> getCategoriesWithTaskCount() {
        List<Object[]> results = categoryRepository.findCategoriesWithTaskCount();
        Map<String, Integer> taskCountMap = new HashMap<>();
        for (Object[] row : results) {
            String categoryName = (String) row[0]; // category name
            Integer taskCount = ((Long) row[1]).intValue(); // task count
            taskCountMap.put(categoryName, taskCount);
        }
        return taskCountMap;
    }
 
    // Check if category exists by name
    public boolean categoryExists(String categoryName) {
        return categoryRepository.existsByCategoryNameAndIsDeletedFalse(categoryName);
    }
}