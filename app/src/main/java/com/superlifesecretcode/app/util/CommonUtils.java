package com.superlifesecretcode.app.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateUtils;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.superlifesecretcode.app.BuildConfig;
import com.superlifesecretcode.app.GlideApp;
import com.superlifesecretcode.app.ui.base.BaseActivity;
import com.superlifesecretcode.app.ui.main.MainActivity;
import com.superlifesecretcode.app.ui.splash.SplashActivity;

import java.io.ByteArrayOutputStream;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import static android.text.format.DateUtils.getRelativeTimeSpanString;

/**
 * Created by Divya on 21-02-2018.
 */

public class CommonUtils {

    public static void showSnakeBar(Context mContext, String message) {
        Snackbar.make(((AppCompatActivity) mContext).findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG).show();
    }

    public static void showLog(String tag, String message) {
        if (BuildConfig.showLog)
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

    public final static boolean isValidEmail(CharSequence target) {
        if (target == null) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }

    public static void showAlert(Context baseActivity, String message, String positive, String negative, final ClickListner clickListner) {

        AlertDialog.Builder builder = new AlertDialog.Builder(baseActivity).setMessage(message);
        if (negative != null && !negative.isEmpty()) {
            builder.setPositiveButton(positive, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    clickListner.onPositiveClick();

                }
            });

            builder.setNegativeButton(negative, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                    clickListner.onNegativeClick();
                }
            });
        } else {
            builder.setNeutralButton(positive, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                    clickListner.onPositiveClick();
                }
            });
        }
        builder.show();
    }


    public static void printHashKey(Context pContext) {
        try {
            PackageInfo info = pContext.getPackageManager().getPackageInfo(pContext.getPackageName(), PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String hashKey = new String(Base64.encode(md.digest(), 0));
                Log.i("FACEBOOK", "printHashKey() Hash Key: " + hashKey);
            }
        } catch (NoSuchAlgorithmException e) {
            Log.e("FACEBOOK", "printHashKey()", e);
        } catch (Exception e) {
            Log.e("FACEBOOK", "printHashKey()", e);
        }
    }

    public static String
    getformattedDateFromString(String inputFormat, String outputFormat, String inputDate) {
        if (inputFormat.equals("")) { // if inputFormat = "", set a default input format.
            inputFormat = "yyyy-MM-dd hh:mm:ss";
        }
        if (outputFormat.equals("")) {
            outputFormat = "EEEE d 'de' MMMM 'del' yyyy"; // if inputFormat = "", set a default output format.
        }
        Date parsed = null;
        String outputDate = "";

        SimpleDateFormat df_input = new SimpleDateFormat(inputFormat, java.util.Locale.getDefault());
        SimpleDateFormat df_output = new SimpleDateFormat(outputFormat, java.util.Locale.getDefault());

        // You can set a different Locale, This example set a locale of Country Mexico.
        //SimpleDateFormat df_input = new SimpleDateFormat(inputFormat, new Locale("es", "MX"));
        //SimpleDateFormat df_output = new SimpleDateFormat(outputFormat, new Locale("es", "MX"));

        try {
            parsed = df_input.parse(inputDate);
            outputDate = df_output.format(parsed);
        } catch (Exception e) {
            Log.e("formattedDateFromString", "Exception in formateDateFromstring(): " + e.getMessage());
        }
        return outputDate;

    }


    public static String getTimeLineDifference(String stringDate) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = null;
        try {
            date = df.parse(stringDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long epoch = 0;
        if (date != null) {
            epoch = date.getTime();
        }

        return (String) getRelativeTimeSpanString(epoch, System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS);
    }

    public static long getTimeInMilis(String dateTime) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = null;
        try {
            date = df.parse(dateTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long epoch = 0;
        if (date != null) {
            epoch = date.getTime();
        }
        return epoch;
    }
    public static long getDateInMilis(String dateTime) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Date date = null;
        try {
            date = df.parse(dateTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long epoch = 0;
        if (date != null) {
            epoch = date.getTime();
        }
        return epoch;
    }
    public static void showToast(Context mContext, String message) {
        Toast.makeText(mContext, message, Toast.LENGTH_LONG).show();
    }

    public static String getFromatttedDate(int year, int month, int dayOfMonth) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(year);
        stringBuilder.append("-");
        stringBuilder.append(String.format(Locale.getDefault(), "%02d", month + 1));
        stringBuilder.append("-");
        stringBuilder.append(String.format(Locale.getDefault(), "%02d", dayOfMonth));
        return getformattedDateFromString("yyyy-MM-dd", ConstantLib.OUTPUT_DATE_FORMATE, stringBuilder.toString());
    }

    public static String getAppendedDate(int year, int month, int dayOfMonth) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(year);
        stringBuilder.append("-");
        stringBuilder.append(String.format(Locale.getDefault(), "%02d", month + 1));
        stringBuilder.append("-");
        stringBuilder.append(String.format(Locale.getDefault(), "%02d", dayOfMonth));
        return stringBuilder.toString();
    }

    public interface ClickListner {
        void onPositiveClick();

        void onNegativeClick();
    }

    public static void shareContent(Context mContext, final String text) {
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("image/*");
        i.putExtra(Intent.EXTRA_TEXT, text);
        mContext.startActivity(i);
    }

    public static int getResurceId(Context mContext, String stringId) {
        return mContext.getResources().getIdentifier(stringId, "drawable", mContext.getPackageName());
    }

    public static void shareImage(String image, final Context mContext) {
        if (image != null && !image.isEmpty()) {
            GlideApp.with(mContext).asBitmap().load(image).into(new SimpleTarget<Bitmap>() {
                @Override
                public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                    Uri imageUri = getImageUri(mContext, resource);
                    if (imageUri != null) {
                        Intent i = new Intent(Intent.ACTION_SEND);
                        i.setType("image/*");
                        i.putExtra(Intent.EXTRA_STREAM, imageUri);
                        mContext.startActivity(Intent.createChooser(i, "Share Image"));
                    }

                }
            });
        }
    }

    private static Uri getImageUri(Context inContext, Bitmap inImage) {
        if (inImage != null) {
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
            String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, UUID.randomUUID().toString() + ".png", "drawing");
            return Uri.parse(path);
        }
        return null;
    }

    public static int getScreenWidth(Context mContext) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) mContext).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;
        return width;
    }


    public static void showDatePicker(Context activity, DatePickerDialog.OnDateSetListener listener,long minDate) {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        final int day = c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                activity, listener, year, month, day);
        datePickerDialog.getDatePicker().setMinDate(minDate);
        datePickerDialog.show();
    }


    public static void showTimePicker(Context mContext, TimePickerDialog.OnTimeSetListener listner) {
        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(mContext, listner, hour, minute, true);//Yes 24 hour time
        mTimePicker.setTitle("Select Time");
        mTimePicker.show();
    }

    public static void hideKeyboard(Context ctx) {
        InputMethodManager inputManager = (InputMethodManager) ctx
                .getSystemService(Context.INPUT_METHOD_SERVICE);

        // check if no view has focus:
        View v = ((Activity) ctx).getCurrentFocus();
        if (v == null)
            return;

        if (inputManager != null) {
            inputManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }
    }
}
