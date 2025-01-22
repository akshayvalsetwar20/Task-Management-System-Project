package com.tms.repository;
 
import com.tms.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
 
import java.util.List;
 
@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {
 
    Comment findByCommentIdAndIsDeletedFalse(Integer commentId);
 
	boolean existsByTextAndIsDeletedFalse(String text);
}