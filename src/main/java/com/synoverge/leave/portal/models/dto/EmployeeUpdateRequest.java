package com.synoverge.leave.portal.models.dto;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class EmployeeUpdateRequest {

    @NotBlank(message = "First name is mandatory")
    @NotNull(message = "First name is mandatory")
    private String firstName;
    @NotBlank(message = "Last name is mandatory")
    @NotNull(message = "Last name is mandatory")
    private String lastName;
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
    @NotNull(message = "Email is mandatory")
    private String email;

    public EmployeeUpdateRequest() {
    }

    public EmployeeUpdateRequest(String firstName, String lastName, String addressLine1, String addressLine2, String addressLine3, String contactNumber, int age, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.addressLine1 = addressLine1;
        this.addressLine2 = addressLine2;
        this.addressLine3 = addressLine3;
        this.contactNumber = contactNumber;
        this.age = age;
        this.email = email;
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
}
