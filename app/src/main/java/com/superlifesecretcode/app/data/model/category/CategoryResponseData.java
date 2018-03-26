package com.superlifesecretcode.app.data.model.category;

/**
 * Created by Divya on 12-03-2018.
 */

public class CategoryResponseData {
    private String id;
    private String parent_id;
    private String language_id;
    private String type;
    private String title;
    private String link;
    private String content;
    private String image;
    private String color;
    private String country_name;
    private String country;
    private int icon;
    private int position;
    private String alert;
    private String alert_count;
    private String alert_text;
    private String positive_resp;
    private String negative_resp;

    public String getPositive_resp() {
        return positive_resp;
    }

    public void setNegative_resp(String negative_resp) {
        this.negative_resp = negative_resp;
    }

    public void setPositive_resp(String positive_resp) {
        this.positive_resp = positive_resp;
    }

    public String getNegative_resp() {
        return negative_resp;
    }

    public void setAlert_count(String alert_count) {
        this.alert_count = alert_count;
    }

    public String getAlert_count() {
        return alert_count;
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

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
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

    public String getCountry_name() {
        return country_name;
    }

    public void setCountry_name(String country_name) {
        this.country_name = country_name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public int getIcon() {
        return icon;
    }
}
