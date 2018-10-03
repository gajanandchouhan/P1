package com.superlifesecretcode.app.ui.kpi_summery;

public class OnSiteSharing {

    private String activity_id;
    private String activity_title;
    private String activity_description;
    private String activity_image;

    public OnSiteSharing(String activity_id, String activity_title, String activity_description, String activity_image) {
        this.activity_id = activity_id;
        this.activity_title = activity_title;
        this.activity_description = activity_description;
        this.activity_image = activity_image;
    }

    public String getActivity_id() {
        return activity_id;
    }

    public String getActivity_title() {
        return activity_title;
    }

    public String getActivity_description() {
        return activity_description;
    }

    public String getActivity_image() {
        return activity_image;
    }

}
