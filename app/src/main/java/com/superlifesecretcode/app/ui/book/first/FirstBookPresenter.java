package com.superlifesecretcode.app.ui.book.first;

import android.content.Context;

import com.superlifesecretcode.app.R;
import com.superlifesecretcode.app.data.netcomm.ApiController;
import com.superlifesecretcode.app.data.netcomm.CheckNetworkState;
import com.superlifesecretcode.app.data.netcomm.RequestType;
import com.superlifesecretcode.app.data.netcomm.ResponseHandler;
import com.superlifesecretcode.app.ui.base.BasePresenter;
import com.superlifesecretcode.app.util.CommonUtils;
import com.superlifesecretcode.app.util.ConstantLib;

import java.util.HashMap;
import java.util.Map;

public class FirstBookPresenter extends BasePresenter<FirstBookView> {

    FirstBookView view;

    public FirstBookPresenter(Context mContext) {
        super(mContext);
    }

    @Override
    protected void setView(FirstBookView view) {
        this.view = view;
    }

    public void getBookList(HashMap<String, String> params, Map<String, String> headers) {
        if (!CheckNetworkState.isOnline(mContext)) {
            CommonUtils.showSnakeBar(mContext, mContext.getString(R.string.no_internet));
            return;
        }
        view.showProgress();
        ApiController apiController = ApiController.getInstance();
        apiController.callWithHeader(mContext, RequestType.REQ_GET_BOOK_LIST, new ResponseHandler<BookList>() {
            @Override
            public void onResponse(BookList categoryResponseModel) {
                view.hideProgress();
                if (categoryResponseModel != null) {
                    if (categoryResponseModel.getStatus().equalsIgnoreCase(ConstantLib.RESPONSE_SUCCESS))
                        view.getBookList(categoryResponseModel);
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
        }, params, headers);
    }


}

