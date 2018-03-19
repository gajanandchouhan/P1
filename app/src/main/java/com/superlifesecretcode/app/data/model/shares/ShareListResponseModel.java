package com.superlifesecretcode.app.data.model.shares;

import com.superlifesecretcode.app.data.model.BaseResponseModel;

import java.util.List;

/**
 * Created by Divya on 19-03-2018.
 */

public class ShareListResponseModel extends BaseResponseModel {
    private List<ShareListResponseData> data;

    public void setData(List<ShareListResponseData> data) {
        this.data = data;
    }

    public List<ShareListResponseData> getData() {
        return data;
    }
}
