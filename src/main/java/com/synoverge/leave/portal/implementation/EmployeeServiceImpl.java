package com.synoverge.leave.portal.implementation;

import com.synoverge.leave.portal.Exceptions.AlreadyExistUserException;
import com.synoverge.leave.portal.Exceptions.InActiveEmployeeException;
import com.synoverge.leave.portal.models.dto.*;
import com.synoverge.leave.portal.models.entity.Designation;
import com.synoverge.leave.portal.models.entity.Employee;
import com.synoverge.leave.portal.models.entity.LeaveAudit;
import com.synoverge.leave.portal.models.entity.RoleEntity;
import com.synoverge.leave.portal.repository.DesignationRepository;
import com.synoverge.leave.portal.repository.EmployeeRepository;
import com.synoverge.leave.portal.repository.LeaveRepository;
import com.synoverge.leave.portal.repository.RoleRepository;
import com.synoverge.leave.portal.services.EmployeeServices;
import com.synoverge.leave.portal.utility.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.*;

@Service
public class EmployeeServiceImpl implements EmployeeServices {
    private final static Logger logger = LoggerFactory.getLogger(EmployeeServiceImpl.class);

    private EmployeeRepository employeeRepository;

    private LeaveRepository leaveRepository;

    private RoleRepository roleRepository;

    private DesignationRepository designationRepository;

    private AdminServiceImpl adminServiceImpl;

    private PasswordEncoder bcryptEncoder;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository, LeaveRepository leaveRepository, RoleRepository roleRepository, DesignationRepository designationRepository, AdminServiceImpl adminServiceImpl, PasswordEncoder bcryptEncoder) {
        this.employeeRepository = employeeRepository;
        this.leaveRepository = leaveRepository;
        this.roleRepository = roleRepository;
        this.designationRepository = designationRepository;
        this.adminServiceImpl = adminServiceImpl;
        this.bcryptEncoder = bcryptEncoder;
    }

    public EmployeeServiceImpl() {
    }

    @Override
    public String registerEmployee(EmployeeRegisterRequest employeeRequest) {
        logger.debug("Entry!! :: EmployeeServiceImpl :: registerEmployee :: with employee => {}", employeeRequest);
        if (employeeRequest == null) {
            logger.error("Exit!! :: EmployeeServiceImpl :: registerEmployee :: Bad request!!");
            throw new NoSuchElementException(ErrorMessages.EMPTY_FIELDS);
        }
        Optional<RoleEntity> role = roleRepository.findById(employeeRequest.getRoleId());
        if (role.isPresent()) {
            RoleEntity roleId = role.get();
            if (roleId.getRoleName().equals(Roles.SUPER_ADMIN.getRoleName())) {
                Optional<Employee> superAdminEmployee = employeeRepository.findEmployeeByRoleId(employeeRequest.getRoleId());
                if (superAdminEmployee.isEmpty()) {
                     saveEmployee(employeeRequest);
                } else {
                    logger.debug("Exit! :: EmployeeServiceImpl :: registerEmployee :: Super-Admin is already exist!!");
                     throw new AlreadyExistUserException(ErrorMessages.USER_EXIST);
                }
            } else {
                saveEmployee(employeeRequest);
            }
        }
        logger.debug("Exit!! :: EmployeeServiceImpl :: registerEmployee :: Successfully!!");
        return SuccessMessage.registrationSuccess;
    }

    @Override
    public PageResponseVo getAllEmployeeDetails(PaginationRequest paginationRequest) {
        logger.debug("Entry!! :: EmployeeServiceImpl :: getAllEmployeeDetails ");
        Sort sort = createSortingRequest(paginationRequest);
        Pageable pageable = PageRequest.of(paginationRequest.getPageNumber(), paginationRequest.getItemPerPage(), sort);
        logger.debug("EmployeeServiceImpl :: getAllEmployeeDetails :: Paging request is prepare!!");
        Page<Employee> listOfEmployee = employeeRepository.findAll(pageable);
        logger.debug("EmployeeServiceImpl :: getAllEmployeeDetails :: Page is ready with data");
        if (listOfEmployee.isEmpty()) {
            logger.error("Exit!! :: EmployeeServiceImpl :: getAllEmployeeDetails :: No such element");
            return new PageResponseVo(0, 0, 0L, new Object());
        }
        PageResponseVo listOfEmployeeDetails = getEmployeeDTOS(listOfEmployee);
        logger.debug("Entry!! :: EmployeeServiceImpl :: getAllEmployeeDetails ");
        return listOfEmployeeDetails;
    }

    private static PageResponseVo getEmployeeDTOS(Page<Employee> listOfEmployee) {
        List<EmployeeDTO> listOfEmployeeDetails = new ArrayList<>();
        listOfEmployee.stream().forEach(employee -> listOfEmployeeDetails.add(
                new EmployeeDTO(employee.getId(), employee.getEmployeeCode(),
                                employee.getFirstName(), employee.getLastName(),
                                employee.getDesignation().getDesignationName(),
                                employee.getDesignation().getDepartment().getDepartmentCode(),
                                employee.getDesignation().getDepartment().getDepartmentName(),
                                employee.getAddressLine1(), employee.getAddressLine2(),
                                employee.getAddressLine3(), employee.getContactNumber(),
                                employee.getAge(), employee.getEmail(), employee.getRoles(),
                                employee.getActive(), null, employee.getCreatedAt(),
                                employee.getCreatedBy(), employee.getModifiedAt(),
                                employee.getModifyBy()))
);
        return new PageResponseVo(listOfEmployee.getTotalPages(), listOfEmployee.getNumberOfElements(), listOfEmployee.getTotalElements(), listOfEmployeeDetails);
    }

    @Override
    public EmployeeDTO getEmployeeByCode(String empCode) {
        logger.debug("Entry!! :: EmployeeServiceImpl :: getEmployeeByCode :: =>{}", empCode);
        Optional<Employee> employeeDetails = employeeRepository.findByEmployeeCode(empCode);
        if (employeeDetails.isEmpty()) {
            logger.error("Exit!! :: EmployeeServiceImpl :: getEmployeeByCode :: No any employee found!! ");
            throw new NoSuchElementException(ErrorMessages.EMPLOYEE_CODE_NOT_FOUND);
        }
        if (employeeDetails.get().getActive() != Boolean.TRUE) {
            logger.error("EmployeeServiceImpl :: leaveApply :: Employee is In-Active!!");
            throw new InActiveEmployeeException(ErrorMessages.INACTIVE_EMPLOYEE);
        }
        Employee employee = employeeDetails.get();
        logger.debug("Exit!! :: EmployeeServiceImpl :: getEmployeeByCode :: Successfully!! ");
        return new EmployeeDTO(employee.getId(), employee.getEmployeeCode(), employee.getFirstName(), employee.getLastName(), employee.getDesignation().getDesignationName(), employee.getDesignation().getDepartment().getDepartmentCode(), employee.getDesignation().getDepartment().getDepartmentName(), employee.getAddressLine1(), employee.getAddressLine2(), employee.getAddressLine3(), employee.getContactNumber(), employee.getAge(), employee.getEmail(), employee.getRoles(), employee.getActive(), null, employee.getCreatedAt(), employee.getCreatedBy(), employee.getModifiedAt(), employee.getModifyBy());
    }

    @Override
    public void leaveApply(LeaveRequest leaveData, String empCode) {
        logger.debug("Entry!! :: EmployeeServiceImpl :: leaveApply :: With empCode => {}", empCode);
        if (leaveData != null) {
            Date currentDate = new Date();
            if (!leaveData.getFromDate().after(currentDate) || leaveData.getFromDate().equals(currentDate)) {
                logger.error("Entry!! :: EmployeeServiceImpl :: leaveApply :: Selected date is gone!! ");
                throw new IllegalArgumentException(ErrorMessages.ILLEGAL_DATE);
            }
            Optional<Employee> employeeDetails = employeeRepository.findByEmployeeCode(empCode);
            if (employeeDetails.isEmpty()) {
                logger.error("Entry!! :: EmployeeServiceImpl :: leaveApply :: No any employee found!! ");
                throw new NoSuchElementException(ErrorMessages.EMPLOYEE_CODE_NOT_FOUND);
            }
            if (employeeDetails.get().getActive() != Boolean.TRUE) {
                logger.error("EmployeeServiceImpl :: leaveApply :: Employee is In-Active!!");
                throw new InActiveEmployeeException(ErrorMessages.INACTIVE_EMPLOYEE);
            }
            Employee employee = employeeDetails.get();
            LeaveAudit leaveAudit = new LeaveAudit(leaveData.getLeaveType(), leaveData.getRemark(), leaveData.getFromDate(), leaveData.getToDate(), Status.PENDING, null, employee);
            logger.debug("Entry!! :: EmployeeServiceImpl :: leaveApply ::Successfully!!");
            try {
                leaveRepository.save(leaveAudit);
            } catch (RuntimeException ex) {
                throw new RuntimeException(ErrorMessages.DATA_BASE_ERROR);
            }
        }
    }

    @Override
    public Employee updateEmployeeDetails(String empCode, EmployeeUpdateRequest updateEmployeeRequest) {
        logger.debug("Entry!! :: EmployeeServiceImpl :: updateEmployeeDetails :: With empCode => {}", empCode);
        Optional<Employee> employeeDetails = employeeRepository.findByEmployeeCode(empCode);
        if (employeeDetails.isEmpty()) {
            logger.error("Entry!! :: EmployeeServiceImpl :: updateEmployeeDetails :: No such element are found");
            throw new NoSuchElementException(ErrorMessages.EMPLOYEE_CODE_NOT_FOUND);
        }
        if (employeeDetails.get().getActive() != Boolean.TRUE) {
            logger.error("EmployeeServiceImpl :: updateEmployeeDetails :: Employee is In-Active!!");
            throw new InActiveEmployeeException(ErrorMessages.INACTIVE_EMPLOYEE);
        }
        Employee updatedEmployee = getEmployee(updateEmployeeRequest, employeeDetails.get());
        try {
            employeeRepository.save(updatedEmployee);
        } catch (RuntimeException ex) {
            throw new RuntimeException(ErrorMessages.DATA_BASE_ERROR);
        }
        logger.debug("Entry!! :: EmployeeServiceImpl :: updateEmployeeDetails ::Successfully!!");
        return updatedEmployee;
    }

    private static Employee getEmployee(EmployeeUpdateRequest updateEmployeeRequest, Employee employeeDetails) {
        employeeDetails.setFirstName(updateEmployeeRequest.getFirstName());
        employeeDetails.setLastName(updateEmployeeRequest.getLastName());
        employeeDetails.setAddressLine1(updateEmployeeRequest.getAddressLine1());
        employeeDetails.setAddressLine2(updateEmployeeRequest.getAddressLine2());
        employeeDetails.setAddressLine3(updateEmployeeRequest.getAddressLine3());
        employeeDetails.setAge(updateEmployeeRequest.getAge());
        employeeDetails.setContactNumber(updateEmployeeRequest.getContactNumber());
        employeeDetails.setEmail(updateEmployeeRequest.getEmail());
        employeeDetails.setModifiedAt(ZonedDateTime.now());
        return employeeDetails;
    }

    @Override
    public PageResponseVo appliedLeaveByEmployee(String empCode, PaginationRequest paginationRequest) {
        logger.debug("Entry!! :: EmployeeServiceImpl :: appliedLeaveByEmployee :: With empCode => {}", empCode);
        Sort sort = createSortingRequest(paginationRequest);
        Pageable pageable = PageRequest.of(paginationRequest.getPageNumber(), paginationRequest.getItemPerPage(), sort);
        Optional<Employee> employee = employeeRepository.findByEmployeeCode(empCode);
        if (employee.isEmpty()) {
            logger.error("EmployeeServiceImpl :: appliedLeaveByEmployee :: Employee is no more exit!!");
            throw new NoSuchElementException(ErrorMessages.EMPLOYEE_CODE_NOT_FOUND);
        }
        if (employee.get().getActive() != Boolean.TRUE) {
            logger.error("EmployeeServiceImpl :: appliedLeaveByEmployee :: Employee is In-Active!!");
            throw new InActiveEmployeeException(ErrorMessages.INACTIVE_EMPLOYEE);
        }
        Page<LeaveAudit> listOfLeaves = leaveRepository.getAllLeaveByEmployee(employee.get().getEmployeeCode(), pageable);
        if (listOfLeaves.isEmpty()) {
            logger.error("EmployeeServiceImpl :: appliedLeaveByEmployee :: No leave are found");
            throw new NoSuchElementException(ErrorMessages.NO_LEAVE_TAKEN_BY_EMPLOYEE);
        }
        List<LeaveResponse> leaveResponses = new ArrayList<>();
        listOfLeaves.stream().forEachOrdered(leaves -> leaveResponses.add(adminServiceImpl.getLeaveResponse(leaves)));
        logger.debug("Exit!! :: EmployeeServiceImpl :: appliedLeaveByEmployee :: Successfully");
        return new PageResponseVo(listOfLeaves.getTotalPages(), listOfLeaves.getNumberOfElements(), listOfLeaves.getTotalElements(), leaveResponses);
    }

    @Override
    public String enableEmployee(String empCode, Boolean active) {
        Optional<Employee> employeeDetails = employeeRepository.findByEmployeeCode(empCode);
        if (employeeDetails.isEmpty()) {
            logger.error("Entry!! :: AdminServiceImpl :: enableEmployee :: No any employee found!! ");
            throw new NoSuchElementException(ErrorMessages.EMPLOYEE_CODE_NOT_FOUND);
        }
        employeeDetails.get().setActive(active);
        try {
            employeeRepository.save(employeeDetails.get());
        } catch (RuntimeException ex) {
            throw new RuntimeException(ErrorMessages.DATA_BASE_ERROR);
        }
        return SuccessMessage.EMPLOYEE_ACTIVE;
    }

    @Override
    public String deleteEmployee(String empCode) {
        logger.debug("Entry!! :: EmployeeServiceImpl :: deleteEmployee :: with employee code : => {}", empCode);
        Optional<Employee> employee = employeeRepository.findByEmployeeCode(empCode);
        if (employee.isEmpty()) {
            logger.error("EmployeeServiceImpl :: deleteEmployee :: Employee is no more exit!!");
            throw new NoSuchElementException(ErrorMessages.EMPLOYEE_CODE_NOT_FOUND);
        }
        if(employee.get().getRoles().getRoleName().equals(Roles.SUPER_ADMIN.getRoleName())){
            logger.error("EmployeeServiceImpl :: deleteEmployee :: You can not delete the Super-admin!!");
            throw new IllegalArgumentException(ErrorMessages.RESTRICT_DELETE_SUPER_ADMIN);
        }
        if (employee.get().getActive() != Boolean.TRUE) {
            logger.error("EmployeeServiceImpl :: deleteEmployee :: Employee is In-Active!!");
            throw new InActiveEmployeeException(ErrorMessages.INACTIVE_EMPLOYEE);
        }
        logger.error("Exit!! :: EmployeeServiceImpl :: deleteEmployee :: Employee Successfully deleted!!");
        try {
            Employee employeeRemoved = employee.get();
            employeeRemoved.setActive(Boolean.FALSE);
            employeeRepository.save(employeeRemoved);
        } catch (RuntimeException ex) {
            throw new RuntimeException(ErrorMessages.DATA_BASE_ERROR);
        }
        return SuccessMessage.DELETE_EMPLOYEE;
    }

    private void saveEmployee(EmployeeRegisterRequest employeeRequest) {
        Optional<RoleEntity> role = roleRepository.findById(employeeRequest.getRoleId());
        Optional<Designation> designation = designationRepository.findById(employeeRequest.getDesignationId());
        Employee employee = new Employee(employeeRequest.getEmployeeCode(), employeeRequest.getFirstName(), employeeRequest.getLastName(), designation.get(), employeeRequest.getAddressLine1(), employeeRequest.getAddressLine2(), employeeRequest.getAddressLine3(), employeeRequest.getContactNumber(), employeeRequest.getAge(), employeeRequest.getEmail(), role.get(), bcryptEncoder.encode(employeeRequest.getPassword()), Boolean.TRUE, ZonedDateTime.now(), employeeRequest.getEmployeeCode(), ZonedDateTime.now(), employeeRequest.getEmployeeCode());
        try {
            employeeRepository.save(employee);
        } catch (RuntimeException ex) {
            throw new RuntimeException(ErrorMessages.DATA_BASE_ERROR);
        }
    }

    public static Sort createSortingRequest(PaginationRequest paginationRequest) {
        Sort sorting;
        logger.debug("Entry!!.. :: EmployeeServiceImpl :: createSortingRequest :: with request order :: =>{}", paginationRequest.getSortingOrder());
        logger.debug("EmployeeServiceImpl :: createSortingRequest :: with request field :: => {}", paginationRequest.getSortingField());
        if (paginationRequest.getSortingOrder().equals(SortingOrder.DEC)) {
            sorting = Sort.by(paginationRequest.getSortingField()).descending();
        } else {
            sorting = Sort.by(paginationRequest.getSortingField()).ascending();
        }
        logger.debug("Exist!!.. :: EmployeeServiceImpl :: createSortingRequest :: with successfully sorting!!..");
        return sorting;
    }
}
