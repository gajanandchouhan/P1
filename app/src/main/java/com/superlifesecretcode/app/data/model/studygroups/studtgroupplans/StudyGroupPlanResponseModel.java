package com.superlifesecretcode.app.data.model.studygroups.studtgroupplans;

import com.superlifesecretcode.app.data.model.BaseResponseModel;

import java.util.List;

public class StudyGroupPlanResponseModel extends BaseResponseModel {
    List<StudyGroupPlanData> data;

    public List<StudyGroupPlanData> getData() {
        return data;
    }

    public void setData(List<StudyGroupPlanData> data) {
        this.data = data;
    }
}
