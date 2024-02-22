package com.synoverge.leave.portal.models.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.synoverge.leave.portal.utility.LeaveType;
import com.synoverge.leave.portal.utility.Status;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name="leave_audit")
public class LeaveAudit implements Serializable,Comparable<LeaveAudit> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
    private Long id;

    @Column(name="leave_type")
    @Enumerated(EnumType.STRING)
    private LeaveType leaveType;

    @Column(name="remark")
    private String remark;
    @Column(name="leave_from_date")
    @JsonFormat(pattern = "dd-MM-yyyy")
    private Date fromDate;
    @Column(name="leave_to_date")
    @JsonFormat(pattern = "dd-MM-yyyy")
    private Date toDate;
    @Column(name="status")
    @Enumerated(EnumType.STRING)
    private Status status;
    @Column(name="reject_msg")
    private String rejectionMessage;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "emp_code", referencedColumnName = "emp_code")
    private Employee employee;

    public LeaveAudit() {
    }

    public LeaveAudit(LeaveType leaveType, String remark, Date fromDate, Date toDate, Status status, String rejectionMessage, Employee employee) {
        this.leaveType = leaveType;
        this.remark = remark;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.status = status;
        this.rejectionMessage = rejectionMessage;
        this.employee = employee;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LeaveType getLeaveType() {
        return leaveType;
    }

    public void setLeaveType(LeaveType leaveType) {
        this.leaveType = leaveType;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

    public void setStatus(Status status) {
        this.status = status;
    }

    public Status getStatus() {
        return status;
    }

    public String getRejectionMessage() {
        return rejectionMessage;
    }

    public void setRejectionMessage(String rejectionMessage) {
        this.rejectionMessage = rejectionMessage;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    @Override
    public int compareTo(LeaveAudit o) {
        return o.getFromDate().compareTo(getFromDate());
    }
}
