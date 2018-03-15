package com.superlifesecretcode.app.data.model.events;

import java.io.Serializable;

/**
 * Created by Divya on 14-03-2018.
 */

public class EventsInfoModel implements Serializable {
    private String announcement_id;
    private String user_id;
    private String announcement_type;
    private String announcement_name;
    private String announcement_description;
    private String image;
    private String announcement_date;
    private String announcement_time;
    private String status;
    private String venue;
    private String username;
    private String country;
    private String state;
    private String countryName;
    private String stateName;
    private String liked_by;
    private String shared_by;
    private String readed_by;
    private String announcementTypeName;
    private String liked_by_user;
    private String shared_by_user;
    private String readed_by_user;
    String userIntrested;

    public String getUserIntrested() {
        return userIntrested;
    }

    public void setUserIntrested(String userIntrested) {
        this.userIntrested = userIntrested;
    }

    public String getAnnouncement_id() {
        return announcement_id;
    }

    public void setAnnouncement_id(String announcement_id) {
        this.announcement_id = announcement_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getAnnouncement_type() {
        return announcement_type;
    }

    public void setAnnouncement_type(String announcement_type) {
        this.announcement_type = announcement_type;
    }

    public String getAnnouncement_name() {
        return announcement_name;
    }

    public void setAnnouncement_name(String announcement_name) {
        this.announcement_name = announcement_name;
    }

    public String getAnnouncement_description() {
        return announcement_description;
    }

    public void setAnnouncement_description(String announcement_description) {
        this.announcement_description = announcement_description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getAnnouncement_date() {
        return announcement_date;
    }

    public void setAnnouncement_date(String announcement_date) {
        this.announcement_date = announcement_date;
    }

    public String getAnnouncement_time() {
        return announcement_time;
    }

    public void setAnnouncement_time(String announcement_time) {
        this.announcement_time = announcement_time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public String getLiked_by() {
        return liked_by;
    }

    public void setLiked_by(String liked_by) {
        this.liked_by = liked_by;
    }

    public String getShared_by() {
        return shared_by;
    }

    public void setShared_by(String shared_by) {
        this.shared_by = shared_by;
    }

    public String getReaded_by() {
        return readed_by;
    }

    public void setReaded_by(String readed_by) {
        this.readed_by = readed_by;
    }

    public String getAnnouncementTypeName() {
        return announcementTypeName;
    }

    public void setAnnouncementTypeName(String announcementTypeName) {
        this.announcementTypeName = announcementTypeName;
    }

    public String getLiked_by_user() {
        return liked_by_user;
    }

    public void setLiked_by_user(String liked_by_user) {
        this.liked_by_user = liked_by_user;
    }

    public String getShared_by_user() {
        return shared_by_user;
    }

    public void setShared_by_user(String shared_by_user) {
        this.shared_by_user = shared_by_user;
    }

    public String getReaded_by_user() {
        return readed_by_user;
    }

    public void setReaded_by_user(String readed_by_user) {
        this.readed_by_user = readed_by_user;
    }
}
