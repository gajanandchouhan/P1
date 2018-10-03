package com.superlifesecretcode.app.ui.book.second;

import java.util.ArrayList;

public class DeliveryDiscountBean {

    String information;
    ArrayList<DeliveryData> delivery_charges;
    ArrayList<Discount> discounts;
    String print_affordability_des , print_quantity_des;

    public String getPrint_affordability_des() {
        return print_affordability_des;
    }

    public void setPrint_affordability_des(String print_affordability_des) {
        this.print_affordability_des = print_affordability_des;
    }

    public String getPrint_quantity_des() {
        return print_quantity_des;
    }

    public void setPrint_quantity_des(String print_quantity_des) {
        this.print_quantity_des = print_quantity_des;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

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
