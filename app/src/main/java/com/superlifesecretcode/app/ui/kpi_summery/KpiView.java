package com.superlifesecretcode.app.ui.kpi_summery;

import com.superlifesecretcode.app.data.model.kpi.KPI;
import com.superlifesecretcode.app.ui.base.BaseView;

public interface KpiView extends BaseView{

    void getKpiSummeryData(KPI kpi);
}
