package com.superlifesecretcode.app.data.model.disclosure;

import com.superlifesecretcode.app.data.model.BaseResponseModel;

/**
 * Created by Divya on 23-04-2018.
 */

public class DisclosureResponseModel extends BaseResponseModel{
    private DisclosureResponseData data;

    public DisclosureResponseData getData() {
        return data;
    }

    public void setData(DisclosureResponseData data) {
        this.data = data;
    }
}
