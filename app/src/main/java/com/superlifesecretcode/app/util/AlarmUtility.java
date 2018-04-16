package com.superlifesecretcode.app.util;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.superlifesecretcode.app.FCMReceiver;

import java.util.Calendar;

/**
 * Created by Divya on 16-04-2018.
 */

public class AlarmUtility {

    private static AlarmUtility instance;
    private final Context mContext;
    private AlarmManager alarmManager;

    public AlarmUtility(Context mContext) {
        alarmManager = (AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE);
        this.mContext = mContext;
    }

    public static AlarmUtility getInstance(Context mContext) {
        if (instance == null) {
            instance = new AlarmUtility(mContext);
        }
        return instance;
    }

    public void setAlarm(int id, String title, String message, long time, boolean isRepeating) {
        Calendar instance = Calendar.getInstance();
        instance.setTimeInMillis(time);
        Intent intent = new Intent(mContext, FCMReceiver.class);
        intent.putExtra("title", title);
        intent.putExtra("body", message);
        intent.putExtra("type", ConstantLib.NOTIFICATION_EVENT);
        // intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(mContext, id,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);
        // Single alarms in 1, 2, ..., 10 minutes (in `i` minutes)
        if (isRepeating) {
            if (instance.getTimeInMillis() < System.currentTimeMillis() + 60000) {
                instance.setTimeInMillis(time + 1000 * 60 * 60 * 24);
                Log.v("ALARM", "" + instance.getTimeInMillis());
            }
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
                    instance.getTimeInMillis(), 1000 * 60 * 60 * 24, pendingIntent);
        } else {
            if (instance.getTimeInMillis() > System.currentTimeMillis() + 60000) {
                alarmManager.set(AlarmManager.RTC_WAKEUP,
                        instance.getTimeInMillis(),
                        pendingIntent);
                Log.v("ALARM", "" + instance.getTimeInMillis());
            }
        }

    }

    public void removeAlarm(int id) {
        Intent intent = new Intent(mContext, FCMReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                mContext, id, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        if (pendingIntent != null) {
            alarmManager.cancel(pendingIntent);
        }
    }
}
