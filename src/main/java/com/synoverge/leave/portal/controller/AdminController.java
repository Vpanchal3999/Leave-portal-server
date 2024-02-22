package com.synoverge.leave.portal.controller;

import com.synoverge.leave.portal.DTO.BaseResponseEntity;
import com.synoverge.leave.portal.models.dto.*;
import com.synoverge.leave.portal.models.entity.Designation;
import com.synoverge.leave.portal.models.entity.LeaveAudit;
import com.synoverge.leave.portal.services.AdminServices;
import com.synoverge.leave.portal.utility.Constants;
import com.synoverge.leave.portal.utility.SortingOrder;
import com.synoverge.leave.portal.utility.Status;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping(value = Constants.baseUrl)
@CrossOrigin(origins = "*")
@SecurityRequirement(name = "bearerAuth")
public class AdminController {

    public AdminServices adminServices;

    private final Logger log = LoggerFactory.getLogger(AdminController.class);

    @Autowired
    public AdminController(AdminServices adminServices) {
        this.adminServices = adminServices;
    }

    @PatchMapping("/admin/updateLeave/status")
    public ResponseEntity<BaseResponseEntity> updateLeaveStatus(@RequestBody LeaveRejectRequest leaveRequest) {
        log.debug("Entry Admin Controller!! : updateLeaveStatus with employee code {}", leaveRequest.getEmployeeCode());
        String responseMessage = adminServices.updateLeaveStatus(leaveRequest);
        log.debug("Exist Admin Controller!! :: updateLeaveStatus");
        BaseResponseEntity response = new BaseResponseEntity(HttpStatus.OK.value(), HttpStatus.OK.name(), "Leave status is updated", responseMessage);
        return new ResponseEntity<BaseResponseEntity>(response, HttpStatus.OK);
    }

    @GetMapping("/admin/leaveList")
    public ResponseEntity<BaseResponseEntity> listOfAllEmployeesLeave(@RequestParam(name = "pageNumber") int pageNumber, @RequestParam(name = "itemPerPage") int itemPerPage, @RequestParam(name = "sortingField") String sortingField, @RequestParam(name = "sortingOrder") SortingOrder sortingOrder) {
        log.debug("Entry Admin Controller!! : listOfAllEmployeesLeave");
        PaginationRequest paginationRequest = new PaginationRequest(pageNumber, itemPerPage, sortingOrder, sortingField);
        PageResponseVo listOfAllEmployeeOnLeaves = adminServices.listOfAllEmployeeLeaves(paginationRequest);
        BaseResponseEntity response = new BaseResponseEntity(HttpStatus.OK.value(), HttpStatus.OK.name(), "List of All employee leaves", listOfAllEmployeeOnLeaves);
        log.debug("Exist Admin Controller!! : listOfAllEmployeesLeave");
        return new ResponseEntity<BaseResponseEntity>(response, HttpStatus.OK);
    }

    @GetMapping("/admin/listDesignation")
    public ResponseEntity<BaseResponseEntity> listOfAllDesignation() {
        log.debug("Entry Admin Controller!! : listOfAllDesignation");
        List<Designation> designations = adminServices.listOfAllDesignation();
        BaseResponseEntity response = new BaseResponseEntity(HttpStatus.OK.value(), HttpStatus.OK.name(), "List of All designation", designations);
        log.debug("Exist Admin Controller!! : listOfAllDesignation");
        return new ResponseEntity<BaseResponseEntity>(response, HttpStatus.OK);
    }
}
