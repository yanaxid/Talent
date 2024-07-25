package com.tujuhsembilan.app.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.tujuhsembilan.app.models.EmployeeStatus;

@Repository
public interface EmployeeStatusRepository extends JpaRepository<EmployeeStatus, UUID> {

   @Query("SELECT CASE WHEN COUNT(e) > 0 THEN true ELSE false END " +
         "FROM EmployeeStatus e " +
         "WHERE LOWER(e.employeeStatusName) = LOWER(:employeeStatusName)")
   boolean existsByEmployeeStatusNameIgnoreCase(@Param("employeeStatusName") String employeeStatusName);
}
