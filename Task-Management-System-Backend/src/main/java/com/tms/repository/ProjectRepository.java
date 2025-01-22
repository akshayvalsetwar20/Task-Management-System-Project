package com.tms.repository;
import com.tms.model.Project;
import com.tms.model.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long>{

		Optional<Project> findByProjectName(String projectName);
		
		List<Project> findByStartDateBeforeAndEndDateAfter(LocalDate startDate, LocalDate endDate);

		List<Project> findByStartDateBetween(LocalDate startDate, LocalDate endDate);

		@Query(value = "SELECT p.* FROM project p JOIN task t ON p.projectid = t.projectid WHERE t.status = :status AND p.is_deleted = false", nativeQuery = true)
		List<Project> findProjectsByTaskStatus(@Param("status") String status);

		Optional<Project> findByProjectID(Integer projectId);
		
		@Query(value = """
		        SELECT
		            p.projectID,
		            p.projectName,
		            p.description,
		            p.startDate,
		            p.endDate,
		            u.userID,
		            u.username,
		            ur.roleName
		        FROM
		            project p
		        JOIN
		            "user" u ON p.userid = u.userid
		        JOIN
		            userroles ur_map ON u.userid = ur_map.userid
		        JOIN
		            userrole ur ON ur_map.userroleid = ur.userroleid
		        WHERE
		            ur.rolename = :roleName
		            AND p.is_deleted = false
		        """, nativeQuery = true)
		    List<Object[]> findProjectsByRoleName(@Param("roleName") String roleName);


}
