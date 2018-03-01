package com.superlifesecretcode.app.data.model.country;

import com.superlifesecretcode.app.data.model.BaseResponseModel;

import java.util.List;

/**
 * Created by Divya on 01-03-2018.
 */

public class CountryResponseModel extends BaseResponseModel {
    private List<CountryResponseData> data;

    public List<CountryResponseData> getData() {
        return data;
    }

    public void setData(List<CountryResponseData> data) {
        this.data = data;
    }
}
