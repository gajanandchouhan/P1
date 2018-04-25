package com.superlifesecretcode.app.data.model.allmenu;

/**
 * Created by Divya on 31-03-2018.
 */

public class AllMenuResponseData {

    private String id;
    private String parent_id;
    private String language_id;
    private String type;
    private String title;
    private String link;
    private String content;
    private String image;
    private String color;
    private String alert;
    private String alert_text;
    private String alert_count;
    private String positive_resp;
    private String negative_resp;
    private String parent_image;
    private boolean selected;

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public String getParent_image() {
        return parent_image;
    }

    public void setParent_image(String parent_image) {
        this.parent_image = parent_image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParent_id() {
        return parent_id;
    }

    public void setParent_id(String parent_id) {
        this.parent_id = parent_id;
    }

    public String getLanguage_id() {
        return language_id;
    }

    public void setLanguage_id(String language_id) {
        this.language_id = language_id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getAlert() {
        return alert;
    }

    public void setAlert(String alert) {
        this.alert = alert;
    }

    public String getAlert_text() {
        return alert_text;
    }

    public void setAlert_text(String alert_text) {
        this.alert_text = alert_text;
    }

    public String getAlert_count() {
        return alert_count;
    }

    public void setAlert_count(String alert_count) {
        this.alert_count = alert_count;
    }

    public String getPositive_resp() {
        return positive_resp;
    }

    public void setPositive_resp(String positive_resp) {
        this.positive_resp = positive_resp;
    }

    public String getNegative_resp() {
        return negative_resp;
    }

    public void setNegative_resp(String negative_resp) {
        this.negative_resp = negative_resp;
    }
}
