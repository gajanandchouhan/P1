package com.superlifesecretcode.app.ui.kpi_summery;

public class Sharing {

    private String total_sharings;
    private String total_sharings_liked;

    public Sharing(String total_sharings, String total_sharings_liked) {
        this.total_sharings = total_sharings;
        this.total_sharings_liked = total_sharings_liked;
    }

    public String getTotal_sharings() {
        return total_sharings;
    }

    public String getTotal_sharings_liked() {
        return total_sharings_liked;
    }
}
