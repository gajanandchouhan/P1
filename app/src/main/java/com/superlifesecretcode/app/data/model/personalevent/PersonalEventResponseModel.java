package com.superlifesecretcode.app.data.model.personalevent;

import com.superlifesecretcode.app.data.model.BaseResponseModel;

import java.util.List;

/**
 * Created by Divya on 27-03-2018.
 */

public class PersonalEventResponseModel extends BaseResponseModel {
    private List<PersonalEventResponseData> data;

    public List<PersonalEventResponseData> getData() {
        return data;
    }

    public void setData(List<PersonalEventResponseData> data) {
        this.data = data;
    }
}
