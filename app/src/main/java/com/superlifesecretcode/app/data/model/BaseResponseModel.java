package com.superlifesecretcode.app.data.model;

/**
 * Created by Divya on 21-02-2018.
 */

public class BaseResponseModel {
    private String status;
    private String message;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
