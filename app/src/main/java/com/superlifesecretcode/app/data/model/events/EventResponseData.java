package com.superlifesecretcode.app.data.model.events;

import java.util.List;

/**
 * Created by Divya on 15-03-2018.
 */

public class EventResponseData {
    private List<EventsInfoModel> today;
    private List<EventsInfoModel> upcoming;

    public List<EventsInfoModel> getToday() {
        return today;
    }

    public void setToday(List<EventsInfoModel> today) {
        this.today = today;
    }

    public List<EventsInfoModel> getUpcoming() {
        return upcoming;
    }

    public void setUpcoming(List<EventsInfoModel> upcoming) {
        this.upcoming = upcoming;
    }
}
