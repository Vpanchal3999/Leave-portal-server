package com.synoverge.leave.portal.utility;

import javax.servlet.http.PushBuilder;

public class ErrorMessages {

    public static final String  EMPLOYEE_CODE_NOT_FOUND = "Employee doesn't exist with this code!!...";
    public static final String EMPLOYEE_LIST_NOT_FOUND = "No any employee's list are found!!...";
    public static final String NO_LEAVE_TAKEN_BY_EMPLOYEE = "No any leaves taken by you!!..";
    public static final String EMPTY_FIELDS = "Bad request!.. Please fill the proper values!!..";
    public static final String ILLEGAL_DATE = "You can not take leave!!.. Because of you choose the passed date.";
    public static final String ILLEGAL_PASSWORD_MATCH = "Confirm password and new password is not matched!!..";
    public static final String OLD_NEW_PASSWORD_INVALID = "Old password doesn't match with the existing!!..";
    public static final String ID_PASSWORD_NOT_MATCHED = "Cred doesn't match!!.. Please try again";
    public static final String DATA_BASE_ERROR = "Data is not save successfully!!..";
    public static final String TOKEN_EXPIRE = "Token is expire!!..Please Login again!!..";
    public static final String INACTIVE_EMPLOYEE = "Employee is currently in active!!...";
    public static final String USER_EXIST = "This user is already exist!!..";

    public static final String RESTRICT_DELETE_SUPER_ADMIN = "You can't delete Super-Admin!!..";
}
