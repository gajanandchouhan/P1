package com.superlifesecretcode.app.ui.splash;

import com.superlifesecretcode.app.data.model.BaseResponseModel;
import com.superlifesecretcode.app.ui.base.BaseView;

/**
 * Created by Divya on 21-02-2018.
 */

public interface SplashView extends BaseView {
    void navigateToNextScreen();

    void validateVersion(BaseResponseModel baseResponseModel);
}
