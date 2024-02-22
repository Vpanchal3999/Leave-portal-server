package com.synoverge.leave.portal.DTO;

public class BaseResponseEntity {

    public int responseCode;
    public String responseStatus;
    public String responseMessage;

    public Object data;

    public BaseResponseEntity() {
    }

    public BaseResponseEntity(int responseCode, String responseStatus, String responseMessage, Object data) {
        this.responseCode = responseCode;
        this.responseStatus = responseStatus;
        this.responseMessage = responseMessage;
        this.data =  data;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public String getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(String responseStatus) {
        this.responseStatus = responseStatus;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}

