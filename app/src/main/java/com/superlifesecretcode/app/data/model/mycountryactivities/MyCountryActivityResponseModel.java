package com.superlifesecretcode.app.data.model.mycountryactivities;

import com.google.gson.annotations.SerializedName;
import com.superlifesecretcode.app.data.model.BaseResponseModel;
import com.superlifesecretcode.app.data.model.myannoucement.MyAnnouncementResponseData;

import java.util.List;

public class MyCountryActivityResponseModel extends BaseResponseModel {
    private List<MyCountryActivityResponseData> data;

    @SerializedName("permission_status")
    private String permissionStatus;

    public List<MyCountryActivityResponseData> getData() {
        return data;
    }

    public void setData(List<MyCountryActivityResponseData> data) {
        this.data = data;
    }

    public String getPermissionStatus() {
        return permissionStatus;
    }

    public void setPermissionStatus(String permissionStatus) {
        this.permissionStatus = permissionStatus;
    }
}
