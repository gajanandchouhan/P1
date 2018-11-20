package com.superlifesecretcode.app.data.model.kpi;

public class TaskDetails {
    private String title;
    private String point;
    private String activity_date;

    public TaskDetails(String title, String point,String activity_date) {
        this.title = title;
        this.point = point;
        this.activity_date=activity_date;
    }

    public String getTitle() {
        return title;
    }

    public String getPoint() {
        return point;
    }

    public String getDate() {
        return activity_date;
    }
}
