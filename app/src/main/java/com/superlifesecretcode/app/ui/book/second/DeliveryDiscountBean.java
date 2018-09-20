package com.superlifesecretcode.app.ui.book.second;

import java.util.ArrayList;

public class DeliveryDiscountBean {

    ArrayList<DeliveryData> delivery_charges;
    ArrayList<Discount> discounts;

    public ArrayList<DeliveryData> getDelivery_charges() {
        return delivery_charges;
    }

    public void setDelivery_charges(ArrayList<DeliveryData> delivery_charges) {
        this.delivery_charges = delivery_charges;
    }

    public ArrayList<Discount> getDiscounts() {
        return discounts;
    }

    public void setDiscounts(ArrayList<Discount> discounts) {
        this.discounts = discounts;
    }
}
