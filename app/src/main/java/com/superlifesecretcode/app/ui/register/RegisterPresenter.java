package com.superlifesecretcode.app.ui.register;

import android.content.Context;

import com.superlifesecretcode.app.R;
import com.superlifesecretcode.app.data.model.country.CountryResponseModel;
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

public class RegisterPresenter extends BasePresenter<RegisterView> {
    private RegisterView view;

    public RegisterPresenter(Context mContext) {
        super(mContext);
    }

    @Override
    protected void setView(RegisterView registerView) {
        view = registerView;
    }

    public void getCountry() {
        if (!CheckNetworkState.isOnline(mContext)) {
            CommonUtils.showSnakeBar(mContext, mContext.getString(R.string.no_internet));
            return;
        }
        view.showProgress();
        ApiController apiController = ApiController.getInstance();
        apiController.callGet(mContext, RequestType.REQ_GET_COUNTRY, new ResponseHandler<CountryResponseModel>() {
            @Override
            public void onResponse(CountryResponseModel countryResponseModel) {
                view.hideProgress();
                if (countryResponseModel != null) {
                    if (countryResponseModel.getStatus().equalsIgnoreCase(ConstantLib.RESPONSE_SUCCESS)) {
                        view.setCountryData(countryResponseModel.getData());
                    } else {
                        CommonUtils.showSnakeBar(mContext, countryResponseModel.getMessage());
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
        });
    }

    public void getStates(HashMap requestBody) {
        if (!CheckNetworkState.isOnline(mContext)) {
            CommonUtils.showSnakeBar(mContext, mContext.getString(R.string.no_internet));
            return;
        }
        view.showProgress();
        ApiController apiController = ApiController.getInstance();
        apiController.call(mContext, RequestType.REQ_GET_STATE, new ResponseHandler<CountryResponseModel>() {
            @Override
            public void onResponse(CountryResponseModel countryResponseModel) {
                view.hideProgress();
                if (countryResponseModel != null) {
                    if (countryResponseModel.getStatus().equalsIgnoreCase(ConstantLib.RESPONSE_SUCCESS)) {
                        view.setStateData(countryResponseModel.getData());
                    } else {
                        CommonUtils.showSnakeBar(mContext, countryResponseModel.getMessage());
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
