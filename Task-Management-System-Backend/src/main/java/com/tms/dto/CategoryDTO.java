package com.tms.dto;
 
import org.hibernate.sql.Update;
 
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
 
public class CategoryDTO {
 
	@NotNull(groups = Update.class)
    @NotNull(message = "Category ID cannot be null")
    private Integer categoryId;
 
    @NotEmpty(message = "Category Name cannot be empty")
    @Size(min = 2, max = 100, message = "Category Name must be between 2 and 100 characters")
    private String categoryName;
 
    // Default constructor
    public CategoryDTO() {
    }
 
    // Constructor for CategoryDTO
    public CategoryDTO(Integer categoryId, String categoryName) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
    }
 
    public Integer getCategoryId() {
        return categoryId;
    }
 
    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }
 
    public String getCategoryName() {
        return categoryName;
    }
 
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
 
    @Override
    public String toString() {
        return "CategoryDTO{" +
                "categoryId=" + categoryId +
                ", categoryName='" + categoryName + '\'' +
                '}';
    }
}