package com.synoverge.leave.portal.controller;

import com.synoverge.leave.portal.DTO.BaseResponseEntity;
import com.synoverge.leave.portal.models.dto.*;
import com.synoverge.leave.portal.models.entity.Employee;
import com.synoverge.leave.portal.services.EmployeeServices;
import com.synoverge.leave.portal.utility.Constants;
import com.synoverge.leave.portal.utility.SortingOrder;
import com.synoverge.leave.portal.utility.SuccessMessage;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping(value = Constants.baseUrl)
@CrossOrigin(origins="*",allowedHeaders="*")
@SecurityRequirement(name = "bearerAuth")
public class EmployeeController {

    private final Logger log = LoggerFactory.getLogger(EmployeeController.class);

    public final EmployeeServices employeeServices;

    @Autowired
    public EmployeeController(EmployeeServices employeeServices) {
        this.employeeServices = employeeServices;
    }

    @PostMapping(value = "/employee/registerEmployee")
    public ResponseEntity<BaseResponseEntity> registerEmployee(@Valid @RequestBody EmployeeRegisterRequest employee) {
        log.debug("Entry Employee Controller!! :: registerEmployee with this data => {}", employee);
        String response = employeeServices.registerEmployee(employee);
        BaseResponseEntity baseResponse = new BaseResponseEntity(HttpStatus.CREATED.value(), HttpStatus.CREATED.name(), response, "success");
        log.debug("Exit Employee Controller!! :: registerEmployee");
        return new ResponseEntity<BaseResponseEntity>(baseResponse, HttpStatus.CREATED);
    }

    @GetMapping(value = "/employee/getAllEmployee")
    public ResponseEntity<BaseResponseEntity> getAllEmployeeDetails(@RequestParam(name = "pageNumber") int pageNumber,@RequestParam(name = "itemPerPage") int itemPerPage,@RequestParam(name = "sortingField") String sortingField,@RequestParam(name = "sortingOrder") SortingOrder sortingOrder) {
        log.debug("Entry Employee Controller!! :: getAllEmployeeDetails");
        PaginationRequest paginationRequest = new PaginationRequest(pageNumber,itemPerPage,sortingOrder,sortingField);
        PageResponseVo listOfAllEmployee = employeeServices.getAllEmployeeDetails(paginationRequest);
        BaseResponseEntity response = new BaseResponseEntity(HttpStatus.OK.value(), HttpStatus.OK.name(), SuccessMessage.getAllEmployeeSuccess, listOfAllEmployee);
        log.debug("Exit Employee Controller!! :: getAllEmployeeDetails");
        return new ResponseEntity<BaseResponseEntity>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/employee/{empCode}")
    public ResponseEntity<BaseResponseEntity> getEmployeeDetailsById(@PathVariable @NotEmpty(message = "Employee code is mandatory") String empCode) {
        log.debug("Entry Employee Controller!! :: getEmployeeDetailsById :: empCode :{}", empCode);
        EmployeeDTO employeeDetails = employeeServices.getEmployeeByCode(empCode);
        BaseResponseEntity response = new BaseResponseEntity(HttpStatus.OK.value(), HttpStatus.OK.name(), SuccessMessage.employeeByIdSuccess, employeeDetails);
        log.debug("Exit Employee Controller!! :: getEmployeeDetailsById");
        return new ResponseEntity<BaseResponseEntity>(response, HttpStatus.OK);
    }

    @PostMapping(value = "/employee/applyLeave")
    public ResponseEntity<BaseResponseEntity> applyLeaveForEmployee(@RequestBody @Valid LeaveRequest leaveRequest) {
        log.debug("Entry Employee Controller!! :: applyLeaveForEmployee :: empCode :{}", leaveRequest.getEmployeeCode());
        employeeServices.leaveApply(leaveRequest, leaveRequest.getEmployeeCode());
        BaseResponseEntity response = new BaseResponseEntity(HttpStatus.OK.value(), HttpStatus.CREATED.name(), SuccessMessage.leaveCreated, "Success");
        log.debug("Exit Employee Controller!! :: applyLeaveForEmployee");
        return new ResponseEntity<BaseResponseEntity>(response, HttpStatus.CREATED);
    }

    @PutMapping(value = "/employee/updateEmployee/{empCode}")
    public ResponseEntity<BaseResponseEntity> updateEmployeeDetails(@PathVariable @NotEmpty(message = "Employee code is mandatory") String empCode, @RequestBody @Valid EmployeeUpdateRequest employeeUpdateRequest) {
        log.debug("Entry Employee Controller!! :: updateEmployeeDetails :: empCode :{}", empCode);
        Employee employee = employeeServices.updateEmployeeDetails(empCode, employeeUpdateRequest);
        BaseResponseEntity response = new BaseResponseEntity(HttpStatus.OK.value(), HttpStatus.CREATED.name(), SuccessMessage.updateEmployeeSuccess, employee);
        log.debug("Exit Employee Controller!! :: updateEmployeeDetails");
        return new ResponseEntity<BaseResponseEntity>(response, HttpStatus.CREATED);
    }

    @GetMapping(value = "/employee/leaveDetails/{empCode}")
    public ResponseEntity<BaseResponseEntity> appliedLeaveByEmployee(@PathVariable String empCode, @RequestParam(name = "pageNumber") int pageNumber,@RequestParam(name = "itemPerPage") int itemPerPage,@RequestParam(name = "sortingField") String sortingField,@RequestParam(name = "sortingOrder") SortingOrder sortingOrder) {
        log.debug("Entry Employee Controller!! :: appliedLeaveByEmployee :: empCode :{}", empCode);
        PaginationRequest paginationRequest = new PaginationRequest(pageNumber,itemPerPage,sortingOrder,sortingField);
        PageResponseVo leaveResponse = employeeServices.appliedLeaveByEmployee(empCode, paginationRequest);
        BaseResponseEntity response = new BaseResponseEntity(HttpStatus.OK.value(), HttpStatus.OK.name(), SuccessMessage.leaveGetByEmployee, leaveResponse);
        log.debug("Entry Employee Controller!! :: appliedLeaveByEmployee");
        return new ResponseEntity<BaseResponseEntity>(response, HttpStatus.OK);
    }

    @PatchMapping(value = "/employee/active/{empCode}")
    public ResponseEntity<BaseResponseEntity> activeEmployeeDetails(@PathVariable @NotEmpty(message = "Employee code is mandatory") String empCode,@RequestParam(name = "isActive") @NotNull(message = "Status is mandatory") Boolean isActive) {

        log.debug("Entry Employee Controller!! :: activeEmployeeDetails :: empCode :{}", empCode);
        String responseMessage = employeeServices.enableEmployee(empCode,isActive);
        BaseResponseEntity response = new BaseResponseEntity(HttpStatus.OK.value(), HttpStatus.CREATED.name(), SuccessMessage.EMPLOYEE_ACTIVE, responseMessage);
        log.debug("Exit Employee Controller!! :: activeEmployeeDetails");
        return new ResponseEntity<BaseResponseEntity>(response, HttpStatus.CREATED);

    }
    @DeleteMapping(value = "/employee/delete/{empCode}")
    public ResponseEntity<BaseResponseEntity> deleteEmployeeDetails(@PathVariable @NotEmpty(message = "Employee code is mandatory") String empCode) {

        log.debug("Entry Employee Controller!! :: deleteEmployeeDetails :: empCode :{}", empCode);
        String responseMessage = employeeServices.deleteEmployee(empCode);
        BaseResponseEntity response = new BaseResponseEntity(HttpStatus.OK.value(), HttpStatus.CREATED.name(), SuccessMessage.DELETE_EMPLOYEE, responseMessage);
        log.debug("Exit Employee Controller!! :: deleteEmployeeDetails");
        return new ResponseEntity<BaseResponseEntity>(response, HttpStatus.CREATED);

    }

}
