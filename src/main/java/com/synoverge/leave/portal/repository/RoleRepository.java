package com.synoverge.leave.portal.repository;

import com.synoverge.leave.portal.models.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Repository
@Component
public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
}
