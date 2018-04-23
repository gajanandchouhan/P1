package com.superlifesecretcode.app.ui.disclosure;

import com.superlifesecretcode.app.data.model.disclosure.DisclosureResponseData;
import com.superlifesecretcode.app.data.model.language.LanguageResponseData;
import com.superlifesecretcode.app.ui.base.BaseView;

/**
 * Created by Divya on 01-03-2018.
 */

public interface DisclosureView extends BaseView{
    void setDisclosureData(DisclosureResponseData data);
}
