package com.superlifesecretcode.app.ui.countryactivities;

import android.content.Context;

import com.superlifesecretcode.app.R;
import com.superlifesecretcode.app.data.model.BaseResponseModel;
import com.superlifesecretcode.app.data.model.country.CountryResponseModel;
import com.superlifesecretcode.app.data.model.countryactivities.CountryActivitiesResponseModel;
import com.superlifesecretcode.app.data.model.countryactivities.CountryActivityDetailResponseModel;
import com.superlifesecretcode.app.data.model.events.EventResponseModel;
import com.superlifesecretcode.app.data.netcomm.ApiController;
import com.superlifesecretcode.app.data.netcomm.CheckNetworkState;
import com.superlifesecretcode.app.data.netcomm.RequestType;
import com.superlifesecretcode.app.data.netcomm.ResponseHandler;
import com.superlifesecretcode.app.data.persistance.SuperLifeSecretPreferences;
import com.superlifesecretcode.app.ui.base.BasePresenter;
import com.superlifesecretcode.app.util.CommonUtils;
import com.superlifesecretcode.app.util.ConstantLib;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Divya on 14-03-2018.
 */

public class CountryAcivitiesPresenter extends BasePresenter<CountryActivitiesView> {
    CountryActivitiesView view;

    public CountryAcivitiesPresenter(Context mContext) {
        super(mContext);
    }

    @Override
    public void setView(CountryActivitiesView newsView) {
        view = newsView;
    }

    public void getAcivities(HashMap<String, String> params, Map<String, String> headers, boolean showProgress) {
        if (!CheckNetworkState.isOnline(mContext)) {
            CommonUtils.showSnakeBar(mContext, mContext.getString(R.string.no_internet));
            return;
        }
        if (showProgress)
            view.showProgress();
        ApiController apiController = ApiController.getInstance();
        apiController.callWithHeader(mContext, RequestType.REQ_GET_COUNTRY_ACTIVITY, new ResponseHandler<CountryActivitiesResponseModel>() {
            @Override
            public void onResponse(CountryActivitiesResponseModel activitiesResponseModel) {
                view.hideProgress();
                if (activitiesResponseModel != null) {
                    if (activitiesResponseModel.getStatus().equalsIgnoreCase(ConstantLib.RESPONSE_SUCCESS)) {
                        view.setCountyAcivityData(activitiesResponseModel.getData());
                    } else
                        CommonUtils.showToast(mContext, SuperLifeSecretPreferences.getInstance().getConversionData().getNo_activity());
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

    public void makeInterested(HashMap<String, String> params, Map<String, String> headers) {
        if (!CheckNetworkState.isOnline(mContext)) {
            CommonUtils.showSnakeBar(mContext, mContext.getString(R.string.no_internet));
            return;
        }
        view.showProgress();
        ApiController apiController = ApiController.getInstance();
        apiController.callWithHeader(mContext, RequestType.REQ_MAKEINTERESTED_COUNTRY_ACTIVITY, new ResponseHandler<BaseResponseModel>() {
            @Override
            public void onResponse(BaseResponseModel newsResponseModel) {
                view.hideProgress();
                if (newsResponseModel != null) {
                    if (newsResponseModel.getStatus().equalsIgnoreCase(ConstantLib.RESPONSE_SUCCESS)) {
                        view.onUpdateInteresed();
                    } else
                        CommonUtils.showToast(mContext, newsResponseModel.getMessage());
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

    public void getDetails(HashMap<String, String> params, Map<String, String> headers) {
        if (!CheckNetworkState.isOnline(mContext)) {
            CommonUtils.showSnakeBar(mContext, mContext.getString(R.string.no_internet));
            return;
        }
        view.showProgress();
        ApiController apiController = ApiController.getInstance();
        apiController.callWithHeader(mContext, RequestType.REQ_COUNTRY_ACTIVITY_DETAILS, new ResponseHandler<CountryActivityDetailResponseModel>() {
            @Override
            public void onResponse(CountryActivityDetailResponseModel activitiesResponseModel) {
                view.hideProgress();
                if (activitiesResponseModel != null) {
                    if (activitiesResponseModel.getStatus().equalsIgnoreCase(ConstantLib.RESPONSE_SUCCESS)) {
                        view.setActivtyDetails(activitiesResponseModel.getData());
                    } else
                        CommonUtils.showToast(mContext, activitiesResponseModel.getMessage());
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

    public void getCities(HashMap requestBody) {
        if (!CheckNetworkState.isOnline(mContext)) {
            CommonUtils.showSnakeBar(mContext, mContext.getString(R.string.no_internet));
            return;
        }
        view.showProgress();
        ApiController apiController = ApiController.getInstance();
        apiController.call(mContext, RequestType.REQ_GET_CITIES, new ResponseHandler<CountryResponseModel>() {
            @Override
            public void onResponse(CountryResponseModel countryResponseModel) {
                view.hideProgress();
                if (countryResponseModel != null) {
                    if (countryResponseModel.getStatus().equalsIgnoreCase(ConstantLib.RESPONSE_SUCCESS)) {
                        view.setCities(countryResponseModel.getData());
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
