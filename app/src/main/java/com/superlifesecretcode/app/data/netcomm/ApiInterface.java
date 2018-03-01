package com.superlifesecretcode.app.data.netcomm;


import com.superlifesecretcode.app.data.model.country.CountryResponseModel;
import com.superlifesecretcode.app.data.model.language.LanguageResponseModel;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;

/**
 * Created by JAIN COMPUTERS on 11/18/2017.
 */

public interface ApiInterface {
    @Multipart
    @Headers({"username:richestLifeAdmin", "password:123456"})
    @POST("getLanguageConversions")
    Observable<LanguageResponseModel> getConversion(@PartMap() Map<String, RequestBody> params);

    @Headers({"username:richestLifeAdmin", "password:123456"})
    @GET("getCountries")
    Observable<CountryResponseModel> getCountry();

    @Multipart
    @Headers({"username:richestLifeAdmin", "password:123456"})
    @POST("getStates")
    Observable<CountryResponseModel> getState(@PartMap() Map<String, RequestBody> params);
}