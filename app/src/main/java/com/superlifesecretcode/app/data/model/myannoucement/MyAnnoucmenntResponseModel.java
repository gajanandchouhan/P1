package com.superlifesecretcode.app.data.model.myannoucement;

import com.google.gson.annotations.SerializedName;
import com.superlifesecretcode.app.data.model.BaseResponseModel;

import java.util.List;

public class MyAnnoucmenntResponseModel extends BaseResponseModel {
    private List<MyAnnouncementResponseData> data;

    @SerializedName("permission_status")
    private String permissionStatus;

    public List<MyAnnouncementResponseData> getData() {
        return data;
    }

    public void setData(List<MyAnnouncementResponseData> data) {
        this.data = data;
    }

    public String getPermissionStatus() {
        return permissionStatus;
    }

    public void setPermissionStatus(String permissionStatus) {
        this.permissionStatus = permissionStatus;
    }
}
