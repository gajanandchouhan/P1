package com.superlifesecretcode.app;

import android.app.Application;
import android.util.Log;

import com.inscripts.interfaces.Callbacks;
import com.superlifesecretcode.app.data.model.language.LanguageResponseData;
import com.superlifesecretcode.app.data.persistance.SuperLifeSecretPreferences;
import com.twitter.sdk.android.core.Twitter;

import org.json.JSONObject;

import cometchat.inscripts.com.cometchatcore.coresdk.CometChat;

/**
 * Created by Divya on 21-02-2018.
 */

public class SuperLifeSecretCodeApp extends Application {
    static SuperLifeSecretCodeApp instance;
    private LanguageResponseData conversionData;
    private String licenseKey = "COMETCHAT-UY0G8-4PGBJ-VQ3CL-7JUTT";
    private String apiKey = "51462x2c751943dc1b06a7c7ec3aad70f1def8";
    private static CometChat cometChatInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        Twitter.initialize(this);
        final CometChat cometChat = CometChat.getInstance(this);
        cometChat.initializeCometChat("", licenseKey, apiKey, true, new Callbacks() {
            @Override
            public void successCallback(JSONObject jsonObject) {
                Log.v("COMET CHAT", "Success :"+jsonObject.toString());
                cometChatInstance=cometChat;

            }

            @Override
            public void failCallback(JSONObject jsonObject) {
                Log.v("COMET CHAT", "Failed :"+jsonObject.toString());
            }
        });
    }

    public LanguageResponseData getConversionData() {
        conversionData = SuperLifeSecretPreferences.getInstance().getConversionData();
        return conversionData;
    }

    public static SuperLifeSecretCodeApp getInstance() {
        return instance;
    }

    public static CometChat getCometChatInstance() {
        return cometChatInstance;
    }
}
