package com.superlifesecretcode.app.ui.register;

import com.superlifesecretcode.app.data.model.country.CountryResponseData;
import com.superlifesecretcode.app.data.model.userdetails.UserDetailResponseData;
import com.superlifesecretcode.app.ui.base.BaseView;

import java.util.List;

/**
 * Created by Divya on 01-03-2018.
 */

interface RegisterView extends BaseView {
    void setCountryData(List<CountryResponseData> data);

    void setStateData(List<CountryResponseData> data);

    void setUserData(UserDetailResponseData data);

    void setCities(List<CountryResponseData> data);
}
