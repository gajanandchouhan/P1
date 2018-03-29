package com.superlifesecretcode.app.data.model.countryactivities;

import com.superlifesecretcode.app.data.model.BaseResponseModel;

/**
 * Created by Divya on 29-03-2018.
 */

public class CountryActivitiesResponseModel extends BaseResponseModel {
    private int total;
    private CounActivtyResponseData data;


    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public CounActivtyResponseData getData() {
        return data;
    }

    public void setData(CounActivtyResponseData data) {
        this.data = data;
    }
}
