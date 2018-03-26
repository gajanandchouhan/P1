package com.superlifesecretcode.app.ui.dailyactivities.personalevent;

import com.superlifesecretcode.app.data.model.standardevent.StandardEventResponseData;
import com.superlifesecretcode.app.ui.base.BaseView;

import java.util.List;

/**
 * Created by Divya on 26-03-2018.
 */

public interface AddEventView extends BaseView {
    void setStandardActivities(List<StandardEventResponseData> data);
}
