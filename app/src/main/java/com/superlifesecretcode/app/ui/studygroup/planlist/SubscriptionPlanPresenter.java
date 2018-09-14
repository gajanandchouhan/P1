package com.superlifesecretcode.app.ui.studygroup.planlist;

import android.content.Context;

import com.superlifesecretcode.app.R;
import com.superlifesecretcode.app.data.model.studygroups.studtgroupplans.StudyGroupPlanResponseModel;
import com.superlifesecretcode.app.data.netcomm.ApiController;
import com.superlifesecretcode.app.data.netcomm.CheckNetworkState;
import com.superlifesecretcode.app.data.netcomm.RequestType;
import com.superlifesecretcode.app.data.netcomm.ResponseHandler;
import com.superlifesecretcode.app.ui.base.BasePresenter;
import com.superlifesecretcode.app.util.CommonUtils;
import com.superlifesecretcode.app.util.ConstantLib;

import java.util.HashMap;
import java.util.Map;

public class SubscriptionPlanPresenter extends BasePresenter<SubscriptionPlanView> {
    SubscriptionPlanView view;

    public SubscriptionPlanPresenter(Context mContext) {
        super(mContext);
    }

    @Override
    protected void setView(SubscriptionPlanView subscriptionPlanView) {
        view = subscriptionPlanView;
    }
    public void getPlanList(HashMap<String, String> params, Map<String, String> headers) {
        if (!CheckNetworkState.isOnline(mContext)) {
            CommonUtils.showSnakeBar(mContext, mContext.getString(R.string.no_internet));
            return;
        }
        view.showProgress();
        ApiController apiController = ApiController.getInstance();
        apiController.callWithHeader(mContext, RequestType.REQ_GET_SUBSCRIPTION_PLAN, new ResponseHandler<StudyGroupPlanResponseModel>() {
            @Override
            public void onResponse(StudyGroupPlanResponseModel baseResponseModel) {
                view.hideProgress();
                if (baseResponseModel != null) {
                    if (baseResponseModel.getStatus().equalsIgnoreCase(ConstantLib.RESPONSE_SUCCESS)) {
                        view.setItemList(baseResponseModel.getData());
                    } else
                        CommonUtils.showToast(mContext, baseResponseModel.getMessage());
                } else {
                    CommonUtils.showSnakeBar(mContext, mContext.getString(R.string.server_error));
                }

            }

            @Override
            public void onError() {
                view.hideProgress();
                CommonUtils.showSnakeBar(mContext, mContext.getString(R.string.server_error));

            }
        }, params, headers);
    }
}
