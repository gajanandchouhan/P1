package com.superlifesecretcode.app;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.superlifesecretcode.app.ui.player.PLayerPopupActivity;

/**
 * Created by Divya on 10-04-2018.
 */

public class FCMReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent intent1 = new Intent(context, PLayerPopupActivity.class);
        intent1.putExtra("bundle", intent.getExtras());
        intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent1);
    }
}
