package com.synoverge.leave.portal.repository;

import com.synoverge.leave.portal.models.entity.Designation;
import jdk.jfr.Description;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DesignationRepository extends JpaRepository<Designation,Long> {
}
