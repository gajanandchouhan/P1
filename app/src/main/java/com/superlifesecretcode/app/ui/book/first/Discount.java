package com.superlifesecretcode.app.ui.book.first;

import java.io.Serializable;

public class Discount implements Serializable{

    String min_qty;
    String max_qty;
    String discount_type;
    double discount_amount;

    public String getMin_qty() {
        return min_qty;
    }

    public void setMin_qty(String min_qty) {
        this.min_qty = min_qty;
    }

    public String getMax_qty() {
        return max_qty;
    }

    public void setMax_qty(String max_qty) {
        this.max_qty = max_qty;
    }

    public String getDiscount_type() {
        return discount_type;
    }

    public void setDiscount_type(String discount_type) {
        this.discount_type = discount_type;
    }

    public double getDiscount_amount() {
        return discount_amount;
    }

    public void setDiscount_amount(double discount_amount) {
        this.discount_amount = discount_amount;
    }
}
