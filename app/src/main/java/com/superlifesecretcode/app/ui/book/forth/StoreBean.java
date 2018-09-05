package com.superlifesecretcode.app.ui.book.forth;

import com.superlifesecretcode.app.data.model.BaseResponseModel;

import java.util.ArrayList;

public class StoreBean extends BaseResponseModel{

    ArrayList<Stores> data;

    public ArrayList<Stores> getStores() {
        return data;
    }

    public void setStores(ArrayList<Stores> stores) {
        this.data = data;
    }
}
