package com.superlifesecretcode.app.ui.language;

import com.superlifesecretcode.app.data.model.language.LanguageResponseData;
import com.superlifesecretcode.app.ui.base.BaseView;

/**
 * Created by Divya on 01-03-2018.
 */

interface LanguageView extends BaseView{
    void setConversionContent(LanguageResponseData data);
}
