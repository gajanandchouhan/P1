package com.superlifesecretcode.app.ui.book.second;

import com.superlifesecretcode.app.data.model.BaseResponseModel;

import java.util.ArrayList;

public class Delivery extends BaseResponseModel{
    ArrayList<DeliveryData> data;

    public ArrayList<DeliveryData> getData() {
        return data;
    }

    public void setData(ArrayList<DeliveryData> data) {
        this.data = data;
    }
}
