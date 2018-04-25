package com.superlifesecretcode.app.ui.main;

import com.superlifesecretcode.app.data.model.allmenu.AllMenuResponseData;
import com.superlifesecretcode.app.data.model.category.BannerModel;
import com.superlifesecretcode.app.data.model.category.CategoryResponseModel;
import com.superlifesecretcode.app.data.model.unreadannouncement.AnnouncementCounData;
import com.superlifesecretcode.app.ui.base.BaseView;

import java.util.List;

/**
 * Created by Divya on 13-03-2018.
 */

interface MainView extends BaseView {

    void setHomeData(CategoryResponseModel categoryResponseDataList);

    void setAnnounceMentCount(AnnouncementCounData announcementCountResponseModel);

    void setBanners(List<BannerModel> banners);

    void setAllCaetgoryData(List<AllMenuResponseData> data);
}
