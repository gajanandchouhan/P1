package com.superlifesecretcode.app.ui.book.five;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;


public class BankBean {

    String status;
    String message;

    @SerializedName("data")
    ArrayList<Bank> bankList;

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

    public ArrayList<Bank> getBankList() {
        return bankList;
    }

    public void setBankList(ArrayList<Bank> bankList) {
        this.bankList = bankList;
    }
}
