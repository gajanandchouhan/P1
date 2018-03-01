package com.superlifesecretcode.app.data.model.userdetails;

import com.superlifesecretcode.app.data.model.BaseResponseModel;

/**
 * Created by Divya on 01-03-2018.
 */

public class UserDetailResponseModel extends BaseResponseModel {

    private UserDetailResponseData data;

    public UserDetailResponseData getData() {
        return data;
    }

    public void setData(UserDetailResponseData data) {
        this.data = data;
    }
}
