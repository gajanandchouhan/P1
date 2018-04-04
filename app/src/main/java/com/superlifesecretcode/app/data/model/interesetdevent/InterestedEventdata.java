package com.superlifesecretcode.app.data.model.interesetdevent;

/**
 * Created by Divya on 29-03-2018.
 */

public class InterestedEventdata {
    private String event_id;
    private String title;
    private String description;
    private String image;
    private String event_date;
    private String event_time;
    private String venue;
    private String event_type;
    private String userIntrested;
    private String remind_before;

    public String getRemind_before() {
        return remind_before;
    }

    public void setRemind_before(String remind_before) {
        this.remind_before = remind_before;
    }

    public String getEvent_id() {
        return event_id;
    }

    public void setEvent_id(String event_id) {
        this.event_id = event_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getEvent_date() {
        return event_date;
    }

    public void setEvent_date(String event_date) {
        this.event_date = event_date;
    }

    public String getEvent_time() {
        return event_time;
    }

    public void setEvent_time(String event_time) {
        this.event_time = event_time;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public String getEvent_type() {
        return event_type;
    }

    public void setEvent_type(String event_type) {
        this.event_type = event_type;
    }

    public String getUserIntrested() {
        return userIntrested;
    }

    public void setUserIntrested(String userIntrested) {
        this.userIntrested = userIntrested;
    }
}
