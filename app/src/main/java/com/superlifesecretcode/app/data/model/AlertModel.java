package com.superlifesecretcode.app.data.model;

/**
 * Created by Divya on 27-03-2018.
 */

public class AlertModel {
    private String id;
    private int count;
    private int showCount;

    public int getShowCount() {
        return showCount;
    }

    public void setShowCount(int showCount) {
        this.showCount = showCount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public boolean equals(Object object) {
        boolean result = false;
        if (object == null || object.getClass() != getClass()) {
            result = false;
        } else {
            AlertModel alertModel = (AlertModel) object;
            if (this.id.equalsIgnoreCase(alertModel.getId())) {
                result = true;
            }
        }
        return result;
    }
}
