package com.superlifesecretcode.app.ui.book.myorder_book;

import com.superlifesecretcode.app.data.model.BaseResponseModel;

import java.util.ArrayList;

public class MyOrderBean extends BaseResponseModel{

    ArrayList<Order> data;

    public ArrayList<Order> getData() {
        return data;
    }

    public void setData(ArrayList<Order> data) {
        this.data = data;
    }
}
