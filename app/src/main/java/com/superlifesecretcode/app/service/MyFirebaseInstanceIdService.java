package com.superlifesecretcode.app.service;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.superlifesecretcode.app.data.persistance.SuperLifeSecretPreferences;

/**
 * Created by Divya on 23-03-2018.
 */

public class MyFirebaseInstanceIdService extends FirebaseInstanceIdService {

    private static final String TAG = "MyFirebaseInstanceId";

    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + refreshedToken);
        SuperLifeSecretPreferences.getInstance().setDeviceToken(refreshedToken);
    }
}
