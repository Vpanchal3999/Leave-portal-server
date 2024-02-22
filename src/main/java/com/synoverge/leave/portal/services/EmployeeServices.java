package com.synoverge.leave.portal.services;

import com.synoverge.leave.portal.DTO.BaseResponseEntity;
import com.synoverge.leave.portal.models.dto.*;
import com.synoverge.leave.portal.models.entity.Employee;
import com.synoverge.leave.portal.models.entity.LeaveAudit;
import org.springframework.data.domain.Page;

import java.util.List;

public interface EmployeeServices {

     String registerEmployee(EmployeeRegisterRequest employee);
     PageResponseVo getAllEmployeeDetails(PaginationRequest paginationRequest);
     EmployeeDTO getEmployeeByCode(String empCode);
     void leaveApply(LeaveRequest leaveData,String empCode);
     Employee updateEmployeeDetails(String empCode, EmployeeUpdateRequest employee);
     PageResponseVo appliedLeaveByEmployee(String empCode,PaginationRequest paginationRequest);
     String deleteEmployee(String empCode);
     String enableEmployee(String empCode,Boolean active);
}
