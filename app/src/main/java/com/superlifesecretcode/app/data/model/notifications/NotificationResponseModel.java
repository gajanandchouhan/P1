package com.superlifesecretcode.app.data.model.notifications;

import com.superlifesecretcode.app.data.model.BaseResponseModel;

import java.util.List;

/**
 * Created by Divya on 13-04-2018.
 */

public class NotificationResponseModel extends BaseResponseModel {
    private List<NotificationResponseData> data;

    public List<NotificationResponseData> getData() {
        return data;
    }

    public void setData(List<NotificationResponseData> data) {
        this.data = data;
    }
}
