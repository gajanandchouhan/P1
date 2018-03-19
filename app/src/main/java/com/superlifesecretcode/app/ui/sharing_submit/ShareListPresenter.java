package com.superlifesecretcode.app.ui.sharing_submit;

import android.content.Context;

import com.superlifesecretcode.app.R;
import com.superlifesecretcode.app.data.model.events.EventResponseModel;
import com.superlifesecretcode.app.data.model.shares.ShareListResponseModel;
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
 * Created by Divya on 19-03-2018.
 */

public class ShareListPresenter extends BasePresenter<ShareListView> {
    ShareListView view;

    public ShareListPresenter(Context mContext) {
        super(mContext);
    }

    @Override
    protected void setView(ShareListView shareListView) {
        this.view = view;
    }

    public void getShare(HashMap<String, String> params, Map<String, String> headers) {
        if (!CheckNetworkState.isOnline(mContext)) {
            CommonUtils.showSnakeBar(mContext, mContext.getString(R.string.no_internet));
            return;
        }
        view.showProgress();
        ApiController apiController = ApiController.getInstance();
        apiController.callWithHeader(mContext, RequestType.REQ_GET_USER_SHARE, new ResponseHandler<ShareListResponseModel>() {
            @Override
            public void onResponse(ShareListResponseModel shareListResponseModel) {
                view.hideProgress();
                if (shareListResponseModel != null) {
                    if (shareListResponseModel.getStatus().equalsIgnoreCase(ConstantLib.RESPONSE_SUCCESS)) {
                        view.setShareListData(shareListResponseModel.getData());
                    } else
                        CommonUtils.showToast(mContext, shareListResponseModel.getMessage());
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
