package com.superlifesecretcode.app.ui.login;

import com.superlifesecretcode.app.data.model.country.CountryResponseData;
import com.superlifesecretcode.app.data.model.userdetails.UserDetailResponseData;
import com.superlifesecretcode.app.ui.base.BaseView;

import java.util.List;

/**
 * Created by Divya on 01-03-2018.
 */

interface LoginView extends BaseView {

    void setUserData(UserDetailResponseData data);
    void setCountryData(List<CountryResponseData> data);
}
