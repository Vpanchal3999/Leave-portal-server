package com.synoverge.leave.portal.utility;

public enum Status {

    APPROVED(1,"APPROVED"),
    PENDING(2,"PENDING"),
    REJECTED(3,"REJECTED");

    final int id;
    final String statusName;

    Status(int id, String statusName) {
        this.id = id;
        this.statusName = statusName;
    }

    public int getId() {
        return id;
    }

    public String getStatusName() {
        return statusName;
    }
}
