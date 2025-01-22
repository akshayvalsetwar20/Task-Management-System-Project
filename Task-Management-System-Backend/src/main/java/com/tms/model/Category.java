package com.tms.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "category") 
public class Category {
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "categoryid")
    private Integer categoryId;

    @Column(name = "categoryname")
    private String categoryName;
    
    
    @Column(nullable=false, name="is_deleted")
    //@JsonIgnore
    private boolean isDeleted=false;
    
    
    @OneToMany(mappedBy = "category")
    private List<TaskCategory> taskCategories; 

	@JsonIgnore
	public boolean isDeleted() {
		return isDeleted;
	}

	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	// Default constructor
    public Category() {
    }

    public Category(String categoryName) {
		super();
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

}
