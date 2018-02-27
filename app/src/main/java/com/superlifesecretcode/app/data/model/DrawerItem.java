package com.superlifesecretcode.app.data.model;

/**
 * Created by Divya on 22-02-2018.
 */

public class DrawerItem {
    private String title;
    private int icon;

    public DrawerItem(String title, int icon) {
        this.title = title;
        this.icon = icon;
    }

    public String getTitle() {
        return title;
    }

    public int getIcon() {
        return icon;
    }
}
