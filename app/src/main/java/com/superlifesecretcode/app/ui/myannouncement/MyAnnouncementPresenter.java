package com.superlifesecretcode.app.ui.myannouncement;

import android.content.Context;

import com.superlifesecretcode.app.R;
import com.superlifesecretcode.app.data.model.BaseResponseModel;
import com.superlifesecretcode.app.data.netcomm.ApiController;
import com.superlifesecretcode.app.data.netcomm.CheckNetworkState;
import com.superlifesecretcode.app.data.netcomm.RequestType;
import com.superlifesecretcode.app.data.netcomm.ResponseHandler;
import com.superlifesecretcode.app.ui.base.BasePresenter;
import com.superlifesecretcode.app.util.CommonUtils;
import com.superlifesecretcode.app.util.ConstantLib;

import java.util.Map;

public class MyAnnouncementPresenter extends BasePresenter<MyAnnouncementView> {
    private MyAnnouncementView view;

    public MyAnnouncementPresenter(Context mContext) {
        super(mContext);
    }

    @Override
    protected void setView(MyAnnouncementView myAnnouncementView) {
        view = myAnnouncementView;
    }

    public void getMyAnnoucement(Map<String, String> headers) {
        if (!CheckNetworkState.isOnline(mContext)) {
            CommonUtils.showSnakeBar(mContext, mContext.getString(R.string.no_internet));
            return;
        }
        view.showProgress();
        ApiController apiController = ApiController.getInstance();
        apiController.callGetWithHeader(mContext, RequestType.REQ_GET_MY_ANNOUCMENT, new ResponseHandler<BaseResponseModel>() {
            @Override
            public void onResponse(BaseResponseModel shareListResponseModel) {
                view.hideProgress();
                if (shareListResponseModel != null) {
                    if (shareListResponseModel.getStatus().equalsIgnoreCase(ConstantLib.RESPONSE_SUCCESS)) {

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
        },headers, null, null);
    }
}
