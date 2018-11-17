package com.superlifesecretcode.app.data.model.kpi;

public class Announcement {

    String readNews , unreadNews , readEvents, unreadEvents;

    public Announcement(String readNews, String unreadNews, String readEvents, String unreadEvents) {
        this.readNews = readNews;
        this.unreadNews = unreadNews;
        this.readEvents = readEvents;
        this.unreadEvents = unreadEvents;
    }

    public String getReadNews() {
        return readNews;
    }

    public String getUnreadNews() {
        return unreadNews;
    }

    public String getReadEvents() {
        return readEvents;
    }

    public String getUnreadEvents() {
        return unreadEvents;
    }
}
