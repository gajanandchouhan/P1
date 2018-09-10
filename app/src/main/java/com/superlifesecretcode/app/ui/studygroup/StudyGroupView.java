package com.superlifesecretcode.app.ui.studygroup;

import com.superlifesecretcode.app.data.model.studygroups.StudyGroupData;
import com.superlifesecretcode.app.ui.base.BaseView;

interface StudyGroupView extends BaseView {
    void setStudyGroupList(StudyGroupData data);
}
