package com.synoverge.leave.portal.utility;

public enum Roles {

    SUPER_ADMIN(1,"Super-admin"),
    ADMIN(2,"Admin"),
    EMPLOYEE(3,"Employee");


    final int id;
    final String roleName;

    Roles(int id, String roleName) {
        this.id = id;
        this.roleName = roleName;
    }

    public int getId() {
        return id;
    }

    public String getRoleName() {
        return roleName;
    }

    
}
