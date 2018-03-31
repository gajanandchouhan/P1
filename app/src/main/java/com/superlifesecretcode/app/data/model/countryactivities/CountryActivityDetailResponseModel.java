package com.superlifesecretcode.app.data.model.countryactivities;

import com.superlifesecretcode.app.data.model.BaseResponseModel;

/**
 * Created by Divya on 31-03-2018.
 */

public class CountryActivityDetailResponseModel extends BaseResponseModel {
    private CountryActivityInfoModel data;

    public CountryActivityInfoModel getData() {
        return data;
    }

    public void setData(CountryActivityInfoModel data) {
        this.data = data;
    }
}
