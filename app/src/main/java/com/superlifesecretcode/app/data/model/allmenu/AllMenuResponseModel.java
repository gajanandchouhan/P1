package com.superlifesecretcode.app.data.model.allmenu;

import com.superlifesecretcode.app.data.model.BaseResponseModel;

import java.util.List;

/**
 * Created by Divya on 31-03-2018.
 */

public class AllMenuResponseModel extends BaseResponseModel {
    private List<AllMenuResponseData> data;

    public List<AllMenuResponseData> getData() {
        return data;
    }

    public void setData(List<AllMenuResponseData> data) {
        this.data = data;
    }
}
