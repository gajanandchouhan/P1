package com.superlifesecretcode.app.data.model.kpi;

public class Sharing {

    private String total_sharings;
    private String total_sharings_liked;
    private String points;

    public Sharing(String total_sharings, String total_sharings_liked, String points) {
        this.total_sharings = total_sharings;
        this.total_sharings_liked = total_sharings_liked;
        this.points = points;
    }

    public String getTotal_sharings() {
        return total_sharings;
    }

    public String getTotal_sharings_liked() {
        return total_sharings_liked;
    }
}
