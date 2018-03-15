package com.superlifesecretcode.app.data.model.news;

import com.superlifesecretcode.app.data.model.BaseResponseModel;

/**
 * Created by Divya on 15-03-2018.
 */

public class SingleNewsResponseModel extends BaseResponseModel {
    private NewsResponseData data;

    public void setData(NewsResponseData data) {
        this.data = data;
    }

    public NewsResponseData getData() {
        return data;
    }
}
