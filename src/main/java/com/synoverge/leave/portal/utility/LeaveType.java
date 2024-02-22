package com.synoverge.leave.portal.utility;

public enum LeaveType {
    SICK_LEAVE(1,"Sick-Leave"),
    PRIVILEGE_LEAVE(2,"Privilege-Leave"),
    CASUAL_LEAVE(3,"Casual-leave");

    final int id;
    final String leaveType;

    LeaveType(int id, String leaveType) {
        this.id = id;
        this.leaveType = leaveType;
    }

    public int getId() {
        return id;
    }

    public String getLeaveType() {
        return leaveType;
    }
}
