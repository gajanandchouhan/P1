package com.superlifesecretcode.app;

import android.app.Application;

/**
 * Created by Divya on 21-02-2018.
 */

public class SuperLifeSecretCodeApp extends Application {
static SuperLifeSecretCodeApp instance;
    @Override
    public void onCreate() {
        super.onCreate();
        instance=this;
    }

    public static SuperLifeSecretCodeApp getInstance() {
        return instance;
    }
}
