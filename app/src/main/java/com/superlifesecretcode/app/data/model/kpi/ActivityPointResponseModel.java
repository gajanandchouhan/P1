package com.superlifesecretcode.app.data.model.kpi;

import com.google.gson.annotations.SerializedName;
import com.superlifesecretcode.app.data.model.BaseResponseModel;

import java.util.List;

public class ActivityPointResponseModel extends BaseResponseModel {

    @SerializedName("data")
    private List<TaskDetails> taskDetails;
    private String sharing_point;

    public ActivityPointResponseModel(List<TaskDetails> taskDetails, String sharing_point) {
        this.taskDetails = taskDetails;
        this.sharing_point = sharing_point;
    }

    public String getSharing_point() {
        return sharing_point;
    }

    public List<TaskDetails> getTaskDetails() {
        return taskDetails;
    }
}
