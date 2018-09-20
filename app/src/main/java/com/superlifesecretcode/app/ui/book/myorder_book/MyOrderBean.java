package com.superlifesecretcode.app.ui.book.myorder_book;

import com.superlifesecretcode.app.data.model.BaseResponseModel;
import com.superlifesecretcode.app.ui.book.first.Currency;

import java.util.ArrayList;

public class MyOrderBean extends BaseResponseModel{

    ArrayList<Order> data;
    Currency currencyUnit;

    public Currency getCurrencyUnit() {
        return currencyUnit;
    }

    public void setCurrencyUnit(Currency currencyUnit) {
        this.currencyUnit = currencyUnit;
    }

    public ArrayList<Order> getData() {
        return data;
    }

    public void setData(ArrayList<Order> data) {
        this.data = data;
    }
}
