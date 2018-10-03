package com.superlifesecretcode.app.ui.mycountryactivities;

import android.content.Context;
import com.superlifesecretcode.app.R;
import com.superlifesecretcode.app.data.model.BaseResponseModel;
import com.superlifesecretcode.app.data.model.mycountryactivities.MyCountryActivityResponseModel;
import com.superlifesecretcode.app.data.netcomm.ApiController;
import com.superlifesecretcode.app.data.netcomm.CheckNetworkState;
import com.superlifesecretcode.app.data.netcomm.RequestType;
import com.superlifesecretcode.app.data.netcomm.ResponseHandler;
import com.superlifesecretcode.app.ui.base.BasePresenter;
import com.superlifesecretcode.app.ui.myannouncement.MyAnnouncementView;
import com.superlifesecretcode.app.util.CommonUtils;
import com.superlifesecretcode.app.util.ConstantLib;

import java.util.HashMap;
import java.util.Map;

public class MyCountryActivitityPresenter extends BasePresenter<MyCountryActivityView> {
    private MyCountryActivityView view;

    public MyCountryActivitityPresenter(Context mContext) {
        super(mContext);
    }

    @Override
    protected void setView(MyCountryActivityView myAnnouncementView) {
        view = myAnnouncementView;
    }

    public void getMyCountryActivity(Map<String, String> headers) {
        if (!CheckNetworkState.isOnline(mContext)) {
            CommonUtils.showSnakeBar(mContext, mContext.getString(R.string.no_internet));
            return;
        }
        view.showProgress();
        ApiController apiController = ApiController.getInstance();
        apiController.callGetWithHeader(mContext, RequestType.REQ_GET_MY_COUNTRY_ACTIVITY, new ResponseHandler<MyCountryActivityResponseModel>() {
            @Override
            public void onResponse(MyCountryActivityResponseModel annoucmenntResponseModel) {
                view.hideProgress();
                if (annoucmenntResponseModel != null) {
                    if (annoucmenntResponseModel.getStatus().equalsIgnoreCase(ConstantLib.RESPONSE_SUCCESS)) {
                        view.setCountryActivties(annoucmenntResponseModel.getData());
                        view.onPermissionStatus(annoucmenntResponseModel.getPermissionStatus());
                    } else {
                        view.onPermissionStatus(annoucmenntResponseModel.getPermissionStatus());
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
        }, headers, null, null);
    }


    public void deleteActivity(HashMap<String, String> params, Map<String, String> headers) {
        if (!CheckNetworkState.isOnline(mContext)) {
            CommonUtils.showSnakeBar(mContext, mContext.getString(R.string.no_internet));
            return;
        }
        view.showProgress();
        ApiController apiController = ApiController.getInstance();
        apiController.callWithHeader(mContext, RequestType.REQ_DELETE_ACTIVITY, new ResponseHandler<BaseResponseModel>() {
            @Override
            public void onResponse(BaseResponseModel baseResponseModel) {
                view.hideProgress();
                if (baseResponseModel != null) {
                    if (baseResponseModel.getStatus().equalsIgnoreCase(ConstantLib.RESPONSE_SUCCESS)) {
                        view.deleted();
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


    public void sendEventAddRequest(Map<String, String> headers) {
        if (!CheckNetworkState.isOnline(mContext)) {
            CommonUtils.showSnakeBar(mContext, mContext.getString(R.string.no_internet));
            return;
        }
        view.showProgress();
        ApiController apiController = ApiController.getInstance();
        apiController.callGetWithHeader(mContext, RequestType.REQ_SEND_EVENT_REQ, new ResponseHandler<BaseResponseModel>() {
            @Override
            public void onResponse(BaseResponseModel annoucmenntResponseModel) {
                view.hideProgress();
                if (annoucmenntResponseModel != null) {
                    if (annoucmenntResponseModel.getStatus().equals(ConstantLib.RESPONSE_SUCCESS))
                        view.onRequestSuccess();
                    else
                        view.onRequestFailed();
                    CommonUtils.showToast(mContext, annoucmenntResponseModel.getMessage());
                } else {
                    CommonUtils.showSnakeBar(mContext, mContext.getString(R.string.server_error));
                }

            }

            @Override
            public void onError() {
                view.hideProgress();
                CommonUtils.showSnakeBar(mContext, mContext.getString(R.string.server_error));

            }
        }, headers, null, null);
    }
}
