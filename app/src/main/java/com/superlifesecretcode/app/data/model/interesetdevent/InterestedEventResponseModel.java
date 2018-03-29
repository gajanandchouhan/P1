package com.superlifesecretcode.app.data.model.interesetdevent;

import com.superlifesecretcode.app.data.model.BaseResponseModel;
import com.superlifesecretcode.app.data.model.events.EventsInfoModel;
import com.superlifesecretcode.app.data.model.news.NewsResponseData;

import java.util.List;

/**
 * Created by Divya on 14-03-2018.
 */

public class InterestedEventResponseModel extends BaseResponseModel {
    private List<InterestedEventdata> data;


    public List<InterestedEventdata> getData() {
        return data;
    }

    public void setData(List<InterestedEventdata> data) {
        this.data = data;
    }
}
