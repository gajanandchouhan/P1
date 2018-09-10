package com.superlifesecretcode.app.data.model.studygroups;

import com.superlifesecretcode.app.data.model.BaseResponseModel;

public class StudyGroupResponseModel extends BaseResponseModel {

    private StudyGroupData data;

    public StudyGroupData getData() {
        return data;
    }

    public void setData(StudyGroupData data) {
        this.data = data;
    }
}
