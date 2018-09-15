package com.superlifesecretcode.app.data.model.studygroups.studygroupitem;

public class StudyGroupItemData {
    private String item_id;
    private String item_title;
    private String item_description;
    private String item_icon;
    private String item_url;
    private String group_name;
    private String item_type;
    private String item_type_id;
    private String duration;
    private String item_expiry_date;

    public String getItem_expiry_date() {
        return item_expiry_date;
    }

    public void setItem_expiry_date(String item_expiry_date) {
        this.item_expiry_date = item_expiry_date;
    }

    public String getItem_id() {
        return item_id;
    }

    public void setItem_id(String item_id) {
        this.item_id = item_id;
    }

    public String getItem_title() {
        return item_title;
    }

    public void setItem_title(String item_title) {
        this.item_title = item_title;
    }

    public String getItem_description() {
        return item_description;
    }

    public void setItem_description(String item_description) {
        this.item_description = item_description;
    }

    public String getItem_icon() {
        return item_icon;
    }

    public void setItem_icon(String item_icon) {
        this.item_icon = item_icon;
    }

    public String getItem_url() {
        return item_url;
    }

    public void setItem_url(String item_url) {
        this.item_url = item_url;
    }

    public String getGroup_name() {
        return group_name;
    }

    public void setGroup_name(String group_name) {
        this.group_name = group_name;
    }

    public String getItem_type() {
        return item_type;
    }

    public void setItem_type(String item_type) {
        this.item_type = item_type;
    }

    public String getItem_type_id() {
        return item_type_id;
    }

    public void setItem_type_id(String item_type_id) {
        this.item_type_id = item_type_id;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getDuration() {
        return duration;
    }
}
