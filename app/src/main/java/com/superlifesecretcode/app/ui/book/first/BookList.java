package com.superlifesecretcode.app.ui.book.first;

import com.superlifesecretcode.app.data.model.BaseResponseModel;

import java.util.ArrayList;

public class BookList  extends BaseResponseModel{

    String total;
    ArrayList<BookBean> data;


    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public ArrayList<BookBean> getData() {
        return data;
    }

    public void setData(ArrayList<BookBean> data) {
        this.data = data;
    }
}
