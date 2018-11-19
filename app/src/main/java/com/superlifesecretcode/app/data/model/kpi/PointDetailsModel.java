package com.superlifesecretcode.app.data.model.kpi;

public class PointDetailsModel {
    private String date;
    private boolean showDate;
    private TaskDetails taskDetails;

    public PointDetailsModel(String date, boolean showDate, TaskDetails taskDetails) {
        this.date = date;
        this.showDate = showDate;
        this.taskDetails = taskDetails;
    }


    public String getDate() {
        return date;
    }

    public boolean isShowDate() {
        return showDate;
    }

    public TaskDetails getTaskDetails() {
        return taskDetails;
    }
}
