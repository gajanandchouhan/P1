package com.superlifesecretcode.app.ui.mycountryactivities.addcountryactivity;

import com.superlifesecretcode.app.data.model.country.CountryResponseData;
import com.superlifesecretcode.app.ui.base.BaseView;

import java.util.List;

interface AddCountryActivityView extends BaseView {
    void setCountryData(List<CountryResponseData> data);
    void setStateData(List<CountryResponseData> data);


    void setCities(List<CountryResponseData> data);
    void onAdded();

    void imageDelete();
}
