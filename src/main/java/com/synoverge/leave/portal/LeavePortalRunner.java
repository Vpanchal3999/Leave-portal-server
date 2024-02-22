package com.synoverge.leave.portal;

import com.synoverge.leave.portal.models.dto.EmployeeRegisterRequest;
import com.synoverge.leave.portal.models.entity.Department;
import com.synoverge.leave.portal.models.entity.Designation;
import com.synoverge.leave.portal.models.entity.Employee;
import com.synoverge.leave.portal.models.entity.RoleEntity;
import com.synoverge.leave.portal.repository.DepartmentRepository;
import com.synoverge.leave.portal.repository.DesignationRepository;
import com.synoverge.leave.portal.repository.EmployeeRepository;
import com.synoverge.leave.portal.repository.RoleRepository;
import com.synoverge.leave.portal.utility.ErrorMessages;
import com.synoverge.leave.portal.utility.Roles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class LeavePortalRunner implements ApplicationRunner {

    private final static Logger logger = LoggerFactory.getLogger(LeavePortalRunner.class);

    @Autowired
    RoleRepository roleRepository;
    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    DesignationRepository designationRepository;
    @Autowired
    DepartmentRepository departmentRepository;
    @Autowired
    PasswordEncoder bcryptEncoder;

    private final List<Designation> listOfAllDesignation = new ArrayList<>();
    private final List<Department> listOfAllDepartment = new ArrayList<>();
    private final List<RoleEntity> listOfRole = new ArrayList<>();

    @Override
    public void run(ApplicationArguments args) throws Exception {
        logger.info("Entry!! :: Run :: To create pre-defined data!!..");
        List<RoleEntity> listOfRoles = roleRepository.findAll();
        if (listOfRoles.isEmpty()){
            createRoles();
        }
        List<Designation> listOfDesignation = designationRepository.findAll();
        if(listOfDesignation.isEmpty()){
            saveDepartmentData();
            saveDesignationData();
        }
        EmployeeRegisterRequest superAdminEmployeeRequest = new EmployeeRegisterRequest("E001", "Super", "Admin", (long) 5, "101-Synoverge", "Gurukul", "Ahmedabad", "7823546349", 50, "superAdmin@mailinator.com", (long) 1, "admin@123");
        Optional<RoleEntity> role = roleRepository.findById(superAdminEmployeeRequest.getRoleId());
        Optional<Designation> designation = designationRepository.findById(superAdminEmployeeRequest.getDesignationId());
        if (role.isPresent()) {
            RoleEntity roleId = role.get();
            if (roleId.getRoleName().equals(Roles.SUPER_ADMIN.getRoleName())) {
                logger.info("Spring-boot :: Run :: Super Admin start to create!!");
                Optional<Employee> superAdminEmployee = employeeRepository.findEmployeeByRoleId(superAdminEmployeeRequest.getRoleId());
                if (superAdminEmployee.isEmpty()) {
                    Employee employee = new Employee(superAdminEmployeeRequest.getEmployeeCode(), superAdminEmployeeRequest.getFirstName(), superAdminEmployeeRequest.getLastName(), designation.get(), superAdminEmployeeRequest.getAddressLine1(), superAdminEmployeeRequest.getAddressLine2(), superAdminEmployeeRequest.getAddressLine3(), superAdminEmployeeRequest.getContactNumber(), superAdminEmployeeRequest.getAge(), superAdminEmployeeRequest.getEmail(), role.get(), bcryptEncoder.encode(superAdminEmployeeRequest.getPassword()), Boolean.TRUE, ZonedDateTime.now(), superAdminEmployeeRequest.getEmployeeCode(), ZonedDateTime.now(), superAdminEmployeeRequest.getEmployeeCode());
                    try {
                        logger.info("Spring-boot :: Run :: Super Admin successfully created!!");
                        employeeRepository.save(employee);
                    } catch (RuntimeException ex) {
                        logger.error("Spring-boot :: Run :: Error to create super-admin!! {}", ex.getMessage());
                        throw new RuntimeException(ErrorMessages.DATA_BASE_ERROR);
                    }
                } else {
                    logger.info("LeavePortalRunner :: Run :: Super-Admin already exist!!");
                }
            }
        }
        //        employeeService.registerEmployee(superAdminEmployee);
    }

    private void saveDepartmentData() {
        logger.info("saveDepartment :: To creating department!!");
        listOfAllDepartment.add(new Department((long) 1, "S001", "HR"));
        listOfAllDepartment.add(new Department((long) 2, "S002", "Developer"));
        listOfAllDepartment.add(new Department((long) 3, "S003", "Finance"));
        listOfAllDepartment.add(new Department((long) 4, "S004", "PMO"));
        listOfAllDepartment.add(new Department((long) 5, "S005", "Admin"));
        logger.info("saveDepartment :: Department Data saved Successfully!!");
        departmentRepository.saveAll(listOfAllDepartment);
    }

    private void saveDesignationData() {
        logger.info("saveDesignation :: To creating designation!!");
        listOfAllDesignation.add(new Designation((long) 1, "Associate Software Engineer", listOfAllDepartment.stream().filter(i -> "S002".equals(i.getDepartmentCode())).findAny().orElse(null)));
        listOfAllDesignation.add(new Designation((long) 2, "Software Engineer", listOfAllDepartment.stream().filter(i -> "S002".equals(i.getDepartmentCode())).findAny().orElse(null)));
        listOfAllDesignation.add(new Designation((long) 3, "Senior Software Engineer",listOfAllDepartment.stream().filter(i -> "S002".equals(i.getDepartmentCode())).findAny().orElse(null)));
        listOfAllDesignation.add(new Designation((long) 4, "Project Manager", listOfAllDepartment.stream().filter(i -> "S004".equals(i.getDepartmentCode())).findAny().orElse(null)));
        listOfAllDesignation.add(new Designation((long) 5, "Technical Project manager",listOfAllDepartment.stream().filter(i -> "S004".equals(i.getDepartmentCode())).findAny().orElse(null)));
        listOfAllDesignation.add(new Designation((long) 6, "Hr-Executive",listOfAllDepartment.stream().filter(i -> "S001".equals(i.getDepartmentCode())).findAny().orElse(null)));
        listOfAllDesignation.add(new Designation((long) 7, "Hr-Senior Executive",listOfAllDepartment.stream().filter(i -> "S001".equals(i.getDepartmentCode())).findAny().orElse(null)));
        listOfAllDesignation.add(new Designation((long) 8, "Finance-Executive",listOfAllDepartment.stream().filter(i -> "S003".equals(i.getDepartmentCode())).findAny().orElse(null)));
        listOfAllDesignation.add(new Designation((long) 9, "CA",listOfAllDepartment.stream().filter(i -> "S003".equals(i.getDepartmentCode())).findAny().orElse(null)));
        listOfAllDesignation.add(new Designation((long) 10, "Admin",listOfAllDepartment.stream().filter(i -> "S005".equals(i.getDepartmentCode())).findAny().orElse(null)));
        logger.info("saveDesignation :: Designation Data saved Successfully!!");
        designationRepository.saveAll(listOfAllDesignation);
    }

    private void createRoles(){
        listOfRole.add(new RoleEntity((long) Roles.SUPER_ADMIN.getId(),Roles.SUPER_ADMIN.getRoleName()));
        listOfRole.add(new RoleEntity((long) Roles.SUPER_ADMIN.getId(),Roles.ADMIN.getRoleName()));
        listOfRole.add(new RoleEntity((long) Roles.SUPER_ADMIN.getId(),Roles.EMPLOYEE.getRoleName()));
        logger.info("Roles :: Roles are saved Successfully!!");
        roleRepository.saveAll(listOfRole);
    }
}
