package com.superlifesecretcode.app.data.persistance;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.superlifesecretcode.app.SuperLifeSecretCodeApp;
import com.superlifesecretcode.app.data.model.language.LanguageResponseData;
import com.superlifesecretcode.app.data.model.userdetails.UserDetailResponseData;

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

    public boolean alertAccepted() {
        return  preferences.getBoolean("alert_accepted", false);
    }

    public void setAlertAccepted(){
        editer.putBoolean("alert_accepted", true).commit();
    }
}
