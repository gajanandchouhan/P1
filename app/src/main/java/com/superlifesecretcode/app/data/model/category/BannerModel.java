package com.superlifesecretcode.app.data.model.category;

import java.io.Serializable;

/**
 * Created by Divya on 12-03-2018.
 */

public class BannerModel implements Serializable {
    private String title;
    private String link;
    private String image;
    private String transition_time;
    private String country;
    private String country_name;
    private String id;

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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTransition_time() {
        return transition_time;
    }

    public void setTransition_time(String transition_time) {
        this.transition_time = transition_time;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCountry_name() {
        return country_name;
    }

    public void setCountry_name(String country_name) {
        this.country_name = country_name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
