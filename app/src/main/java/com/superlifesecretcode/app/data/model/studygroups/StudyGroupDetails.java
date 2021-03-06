package com.superlifesecretcode.app.data.model.studygroups;

import java.io.Serializable;

public class StudyGroupDetails implements Serializable {
    private String group_id;
    private String group_name;
    private String group_description;
    private String group_image;
    private String group_start_date;
    private String group_end_date;
    private String subcription_status;
    private String approval_date;
    private String expiry_date;
    private String reason;

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getGroup_id() {
        return group_id;
    }

    public void setGroup_id(String group_id) {
        this.group_id = group_id;
    }

    public String getGroup_name() {
        return group_name;
    }

    public void setGroup_name(String group_name) {
        this.group_name = group_name;
    }

    public String getGroup_description() {
        return group_description;
    }

    public void setGroup_description(String group_description) {
        this.group_description = group_description;
    }

    public String getGroup_image() {
        return group_image;
    }

    public void setGroup_image(String group_image) {
        this.group_image = group_image;
    }

    public String getGroup_start_date() {
        return group_start_date;
    }

    public void setGroup_start_date(String group_start_date) {
        this.group_start_date = group_start_date;
    }

    public String getGroup_end_date() {
        return group_end_date;
    }

    public void setGroup_end_date(String group_end_date) {
        this.group_end_date = group_end_date;
    }

    public String getSubcription_status() {
        return subcription_status;
    }

    public void setSubcription_status(String subcription_status) {
        this.subcription_status = subcription_status;
    }

    public String getApproval_date() {
        return approval_date;
    }

    public void setApproval_date(String approval_date) {
        this.approval_date = approval_date;
    }

    public String getExpiry_date() {
        return expiry_date;
    }

    public void setExpiry_date(String expiry_date) {
        this.expiry_date = expiry_date;
    }
}
