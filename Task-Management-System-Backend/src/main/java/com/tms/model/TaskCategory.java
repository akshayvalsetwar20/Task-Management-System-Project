package com.tms.model;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "taskcategory")
public class TaskCategory {
	
	 @EmbeddedId
	    private TaskCategoryId id;

	    @ManyToOne
	    @JoinColumn(name = "taskid", insertable = false, updatable = false)  // mark this column as not insertable and not updatable
	    private Task task;

	    @ManyToOne
	    @JoinColumn(name = "categoryid", insertable = false, updatable = false)  // mark this column as not insertable and not updatable
	    private Category category;
	    
	    private boolean isDeleted = false;

	   

		public TaskCategory() {}

	    public TaskCategoryId getId() {
	        return id;
	    }

	    public void setId(TaskCategoryId id) {
	        this.id = id;
	    }

	    public Task getTask() {
	        return task;
	    }

	    public void setTask(Task task) {
	        this.task = task;
	    }

	    public Category getCategory() {
	        return category;
	    }

	    public void setCategory(Category category) {
	        this.category = category;
	    }
	    public boolean isDeleted() {
			return isDeleted;
		}

		public void setDeleted(boolean isDeleted) {
			this.isDeleted = isDeleted;
		}

}
