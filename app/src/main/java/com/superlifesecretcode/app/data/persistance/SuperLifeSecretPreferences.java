package com.superlifesecretcode.app.data.persistance;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.superlifesecretcode.app.SuperLifeSecretCodeApp;
import com.superlifesecretcode.app.data.model.AlertModel;
import com.superlifesecretcode.app.data.model.SubcategoryModel;
import com.superlifesecretcode.app.data.model.allmenu.AllMenuResponseData;
import com.superlifesecretcode.app.data.model.language.LanguageResponseData;
import com.superlifesecretcode.app.data.model.userdetails.UserDetailResponseData;
import com.superlifesecretcode.app.ui.book.first.BookBean;
import com.superlifesecretcode.app.ui.book.second.DeliveryData;
import com.superlifesecretcode.app.ui.book.second.Discount;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Divya on 07-12-2017.
 */

public class SuperLifeSecretPreferences {

    private static final String PREF_NAME = "superLifeScreenPrefrerence";
    public static final String LANGUAGE_SETTED = "language_setted";
    private static SuperLifeSecretPreferences superLifeScreenPrefrerence;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editer;

    public static final String DEVICE_TOKEN = "device_token";
    public static final String IS_LOGGED_IN = "logged_in";
    public static final String DISCLOSE_ACCEPTED = "disclosure_accepted";

    public SuperLifeSecretPreferences() {
        Context context = SuperLifeSecretCodeApp.getInstance().getApplicationContext();
        preferences = context.getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        editer = preferences.edit();
    }

    public static SuperLifeSecretPreferences getInstance() {
        if (superLifeScreenPrefrerence == null) {
            superLifeScreenPrefrerence = new SuperLifeSecretPreferences();
        }
        return superLifeScreenPrefrerence;
    }

    public void putString(String key, String value) {
        editer.putString(key, value).commit();

    }

    public String getString(String key) {
        return preferences.getString(key, "");
    }


    public void clearAll() {
        editer.clear().commit();
    }

    public void putBoolean(String key, boolean value) {
        editer.putBoolean(key, value).commit();
    }

    public boolean getBoolean(String key) {
        return preferences.getBoolean(key, false);
    }

    public void setLanguageId(String languageId) {
        editer.putString("languageId", languageId).commit();
    }

    public String getLanguageId() {
        return preferences.getString("languageId", "3");
    }

    public void setConversionData(LanguageResponseData data) {
        editer.putString("conversion_data", new Gson().toJson(data)).commit();
    }

    public LanguageResponseData getConversionData() {
        String masterData = preferences.getString("conversion_data", "");
        if (masterData.length() > 0) {
            return new Gson().fromJson(masterData, LanguageResponseData.class);
        }
        return null;
    }

    public void setUserDetails(UserDetailResponseData data) {
        editer.putString("user_data", new Gson().toJson(data)).commit();
    }

    public UserDetailResponseData getUserData() {
        String masterData = preferences.getString("user_data", "");
        if (masterData.length() > 0) {
            return new Gson().fromJson(masterData, UserDetailResponseData.class);
        }
        return null;
    }


    public void setLocationData(UserDetailResponseData data) {
        editer.putString("location_data", new Gson().toJson(data)).commit();
    }

    public UserDetailResponseData getLocationData() {
        String masterData = preferences.getString("location_data", "");
        if (masterData.length() > 0) {
            return new Gson().fromJson(masterData, UserDetailResponseData.class);
        }
        return null;
    }


    public void setAlertAccepted(AlertModel alertModel) {
        List<AlertModel> acceptedIds = getAcceptedIds();
        acceptedIds.add(alertModel);
        editer.putString("accepted_id", new Gson().toJson(acceptedIds)).commit();
    }

    public List<AlertModel> getAcceptedIds() {
        String accepted_id = preferences.getString("accepted_id", "");
        if (accepted_id.length() > 0) {
            Type type = new TypeToken<List<AlertModel>>() {
            }.getType();
            List<AlertModel> list = new Gson().fromJson(accepted_id, type);
            return list;
        }
        List<AlertModel> list = new ArrayList<>();
        return list;
    }

    public void setSubMenuList(List<SubcategoryModel> list) {
        editer.putString("sub_menu", new Gson().toJson(list)).commit();
    }

    public List<SubcategoryModel> getSubMenuList() {
        String subMneus = preferences.getString("sub_menu", "");
        if (subMneus.length() > 0) {
            Type type = new TypeToken<List<SubcategoryModel>>() {
            }.getType();
            List<SubcategoryModel> list = new Gson().fromJson(subMneus, type);
            return list;
        }
        return null;
    }

    public void setSelectedBooks(ArrayList<String> list) {
        editer.putString("selected_books", new Gson().toJson(list)).commit();
    }

    public ArrayList<String> getSelectedBooks() {
        String subMneus = preferences.getString("selected_books", "");
        if (subMneus.length() > 0) {
            Type type = new TypeToken<ArrayList<String>>() {
            }.getType();
            ArrayList<String> list = new Gson().fromJson(subMneus, type);
            return list;
        }
        return new ArrayList<String>();
    }

    public void setDeliveryChargesList(ArrayList<DeliveryData> list) {
        editer.putString("delivery_charges_list", new Gson().toJson(list)).commit();
    }

    public ArrayList<DeliveryData> getDeliveryChargesList() {
        String subMneus = preferences.getString("delivery_charges_list", "");
        if (subMneus.length() > 0) {
            Type type = new TypeToken<ArrayList<DeliveryData>>() {
            }.getType();
            ArrayList<DeliveryData> list = new Gson().fromJson(subMneus, type);
            return list;
        }
        return new ArrayList<DeliveryData>();
    }


    public void setDiscountList(ArrayList<Discount> list) {
        editer.putString("discount_list", new Gson().toJson(list)).commit();
    }

    public ArrayList<Discount> getDiscountList() {
        String subMneus = preferences.getString("discount_list", "");
        if (subMneus.length() > 0) {
            Type type = new TypeToken<ArrayList<Discount>>() {
            }.getType();
            ArrayList<Discount> list = new Gson().fromJson(subMneus, type);
            return list;
        }
        return new ArrayList<Discount>();
    }


    public void setSelectedBooksList(ArrayList<BookBean> list) {
        editer.putString("selected_books_list", new Gson().toJson(list)).commit();
    }

    public ArrayList<BookBean> getSelectedBooksList() {
        String subMneus = preferences.getString("selected_books_list", "");
        if (subMneus.length() > 0) {
            Type type = new TypeToken<ArrayList<BookBean>>() {
            }.getType();
            ArrayList<BookBean> list = new Gson().fromJson(subMneus, type);
            return list;
        }
        return new ArrayList<BookBean>();
    }

    public void setDeviceToken(String refreshedToken) {
        editer.putString("device_token", refreshedToken).commit();
    }

    public String getDeviceToken() {
        return preferences.getString("device_token", "");
    }

    public void updateAlertList(List<AlertModel> alertModelList) {
        editer.putString("accepted_id", new Gson().toJson(alertModelList)).commit();
    }

    public void setNewsUndread(int unread) {
        editer.putInt("news_unread", unread).commit();
    }

    public void setEventUndread(int unread) {
        editer.putInt("event_unread", unread).commit();
    }

    public int getNewsUnread() {
        return preferences.getInt("news_unread", 0);
    }

    public int getEventUnread() {
        return preferences.getInt("event_unread", 0);
    }

    public void setAllCategories(List<AllMenuResponseData> data) {
        editer.putString("all_menu", new Gson().toJson(data)).commit();
    }

    public List<AllMenuResponseData> getAllCategories() {
        String subMneus = preferences.getString("all_menu", "");
        if (subMneus.length() > 0) {
            Type type = new TypeToken<List<AllMenuResponseData>>() {
            }.getType();
            List<AllMenuResponseData> list = new Gson().fromJson(subMneus, type);
            return list;
        }
        return null;
    }

    public void clearAllCategories() {
        editer.putString("all_menu", "").commit();
    }

    public String getCometChaUserId() {
        return preferences.getString("comet_chat_user_id", "");
    }


    public void setCometChatUserId(String uid) {
        editer.putString("comet_chat_user_id", uid).commit();
    }
}
