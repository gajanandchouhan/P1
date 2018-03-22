package com.superlifesecretcode.app.ui.dailyactivities;

import android.content.Context;

import com.superlifesecretcode.app.R;
import com.superlifesecretcode.app.data.model.interesetdevent.InterestedEventResponseModel;
import com.superlifesecretcode.app.data.netcomm.ApiController;
import com.superlifesecretcode.app.data.netcomm.CheckNetworkState;
import com.superlifesecretcode.app.data.netcomm.RequestType;
import com.superlifesecretcode.app.data.netcomm.ResponseHandler;
import com.superlifesecretcode.app.ui.base.BasePresenter;
import com.superlifesecretcode.app.util.CommonUtils;
import com.superlifesecretcode.app.util.ConstantLib;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Divya on 14-03-2018.
 */

public class InterestedEventPresenter extends BasePresenter<InterestedEventView> {
    InterestedEventView view;

    public InterestedEventPresenter(Context mContext) {
        super(mContext);
    }

    @Override
    protected void setView(InterestedEventView newsView) {
        view = newsView;
    }

    public void getInterestedEvent(HashMap<String, String> params, Map<String, String> headers) {
        if (!CheckNetworkState.isOnline(mContext)) {
            CommonUtils.showSnakeBar(mContext, mContext.getString(R.string.no_internet));
            return;
        }
        view.showProgress();
        ApiController apiController = ApiController.getInstance();
        apiController.callWithHeader(mContext, RequestType.REQ_MY_INTERESTED_EVENT, new ResponseHandler<InterestedEventResponseModel>() {
            @Override
            public void onResponse(InterestedEventResponseModel newsResponseModel) {
                view.hideProgress();
                if (newsResponseModel != null) {
                    if (newsResponseModel.getStatus().equalsIgnoreCase(ConstantLib.RESPONSE_SUCCESS)) {
                        view.setEventData(newsResponseModel.getData());
                    } else
                        CommonUtils.showToast(mContext, newsResponseModel.getMessage());
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


