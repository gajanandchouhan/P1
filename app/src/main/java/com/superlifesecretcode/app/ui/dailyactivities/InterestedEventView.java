package com.superlifesecretcode.app.ui.dailyactivities;

import com.superlifesecretcode.app.data.model.events.EventsInfoModel;
import com.superlifesecretcode.app.ui.base.BaseView;

import java.util.List;

/**
 * Created by Divya on 14-03-2018.
 */

interface InterestedEventView extends BaseView{

    void setEventData(List<EventsInfoModel> interestedEventResponseDataList);

}
