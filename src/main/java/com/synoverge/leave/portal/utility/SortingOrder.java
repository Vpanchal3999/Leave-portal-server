package com.synoverge.leave.portal.utility;

public enum SortingOrder {
    DEC(1,"DESCENDING"),
    ASC(2,"ASCENDING");

    final int id;
    final String directionName;
    SortingOrder(int id, String directionName) {
        this.id = id;
        this.directionName = directionName;
    }

    public int getId() {
        return id;
    }

    public String getDirectionName() {
        return directionName;
    }
}
