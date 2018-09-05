package com.superlifesecretcode.app.ui.mycountryactivities;

import com.superlifesecretcode.app.data.model.mycountryactivities.MyCountryActivityResponseData;
import com.superlifesecretcode.app.ui.base.BaseView;

import java.util.List;

public interface MyCountryActivityView extends BaseView {
    void setCountryActivties(List<MyCountryActivityResponseData> data);

    void deleted();

    void onPermissionStatus(String permissionStatus);

    void onRequestSuccess();

    void onRequestFailed();
}
