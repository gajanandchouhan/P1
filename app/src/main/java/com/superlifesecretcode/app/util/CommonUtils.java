package com.superlifesecretcode.app.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.superlifesecretcode.app.ui.base.BaseActivity;
import com.superlifesecretcode.app.ui.main.MainActivity;
import com.superlifesecretcode.app.ui.splash.SplashActivity;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Collections;
import java.util.List;

/**
 * Created by Divya on 21-02-2018.
 */

public class CommonUtils {
    static boolean showLog = true;

    public static void showSnakeBar(Context mContext, String message) {
        Snackbar.make(((AppCompatActivity) mContext).findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG).show();
    }

    public static void showLog(String tag, String message) {
        if (showLog)
            Log.v(tag, message);
    }

    public static void startActivity(Activity activity, Class clazz) {
        activity.startActivity(new Intent(activity, clazz));
    }
    public static void startActivity(AppCompatActivity activity, Class c, Bundle bundle, boolean clearTop) {
        Intent intent = new Intent(activity, c);
        if (bundle != null)
            intent.putExtra("bundle", bundle);
        if (clearTop) {
            ActivityCompat.finishAffinity(activity);
        }
        activity.startActivity(intent);
    }

    public static String getIPAddress(boolean useIPv4) {
        try {
            List<NetworkInterface> interfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface intf : interfaces) {
                List<InetAddress> addrs = Collections.list(intf.getInetAddresses());
                for (InetAddress addr : addrs) {
                    if (!addr.isLoopbackAddress()) {
                        String sAddr = addr.getHostAddress().toUpperCase();
                        return sAddr;
                    }
                }
            }
        } catch (Exception ex) {
            return null;
        }
        return null;
    }

    public static boolean hasPermissions(Context context, String... permissions) {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    public static void showAlert(Context baseActivity, String message, String positive, String negative, final ClickListner clickListner) {
        new AlertDialog.Builder(baseActivity).setMessage(message).setPositiveButton(positive, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                clickListner.onPositiveClick();
            }
        }).setNegativeButton(negative, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                clickListner.onNegativeClick();

            }
        }).show();
    }

    public interface ClickListner {
        void onPositiveClick();

        void onNegativeClick();
    }
}
