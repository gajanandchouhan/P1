package com.superlifesecretcode.app.ui.kpi_summery;

import android.content.Context;

import com.superlifesecretcode.app.R;
import com.superlifesecretcode.app.data.model.kpi.ActivityPointResponseModel;
import com.superlifesecretcode.app.data.model.kpi.KPI;
import com.superlifesecretcode.app.data.netcomm.ApiController;
import com.superlifesecretcode.app.data.netcomm.CheckNetworkState;
import com.superlifesecretcode.app.data.netcomm.RequestType;
import com.superlifesecretcode.app.data.netcomm.ResponseHandler;
import com.superlifesecretcode.app.data.persistance.SuperLifeSecretPreferences;
import com.superlifesecretcode.app.ui.base.BasePresenter;
import com.superlifesecretcode.app.util.CommonUtils;
import com.superlifesecretcode.app.util.ConstantLib;

import java.util.Map;

public class PointDetailsPresenter extends BasePresenter<KpiView> {

    KpiView view;

    public PointDetailsPresenter(Context mContext) {
        super(mContext);
    }

    @Override
    protected void setView(KpiView view) {
        this.view = view;
    }

    public void getPointSummary(Map<String, String> headers,Map<String,String> params) {
        if (!CheckNetworkState.isOnline(mContext)) {
            CommonUtils.showSnakeBar(mContext, mContext.getString(R.string.no_internet));
            return;
        }

        view.showProgress();
        ApiController apiController = ApiController.getInstance();
        apiController.callWithHeader(mContext, RequestType.REQ_GET_POINT_DETAILS, new ResponseHandler<ActivityPointResponseModel>() {
            @Override
            public void onResponse(ActivityPointResponseModel activityPointResponseModel) {

                view.hideProgress();
                if (activityPointResponseModel != null) {
                    if (activityPointResponseModel.getStatus().equalsIgnoreCase(ConstantLib.RESPONSE_SUCCESS)) {
                        view.setPointList(activityPointResponseModel.getTaskDetails(),activityPointResponseModel.getSharing_point());
                    } else
                        CommonUtils.showToast(mContext, activityPointResponseModel.getMessage());
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
        }, params,headers);
    }
}
