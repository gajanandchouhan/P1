package com.superlifesecretcode.app.ui.events;

import com.superlifesecretcode.app.data.model.events.EventResponseModel;
import com.superlifesecretcode.app.data.model.news.NewsResponseModel;
import com.superlifesecretcode.app.ui.base.BaseView;

/**
 * Created by Divya on 14-03-2018.
 */

interface EventView extends BaseView{

    void setEventData(EventResponseModel newsResponseModel);

    void onUpdateInteresed();
}
