package com.superlifesecretcode.app.data.model;

/**
 * Created by Divya on 28-02-2018.
 */

public class SubcategoryModel {
    private int icon;
    private String title;
    private String url;

    public SubcategoryModel(int icon, String title, String url) {
        this.icon = icon;
        this.title = title;
        this.url = url;
    }

    public int getIcon() {
        return icon;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }
}
