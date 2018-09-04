package com.superlifesecretcode.app.ui.myannouncement;

import com.superlifesecretcode.app.data.model.myannoucement.MyAnnouncementResponseData;
import com.superlifesecretcode.app.ui.base.BaseView;

import java.util.List;

public interface MyAnnouncementView extends BaseView{
    void setAnnouncementList(List<MyAnnouncementResponseData> data);

    void deleted();
}
