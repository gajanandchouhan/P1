package com.superlifesecretcode.app.ui.kpi_summery;

public class KpiBean {

    Announcement announcements;
    Sharing sharings;
    CountryActivity country_activities;
    DailyActivities daily_activities;

    public KpiBean(Announcement announcements, Sharing sharings, CountryActivity country_activities, DailyActivities daily_activities) {
        this.announcements = announcements;
        this.sharings = sharings;
        this.country_activities = country_activities;
        this.daily_activities = daily_activities;
    }

    public Announcement getAnnouncements() {
        return announcements;
    }

    public Sharing getSharings() {
        return sharings;
    }

    public CountryActivity getCountry_activities() {
        return country_activities;
    }

    public DailyActivities getDaily_activities() {
        return daily_activities;
    }
}
