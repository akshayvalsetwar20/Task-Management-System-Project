package com.tms.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tms.model.UserRoles;
import com.tms.model.UserRolesId;

@Repository
public interface UserRolesRepository extends JpaRepository<UserRoles, UserRolesId>{
	
	boolean existsById(UserRolesId userRolesId);

    List<UserRoles> findByUser_UserID(Integer userId);

    Optional<UserRoles> findByUserRolesId_UserIdAndUserRolesId_UserRoleIdAndIsDeletedFalse(Integer userId, Integer userRoleId);

	List<UserRoles> findByUserRolesId_UserId(Integer userId);

}
