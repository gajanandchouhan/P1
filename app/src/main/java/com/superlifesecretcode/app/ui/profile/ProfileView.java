package com.superlifesecretcode.app.ui.profile;

import com.superlifesecretcode.app.data.model.country.CountryResponseData;
import com.superlifesecretcode.app.data.model.language.LanguageResponseData;
import com.superlifesecretcode.app.data.model.userdetails.UserDetailResponseData;
import com.superlifesecretcode.app.ui.language.LanguageView;

import java.util.List;

/**
 * Created by Divya on 05-03-2018.
 */

interface ProfileView extends LanguageView {

    void setCountryData(List<CountryResponseData> data);

    void setStateData(List<CountryResponseData> data);

    void setUserData(UserDetailResponseData data);
}
