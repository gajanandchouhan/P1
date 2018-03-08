package com.superlifesecretcode.app.ui.profile;

import android.content.Context;

import com.superlifesecretcode.app.R;
import com.superlifesecretcode.app.data.model.country.CountryResponseModel;
import com.superlifesecretcode.app.data.model.language.LanguageResponseModel;
import com.superlifesecretcode.app.data.model.userdetails.UserDetailResponseModel;
import com.superlifesecretcode.app.data.netcomm.ApiController;
import com.superlifesecretcode.app.data.netcomm.CheckNetworkState;
import com.superlifesecretcode.app.data.netcomm.RequestType;
import com.superlifesecretcode.app.data.netcomm.ResponseHandler;
import com.superlifesecretcode.app.ui.base.BasePresenter;
import com.superlifesecretcode.app.util.CommonUtils;
import com.superlifesecretcode.app.util.ConstantLib;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Divya on 05-03-2018.
 */

public class ProfilePresenter extends BasePresenter<ProfileView> {
    ProfileView view;

    public ProfilePresenter(Context mContext) {
        super(mContext);
    }

    @Override
    protected void setView(ProfileView profileView) {
        view = profileView;
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


    public void updateUser(HashMap<String, String> params, HashMap<String, File> files, Map<String, String> headers) {
        if (!CheckNetworkState.isOnline(mContext)) {
            CommonUtils.showSnakeBar(mContext, mContext.getString(R.string.no_internet));
            return;
        }
        view.showProgress();
        ApiController apiController = ApiController.getInstance();
        apiController.callMultiparWithHeader(mContext, RequestType.REQ_UPDATE_PROFILE, new ResponseHandler<UserDetailResponseModel>() {
            @Override
            public void onResponse(UserDetailResponseModel userDetailResponseModel) {
                view.hideProgress();
                if (userDetailResponseModel != null) {
                    if (userDetailResponseModel.getStatus().equalsIgnoreCase(ConstantLib.RESPONSE_SUCCESS)) {
                        view.setUserData(userDetailResponseModel.getData());
                    }
                    CommonUtils.showSnakeBar(mContext, userDetailResponseModel.getMessage());
                } else {
                    CommonUtils.showSnakeBar(mContext, mContext.getString(R.string.server_error));
                }

            }

            @Override
            public void onError() {
                view.hideProgress();
                CommonUtils.showSnakeBar(mContext, mContext.getString(R.string.server_error));

            }
        }, params, files, headers);
    }
}
