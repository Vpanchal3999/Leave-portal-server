package com.synoverge.leave.portal.implementation;

import com.synoverge.leave.portal.models.dto.*;
import com.synoverge.leave.portal.models.entity.Designation;
import com.synoverge.leave.portal.models.entity.LeaveAudit;
import com.synoverge.leave.portal.repository.DesignationRepository;
import com.synoverge.leave.portal.repository.LeaveRepository;
import com.synoverge.leave.portal.services.AdminServices;
import com.synoverge.leave.portal.utility.ErrorMessages;
import com.synoverge.leave.portal.utility.Status;
import com.synoverge.leave.portal.utility.SuccessMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AdminServiceImpl implements AdminServices {

    private final Logger log = LoggerFactory.getLogger(AdminServiceImpl.class);

    public LeaveRepository leaveRepository;

    private final DesignationRepository designationRepository;


    @Autowired
    public AdminServiceImpl(LeaveRepository leaveRepository, DesignationRepository designationRepository) {
        this.leaveRepository = leaveRepository;
        this.designationRepository = designationRepository;
    }

    @Override
    public String updateLeaveStatus(LeaveRejectRequest leaveRequest) {
        log.debug("Entry!! AdminServiceImpl :: updateLeaveStatus :: with leaveId : {} and empCode : {} ",leaveRequest.getLeaveId(), leaveRequest.getEmployeeCode());
        Optional<LeaveAudit> leaveAudit = leaveRepository.getByEmployeeCodeAndLeaveId(leaveRequest.getLeaveId(), leaveRequest.getEmployeeCode());
        Status status = Status.valueOf(leaveRequest.getStatus());
        if (leaveAudit.isEmpty()) {
            log.debug("Exit!! AdminServiceImpl :: updateLeaveStatus :: No any elements find  with leaveId : {} and empCode : {} ",leaveRequest.getLeaveId(), leaveRequest.getEmployeeCode());
            throw new NoSuchElementException(ErrorMessages.NO_LEAVE_TAKEN_BY_EMPLOYEE);
        }
        leaveAudit.get().setStatus(status);
        if(status.equals(Status.REJECTED)){
            leaveAudit.get().setRejectionMessage(leaveRequest.getRejectReason());
        } else {
            leaveAudit.get().setRejectionMessage(SuccessMessage.APPROVE_MESSAGE);
        }
        try {
            leaveRepository.save(leaveAudit.get());
        } catch (RuntimeException ex) {
            throw new RuntimeException(ErrorMessages.DATA_BASE_ERROR);
        }
        log.debug("Exit!! AdminServiceImpl :: updateLeaveStatus :: Successfully");
        if(status.equals(Status.APPROVED)){
            return SuccessMessage.leaveApproved;
        } else if (status.equals(Status.REJECTED)){
            return SuccessMessage.leaveRejected;
        } else {
            return SuccessMessage.leavePending;
        }
    }

    @Override
    public PageResponseVo listOfAllEmployeeLeaves(PaginationRequest paginationRequest) {
        log.debug("Entry!! AdminServiceImpl :: listOfAllEmployeeLeaves ");
        Sort sort = EmployeeServiceImpl.createSortingRequest(paginationRequest);
        Pageable pageable = PageRequest.of(paginationRequest.getPageNumber(), paginationRequest.getItemPerPage(), sort);
        Page<LeaveAudit> listOfAllEmployeeLeaves = leaveRepository.findAllActiveEmployeeLeaves(pageable);
        List<LeaveResponse> leaveResponses = new ArrayList<>();
        if (listOfAllEmployeeLeaves.isEmpty()) {
            log.error("Exit!! AdminServiceImpl :: listOfAllEmployeeLeaves :: No any employees are found");
            return new PageResponseVo(0,0,0L,new Object());
        }
        listOfAllEmployeeLeaves.stream().forEachOrdered(leaves -> leaveResponses.add(getLeaveResponse(leaves)));
        log.debug("Exit!! AdminServiceImpl :: listOfAllEmployeeLeaves :: Successfully!!");
        return new PageResponseVo(listOfAllEmployeeLeaves.getTotalPages(),listOfAllEmployeeLeaves.getNumberOfElements(),listOfAllEmployeeLeaves.getTotalElements(),leaveResponses);
    }

    public LeaveResponse getLeaveResponse(LeaveAudit leaveAudit) {
        log.debug("Entry!! AdminServiceImpl :: getLeaveResponse ");
        LeaveResponse leaveDataResponse = new LeaveResponse();
        leaveDataResponse.setLeaveId(leaveAudit.getId());
        leaveDataResponse.setEmployeeCode(leaveAudit.getEmployee().getEmployeeCode());
        leaveDataResponse.setFirstName(leaveAudit.getEmployee().getFirstName());
        leaveDataResponse.setLastName(leaveAudit.getEmployee().getLastName());
        leaveDataResponse.setLeaveType(leaveAudit.getLeaveType().name());
        leaveDataResponse.setDesignationName(leaveAudit.getEmployee().getDesignation().getDesignationName());
        leaveDataResponse.setFromDate(leaveAudit.getFromDate());
        leaveDataResponse.setToDate(leaveAudit.getToDate());
        leaveDataResponse.setRemark(leaveAudit.getRemark());
        leaveDataResponse.setStatus(leaveAudit.getStatus());
        leaveDataResponse.setRejectionMessage(leaveAudit.getRejectionMessage());
        log.debug("Exit!! AdminServiceImpl :: getLeaveResponse ");
        return leaveDataResponse;
    }

    @Override
    public List<Designation> listOfAllDesignation() {
        log.debug("Entry!! AdminServiceImpl :: listOfAllDesignation ");
        List<Designation> listOfAllDesignation = designationRepository.findAll();
        if (listOfAllDesignation.isEmpty()) {
            log.error("Exit!! AdminServiceImpl :: listOfAllDesignation :: No any designation are available!!");
            return new ArrayList<>();
        }
        log.debug("Exit!! AdminServiceImpl :: listOfAllDesignation ");
        return listOfAllDesignation;
    }

}
