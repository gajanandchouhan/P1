package com.superlifesecretcode.app.data.model;

/**
 * Created by Divya on 28-02-2018.
 */

public class SubcategoryModel {
    private int icon;
    private String title;
    private String url;
    private int type;


    public SubcategoryModel(int icon, String title, String url,int type) {
        this.icon = icon;
        this.title = title;
        this.url = url;
        this.type=type;
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

    public int getType() {
        return type;
    }
}
