package com.tms.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tms.model.UserRole;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Integer> {
	
	Optional<UserRole> findById(Integer userRoleId);
	
    List<UserRole> findByIsDeletedFalse();

    Optional<UserRole> findByUserRoleIdAndIsDeletedFalse(int userRoleId);

    Optional<UserRole> findByRoleNameAndIsDeletedFalse(String roleName);

}
