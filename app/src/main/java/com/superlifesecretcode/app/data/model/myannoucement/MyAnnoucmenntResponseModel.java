package com.superlifesecretcode.app.data.model.myannoucement;

import com.superlifesecretcode.app.data.model.BaseResponseModel;

import java.util.List;

public class MyAnnoucmenntResponseModel extends BaseResponseModel {
    private List<MyAnnouncementResponseData> data;

    public List<MyAnnouncementResponseData> getData() {
        return data;
    }

    public void setData(List<MyAnnouncementResponseData> data) {
        this.data = data;
    }
}
