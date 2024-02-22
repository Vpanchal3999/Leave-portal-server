package com.synoverge.leave.portal.models.dto;

import com.synoverge.leave.portal.utility.LeaveType;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

public class LeaveRejectRequest {
    @NotNull(message ="Leave id is Mandatory")
    private Long leaveId;
    @NotNull(message ="Employee code is Mandatory")
    private String employeeCode;
    @NotNull(message ="Employee code is Mandatory")
    private String status;
    private String rejectReason;

    public LeaveRejectRequest() {
    }

    public Long getLeaveId() {
        return leaveId;
    }

    public void setLeaveId(Long leaveId) {
        this.leaveId = leaveId;
    }

    public String getEmployeeCode() {
        return employeeCode;
    }

    public void setEmployeeCode(String employeeCode) {
        this.employeeCode = employeeCode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRejectReason() {
        return rejectReason;
    }

    public void setRejectReason(String rejectReason) {
        this.rejectReason = rejectReason;
    }
}
