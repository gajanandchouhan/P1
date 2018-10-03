package com.superlifesecretcode.app.ui.kpi_summery;

import android.content.Context;

import com.superlifesecretcode.app.R;
import com.superlifesecretcode.app.data.model.BaseResponseModel;
import com.superlifesecretcode.app.data.netcomm.ApiController;
import com.superlifesecretcode.app.data.netcomm.CheckNetworkState;
import com.superlifesecretcode.app.data.netcomm.RequestType;
import com.superlifesecretcode.app.data.netcomm.ResponseHandler;
import com.superlifesecretcode.app.data.persistance.SuperLifeSecretPreferences;
import com.superlifesecretcode.app.ui.base.BasePresenter;
import com.superlifesecretcode.app.util.CommonUtils;
import com.superlifesecretcode.app.util.ConstantLib;

import java.util.Map;

public class KpiPresenter extends BasePresenter<KpiView> {

    KpiView view;

    public KpiPresenter(Context mContext) {
        super(mContext);
    }

    @Override
    protected void setView(KpiView view) {
        this.view = view;
    }

    public void getKpiSummeryData(Map<String, String> headers) {
        if (!CheckNetworkState.isOnline(mContext)) {
            CommonUtils.showSnakeBar(mContext, mContext.getString(R.string.no_internet));
            return;
        }

        view.showProgress();
        ApiController apiController = ApiController.getInstance();
        apiController.callGetWithHeader(mContext, RequestType.REQ_GET_KPI_SUMMERY, new ResponseHandler<KPI>() {
            @Override
            public void onResponse(KPI shareListResponseModel) {

                view.hideProgress();
                if (shareListResponseModel != null) {
                    if (shareListResponseModel.getStatus().equalsIgnoreCase(ConstantLib.RESPONSE_SUCCESS)) {
                        view.getKpiSummeryData(shareListResponseModel);
                    } else
                        CommonUtils.showToast(mContext, SuperLifeSecretPreferences.getInstance().getConversionData().getNo_sharing());
                } else {
                    CommonUtils.showToast(mContext,mContext.getString(R.string.server_error));
                    //CommonUtils.showSnakeBar(mContext, mContext.getString(R.string.server_error));
                }
            }

            @Override
            public void onError() {
                view.hideProgress();
                CommonUtils.showToast(mContext,mContext.getString(R.string.server_error));
                //CommonUtils.showSnakeBar(mContext, mContext.getString(R.string.server_error));
            }
        }, headers,"","");
    }
}
