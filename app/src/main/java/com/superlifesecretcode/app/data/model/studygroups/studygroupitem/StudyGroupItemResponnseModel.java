package com.superlifesecretcode.app.data.model.studygroups.studygroupitem;

import com.superlifesecretcode.app.data.model.BaseResponseModel;

import java.util.List;

public class StudyGroupItemResponnseModel extends BaseResponseModel {
    private List<StudyGroupItemData> data;

    public List<StudyGroupItemData> getData() {
        return data;
    }

    public void setData(List<StudyGroupItemData> data) {
        this.data = data;
    }
}
