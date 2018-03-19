package com.superlifesecretcode.app.data.model.shares;

import java.util.List;

/**
 * Created by Divya on 19-03-2018.
 */

public class ShareListResponseData {
    private String sharing_id;
    private String user_id;
    private String content;
    private String username;
    private String country;
    private String state;
    private String countryName;
    private String stateName;
    private String status;
    private String liked_by;
    private String liked_by_user;
    private List<FileResponseData> sharing_files;

    public String getSharing_id() {
        return sharing_id;
    }

    public void setSharing_id(String sharing_id) {
        this.sharing_id = sharing_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLiked_by() {
        return liked_by;
    }

    public void setLiked_by(String liked_by) {
        this.liked_by = liked_by;
    }

    public String getLiked_by_user() {
        return liked_by_user;
    }

    public void setLiked_by_user(String liked_by_user) {
        this.liked_by_user = liked_by_user;
    }

    public List<FileResponseData> getSharing_files() {
        return sharing_files;
    }

    public void setSharing_files(List<FileResponseData> sharing_files) {
        this.sharing_files = sharing_files;
    }
}
