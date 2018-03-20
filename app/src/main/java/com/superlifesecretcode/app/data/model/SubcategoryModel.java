package com.superlifesecretcode.app.data.model;

/**
 * Created by Divya on 28-02-2018.
 */

public class SubcategoryModel {
    private String icon;
    private String title;
    private String url;
    private int type;
    private boolean selected;

    public boolean isSelected() {
        return selected;
    }


    public SubcategoryModel(String icon, String title, String url, int type, boolean selected) {
        this.icon = icon;
        this.title = title;
        this.url = url;
        this.type = type;
        this.selected = selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public String getIcon() {
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
