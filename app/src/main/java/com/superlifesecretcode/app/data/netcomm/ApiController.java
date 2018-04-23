package com.superlifesecretcode.app.data.netcomm;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.superlifesecretcode.app.R;
import com.superlifesecretcode.app.data.model.BaseResponseModel;
import com.superlifesecretcode.app.data.model.allmenu.AllMenuResponseModel;
import com.superlifesecretcode.app.data.model.banner.BannerResponseModel;
import com.superlifesecretcode.app.data.model.category.CategoryResponseModel;
import com.superlifesecretcode.app.data.model.country.CountryResponseModel;
import com.superlifesecretcode.app.data.model.countryactivities.CountryActivitiesResponseModel;
import com.superlifesecretcode.app.data.model.countryactivities.CountryActivityDetailResponseModel;
import com.superlifesecretcode.app.data.model.disclosure.DisclosureResponseModel;
import com.superlifesecretcode.app.data.model.events.EventResponseModel;
import com.superlifesecretcode.app.data.model.interesetdevent.InterestedEventResponseModel;
import com.superlifesecretcode.app.data.model.language.LanguageResponseModel;
import com.superlifesecretcode.app.data.model.news.NewsResponseModel;
import com.superlifesecretcode.app.data.model.news.SingleNewsResponseModel;
import com.superlifesecretcode.app.data.model.notifications.NotificationResponseModel;
import com.superlifesecretcode.app.data.model.personalevent.PersonalEventResponseModel;
import com.superlifesecretcode.app.data.model.shares.ShareListResponseModel;
import com.superlifesecretcode.app.data.model.standardevent.StandardEventResponseModel;
import com.superlifesecretcode.app.data.model.unreadannouncement.AnnouncementCountResponseModel;
import com.superlifesecretcode.app.data.model.userdetails.UserDetailResponseModel;
import com.superlifesecretcode.app.util.CommonUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
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
        ApiClient.ADD_LOG = true;
        HashMap requestParams = getRequestParams(body);
        switch (reqTyoe) {
            case REQ_CONVERSION:
                Observable<LanguageResponseModel> langObservable = apiInterface.getConversion(requestParams);
                langObservable.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new ResponseObserver<LanguageResponseModel>(handler));
                break;
            case REQ_GET_DISCLOSURE:
                Observable<DisclosureResponseModel> disclosureObservable = apiInterface.getDisclosure(requestParams);
                disclosureObservable.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new ResponseObserver<DisclosureResponseModel>(handler));
                break;
            case REQ_GET_STATE:
                Observable<CountryResponseModel> stateObservable = apiInterface.getState(requestParams);
                stateObservable.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new ResponseObserver<CountryResponseModel>(handler));
                break;


            case REQ_LOGIN:
                Observable<UserDetailResponseModel> loginObservable = apiInterface.loginUser(requestParams);
                loginObservable.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new ResponseObserver<UserDetailResponseModel>(handler));
                break;
            case REQ_SOCIAL_LOGIN:
                Observable<UserDetailResponseModel> socialLoginObservable = apiInterface.socialLogin(requestParams);
                socialLoginObservable.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new ResponseObserver<UserDetailResponseModel>(handler));
                break;

            case REQ_GET_CATEGORY:
                Observable<CategoryResponseModel> getCatObservable = apiInterface.getHomeCategories(requestParams);
                getCatObservable.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new ResponseObserver<CategoryResponseModel>(handler));
                break;
            case REQ_GET_SUB_CATEGORY:
                Observable<CategoryResponseModel> getSubCategoryResponseModelObservable = apiInterface.getSubCategories(requestParams);
                getSubCategoryResponseModelObservable.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new ResponseObserver<CategoryResponseModel>(handler));
                break;

            case REQ_GET_STANDARD_EVENT:
                Observable<StandardEventResponseModel> standardEventObservable = apiInterface.getStandardEvents(requestParams);
                standardEventObservable.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new ResponseObserver<StandardEventResponseModel>(handler));
                break;
            case REQ_GET_ALL_MENU:
                Observable<AllMenuResponseModel> allMenuObservable = apiInterface.getAllMenus(requestParams);
                allMenuObservable.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new ResponseObserver<AllMenuResponseModel>(handler));
                break;

            case REQ_GET_RESET_CODE:
                Observable<BaseResponseModel> getCodeObservable = apiInterface.generateTempPassword(requestParams);
                getCodeObservable.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new ResponseObserver<BaseResponseModel>(handler));
                break;

            case REQ_RESET_PASS:
                Observable<BaseResponseModel> resetPassObservable = apiInterface.updatePassword(requestParams);
                resetPassObservable.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new ResponseObserver<BaseResponseModel>(handler));
                break;
            case REQ_GET_BANNERS:
                Observable<BannerResponseModel> bannerObservable = apiInterface.getBanners(requestParams);
                bannerObservable.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new ResponseObserver<BannerResponseModel>(handler));
                break;
        }


    }

    public void callMultipart(Context mContext, byte reqTyoe, ResponseHandler handler, Map<String, String> params, Map<String, File> files) {
        ApiClient.ADD_LOG = false;
        try {
            if (!CheckNetworkState.isOnline(mContext)) {
                CommonUtils.showSnakeBar(mContext, mContext.getString(R.string.no_internet));
                return;
            }
            Map<String, RequestBody> stringMultipartParamsParams = getRequestParams(params);
            List<MultipartBody.Part> filesMultipart = getFilesMultipart(files);
            switch (reqTyoe) {
                case REQ_REGISTER_USER:
                    Observable<UserDetailResponseModel> registerObservable = apiInterface.registerUser(stringMultipartParamsParams, filesMultipart.size() > 0 ? filesMultipart.get(0) : null);
                    registerObservable.subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new ResponseObserver<UserDetailResponseModel>(handler));
                    break;


            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void callMultiparWithHeader(Context mContext, byte reqTyoe, ResponseHandler handler, Map<String, String> params, Map<String, File> files, Map<String, String> header) {
        try {
            ApiClient.ADD_LOG = false;
            if (!CheckNetworkState.isOnline(mContext)) {
                CommonUtils.showSnakeBar(mContext, mContext.getString(R.string.no_internet));
                return;
            }
            Map<String, RequestBody> stringMultipartParamsParams = getRequestParams(params);
            List<MultipartBody.Part> filesMultipart = getFilesMultipart(files);
            switch (reqTyoe) {
                case REQ_UPDATE_PROFILE:
                    Observable<UserDetailResponseModel> updateProfileObservable = apiInterface.updateProfile(stringMultipartParamsParams,
                            filesMultipart.size() > 0 ? filesMultipart.get(0) : null, header);
                    updateProfileObservable.subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new ResponseObserver<UserDetailResponseModel>(handler));
                    break;

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void callWithHeader(Context mContext, byte reqTyoe, ResponseHandler handler, Map<String, String> params, Map<String, String> header) {
        try {
            ApiClient.ADD_LOG = true;
            if (!CheckNetworkState.isOnline(mContext)) {
                CommonUtils.showSnakeBar(mContext, mContext.getString(R.string.no_internet));
                return;
            }
            Map<String, RequestBody> stringMultipartParamsParams = getRequestParams(params);
            switch (reqTyoe) {
                case REQ_GET_NEWS_UPDATES:
                    Observable<NewsResponseModel> updateProfileObservable = apiInterface.getNews(stringMultipartParamsParams, header);
                    updateProfileObservable.subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new ResponseObserver<NewsResponseModel>(handler));
                    break;
                case REQ_GET_EVENTS:
                    Observable<EventResponseModel> getEventObservable = apiInterface.getEvents(stringMultipartParamsParams, header);
                    getEventObservable.subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new ResponseObserver<EventResponseModel>(handler));
                    break;

                case REQ_MARK_READ:
                    Observable<SingleNewsResponseModel> readMarkObservable = apiInterface.readMark(stringMultipartParamsParams, header);
                    readMarkObservable.subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new ResponseObserver<SingleNewsResponseModel>(handler));
                    break;
                case REQ_INTERESTED_EVENT:
                    Observable<BaseResponseModel> interestedObservable = apiInterface.makeInterested(stringMultipartParamsParams, header);
                    interestedObservable.subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new ResponseObserver<BaseResponseModel>(handler));
                    break;
                case REQ_LIKE_NEWS:
                    Observable<BaseResponseModel> likeNewsObservable = apiInterface.makeLike(stringMultipartParamsParams, header);
                    likeNewsObservable.subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new ResponseObserver<BaseResponseModel>(handler));
                    break;
                case REQ_GET_USER_SHARE:
                    Observable<ShareListResponseModel> getShareObservable = apiInterface.getUserShare(stringMultipartParamsParams, header);
                    getShareObservable.subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new ResponseObserver<ShareListResponseModel>(handler));
                    break;

                case REQ_LIKE_SHARING:
                    Observable<BaseResponseModel> likeSharingObservable = apiInterface.likeSharing(stringMultipartParamsParams, header);
                    likeSharingObservable.subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new ResponseObserver<BaseResponseModel>(handler));
                    break;
                case REQ_MY_INTERESTED_EVENT:
                    Observable<InterestedEventResponseModel> myEventObservable = apiInterface.userIntrestedEvents(stringMultipartParamsParams, header);
                    myEventObservable.subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new ResponseObserver<InterestedEventResponseModel>(handler));
                    break;

                case REQ_ADD_ACTIVITY:
                    Observable<BaseResponseModel> addActivityObservable = apiInterface.addActivity(stringMultipartParamsParams, header);
                    addActivityObservable.subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new ResponseObserver<BaseResponseModel>(handler));
                    break;

                case REQ_GET_PERSONAL_EVENT:
                    Observable<PersonalEventResponseModel> personalEventObservable = apiInterface.getPersonalAcivities(stringMultipartParamsParams, header);
                    personalEventObservable.subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new ResponseObserver<PersonalEventResponseModel>(handler));
                    break;

                case REQ_UPDATE_EVENT_STATUS:
                    Observable<BaseResponseModel> updateEventStatusObservable = apiInterface.updateEventStatus(stringMultipartParamsParams, header);
                    updateEventStatusObservable.subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new ResponseObserver<BaseResponseModel>(handler));
                    break;
                case REQ_GET_COUNTRY_ACTIVITY:
                    Observable<CountryActivitiesResponseModel> countryActivitiesResponseModelObservable = apiInterface.getCountryAcivities(stringMultipartParamsParams, header);
                    countryActivitiesResponseModelObservable.subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new ResponseObserver<CountryActivitiesResponseModel>(handler));
                    break;
                case REQ_MAKEINTERESTED_COUNTRY_ACTIVITY:
                    Observable<BaseResponseModel> cpuntInterestObservable = apiInterface.makeCountryActivityInterested(stringMultipartParamsParams, header);
                    cpuntInterestObservable.subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new ResponseObserver<BaseResponseModel>(handler));
                    break;
                case REQ_COUNTRY_ACTIVITY_DETAILS:
                    Observable<CountryActivityDetailResponseModel> countryAcivitiesDetailsObservable = apiInterface.getCountryAcivityDetails(stringMultipartParamsParams, header);
                    countryAcivitiesDetailsObservable.subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new ResponseObserver<CountryActivityDetailResponseModel>(handler));
                    break;

                case REQ_REMOVE_ACTIVITY:
                    Observable<BaseResponseModel> removeActivtyObservable = apiInterface.removeActivity(stringMultipartParamsParams, header);
                    removeActivtyObservable.subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new ResponseObserver<BaseResponseModel>(handler));
                    break;
                case REQ_ANNOUNCE_REMINDER:
                    Observable<BaseResponseModel> announceReminderObservable = apiInterface.updateAnnouncementReminder(stringMultipartParamsParams, header);
                    announceReminderObservable.subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new ResponseObserver<BaseResponseModel>(handler));
                    break;
                case REQ_COUNTRY_ACTIVITY_REMINDER:
                    Observable<BaseResponseModel> countryActivityReminderObservable = apiInterface.updateCountryActivityReminder(stringMultipartParamsParams, header);
                    countryActivityReminderObservable.subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new ResponseObserver<BaseResponseModel>(handler));
                    break;
                case REQ_GET_ANNOUNCEMENT_COUNT:
                    Observable<AnnouncementCountResponseModel> announcementCountObservable = apiInterface.getUnreadAnnouncementCount(stringMultipartParamsParams, header);
                    announcementCountObservable.subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new ResponseObserver<AnnouncementCountResponseModel>(handler));
                    break;
                case REQ_GET_NOTIFICATIONS:
                    Observable<NotificationResponseModel> notificationObservable = apiInterface.getNotifications(stringMultipartParamsParams, header);
                    notificationObservable.subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new ResponseObserver<NotificationResponseModel>(handler));
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void callGet(Context mContext, byte reqTyoe, ResponseHandler handler) {
        ApiClient.ADD_LOG = true;
        if (!CheckNetworkState.isOnline(mContext)) {
            CommonUtils.showSnakeBar(mContext, mContext.getString(R.string.no_internet));
            return;
        }
        switch (reqTyoe) {
            case REQ_GET_COUNTRY:
                Observable<CountryResponseModel> countryObservable = apiInterface.getCountry();
                countryObservable.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new ResponseObserver<CountryResponseModel>(handler));
                break;
        }

    }

    public void callGetWithHeader(Context mContext, byte reqTyoe, ResponseHandler handler, Map<String, String> headers) {

        if (!CheckNetworkState.isOnline(mContext)) {
            CommonUtils.showSnakeBar(mContext, mContext.getString(R.string.no_internet));
            return;
        }
        ApiClient.ADD_LOG = true;
        switch (reqTyoe) {
            case REQ_GET_ALL_LATEST:
                Observable<ShareListResponseModel> countryObservable = apiInterface.getAllShareList(headers);
                countryObservable.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new ResponseObserver<ShareListResponseModel>(handler));
                break;
        }

    }

    public HashMap<String, RequestBody> getRequestParams(Map<String, String> stringParams) {
        HashMap<String, RequestBody> params = new HashMap<>();
        for (Map.Entry<String, String> entry : stringParams.entrySet()) {
            RequestBody requestBody = RequestBody.create(
                    MultipartBody.FORM, entry.getValue());
            params.put(entry.getKey(), requestBody);
        }
        return params;
    }


    private List<MultipartBody.Part> getFilesMultipart(Map<String, File> params) {
        List<MultipartBody.Part> files = new ArrayList<>();
        if (params != null) {
            Set<Map.Entry<String, File>> entries = params.entrySet();
            for (Map.Entry<String, File> entry : entries) {
                RequestBody requestFile =
                        RequestBody.create(MediaType.parse("multipart/form-data"), entry.getValue());
                // MultipartBody.Part is used to send also the actual file name
                MultipartBody.Part formData = MultipartBody.Part.createFormData(entry.getKey(), entry.getValue().getName(), requestFile);
                files.add(formData);
            }

        }
        return files;
    }

    public void callMultpleFileUpload(byte reqType, ResponseHandler responseHandler, RequestBody fileParams, Map<String, String> headers) {
        ApiClient.ADD_LOG = false;
        switch (reqType) {
            case REQ_ADD_SHARE:
                Observable<BaseResponseModel> addShareObservable = apiInterface.addShare(fileParams, headers);
                addShareObservable.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new ResponseObserver<BaseResponseModel>(responseHandler));
                break;
        }
    }

}
