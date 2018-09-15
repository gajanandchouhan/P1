package com.superlifesecretcode.app.ui.studygroup.planlist;

import com.superlifesecretcode.app.data.model.studygroups.studtgroupplans.StudyGroupPlanData;
import com.superlifesecretcode.app.ui.base.BaseView;

import java.util.List;

public interface SubscriptionPlanView extends BaseView{
    void setItemList(List<StudyGroupPlanData> data);
}
