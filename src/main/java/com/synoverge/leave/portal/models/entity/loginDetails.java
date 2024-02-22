package com.synoverge.leave.portal.models.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

public class loginDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String username;
    private String password;

}
