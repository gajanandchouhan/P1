package com.superlifesecretcode.app.ui.book.forth;

import android.location.Address;

import com.superlifesecretcode.app.data.model.BaseResponseModel;

import java.util.ArrayList;

public class AddressBean extends BaseResponseModel{

    ArrayList<Addresss> data;

    public ArrayList<Addresss> getData() {
        return data;
    }

    public void setData(ArrayList<Addresss> data) {
        this.data = data;
    }
}
