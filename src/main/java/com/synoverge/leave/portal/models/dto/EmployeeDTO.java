package com.synoverge.leave.portal.models.dto;

import com.synoverge.leave.portal.models.entity.RoleEntity;

import java.time.ZonedDateTime;

public class EmployeeDTO {

    private Long id;
    private String employeeCode;
    private String firstName;
    private String lastName;
    private String designationName;
    private String departmentCode;
    private String departmentName;
    private String addressLine1;
    private String addressLine2;
    private String addressLine3;
    private String contactNumber;
    private int age;
    private String email;
    private RoleEntity roles;
    private Boolean isActive;
    private String token;

    private ZonedDateTime createdAt;

    private String createdBy;
    private ZonedDateTime modifyAt;
    private String modifyBy;

    public EmployeeDTO() {
    }

    public EmployeeDTO(Long id, String employeeCode, String firstName, String lastName, String designationName, String departmentCode, String departmentName, String addressLine1, String addressLine2, String addressLine3, String contactNumber, int age, String email, RoleEntity roles, Boolean isActive, String token, ZonedDateTime createdAt, String createdBy, ZonedDateTime modifyAt, String modifyBy) {
        this.id = id;
        this.employeeCode = employeeCode;
        this.firstName = firstName;
        this.lastName = lastName;
        this.designationName = designationName;
        this.departmentCode = departmentCode;
        this.departmentName = departmentName;
        this.addressLine1 = addressLine1;
        this.addressLine2 = addressLine2;
        this.addressLine3 = addressLine3;
        this.contactNumber = contactNumber;
        this.age = age;
        this.email = email;
        this.roles = roles;
        this.isActive = isActive;
        this.token = token;
        this.createdAt = createdAt;
        this.createdBy = createdBy;
        this.modifyAt = modifyAt;
        this.modifyBy = modifyBy;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getDesignationName() {
        return designationName;
    }

    public void setDesignationName(String designationName) {
        this.designationName = designationName;
    }

    public String getDepartmentCode() {
        return departmentCode;
    }

    public void setDepartmentCode(String departmentCode) {
        this.departmentCode = departmentCode;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
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

    public RoleEntity getRoles() {
        return roles;
    }

    public void setRoles(RoleEntity roles) {
        this.roles = roles;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public ZonedDateTime getModifyAt() {
        return modifyAt;
    }

    public void setModifyAt(ZonedDateTime modifyAt) {
        this.modifyAt = modifyAt;
    }

    public String getModifyBy() {
        return modifyBy;
    }

    public void setModifyBy(String modifyBy) {
        this.modifyBy = modifyBy;
    }
}
