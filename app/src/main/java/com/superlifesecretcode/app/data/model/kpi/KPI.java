package com.superlifesecretcode.app.data.model.kpi;

import com.superlifesecretcode.app.data.model.BaseResponseModel;

public class KPI extends BaseResponseModel{

    KpiBean data;

    public KpiBean getData() {
        return data;
    }

    public void setData(KpiBean data) {
        this.data = data;
    }
}
