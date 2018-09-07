package com.superlifesecretcode.app.data.model.mycountryactivities;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class MyCountryActivityResponseData implements Serializable {
    private String activity_id;
    private String activity_type;
    private String activity_title;
    private String activity_discription;
    private String activity_contact_name;
    private String activity_contact_number;
    private String activity_contact_email;
    private String activity_venue;
    private String activity_location_lat;
    private String activity_location_long;
    private String activity_country;
    private String activity_state;
    private String activity_city;
    private String activity_date;
    private String activity_time;
    private String activity_day;
    private List<ImageData> activity_images;
    private String reason;
    private String status;
    private String activity_country_id;
    private String activity_state_id;
    private String activity_city_id;
    @SerializedName("activity_map_location")
    private String map_location;

    public String getMap_location() {
        return map_location;
    }

    public void setMap_location(String map_location) {
        this.map_location = map_location;
    }

    public String getActivity_country_id() {
        return activity_country_id;
    }

    public void setActivity_country_id(String activity_country_id) {
        this.activity_country_id = activity_country_id;
    }

    public String getActivity_state_id() {
        return activity_state_id;
    }

    public void setActivity_state_id(String activity_state_id) {
        this.activity_state_id = activity_state_id;
    }

    public String getActivity_city_id() {
        return activity_city_id;
    }

    public void setActivity_city_id(String activity_city_id) {
        this.activity_city_id = activity_city_id;
    }

    public String getActivity_id() {
        return activity_id;
    }

    public void setActivity_id(String activity_id) {
        this.activity_id = activity_id;
    }

    public String getActivity_type() {
        return activity_type;
    }

    public void setActivity_type(String activity_type) {
        this.activity_type = activity_type;
    }

    public String getActivity_title() {
        return activity_title;
    }

    public void setActivity_title(String activity_title) {
        this.activity_title = activity_title;
    }

    public String getActivity_discription() {
        return activity_discription;
    }

    public void setActivity_discription(String activity_discription) {
        this.activity_discription = activity_discription;
    }

    public String getActivity_contact_name() {
        return activity_contact_name;
    }

    public void setActivity_contact_name(String activity_contact_name) {
        this.activity_contact_name = activity_contact_name;
    }

    public String getActivity_contact_number() {
        return activity_contact_number;
    }

    public void setActivity_contact_number(String activity_contact_number) {
        this.activity_contact_number = activity_contact_number;
    }

    public String getActivity_contact_email() {
        return activity_contact_email;
    }

    public void setActivity_contact_email(String activity_contact_email) {
        this.activity_contact_email = activity_contact_email;
    }

    public String getActivity_venue() {
        return activity_venue;
    }

    public void setActivity_venue(String activity_venue) {
        this.activity_venue = activity_venue;
    }

    public String getActivity_location_lat() {
        return activity_location_lat;
    }

    public void setActivity_location_lat(String activity_location_lat) {
        this.activity_location_lat = activity_location_lat;
    }

    public String getActivity_location_long() {
        return activity_location_long;
    }

    public void setActivity_location_long(String activity_location_long) {
        this.activity_location_long = activity_location_long;
    }

    public String getActivity_country() {
        return activity_country;
    }

    public void setActivity_country(String activity_country) {
        this.activity_country = activity_country;
    }

    public String getActivity_state() {
        return activity_state;
    }

    public void setActivity_state(String activity_state) {
        this.activity_state = activity_state;
    }

    public String getActivity_city() {
        return activity_city;
    }

    public void setActivity_city(String activity_city) {
        this.activity_city = activity_city;
    }

    public String getActivity_date() {
        return activity_date;
    }

    public void setActivity_date(String activity_date) {
        this.activity_date = activity_date;
    }

    public String getActivity_time() {
        return activity_time;
    }

    public void setActivity_time(String activity_time) {
        this.activity_time = activity_time;
    }

    public String getActivity_day() {
        return activity_day;
    }

    public void setActivity_day(String activity_day) {
        this.activity_day = activity_day;
    }

    public List<ImageData> getActivity_images() {
        return activity_images;
    }

    public void setActivity_images(List<ImageData> activity_images) {
        this.activity_images = activity_images;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
