package com.superlifesecretcode.app.data.model.kpi;

public class KpiBean {

    Announcement announcements;
    Sharing sharings;
    CountryActivity country_activities;
    DailyActivities daily_activities;
    String totalPoints;
    String color_code;

    public String getTotalPoints() {
        return totalPoints;
    }

    public void setTotalPoints(String totalPoints) {
        this.totalPoints = totalPoints;
    }

    public String getColor_code() {
        return color_code;
    }

    public void setColor_code(String color_code) {
        this.color_code = color_code;
    }

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
