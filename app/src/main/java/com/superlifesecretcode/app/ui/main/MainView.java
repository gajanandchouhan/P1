package com.superlifesecretcode.app.ui.main;

import com.superlifesecretcode.app.data.model.category.CategoryResponseModel;
import com.superlifesecretcode.app.ui.base.BaseView;

/**
 * Created by Divya on 13-03-2018.
 */

interface MainView extends BaseView{

    void setHomeData(CategoryResponseModel categoryResponseDataList);
}
