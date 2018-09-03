package com.superlifesecretcode.app.service;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.superlifesecretcode.app.FCMReceiver;
import com.superlifesecretcode.app.R;
import com.superlifesecretcode.app.ui.dailyactivities.interestedevent.InterestedEventCalendarActivity;
import com.superlifesecretcode.app.ui.dailyactivities.personalevent.PersonalEventCalendarActivity;
import com.superlifesecretcode.app.ui.main.MainActivity;
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
            if (remoteMessage.getData().get("notification_type")!=null){
                Intent intent = new Intent(this, FCMReceiver.class);
                intent.putExtra("title", remoteMessage.getData().get("title"));
                intent.putExtra("body", remoteMessage.getData().get("body"));
                intent.putExtra("type", remoteMessage.getData().get("notification_type"));
                // intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0,
                        intent, PendingIntent.FLAG_UPDATE_CURRENT);
                try {
                    pendingIntent.send();
                } catch (PendingIntent.CanceledException e) {
                    e.printStackTrace();
                }
//            sendNotification(remoteMessage.getData());
                Log.d(TAG, "Message data payload: " + remoteMessage.getData());
            }

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
            case ConstantLib.NOTIFICATION_COUNTY_ACTIVITY:
            case ConstantLib.NOTIFICATION_EVENT:
                clazz = InterestedEventCalendarActivity.class;
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
            notificationBuilder.setColor(ContextCompat.getColor(this,R.color.colorPrimary));
            notificationBuilder.setSmallIcon(R.mipmap.ic_launcher);
        } else {
            notificationBuilder.setSmallIcon(R.mipmap.ic_launcher);
        }
        notificationBuilder.setContentTitle(messageBody.get("title"));
        notificationBuilder.setContentText(messageBody.get("body"));
        notificationBuilder.setAutoCancel(true);
        notificationBuilder.setSound(soundUri);
        notificationBuilder.setVibrate(new long[]{1000, 1000, 1000, 1000, 1000});
        notificationBuilder.setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        id = id + 1;
        Notification notification = notificationBuilder.build();
//        notification.flags = Notification.DEFAULT_LIGHTS | Notification.FLAG_AUTO_CANCEL | Notification.DEFAULT_SOUND|Notification.DEFAULT_VIBRATE;
        notificationManager.notify(id, notification);
    }

    public boolean foregrounded() {
        ActivityManager.RunningAppProcessInfo appProcessInfo = new ActivityManager.RunningAppProcessInfo();
        ActivityManager.getMyMemoryState(appProcessInfo);
        return (appProcessInfo.importance == IMPORTANCE_FOREGROUND || appProcessInfo.importance == IMPORTANCE_VISIBLE);
    }
}
