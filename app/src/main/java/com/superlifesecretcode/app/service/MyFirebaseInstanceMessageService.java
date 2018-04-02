package com.superlifesecretcode.app.service;

import android.app.ActivityManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.superlifesecretcode.app.R;
import com.superlifesecretcode.app.ui.countryactivities.CountryAcitvitiesActivity;
import com.superlifesecretcode.app.ui.dailyactivities.interestedevent.InterestedEventCalendarActivity;
import com.superlifesecretcode.app.ui.dailyactivities.personalevent.PersonalEventCalendarActivity;
import com.superlifesecretcode.app.ui.main.MainActivity;
import com.superlifesecretcode.app.ui.splash.SplashActivity;
import com.superlifesecretcode.app.util.ConstantLib;

import java.util.Map;

import static android.app.ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND;
import static android.app.ActivityManager.RunningAppProcessInfo.IMPORTANCE_VISIBLE;

/**
 * Created by Divya on 23-03-2018.
 */

public class MyFirebaseInstanceMessageService extends FirebaseMessagingService {
    private static final String TAG = "FCMMESSAGE";
    private String CHANNEL_ID = "123";
    int id = 0;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.d(TAG, "From: " + remoteMessage.getFrom());

        if (remoteMessage.getData().size() > 0) {
            sendNotification(remoteMessage.getData());
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());
        }

        if (remoteMessage.getNotification() != null) {
//            sendNotification(remoteMessage.getNotification());
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
        }

    }

    private void sendNotification(Map<String, String> messageBody) {
        Class clazz = MainActivity.class;
        switch (messageBody.get("notification_type")) {
            case ConstantLib.NOTIFICATION_PERSONAL:
                clazz = PersonalEventCalendarActivity.class;
                break;
            case ConstantLib.NOTIFICATION_EVENT:
                clazz = InterestedEventCalendarActivity.class;
                break;
            case ConstantLib.NOTIFICATION_COUNTY_ACTIVITY:
                clazz = CountryAcitvitiesActivity.class;
                break;
        }
        Intent intent = new Intent(this, clazz);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                PendingIntent.FLAG_ONE_SHOT);
        Uri soundUri;
        int checkExistence = getResources().getIdentifier(messageBody.get("sound"), "raw", getPackageName());
        if (checkExistence != 0) {
            soundUri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE
                    + "://" + getPackageName() + "/raw/" + messageBody.get("sound"));
        } else {
            soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        }

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, CHANNEL_ID);
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            notificationBuilder.setColor(getColor(R.color.colorPrimary));
            notificationBuilder.setSmallIcon(R.mipmap.ic_launcher);
        } else {
            notificationBuilder.setSmallIcon(R.mipmap.ic_launcher);
        }
        notificationBuilder.setContentTitle(messageBody.get("title"));
        notificationBuilder.setContentText(messageBody.get("body"));
        notificationBuilder.setAutoCancel(true);
        notificationBuilder.setSound(soundUri);
        notificationBuilder.setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        id = id + 1;
        notificationManager.notify(id, notificationBuilder.build());
    }

    public boolean foregrounded() {
        ActivityManager.RunningAppProcessInfo appProcessInfo = new ActivityManager.RunningAppProcessInfo();
        ActivityManager.getMyMemoryState(appProcessInfo);
        return (appProcessInfo.importance == IMPORTANCE_FOREGROUND || appProcessInfo.importance == IMPORTANCE_VISIBLE);
    }
}
