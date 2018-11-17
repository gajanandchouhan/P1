package com.superlifesecretcode.app.data.model.kpi;

public class DailyActivities {
    private String completed;
    private String incompleted;
    private String points;

    public void setCompleted(String completed) {
        this.completed = completed;
    }

    public void setIncompleted(String incompleted) {
        this.incompleted = incompleted;
    }

    public String getPoints() {
        return points;
    }

    public void setPoints(String points) {
        this.points = points;
    }

    public String getCompleted() {
        return completed;
    }

    public String getIncompleted() {
        return incompleted;
    }
}
