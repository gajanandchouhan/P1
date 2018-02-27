package com.superlifesecretcode.app.data.persistance;

import android.content.Context;
import android.content.SharedPreferences;

import com.superlifesecretcode.app.SuperLifeSecretCodeApp;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Divya on 07-12-2017.
 */

public class SuperLifeSecretPreferences {

    private static final String PREF_NAME = "superLifeScreenPrefrerence";
    private static SuperLifeSecretPreferences superLifeScreenPrefrerence;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editer;

    public static final String DEVICE_TOKEN = "device_token";
    public static final String IS_LOGGED_IN = "logged_in";
    public static final String DISCLOSE_ACCEPTED="disclosure_accepted";

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

}
