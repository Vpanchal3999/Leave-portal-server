package com.synoverge.leave.portal.models.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="department")
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "department_code",unique = true)
    private String departmentCode;
    @Column(name = "department_name")
    private String departmentName;

    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Designation> designation;

    public Department() {
    }
    public Department(Long id, String departmentCode, String departmentName) {
        this.id = id;
        this.departmentCode = departmentCode;
        this.departmentName = departmentName;
    }

    public Department(Long id, String departmentCode, String departmentName, List<Designation> designation) {
        this.id = id;
        this.departmentCode = departmentCode;
        this.departmentName = departmentName;
        this.designation = designation;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public List<Designation> getDesignation() {
        return designation;
    }

    public void setDesignation(List<Designation> designation) {
        this.designation = designation;
    }
}
