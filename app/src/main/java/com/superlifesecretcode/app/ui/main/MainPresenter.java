package com.superlifesecretcode.app.ui.main;

import android.content.Context;

import com.superlifesecretcode.app.R;
import com.superlifesecretcode.app.data.model.category.CategoryResponseModel;
import com.superlifesecretcode.app.data.model.userdetails.UserDetailResponseModel;
import com.superlifesecretcode.app.data.netcomm.ApiController;
import com.superlifesecretcode.app.data.netcomm.CheckNetworkState;
import com.superlifesecretcode.app.data.netcomm.RequestType;
import com.superlifesecretcode.app.data.netcomm.ResponseHandler;
import com.superlifesecretcode.app.ui.base.BasePresenter;
import com.superlifesecretcode.app.util.CommonUtils;
import com.superlifesecretcode.app.util.ConstantLib;

import java.util.HashMap;

/**
 * Created by Divya on 13-03-2018.
 */

public class MainPresenter extends BasePresenter<MainView> {
    MainView view;

    public MainPresenter(Context mContext) {
        super(mContext);
    }

    @Override
    protected void setView(MainView mainView) {
        this.view = mainView;
    }

    public void getHomeCategories(HashMap requestBody) {
        if (!CheckNetworkState.isOnline(mContext)) {
            CommonUtils.showSnakeBar(mContext, mContext.getString(R.string.no_internet));
            return;
        }
        view.showProgress();
        ApiController apiController = ApiController.getInstance();
        apiController.call(mContext, RequestType.REQ_GET_CATEGORY, new ResponseHandler<CategoryResponseModel>() {
            @Override
            public void onResponse(CategoryResponseModel categoryResponseModel) {
                view.hideProgress();
                if (categoryResponseModel != null) {
                    if (categoryResponseModel.getStatus().equalsIgnoreCase(ConstantLib.RESPONSE_SUCCESS))
                        view.setHomeData(categoryResponseModel);
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
        }, requestBody);
    }
}
