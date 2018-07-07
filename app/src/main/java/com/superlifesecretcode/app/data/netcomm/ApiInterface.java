package com.superlifesecretcode.app.data.netcomm;


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

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Query;

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

    @Multipart
    @Headers({"username:richestLifeAdmin", "password:123456"})
    @POST("getCities")
    Observable<CountryResponseModel> getCities(@PartMap() Map<String, RequestBody> params);

    @Multipart
    @Headers({"username:richestLifeAdmin", "password:123456"})
    @POST("registration")
    Observable<UserDetailResponseModel> registerUser(@PartMap() Map<String, RequestBody> partMap,
                                                     @Part MultipartBody.Part file);

    @Multipart
    @Headers({"username:richestLifeAdmin", "password:123456"})
    @POST("login")
    Observable<UserDetailResponseModel> loginUser(@PartMap() Map<String, RequestBody> params);

    @Multipart
    @Headers({"username:richestLifeAdmin", "password:123456"})
    @POST("socialLogin")
    Observable<UserDetailResponseModel> socialLogin(@PartMap() Map<String, RequestBody> params);


    @Multipart
    @Headers({"username:richestLifeAdmin", "password:123456"})
    @POST("updateUser")
    Observable<UserDetailResponseModel> updateProfile(@PartMap() Map<String, RequestBody> partMap,
                                                      @Part MultipartBody.Part file, @HeaderMap Map<String, String> headers);

    @Multipart
    @Headers({"username:richestLifeAdmin", "password:123456"})
    @POST("getHomeCategories")
    Observable<CategoryResponseModel> getHomeCategories(@PartMap() Map<String, RequestBody> params);


    @Multipart
    @Headers({"username:richestLifeAdmin", "password:123456"})
    @POST("getSubCategories")
    Observable<CategoryResponseModel> getSubCategories(@PartMap() Map<String, RequestBody> params);

    @Multipart
    @Headers({"username:richestLifeAdmin", "password:123456"})
    @POST("announcements")
    Observable<NewsResponseModel> getNews(@PartMap() Map<String, RequestBody> partMap,
                                          @HeaderMap Map<String, String> headers);

    @Multipart
    @Headers({"username:richestLifeAdmin", "password:123456"})
    @POST("announcements")
    Observable<EventResponseModel> getEvents(@PartMap() Map<String, RequestBody> partMap,
                                             @HeaderMap Map<String, String> headers);

    @Multipart
    @Headers({"username:richestLifeAdmin", "password:123456"})
    @POST("getSingleAnnouncements")
    Observable<SingleNewsResponseModel> readMark(@PartMap() Map<String, RequestBody> partMap,
                                                 @HeaderMap Map<String, String> headers);

    @Multipart
    @Headers({"username:richestLifeAdmin", "password:123456"})
    @POST("attendance")
    Observable<BaseResponseModel> makeInterested(@PartMap() Map<String, RequestBody> partMap,
                                                 @HeaderMap Map<String, String> headers);

    @Multipart
    @Headers({"username:richestLifeAdmin", "password:123456"})
    @POST("like")
    Observable<BaseResponseModel> makeLike(@PartMap() Map<String, RequestBody> partMap,
                                           @HeaderMap Map<String, String> headers);

    @Headers({"username:richestLifeAdmin", "password:123456"})
    @POST("addSharing")
    Observable<BaseResponseModel> addShare(@Body RequestBody file, @HeaderMap() Map<String, String> headers);

    @Multipart
    @Headers({"username:richestLifeAdmin", "password:123456"})
    @POST("usersSharing")
    Observable<ShareListResponseModel> getUserShare(@PartMap() Map<String, RequestBody> partMap,
                                                    @HeaderMap Map<String, String> headers);

    @Headers({"username:richestLifeAdmin", "password:123456"})
    @GET("latest")
    Observable<ShareListResponseModel> getAllShareList(@HeaderMap Map<String, String> headers, @Query("country_id") String countryId, @Query("page") String page);

    @Multipart
    @Headers({"username:richestLifeAdmin", "password:123456"})
    @POST("likeSharing")
    Observable<BaseResponseModel> likeSharing(@PartMap() Map<String, RequestBody> partMap,
                                              @HeaderMap Map<String, String> headers);

    @Multipart
    @Headers({"username:richestLifeAdmin", "password:123456"})
    @POST("userIntrestedEvents")
    Observable<InterestedEventResponseModel> userIntrestedEvents(@PartMap() Map<String, RequestBody> partMap,
                                                                 @HeaderMap Map<String, String> headers);

    @Multipart
    @Headers({"username:richestLifeAdmin", "password:123456"})
    @POST("getActivityTypes")
    Observable<StandardEventResponseModel> getStandardEvents(@PartMap() Map<String, RequestBody> partMap);

    @Multipart
    @Headers({"username:richestLifeAdmin", "password:123456"})
    @POST("addDailyActivity")
    Observable<BaseResponseModel> addActivity(@PartMap() Map<String, RequestBody> partMap,
                                              @HeaderMap Map<String, String> headers);

    @Multipart
    @Headers({"username:richestLifeAdmin", "password:123456"})
    @POST("dailyActivities")
    Observable<PersonalEventResponseModel> getPersonalAcivities(@PartMap() Map<String, RequestBody> partMap,
                                                                @HeaderMap Map<String, String> headers);

    @Multipart
    @Headers({"username:richestLifeAdmin", "password:123456"})
    @POST("updateDailyActivityStatus")
    Observable<BaseResponseModel> updateEventStatus(@PartMap() Map<String, RequestBody> partMap,
                                                    @HeaderMap Map<String, String> headers);

    @Multipart
    @Headers({"username:richestLifeAdmin", "password:123456"})
    @POST("activities")
    Observable<CountryActivitiesResponseModel> getCountryAcivities(@PartMap() Map<String, RequestBody> partMap,
                                                                   @HeaderMap Map<String, String> headers);

    @Multipart
    @Headers({"username:richestLifeAdmin", "password:123456"})
    @POST("ca_attendance")
    Observable<BaseResponseModel> makeCountryActivityInterested(@PartMap() Map<String, RequestBody> partMap,
                                                                @HeaderMap Map<String, String> headers);

    @Multipart
    @Headers({"username:richestLifeAdmin", "password:123456"})
    @POST("getSingleActivity")
    Observable<CountryActivityDetailResponseModel> getCountryAcivityDetails(@PartMap() Map<String, RequestBody> partMap,
                                                                            @HeaderMap Map<String, String> headers);


    @Multipart
    @Headers({"username:richestLifeAdmin", "password:123456"})
    @POST("getAllCategories")
    Observable<AllMenuResponseModel> getAllMenus(@PartMap() Map<String, RequestBody> partMap);


    @Multipart
    @Headers({"username:richestLifeAdmin", "password:123456"})
    @POST("removeActivity")
    Observable<BaseResponseModel> removeActivity(@PartMap() Map<String, RequestBody> partMap,
                                                 @HeaderMap Map<String, String> headers);


    @Multipart
    @Headers({"username:richestLifeAdmin", "password:123456"})
    @POST("announcementRemindTime")
    Observable<BaseResponseModel> updateAnnouncementReminder(@PartMap() Map<String, RequestBody> partMap,
                                                             @HeaderMap Map<String, String> headers);


    @Multipart
    @Headers({"username:richestLifeAdmin", "password:123456"})
    @POST("activityRemindTime")
    Observable<BaseResponseModel> updateCountryActivityReminder(@PartMap() Map<String, RequestBody> partMap,
                                                                @HeaderMap Map<String, String> headers);

    @Multipart
    @Headers({"username:richestLifeAdmin", "password:123456"})
    @POST("generateTempPassword")
    Observable<BaseResponseModel> generateTempPassword(@PartMap() Map<String, RequestBody> params);

    @Multipart
    @Headers({"username:richestLifeAdmin", "password:123456"})
    @POST("updatePassword")
    Observable<BaseResponseModel> updatePassword(@PartMap() Map<String, RequestBody> params);

    @Multipart
    @Headers({"username:richestLifeAdmin", "password:123456"})
    @POST("getUnreadAnnouncementCount")
    Observable<AnnouncementCountResponseModel> getUnreadAnnouncementCount(@PartMap() Map<String, RequestBody> partMap,
                                                                          @HeaderMap Map<String, String> headers);

    @Multipart
    @Headers({"username:richestLifeAdmin", "password:123456"})
    @POST("getBanners")
    Observable<BannerResponseModel> getBanners(@PartMap() Map<String, RequestBody> params);

    @Multipart
    @Headers({"username:richestLifeAdmin", "password:123456"})
    @POST("intrestedUsersNotificationList")
    Observable<NotificationResponseModel> getNotifications(@PartMap() Map<String, RequestBody> partMap,
                                                           @HeaderMap Map<String, String> headers);

    @Multipart
    @Headers({"username:richestLifeAdmin", "password:123456"})
    @POST("getDisclosure")
    Observable<DisclosureResponseModel> getDisclosure(@PartMap() Map<String, RequestBody> params);

    @Headers({"username:richestLifeAdmin", "password:123456"})
    @GET("getEventCountry")
    Observable<CountryResponseModel> getEventCountry();

    @Headers({"username:richestLifeAdmin", "password:123456"})
    @GET("getUserSharedCountry")
    Observable<CountryResponseModel> getShareCountry();

    @Multipart
    @Headers({"username:richestLifeAdmin", "password:123456"})
    @POST("createLead")
    Observable<BaseResponseModel> createLead(@PartMap() Map<String, RequestBody> partMap);
}