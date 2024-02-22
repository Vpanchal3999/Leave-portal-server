package com.synoverge.leave.portal.models.dto;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

public class AuthDTO implements Serializable {

    private static final long serialVersionUID = 5926468583005150707L;

    @NotEmpty(message = "Employee code is mandatory")
    private String employeeCode;
    @NotEmpty(message = "Employee code is mandatory")
    private String password;
    private String oldPassword;
    private String confirmPassword;

    public AuthDTO() {
    }

    public AuthDTO(String employeeCode, String password, String oldPassword, String confirmPassword) {
        this.employeeCode = employeeCode;
        this.password = password;
        this.oldPassword = oldPassword;
        this.confirmPassword = confirmPassword;
    }

    public String getEmployeeCode() {
        return employeeCode;
    }

    public void setEmployeeCode(String employeeCode) {
        this.employeeCode = employeeCode;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
