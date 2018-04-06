package com.superlifesecretcode.app.data.model.unreadannouncement;

import com.superlifesecretcode.app.data.model.BaseResponseModel;

/**
 * Created by Divya on 06-04-2018.
 */

public class AnnouncementCountResponseModel extends BaseResponseModel {
    private AnnouncementCounData data;

    public AnnouncementCounData getData() {
        return data;
    }

    public void setData(AnnouncementCounData data) {
        this.data = data;
    }
}
