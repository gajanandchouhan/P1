package com.superlifesecretcode.app.ui.login;

import android.content.Context;

import com.superlifesecretcode.app.R;
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
 * Created by Divya on 01-03-2018.
 */

public class LoginPresenter extends BasePresenter<LoginView> {
    private LoginView view;

    public LoginPresenter(Context mContext) {
        super(mContext);
    }

    @Override
    protected void setView(LoginView registerView) {
        view = registerView;
    }


    public void loginUser(HashMap requestBody) {
        if (!CheckNetworkState.isOnline(mContext)) {
            CommonUtils.showSnakeBar(mContext, mContext.getString(R.string.no_internet));
            return;
        }
        view.showProgress();
        ApiController apiController = ApiController.getInstance();
        apiController.call(mContext, RequestType.REQ_LOGIN, new ResponseHandler<UserDetailResponseModel>() {
            @Override
            public void onResponse(UserDetailResponseModel userDetailResponseModel) {
                view.hideProgress();
                if (userDetailResponseModel != null) {
                    if (userDetailResponseModel.getStatus().equalsIgnoreCase(ConstantLib.RESPONSE_SUCCESS)) {
                        view.setUserData(userDetailResponseModel.getData());
                    } else {
                        CommonUtils.showSnakeBar(mContext, userDetailResponseModel.getMessage());
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
