package com.synoverge.leave.portal.models.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class EmployeeRegisterRequest {

    @NotBlank(message = "Employee Code is mandatory")
    @NotNull(message = "Employee Code is mandatory")
    private String employeeCode;
    @NotBlank(message = "First name is mandatory")
    @NotNull(message = "First name is mandatory")
    private String firstName;
    @NotBlank(message = "Last name is mandatory")
    private String lastName;
    @NotNull(message = "Designation is mandatory")
    private Long designationId;
    @NotNull(message = "Address line 1 is mandatory")
    private String addressLine1;
    @NotNull(message = "Address line 2 is mandatory")
    private String addressLine2;
    @NotNull(message = "Address line 3 is mandatory")
    private String addressLine3;
    @NotNull(message = "Contact number is mandatory")
    private String contactNumber;
    private int age;
    @NotBlank(message = "Email is mandatory")
    private String email;
    @NotNull(message = "Role is mandatory")
    private Long roleId;
    @NotBlank(message = "Password is mandatory")
    @NotNull(message = "Password is mandatory")
    private String password;

    public EmployeeRegisterRequest() {
    }

    public EmployeeRegisterRequest(String employeeCode, String firstName, String lastName, Long designationId, String addressLine1, String addressLine2, String addressLine3, String contactNumber, int age, String email, Long roleId, String password) {
        this.employeeCode = employeeCode;
        this.firstName = firstName;
        this.lastName = lastName;
        this.designationId = designationId;
        this.addressLine1 = addressLine1;
        this.addressLine2 = addressLine2;
        this.addressLine3 = addressLine3;
        this.contactNumber = contactNumber;
        this.age = age;
        this.email = email;
        this.roleId = roleId;
        this.password = password;
    }

    public String getEmployeeCode() {
        return employeeCode;
    }

    public void setEmployeeCode(String employeeCode) {
        this.employeeCode = employeeCode;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Long getDesignationId() {
        return designationId;
    }

    public void setDesignationId(Long designationId) {
        this.designationId = designationId;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public String getAddressLine3() {
        return addressLine3;
    }

    public void setAddressLine3(String addressLine3) {
        this.addressLine3 = addressLine3;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
