package com.superlifesecretcode.app.ui.countryactivities;

import com.superlifesecretcode.app.data.model.countryactivities.CounActivtyResponseData;
import com.superlifesecretcode.app.ui.base.BaseView;

/**
 * Created by Divya on 28-03-2018.
 */

interface CountryActivitiesView extends BaseView {
    void setCountyAcivityData(CounActivtyResponseData data);
}
