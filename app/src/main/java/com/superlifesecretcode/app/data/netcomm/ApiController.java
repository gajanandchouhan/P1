package com.superlifesecretcode.app.data.netcomm;


import android.content.Context;

import com.superlifesecretcode.app.R;
import com.superlifesecretcode.app.util.CommonUtils;

import java.util.HashMap;

import retrofit2.Retrofit;


/**
 * Created by JAIN COMPUTERS on 11/18/2017.
 */

public class ApiController implements RequestType {

    private static ApiController apiController;
    private static ApiInterface apiInterface;


    public static ApiController getInstance() {
        if (apiController == null) {
            apiController = new ApiController();
            Retrofit client = ApiClient.getClient();
            apiInterface = client.create(ApiInterface.class);
        }
        return apiController;
    }

    public void call(Context mContext, byte reqTyoe, ResponseHandler handler, HashMap body) {
        if (!CheckNetworkState.isOnline(mContext)) {
            CommonUtils.showSnakeBar(mContext, mContext.getString(R.string.no_internet));
            return;
        }
        switch (reqTyoe) {
            case REQ_TYPE_REGISTER:
             /*   Observable<BaseResponseModel> register = apiInterface.register(language, body);
                register.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new ResponseObserver<BaseResponseModel>(handler));*/
                break;
        }
    }

}
