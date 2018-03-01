package com.superlifesecretcode.app.data.model.language;

import com.superlifesecretcode.app.data.model.BaseResponseModel;

/**
 * Created by Divya on 01-03-2018.
 */

public class LanguageResponseModel extends BaseResponseModel {
    private LanguageResponseData data;

    public LanguageResponseData getData() {
        return data;
    }

    public void setData(LanguageResponseData data) {
        this.data = data;
    }
}
