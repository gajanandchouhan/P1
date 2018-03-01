package com.superlifesecretcode.app;

import android.app.Application;

import com.superlifesecretcode.app.data.model.language.LanguageResponseData;
import com.superlifesecretcode.app.data.persistance.SuperLifeSecretPreferences;

/**
 * Created by Divya on 21-02-2018.
 */

public class SuperLifeSecretCodeApp extends Application {
    static SuperLifeSecretCodeApp instance;
    private LanguageResponseData conversionData;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public LanguageResponseData getConversionData() {
        conversionData = SuperLifeSecretPreferences.getInstance().getConversionData();
        return conversionData;
    }

    public static SuperLifeSecretCodeApp getInstance() {
        return instance;
    }
}
