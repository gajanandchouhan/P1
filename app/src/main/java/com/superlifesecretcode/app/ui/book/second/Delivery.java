package com.superlifesecretcode.app.ui.book.second;

import com.superlifesecretcode.app.data.model.BaseResponseModel;

import java.util.ArrayList;

public class Delivery extends BaseResponseModel{
    DeliveryDiscountBean  data;

    public DeliveryDiscountBean getData() {
        return data;
    }

    public void setData(DeliveryDiscountBean data) {
        this.data = data;
    }
}
