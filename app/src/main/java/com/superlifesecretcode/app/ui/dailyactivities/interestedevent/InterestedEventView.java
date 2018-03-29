package com.superlifesecretcode.app.ui.dailyactivities.interestedevent;

import com.superlifesecretcode.app.data.model.interesetdevent.InterestedEventdata;
import com.superlifesecretcode.app.data.model.news.SingleNewsResponseModel;
import com.superlifesecretcode.app.ui.base.BaseView;

import java.util.List;

/**
 * Created by Divya on 14-03-2018.
 */

interface InterestedEventView extends BaseView{

    void setEventData(List<InterestedEventdata> interestedEventResponseDataList);

    void setDetails(SingleNewsResponseModel newsResponseModel);

    void onUpdateInteresed();
}
