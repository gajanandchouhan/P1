package com.superlifesecretcode.app.data.model.kpi;

public class TaskDetails {

    private String title;
    private String point;
    private String date;

    public TaskDetails(String title, String point,String date) {
        this.title = title;
        this.point = point;
        this.date=date;
    }

    public String getTitle() {
        return title;
    }

    public String getPoint() {
        return point;
    }

    public String getDate() {
        return date;
    }
}
