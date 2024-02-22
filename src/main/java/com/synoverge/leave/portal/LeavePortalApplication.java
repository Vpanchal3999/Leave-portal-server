package com.synoverge.leave.portal;

import com.synoverge.leave.portal.implementation.EmployeeServiceImpl;
import com.synoverge.leave.portal.models.dto.EmployeeRegisterRequest;
import com.synoverge.leave.portal.models.entity.Employee;
import com.synoverge.leave.portal.repository.EmployeeRepository;
import com.synoverge.leave.portal.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class LeavePortalApplication {

    public static void main(String[] args) {
        SpringApplication.run(LeavePortalApplication.class, args);
    }
}
