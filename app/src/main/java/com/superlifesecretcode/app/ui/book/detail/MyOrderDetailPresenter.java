package com.superlifesecretcode.app.ui.book.detail;

import android.content.Context;

import com.superlifesecretcode.app.R;
import com.superlifesecretcode.app.data.model.BaseResponseModel;
import com.superlifesecretcode.app.data.netcomm.ApiController;
import com.superlifesecretcode.app.data.netcomm.CheckNetworkState;
import com.superlifesecretcode.app.data.netcomm.RequestType;
import com.superlifesecretcode.app.data.netcomm.ResponseHandler;
import com.superlifesecretcode.app.ui.base.BasePresenter;
import com.superlifesecretcode.app.ui.book.myorder_book.MyOrderBean;
import com.superlifesecretcode.app.util.CommonUtils;
import com.superlifesecretcode.app.util.ConstantLib;

import java.util.HashMap;
import java.util.Map;

public class MyOrderDetailPresenter extends BasePresenter<MyOrderDetailView> {

    MyOrderDetailView view;

    public MyOrderDetailPresenter(Context mContext) {
        super(mContext);
    }

    @Override
    protected void setView(MyOrderDetailView view) {
        this.view=view;
    }

    public void getOrderDetail(HashMap<String, String> params, Map<String, String> headers) {
        if (!CheckNetworkState.isOnline(mContext)) {
            CommonUtils.showSnakeBar(mContext, mContext.getString(R.string.no_internet));
            return;
        }

        view.showProgress();
        ApiController apiController = ApiController.getInstance();
        apiController.callWithHeader(mContext, RequestType.REQ_GET_ORDER_DETAIL_BOOK, new ResponseHandler<DetailBean>() {
            @Override
            public void onResponse(DetailBean newsResponseModel) {
                view.hideProgress();
                if (newsResponseModel != null) {
                    if (newsResponseModel.getStatus().equalsIgnoreCase(ConstantLib.RESPONSE_SUCCESS)) {
                        view.getOrderDetail(newsResponseModel);
                    }
                    //CommonUtils.showToast(mContext, newsResponseModel.getMessage());
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
