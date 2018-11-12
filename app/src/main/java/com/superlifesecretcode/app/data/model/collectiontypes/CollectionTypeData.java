package com.superlifesecretcode.app.data.model.collectiontypes;

import com.google.gson.annotations.SerializedName;

public class CollectionTypeData {
    @SerializedName("label")
    private String title;
    @SerializedName("type")
    private int tag;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getTag() {
        return tag;
    }

    public void setTag(int tag) {
        this.tag = tag;
    }
}
