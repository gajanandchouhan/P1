package com.superlifesecretcode.app.ui.dailyactivities.personalevent;

import android.content.Context;

import com.superlifesecretcode.app.R;
import com.superlifesecretcode.app.data.model.standardevent.StandardEventResponseModel;
import com.superlifesecretcode.app.data.netcomm.ApiController;
import com.superlifesecretcode.app.data.netcomm.CheckNetworkState;
import com.superlifesecretcode.app.data.netcomm.RequestType;
import com.superlifesecretcode.app.data.netcomm.ResponseHandler;
import com.superlifesecretcode.app.ui.base.BasePresenter;
import com.superlifesecretcode.app.util.CommonUtils;
import com.superlifesecretcode.app.util.ConstantLib;

import java.util.HashMap;

/**
 * Created by Divya on 14-03-2018.
 */

public class AddEventPresenter extends BasePresenter<AddEventView> {
    AddEventView view;

    public AddEventPresenter(Context mContext) {
        super(mContext);
    }

    @Override
    protected void setView(AddEventView newsView) {
        view = newsView;
    }

    public void getStandardevents(HashMap<String, String> params) {
        if (!CheckNetworkState.isOnline(mContext)) {
            CommonUtils.showSnakeBar(mContext, mContext.getString(R.string.no_internet));
            return;
        }
        view.showProgress();
        ApiController apiController = ApiController.getInstance();
        apiController.call(mContext, RequestType.REQ_GET_STANDARD_EVENT, new ResponseHandler<StandardEventResponseModel>() {
            @Override
            public void onResponse(StandardEventResponseModel standardEventResponseModel) {
                view.hideProgress();
                if (standardEventResponseModel != null) {
                    if (standardEventResponseModel.getStatus().equalsIgnoreCase(ConstantLib.RESPONSE_SUCCESS)) {
                        view.setStandardActivities(standardEventResponseModel.getData());
                    } else
                        CommonUtils.showToast(mContext, standardEventResponseModel.getMessage());
                } else {
                    CommonUtils.showSnakeBar(mContext, mContext.getString(R.string.server_error));
                }

            }

            @Override
            public void onError() {
                view.hideProgress();
                CommonUtils.showSnakeBar(mContext, mContext.getString(R.string.server_error));

            }
        }, params);
    }


}
