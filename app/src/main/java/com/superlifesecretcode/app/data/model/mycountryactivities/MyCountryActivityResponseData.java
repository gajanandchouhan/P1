package com.superlifesecretcode.app.data.model.mycountryactivities;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class MyCountryActivityResponseData implements Serializable{
    private String announcement_id;
    private String user_id;
    private String announcement_type;
    private String announcement_name;
    private String announcement_description;
    private String start_date;
    private String start_time;
    private String venue;
    private String country;
    private String countryName;
    private String created_at;
    private String end_date_time;
    @SerializedName("status")
    private String approval_status;
    private String end_date;
    private String end_time;
    private List<ImageData> announcement_images;
    private String reason;

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

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getEnd_date_time() {
        return end_date_time;
    }

    public void setEnd_date_time(String end_date_time) {
        this.end_date_time = end_date_time;
    }

    public String getApproval_status() {
        return approval_status;
    }

    public void setApproval_status(String approval_status) {
        this.approval_status = approval_status;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }


    public void setAnnouncement_images(List<ImageData> announcement_images) {
        this.announcement_images = announcement_images;
    }

    public List<ImageData> getAnnouncement_images() {
        return announcement_images;
    }
    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getReason() {
        return reason;
    }
    public class ImageData implements Serializable {
        private String id;
        @SerializedName("activity_image")
        private String image;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }
    }


}
