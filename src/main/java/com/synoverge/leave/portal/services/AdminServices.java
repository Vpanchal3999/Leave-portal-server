package com.synoverge.leave.portal.services;

import com.synoverge.leave.portal.models.dto.*;
import com.synoverge.leave.portal.models.entity.Designation;
import com.synoverge.leave.portal.models.entity.LeaveAudit;
import com.synoverge.leave.portal.utility.Status;

import java.util.List;

public interface AdminServices {

   String updateLeaveStatus(LeaveRejectRequest leaveRequest);

   PageResponseVo listOfAllEmployeeLeaves(PaginationRequest paginationRequest);

   List<Designation> listOfAllDesignation();


}
