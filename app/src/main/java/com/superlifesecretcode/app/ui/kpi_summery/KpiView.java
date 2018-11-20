package com.superlifesecretcode.app.ui.kpi_summery;

import com.superlifesecretcode.app.data.model.kpi.KPI;
import com.superlifesecretcode.app.data.model.kpi.TaskDetails;
import com.superlifesecretcode.app.ui.base.BaseView;

import java.util.List;

public interface KpiView extends BaseView{

    void getKpiSummeryData(KPI kpi);

    void setPointList(List<TaskDetails> taskDetails,String sharingPoints);
}
