package com.superlifesecretcode.app.ui.countryactivities;

import com.superlifesecretcode.app.data.model.country.CountryResponseData;
import com.superlifesecretcode.app.data.model.countryactivities.CounActivtyResponseData;
import com.superlifesecretcode.app.data.model.countryactivities.CountryActivityInfoModel;
import com.superlifesecretcode.app.ui.base.BaseView;

import java.util.List;

/**
 * Created by Divya on 28-03-2018.
 */

public interface CountryActivitiesView extends BaseView {
    void setCountyAcivityData(CounActivtyResponseData data);

    void setCountryData(List<CountryResponseData> data);

    void setStateData(List<CountryResponseData> data);

    void onUpdateInteresed();

    void setActivtyDetails(CountryActivityInfoModel data);

    void setCities(List<CountryResponseData> data);
}
