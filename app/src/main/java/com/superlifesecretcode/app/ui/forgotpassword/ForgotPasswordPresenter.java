package com.superlifesecretcode.app.ui.forgotpassword;

import android.content.Context;

import com.superlifesecretcode.app.R;
import com.superlifesecretcode.app.data.model.BaseResponseModel;
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

public class ForgotPasswordPresenter extends BasePresenter<ForgotPasswordView> {
    private ForgotPasswordView view;

    public ForgotPasswordPresenter(Context mContext) {
        super(mContext);
    }

    @Override
    protected void setView(ForgotPasswordView forgotPasswordView) {
        view = forgotPasswordView;
    }


    public void getResetCode(HashMap requestBody) {
        if (!CheckNetworkState.isOnline(mContext)) {
            CommonUtils.showSnakeBar(mContext, mContext.getString(R.string.no_internet));
            return;
        }
        view.showProgress();
        ApiController apiController = ApiController.getInstance();
        apiController.call(mContext, RequestType.REQ_GET_RESET_CODE, new ResponseHandler<BaseResponseModel>() {
            @Override
            public void onResponse(BaseResponseModel baseResponseModel) {
                view.hideProgress();
                if (baseResponseModel != null) {
                    if (baseResponseModel.getStatus().equalsIgnoreCase(ConstantLib.RESPONSE_SUCCESS)) {
                        view.onTempPassSuccess();
                    }
                    CommonUtils.showToast(mContext, baseResponseModel.getMessage());
                } else {
                    CommonUtils.showToast(mContext, mContext.getString(R.string.server_error));
                }

            }

            @Override
            public void onError() {
                view.hideProgress();
                CommonUtils.showToast(mContext, mContext.getString(R.string.server_error));

            }
        }, requestBody);
    }

    public void resetPassword(HashMap<String, String> requestBody) {
        if (!CheckNetworkState.isOnline(mContext)) {
            CommonUtils.showSnakeBar(mContext, mContext.getString(R.string.no_internet));
            return;
        }
        view.showProgress();
        ApiController apiController = ApiController.getInstance();
        apiController.call(mContext, RequestType.REQ_RESET_PASS, new ResponseHandler<BaseResponseModel>() {
            @Override
            public void onResponse(BaseResponseModel baseResponseModel) {
                view.hideProgress();
                if (baseResponseModel != null) {
                    if (baseResponseModel.getStatus().equalsIgnoreCase(ConstantLib.RESPONSE_SUCCESS)) {
                        view.onPasswrodChanged();
                    }
                    CommonUtils.showToast(mContext, baseResponseModel.getMessage());
                } else {
                    CommonUtils.showToast(mContext, mContext.getString(R.string.server_error));
                }

            }

            @Override
            public void onError() {
                view.hideProgress();
                CommonUtils.showToast(mContext, mContext.getString(R.string.server_error));

            }
        }, requestBody);
    }
}
