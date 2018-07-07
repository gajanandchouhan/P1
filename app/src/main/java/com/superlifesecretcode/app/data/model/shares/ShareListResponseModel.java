package com.superlifesecretcode.app.data.model.shares;

import com.superlifesecretcode.app.data.model.BaseResponseModel;

import java.util.List;

/**
 * Created by Divya on 19-03-2018.
 */

public class ShareListResponseModel extends BaseResponseModel {
    private String nextPageUrl;
    private List<ShareListResponseData> data;

    public void setData(List<ShareListResponseData> data) {
        this.data = data;
    }

    public List<ShareListResponseData> getData() {
        return data;
    }

    public String getNextPageUrl() {
        return nextPageUrl;
    }

    public void setNextPageUrl(String nextPageUrl) {
        this.nextPageUrl = nextPageUrl;
    }
}
