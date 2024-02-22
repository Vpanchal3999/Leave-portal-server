package com.synoverge.leave.portal.services;

import com.synoverge.leave.portal.models.dto.AuthDTO;
import com.synoverge.leave.portal.models.dto.EmployeeDTO;
import com.synoverge.leave.portal.models.entity.Employee;
import io.jsonwebtoken.Claims;

public interface AuthService {

    EmployeeDTO login(AuthDTO authDTO);

    String changePassword(AuthDTO authDTO);

    String forgotPassword(AuthDTO authDTO);

}
