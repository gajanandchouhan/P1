package com.superlifesecretcode.app.ui.studygroup.studygroupdetails;

import com.superlifesecretcode.app.data.model.studygroups.studygroupitem.StudyGroupItemData;
import com.superlifesecretcode.app.ui.base.BaseView;

import java.util.List;

interface StudyGroupDetailView extends BaseView{
    void setItemList(List<StudyGroupItemData> data);
}
