package com.superlifesecretcode.app.ui.dailyactivities.personalevent;

import com.superlifesecretcode.app.data.model.personalevent.PersonalEventResponseData;
import com.superlifesecretcode.app.ui.base.BaseView;

import java.util.List;

/**
 * Created by Divya on 27-03-2018.
 */

interface PersonalEventView extends BaseView{
    void setEventData(List<PersonalEventResponseData> data);
    void onStatusUpdated();
    void onStatusFailed();

    void onRemoveSuccess();

    void noData();

    void onCompleteStatusUpdateSuccess();
}
