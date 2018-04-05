package com.superlifesecretcode.app.ui.notification;

import android.content.Context;

import com.superlifesecretcode.app.R;
import com.superlifesecretcode.app.data.netcomm.CheckNetworkState;
import com.superlifesecretcode.app.ui.base.BasePresenter;
import com.superlifesecretcode.app.util.CommonUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Divya on 14-03-2018.
 */

public class NotificationPresenter extends BasePresenter<NotificationView> {
    NotificationView view;

    public NotificationPresenter(Context mContext) {
        super(mContext);
    }

    @Override
    protected void setView(NotificationView newsView) {
        view = newsView;
    }

    public void getNotification(HashMap<String, String> params, Map<String, String> headers) {
        if (!CheckNetworkState.isOnline(mContext)) {
            CommonUtils.showSnakeBar(mContext, mContext.getString(R.string.no_internet));
            return;
        }
   /*     view.showProgress();
        ApiController apiController = ApiController.getInstance();
        apiController.callWithHeader(mContext, RequestType.REQ_GET_NEWS_UPDATES, new ResponseHandler<NewsResponseModel>() {
            @Override
            public void onResponse(NewsResponseModel newsResponseModel) {
                view.hideProgress();
                if (newsResponseModel != null) {
                    if (newsResponseModel.getStatus().equalsIgnoreCase(ConstantLib.RESPONSE_SUCCESS)) {
                        view.setNewsData(newsResponseModel);
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
        }, params, headers);*/
    }
}
