package com.superlifesecretcode.app.ui.book.detail;

import com.superlifesecretcode.app.data.model.BaseResponseModel;
import com.superlifesecretcode.app.ui.book.first.Currency;

public class DetailBean extends BaseResponseModel {

    MyOrderDetailBook data;
    Currency currencyUnit;

    public Currency getCurrencyUnit() {
        return currencyUnit;
    }

    public void setCurrencyUnit(Currency currencyUnit) {
        this.currencyUnit = currencyUnit;
    }

    public MyOrderDetailBook getData() {
        return data;
    }

    public void setData(MyOrderDetailBook data) {
        this.data = data;
    }


}
