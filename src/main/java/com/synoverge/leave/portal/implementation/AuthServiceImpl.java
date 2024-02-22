package com.synoverge.leave.portal.implementation;

import com.synoverge.leave.portal.Exceptions.InActiveEmployeeException;
import com.synoverge.leave.portal.config.JwtTokenUtil;
import com.synoverge.leave.portal.models.dto.AuthDTO;
import com.synoverge.leave.portal.models.dto.EmployeeDTO;
import com.synoverge.leave.portal.models.entity.Employee;
import com.synoverge.leave.portal.repository.EmployeeRepository;
import com.synoverge.leave.portal.security.JwtUserDetailsService;
import com.synoverge.leave.portal.services.AuthService;
import com.synoverge.leave.portal.utility.ErrorMessages;
import com.synoverge.leave.portal.utility.SuccessMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.InvalidParameterException;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService {

    private final Logger logger = LoggerFactory.getLogger(AuthServiceImpl.class);
    private final EmployeeRepository employeeRepository;

    private final AuthenticationManager authenticationManager;

    private final JwtTokenUtil jwtTokenUtil;

    private final JwtUserDetailsService userDetailsService;

    private final PasswordEncoder bcryptEncoder;

    @Autowired
    public AuthServiceImpl(EmployeeRepository employeeRepository, AuthenticationManager authenticationManager, JwtTokenUtil jwtTokenUtil, JwtUserDetailsService userDetailsService, PasswordEncoder bcryptEncoder) {
        this.employeeRepository = employeeRepository;
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
        this.userDetailsService = userDetailsService;
        this.bcryptEncoder = bcryptEncoder;
    }

    @Override
    public EmployeeDTO login(AuthDTO authDTO) {
        logger.debug("Entry!! :: AuthServiceImpl :: login :: with employeeCode => {}", authDTO.getEmployeeCode());
        Optional<Employee> employee = employeeRepository.findByEmployeeCode(authDTO.getEmployeeCode());
        if (employee.isEmpty()) {
            logger.error("Exit!! :: AuthServiceImpl :: login :: employee doesn't exist!!");
            throw new NoSuchElementException(ErrorMessages.EMPLOYEE_CODE_NOT_FOUND);
        }
        if (employee.get().getActive() != Boolean.TRUE) {
            logger.error("EmployeeServiceImpl :: Login :: Employee is In-Active!!");
            throw new InActiveEmployeeException(ErrorMessages.INACTIVE_EMPLOYEE);
        }
        Employee loginEmployee = employee.get();
        final Authentication authentication = authenticate(authDTO.getEmployeeCode(), authDTO.getPassword());
        final String token = jwtTokenUtil.generateToken(authentication);
        logger.debug("Exit!! :: AuthServiceImpl :: login :: Successfully!!!");
        return new EmployeeDTO(loginEmployee.getId(), loginEmployee.getEmployeeCode(), loginEmployee.getFirstName(), loginEmployee.getLastName(), loginEmployee.getDesignation().getDesignationName(), loginEmployee.getDesignation().getDepartment().getDepartmentCode(), loginEmployee.getDesignation().getDepartment().getDepartmentName(), loginEmployee.getAddressLine1(), loginEmployee.getAddressLine2(), loginEmployee.getAddressLine3(), loginEmployee.getContactNumber(), loginEmployee.getAge(), loginEmployee.getEmail(), loginEmployee.getRoles(), loginEmployee.getActive(), token, loginEmployee.getCreatedAt(), loginEmployee.getCreatedBy(), loginEmployee.getModifiedAt(), loginEmployee.getModifyBy());
    }

    @Override
    public String changePassword(AuthDTO authDTO) {
        logger.debug("Entry!! :: AuthServiceImpl :: changePassword :: with employeeCode => {}", authDTO.getEmployeeCode());
        if (authDTO.getEmployeeCode() == null && authDTO.getPassword() == null && authDTO.getOldPassword() == null && authDTO.getConfirmPassword() == null) {
            throw new InvalidParameterException(ErrorMessages.EMPTY_FIELDS);
        }
        Optional<Employee> employee = employeeRepository.findByEmployeeCode(authDTO.getEmployeeCode());
        if (employee.isEmpty()) {
            logger.error("Exit!! :: AuthServiceImpl :: changePassword :: NoSuchElementException ");
            throw new NoSuchElementException(ErrorMessages.EMPLOYEE_CODE_NOT_FOUND);
        }
        if (employee.get().getActive() != Boolean.TRUE) {
            logger.error("EmployeeServiceImpl :: changePassword :: Employee is In-Active!!");
            throw new InActiveEmployeeException(ErrorMessages.INACTIVE_EMPLOYEE);
        }
        if (!(bcryptEncoder.matches(authDTO.getOldPassword(), employee.get().getPassword()))) {
            logger.error("Exit!! :: AuthServiceImpl :: changePassword :: old password doesn't match");
            throw new IllegalArgumentException(ErrorMessages.OLD_NEW_PASSWORD_INVALID);
        }
        if (!authDTO.getPassword().equals(authDTO.getConfirmPassword())) {
            logger.error("Exit!! :: AuthServiceImpl :: changePassword :: confirm and new password is not matched!!..");
            throw new InputMismatchException(ErrorMessages.ILLEGAL_PASSWORD_MATCH);
        }
        employee.get().setPassword(bcryptEncoder.encode(authDTO.getPassword()));
        try {
            employeeRepository.save(employee.get());
        } catch (RuntimeException ex) {
            throw new RuntimeException(ErrorMessages.DATA_BASE_ERROR);
        }
        logger.debug("Exit!! :: AuthServiceImpl :: changePassword :: Successfully!!");
        return SuccessMessage.changePasswordSuccessfully;
    }

    @Override
    public String forgotPassword(AuthDTO authDTO) {
        logger.debug("Entry!! :: AuthServiceImpl :: forgotPassword :: with employeeCode => {}", authDTO.getEmployeeCode());
        if (authDTO.getEmployeeCode() == null && authDTO.getPassword() == null) {
            logger.error("Exit!! :: AuthServiceImpl :: forgotPassword :: InvalidParameterException => {}", authDTO);
            throw new InvalidParameterException(ErrorMessages.EMPTY_FIELDS);
        }
        Optional<Employee> employee = employeeRepository.findByEmployeeCode(authDTO.getEmployeeCode());
        if (employee.isEmpty()) {
            logger.error("Exit!! :: AuthServiceImpl :: forgotPassword :: Employee doesn't match");
            throw new NoSuchElementException(ErrorMessages.EMPLOYEE_CODE_NOT_FOUND);
        }
        if (employee.get().getActive() != Boolean.TRUE) {
            logger.error("EmployeeServiceImpl :: forgotPassword :: Employee is In-Active!!");
            throw new InActiveEmployeeException(ErrorMessages.INACTIVE_EMPLOYEE);
        }
        employee.get().setPassword(bcryptEncoder.encode(authDTO.getPassword()));
        try {
            employeeRepository.save(employee.get());
        } catch (RuntimeException ex) {
            throw new RuntimeException(ErrorMessages.DATA_BASE_ERROR);
        }
        logger.debug("Entry!! :: AuthServiceImpl :: forgotPassword :: Successfully!!");
        return SuccessMessage.newPasswordSuccessfully;
    }

    private Authentication authenticate(String employeeCode, String password) {
        try {
            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(employeeCode, password));
        } catch (DisabledException e) {
            throw new IllegalArgumentException("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new IllegalArgumentException(ErrorMessages.ID_PASSWORD_NOT_MATCHED, e);
        }
    }
}
