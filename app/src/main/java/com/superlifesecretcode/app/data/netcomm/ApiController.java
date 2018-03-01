package com.superlifesecretcode.app.data.netcomm;


import android.Manifest;
import android.content.Context;

import com.superlifesecretcode.app.R;
import com.superlifesecretcode.app.data.model.language.LanguageResponseModel;
import com.superlifesecretcode.app.util.CommonUtils;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
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
        HashMap requestParams = getRequestParams(body);
        switch (reqTyoe) {
            case REQ_CONVERSION:
                Observable<LanguageResponseModel> register = apiInterface.getConversion(requestParams);
                register.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new ResponseObserver<LanguageResponseModel>(handler));
                break;
        }

    }

    public HashMap<String, RequestBody> getRequestParams(HashMap<String, String> stringParams) {
        HashMap<String, RequestBody> params = new HashMap<>();
        for (Map.Entry<String, String> entry : stringParams.entrySet()) {
            RequestBody requestBody = RequestBody.create(
                    MultipartBody.FORM, entry.getValue());
            params.put(entry.getKey(), requestBody);
        }
        return params;
    }

}
