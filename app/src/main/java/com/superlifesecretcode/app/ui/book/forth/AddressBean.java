package com.superlifesecretcode.app.ui.book.forth;

import android.location.Address;

import com.superlifesecretcode.app.data.model.BaseResponseModel;

import org.bouncycastle.util.Store;

import java.util.ArrayList;

public class AddressBean extends BaseResponseModel{

    ArrayList<Stores> data;

    public ArrayList<Stores> getData() {
        return data;
    }

    public void setData(ArrayList<Stores> data) {
        this.data = data;
    }
}
