package com.superlifesecretcode.app.ui.dailyactivities.personalevent;

import android.content.Context;

import com.superlifesecretcode.app.R;
import com.superlifesecretcode.app.data.model.BaseResponseModel;
import com.superlifesecretcode.app.data.model.interesetdevent.InterestedEventResponseModel;
import com.superlifesecretcode.app.data.model.personalevent.PersonalEventResponseModel;
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
 * Created by Divya on 27-03-2018.
 */

class PersonalEventPresenter extends BasePresenter<PersonalEventView> {
    PersonalEventView view;

    public PersonalEventPresenter(Context mContext) {
        super(mContext);
    }

    @Override
    protected void setView(PersonalEventView personalEventView) {
        view = personalEventView;
    }


    public void getPersonalEvent(HashMap<String, String> params, Map<String, String> headers) {
        if (!CheckNetworkState.isOnline(mContext)) {
            CommonUtils.showSnakeBar(mContext, mContext.getString(R.string.no_internet));
            return;
        }
        view.showProgress();
        ApiController apiController = ApiController.getInstance();
        apiController.callWithHeader(mContext, RequestType.REQ_GET_PERSONAL_EVENT, new ResponseHandler<PersonalEventResponseModel>() {
            @Override
            public void onResponse(PersonalEventResponseModel newsResponseModel) {
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

    public void updateEventStatus(HashMap<String, String> params, Map<String, String> headers) {
        if (!CheckNetworkState.isOnline(mContext)) {
            view.onStatusFailed();
            CommonUtils.showSnakeBar(mContext, mContext.getString(R.string.no_internet));
            return;
        }
        view.showProgress();
        ApiController apiController = ApiController.getInstance();
        apiController.callWithHeader(mContext, RequestType.REQ_UPDATE_EVENT_STATUS, new ResponseHandler<BaseResponseModel>() {
            @Override
            public void onResponse(BaseResponseModel baseResponseModel) {
                view.hideProgress();
                if (baseResponseModel != null) {
                    if (baseResponseModel.getStatus().equalsIgnoreCase(ConstantLib.RESPONSE_SUCCESS)) {
                        view.onStatusUpdated();
                    } else {
                        view.onStatusFailed();
                        CommonUtils.showToast(mContext, baseResponseModel.getMessage());
                    }

                } else {
                    view.onStatusFailed();
                    CommonUtils.showSnakeBar(mContext, mContext.getString(R.string.server_error));
                }

            }

            @Override
            public void onError() {
                view.onStatusFailed();
                view.hideProgress();
                CommonUtils.showSnakeBar(mContext, mContext.getString(R.string.server_error));

            }
        }, params, headers);
    }

    public void removeActivity(HashMap<String, String> params, Map<String, String> headers) {
        if (!CheckNetworkState.isOnline(mContext)) {
            view.onStatusFailed();
            CommonUtils.showSnakeBar(mContext, mContext.getString(R.string.no_internet));
            return;
        }
        view.showProgress();
        ApiController apiController = ApiController.getInstance();
        apiController.callWithHeader(mContext, RequestType.REQ_REMOVE_ACTIVITY, new ResponseHandler<BaseResponseModel>() {
            @Override
            public void onResponse(BaseResponseModel baseResponseModel) {
                view.hideProgress();
                if (baseResponseModel != null) {
                    if (baseResponseModel.getStatus().equalsIgnoreCase(ConstantLib.RESPONSE_SUCCESS)) {
                        view.onRemoveSuccess();
                    } else {
                        CommonUtils.showToast(mContext, baseResponseModel.getMessage());
                    }

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

