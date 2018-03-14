package com.superlifesecretcode.app.ui.news;

import com.superlifesecretcode.app.data.model.news.NewsResponseModel;
import com.superlifesecretcode.app.ui.base.BaseView;

/**
 * Created by Divya on 14-03-2018.
 */

interface NewsView extends BaseView{

    void setNewsData(NewsResponseModel newsResponseModel);
}
