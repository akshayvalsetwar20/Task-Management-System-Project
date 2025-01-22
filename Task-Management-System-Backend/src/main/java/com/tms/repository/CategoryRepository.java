package com.tms.repository;
import java.util.List;
 
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
 
import com.tms.model.Category;
 
@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
 
    boolean existsByCategoryNameAndIsDeletedFalse(String categoryName);
    
    List<Category> findAllByIsDeletedFalse();
    
    Category findByCategoryIdAndIsDeletedFalse(Integer categoryId);
 
    @Query(value = "SELECT c.categoryname AS categoryName, COUNT(tc.taskid) AS taskCount " +
            "FROM category c LEFT JOIN taskcategory tc ON c.categoryid = tc.categoryid " +
            "WHERE c.is_deleted = false " +
            "GROUP BY c.categoryid, c.categoryname", nativeQuery = true)
    		List<Object[]> findCategoriesWithTaskCount();
 
	
 
}