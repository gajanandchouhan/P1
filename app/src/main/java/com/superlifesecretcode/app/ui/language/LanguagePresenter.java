package com.superlifesecretcode.app.ui.language;

import android.content.Context;

import com.superlifesecretcode.app.R;
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
 * Created by Divya on 01-03-2018.
 */

public class LanguagePresenter extends BasePresenter<LanguageView> {
    private LanguageView view;

    public LanguagePresenter(Context mContext) {
        super(mContext);
    }

    @Override
    protected void setView(LanguageView languageView) {
        view = languageView;
    }

    public void getConversion(HashMap requestBody) {
        if (!CheckNetworkState.isOnline(mContext)) {
            CommonUtils.showSnakeBar(mContext, mContext.getString(R.string.no_internet));
            return;
        }
        view.showProgress();
        ApiController apiController = ApiController.getInstance();
        apiController.call(mContext, RequestType.REQ_CONVERSION, new ResponseHandler<LanguageResponseModel>() {
            @Override
            public void onResponse(LanguageResponseModel languageResponseModel) {
                view.hideProgress();
                if (languageResponseModel != null) {
                    if (languageResponseModel.getStatus().equalsIgnoreCase(ConstantLib.RESPONSE_SUCCESS)) {
                        view.setConversionContent(languageResponseModel.getData());
                    } else {
                        CommonUtils.showSnakeBar(mContext, languageResponseModel.getMessage());
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
