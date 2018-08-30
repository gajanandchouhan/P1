package com.superlifesecretcode.app.ui.book.forth;

import com.superlifesecretcode.app.data.model.BaseResponseModel;

import java.util.ArrayList;

public class StoreBean extends BaseResponseModel{

    ArrayList<Stores> stores;

    public ArrayList<Stores> getStores() {
        return stores;
    }

    public void setStores(ArrayList<Stores> stores) {
        this.stores = stores;
    }
}
