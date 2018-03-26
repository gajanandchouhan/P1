package com.superlifesecretcode.app.data.model.standardevent;

import com.superlifesecretcode.app.data.model.BaseResponseModel;

import java.util.List;

/**
 * Created by Divya on 26-03-2018.
 */

public class StandardEventResponseModel extends BaseResponseModel {
    List<StandardEventResponseData> data;

    public List<StandardEventResponseData> getData() {
        return data;
    }

    public void setData(List<StandardEventResponseData> data) {
        this.data = data;
    }
}
