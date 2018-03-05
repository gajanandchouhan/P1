package com.superlifesecretcode.app.ui.profile;

import com.superlifesecretcode.app.data.model.language.LanguageResponseData;
import com.superlifesecretcode.app.data.model.userdetails.UserDetailResponseData;
import com.superlifesecretcode.app.ui.language.LanguageView;

/**
 * Created by Divya on 05-03-2018.
 */

interface ProfileView extends LanguageView {

    void onProfileUpdated(UserDetailResponseData data);
}
