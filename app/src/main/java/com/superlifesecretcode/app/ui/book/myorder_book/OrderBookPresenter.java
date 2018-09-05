package com.superlifesecretcode.app.ui.book.myorder_book;

import android.content.Context;

import com.superlifesecretcode.app.R;
import com.superlifesecretcode.app.data.netcomm.ApiController;
import com.superlifesecretcode.app.data.netcomm.CheckNetworkState;
import com.superlifesecretcode.app.data.netcomm.RequestType;
import com.superlifesecretcode.app.data.netcomm.ResponseHandler;
import com.superlifesecretcode.app.ui.base.BasePresenter;
import com.superlifesecretcode.app.ui.book.five.BankBean;
import com.superlifesecretcode.app.util.CommonUtils;
import com.superlifesecretcode.app.util.ConstantLib;

import java.util.Map;

public class OrderBookPresenter extends BasePresenter<OrderBookView> {

    OrderBookView view;

    public OrderBookPresenter(Context mContext) {
        super(mContext);
    }

    @Override
    protected void setView(OrderBookView view) {
        this.view=view;
    }


    public void getMyOrderList(Map<String, String> headers) {
        if (!CheckNetworkState.isOnline(mContext)) {
            CommonUtils.showSnakeBar(mContext, mContext.getString(R.string.no_internet));
            return;
        }
        view.showProgress();
        ApiController apiController = ApiController.getInstance();
        apiController.callGetWithHeader(mContext, RequestType.REQ_GET_ORDER_LIST_BOOK, new ResponseHandler<MyOrderBean>() {
            @Override
            public void onResponse(MyOrderBean categoryResponseModel) {
                view.hideProgress();
                if (categoryResponseModel != null) {
                    if (categoryResponseModel.getStatus().equalsIgnoreCase(ConstantLib.RESPONSE_SUCCESS))
                        view.getOrderlist(categoryResponseModel);
                    else {
                        CommonUtils.showSnakeBar(mContext, categoryResponseModel.getMessage());
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
        },headers,"","");
    }

}
