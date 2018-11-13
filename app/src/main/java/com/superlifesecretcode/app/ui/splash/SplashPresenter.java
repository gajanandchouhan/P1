package com.superlifesecretcode.app.ui.splash;

import android.content.Context;
import android.os.Handler;

import com.superlifesecretcode.app.R;
import com.superlifesecretcode.app.data.model.BaseResponseModel;
import com.superlifesecretcode.app.data.model.language.LanguageResponseModel;
import com.superlifesecretcode.app.data.netcomm.ApiController;
import com.superlifesecretcode.app.data.netcomm.CheckNetworkState;
import com.superlifesecretcode.app.data.netcomm.RequestType;
import com.superlifesecretcode.app.data.netcomm.ResponseHandler;
import com.superlifesecretcode.app.ui.base.BasePresenter;
import com.superlifesecretcode.app.util.CommonUtils;
import com.superlifesecretcode.app.util.ConstantLib;

import java.util.HashMap;


/**
 * Created by Divya on 21-02-2018.
 */

public class SplashPresenter extends BasePresenter<SplashView> {
    private SplashView view;

    public SplashPresenter(Context mContext) {
        super(mContext);
    }

    @Override
    protected void setView(SplashView splashView) {
        view = splashView;
    }

    public void delaySplash() {
        mHandler.postDelayed(mRunnable, 3000);
    }

    Handler mHandler = new Handler();
    Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            view.navigateToNextScreen();
        }
    };

    public void cancelDelay() {
        mHandler.removeCallbacks(mRunnable);
    }


    public void validateAppVersion(HashMap requestBody) {
        if (!CheckNetworkState.isOnline(mContext)) {
            CommonUtils.showSnakeBar(mContext, mContext.getString(R.string.no_internet));
            return;
        }
        ApiController apiController = ApiController.getInstance();
        apiController.call(mContext, RequestType.REQ_VALIDATE_VERSION, new ResponseHandler<BaseResponseModel>() {
            @Override
            public void onResponse(BaseResponseModel baseResponseModel) {
                if (baseResponseModel != null) {
                    view.validateVersion(baseResponseModel);
                } else {
                    CommonUtils.showSnakeBar(mContext, mContext.getString(R.string.server_error));
                }

            }

            @Override
            public void onError() {
                CommonUtils.showSnakeBar(mContext, mContext.getString(R.string.server_error));

            }
        }, requestBody);
    }
}
