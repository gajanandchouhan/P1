package com.superlifesecretcode.app.data.model.allmenu;

import com.superlifesecretcode.app.data.model.BaseResponseModel;

/**
 * Created by Divya on 31-03-2018.
 */

public class AllMenuResponseModel extends BaseResponseModel {
    private AllMenuResponseData data;

    public AllMenuResponseData getData() {
        return data;
    }

    public void setData(AllMenuResponseData data) {
        this.data = data;
    }
}
