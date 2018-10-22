package com.superlifesecretcode.app.data.model.deliverycost;

import com.superlifesecretcode.app.data.model.BaseResponseModel;

public class DeliveryCostReponseModel extends BaseResponseModel {

    private String delivery_charges;

    public String getDelivery_charges() {
        return delivery_charges;
    }

    public void setDelivery_charges(String delivery_charges) {
        this.delivery_charges = delivery_charges;
    }
}
