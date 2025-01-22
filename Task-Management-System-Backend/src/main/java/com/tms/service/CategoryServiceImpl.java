package com.tms.service;

import java.util.List;
import java.util.Map;

import com.tms.dto.CategoryDTO;

public interface CategoryServiceImpl {
	
	List<CategoryDTO> getAllCategories();

    CategoryDTO getCategoryById(Integer categoryId);

    void createCategory(CategoryDTO categoryDTO);

    CategoryDTO updateCategory(Integer categoryId, CategoryDTO categoryDTO);

    void softDeleteCategory(Integer categoryId);

    Map<String, Integer> getCategoriesWithTaskCount();

    boolean categoryExists(String categoryName);

}
