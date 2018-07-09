package com.superlifesecretcode.app.data.model;

/**
 * Created by Divya on 21-02-2018.
 */

public class BaseResponseModel {
    private String status;
    private String message;
    private String already_joined;

    public String getAlready_joined() {
        return already_joined;
    }

    public void setAlready_joined(String already_joined) {
        this.already_joined = already_joined;
    }

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
