package com.synoverge.leave.portal.repository;

import com.synoverge.leave.portal.models.entity.Employee;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.Id;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Optional<Employee> findByEmployeeCode(String id);

    @Query(value = "SELECT e FROM Employee e WHERE e.roles.id = :roleId")
    Optional<Employee> findEmployeeByRoleId(long roleId);

}
