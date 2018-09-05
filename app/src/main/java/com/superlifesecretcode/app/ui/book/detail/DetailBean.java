package com.superlifesecretcode.app.ui.book.detail;

import com.superlifesecretcode.app.data.model.BaseResponseModel;

public class DetailBean extends BaseResponseModel {

    MyOrderDetailBook data;

    public MyOrderDetailBook getData() {
        return data;
    }

    public void setData(MyOrderDetailBook data) {
        this.data = data;
    }


}
