package com.superlifesecretcode.app.ui.myannouncement.addannouncement;

import com.superlifesecretcode.app.data.model.country.CountryResponseData;
import com.superlifesecretcode.app.ui.base.BaseView;

import java.util.List;

interface AddAnnouncementView extends BaseView {
    void setCountryData(List<CountryResponseData> data);

    void onAdded();
}
