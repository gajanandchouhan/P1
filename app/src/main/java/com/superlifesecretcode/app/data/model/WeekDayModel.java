package com.superlifesecretcode.app.data.model;

/**
 * Created by Divya on 03-05-2018.
 */

public class WeekDayModel {
    private String index;
    private String day;

    public WeekDayModel(String day, String index) {
        this.index = index;
        this.day = day;
    }

    public String getDay() {
        return day;
    }

    public String getIndex() {
        return index;
    }
}
