package com.superlifesecretcode.app.ui.forgotpassword;

import com.superlifesecretcode.app.data.model.userdetails.UserDetailResponseData;
import com.superlifesecretcode.app.ui.base.BaseView;

/**
 * Created by Divya on 01-03-2018.
 */

interface ForgotPasswordView extends BaseView {

    void onTempPassSuccess();
    void onPasswrodChanged();
}
