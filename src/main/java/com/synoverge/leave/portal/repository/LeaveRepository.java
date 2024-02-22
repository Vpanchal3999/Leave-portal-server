package com.synoverge.leave.portal.repository;

import com.synoverge.leave.portal.models.entity.Employee;
import com.synoverge.leave.portal.models.entity.LeaveAudit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LeaveRepository extends JpaRepository<LeaveAudit,Long> {

    @Query(value = "SELECT l FROM LeaveAudit l WHERE l.employee.employeeCode = :empCode AND l.id = :leaveId AND l.employee.isActive = true")
   Optional<LeaveAudit> getByEmployeeCodeAndLeaveId(@Param("leaveId") Long leaveId,@Param("empCode") String empCode);

    @Query(value = "SELECT l FROM LeaveAudit l WHERE l.employee.employeeCode = :empCode")
    Page<LeaveAudit> getAllLeaveByEmployee(@Param("empCode") String empCode, Pageable pageable);

    @Query(value = "SELECT l FROM LeaveAudit l WHERE l.employee.isActive = true")
    Page<LeaveAudit> findAllActiveEmployeeLeaves(Pageable pageable);
}
