package com.synoverge.leave.portal.models.dto;

import com.synoverge.leave.portal.utility.LeaveType;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

public class LeaveRequest {

    private Long leaveId;
    @NotEmpty(message = "Employee code is mandatory")
    private String employeeCode;

    private String status;
    @NotNull(message = "Leave type is mandatory")
    @Enumerated(EnumType.STRING)
    private LeaveType leaveType;
    @NotNull(message = "From date is mandatory")
    private Date fromDate;
    @NotNull(message = "To date is mandatory")
    private Date toDate;
    @NotEmpty(message = "Remark is mandatory")
    private String remark;

    private String rejectReason;


    public LeaveRequest() {
    }

    public LeaveRequest(Long leaveId, String employeeCode, String status, LeaveType leaveType, Date fromDate, Date toDate, String remark, String rejectReason) {
        this.leaveId = leaveId;
        this.employeeCode = employeeCode;
        this.status = status;
        this.leaveType = leaveType;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.remark = remark;
        this.rejectReason = rejectReason;
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

    public LeaveType getLeaveType() {
        return leaveType;
    }

    public void setLeaveType(LeaveType leaveType) {
        this.leaveType = leaveType;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getRejectReason() {
        return rejectReason;
    }

    public void setRejectReason(String rejectReason) {
        this.rejectReason = rejectReason;
    }
}
