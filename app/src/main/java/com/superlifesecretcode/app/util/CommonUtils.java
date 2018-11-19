package com.superlifesecretcode.app.util;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.MediaMetadataRetriever;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.Spanned;
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
import com.superlifesecretcode.app.R;
import com.superlifesecretcode.app.data.persistance.SuperLifeSecretPreferences;
import com.superlifesecretcode.app.ui.kpi_summery.KpiActivity;

import java.io.ByteArrayOutputStream;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static android.text.format.DateUtils.getRelativeTimeSpanString;

/**
 * Created by Divya on 21-02-2018.
 */

public class CommonUtils {

    public static boolean book_stake = false;

    public static void showSnakeBar(Context mContext, String message) {
        if (SuperLifeSecretPreferences.getInstance().getConversionData() != null) {
            if (message != null && message.equalsIgnoreCase(mContext.getString(R.string.server_error))) {
                message = SuperLifeSecretPreferences.getInstance().getConversionData().getWent_wrong();
            } else if (message != null && message.equalsIgnoreCase(mContext.getString(R.string.no_internet))) {
                message = SuperLifeSecretPreferences.getInstance().getConversionData().getNo_internet();
            } else if (message != null && message.equalsIgnoreCase(ConstantLib.MOBILE_EXISTS)) {
                message = SuperLifeSecretPreferences.getInstance().getConversionData().getMobile_exists();
            } else if (message != null && message.equalsIgnoreCase(ConstantLib.EMAIL_EXISTS)) {
                message = SuperLifeSecretPreferences.getInstance().getConversionData().getEmail_exists();
            } else if (message != null && message.equalsIgnoreCase(ConstantLib.MOBILE_PASS_INVALID)) {
                message = SuperLifeSecretPreferences.getInstance().getConversionData().getInvalid_mobile_pass();
            } else if (message != null && message.equalsIgnoreCase(ConstantLib.RESET_CODE_SENT)) {
                message = SuperLifeSecretPreferences.getInstance().getConversionData().getCode_sent_to_mail();
            } else if (message != null && message.equalsIgnoreCase(ConstantLib.WORNG_CODE_ENTERED)) {
                message = SuperLifeSecretPreferences.getInstance().getConversionData().getInvalid_code_entered();
            } else if (message != null && message.equalsIgnoreCase(ConstantLib.DEACTIVATED_USER)) {
                message = SuperLifeSecretPreferences.getInstance().getConversionData().getDeactivate_user();
            } else if (message != null && message.equalsIgnoreCase(ConstantLib.EMAIL_NOT_REG)) {
                message = SuperLifeSecretPreferences.getInstance().getConversionData().getEmail_not_registerted();
            }
        }
        Snackbar.make(((AppCompatActivity) mContext).findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG).show();
    }

    public static void showLog(String tag, String message) {
        if (BuildConfig.showLog)
            Log.v(tag, message);
    }

    public static void startActivity(Activity activity, Class clazz) {
        activity.startActivity(new Intent(activity, clazz));
    }

    public static Spanned getColorfulText(KpiActivity kpiActivity, String s, Color colorId) {
        String send = "<font color=" + colorId + ">" + s + " </font> ";
        return Html.fromHtml(send);
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

    public static void showAlert(Context baseActivity, String message, String positive, String negative, final com.superlifesecretcode.app.ui.picker.AlertDialog.OnClickListner clickListner) {
        com.superlifesecretcode.app.ui.picker.AlertDialog dialog = new com.superlifesecretcode.app.ui.picker.AlertDialog(baseActivity, message, positive, negative, clickListner);
        dialog.show();
       /* AlertDialog.Builder builder = new AlertDialog.Builder(baseActivity).setMessage(message);
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
        builder.show();*/
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

    public static String getformattedDateFromString(String inputFormat, String outputFormat, String inputDate, boolean needConvert, String timeZone) {
        if (inputFormat.equals("")) { // if inputFormat = "", set a default input format.
            inputFormat = "yyyy-MM-dd hh:mm:ss";
        }
        if (outputFormat.equals("")) {
            outputFormat = "EEEE d 'de' MMMM 'del' yyyy"; // if inputFormat = "", set a default output format.
        }
        Date parsed = null;
        String outputDate = "";

        SimpleDateFormat df_input = new SimpleDateFormat(inputFormat, Locale.ENGLISH);

        SimpleDateFormat df_output = new SimpleDateFormat(outputFormat, Locale.ENGLISH);
        if (needConvert) {
            if (timeZone != null) {
                df_input.setTimeZone(TimeZone.getTimeZone(timeZone));
            }
            df_output.setTimeZone(TimeZone.getDefault());
        }
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
        if (SuperLifeSecretPreferences.getInstance().getConversionData() != null) {
            if (message != null && message.equalsIgnoreCase(mContext.getString(R.string.server_error))) {
                message = SuperLifeSecretPreferences.getInstance().getConversionData().getWent_wrong();
            } else if (message != null && message.equalsIgnoreCase(mContext.getString(R.string.no_internet))) {
                message = SuperLifeSecretPreferences.getInstance().getConversionData().getNo_internet();
            } else if (message != null && message.equalsIgnoreCase(ConstantLib.MOBILE_EXISTS)) {
                message = SuperLifeSecretPreferences.getInstance().getConversionData().getMobile_exists();
            } else if (message != null && message.equalsIgnoreCase(ConstantLib.EMAIL_EXISTS)) {
                message = SuperLifeSecretPreferences.getInstance().getConversionData().getEmail_exists();
            } else if (message != null && message.equalsIgnoreCase(ConstantLib.MOBILE_PASS_INVALID)) {
                message = SuperLifeSecretPreferences.getInstance().getConversionData().getInvalid_mobile_pass();
            } else if (message != null && message.equalsIgnoreCase(ConstantLib.RESET_CODE_SENT)) {
                message = SuperLifeSecretPreferences.getInstance().getConversionData().getCode_sent_to_mail();
            } else if (message != null && message.equalsIgnoreCase(ConstantLib.WORNG_CODE_ENTERED)) {
                message = SuperLifeSecretPreferences.getInstance().getConversionData().getInvalid_code_entered();
            } else if (message != null && message.equalsIgnoreCase(ConstantLib.DEACTIVATED_USER)) {
                message = SuperLifeSecretPreferences.getInstance().getConversionData().getDeactivate_user();
            } else if (message != null && message.equalsIgnoreCase(ConstantLib.EMAIL_NOT_REG)) {
                message = SuperLifeSecretPreferences.getInstance().getConversionData().getEmail_not_registerted();
            }
        }
        Toast.makeText(mContext, message, Toast.LENGTH_LONG).show();
    }

    public static String getFromatttedDate(int year, int month, int dayOfMonth) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(year);
        stringBuilder.append("-");
        stringBuilder.append(String.format(Locale.getDefault(), "%02d", month + 1));
        stringBuilder.append("-");
        stringBuilder.append(String.format(Locale.getDefault(), "%02d", dayOfMonth));
        return getformattedDateFromString("yyyy-MM-dd", ConstantLib.OUTPUT_DATE_FORMATE, stringBuilder.toString(), false, null);
    }

    public static String getAppendedDate(int year, int month, int dayOfMonth) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(year);
        stringBuilder.append("-");
        stringBuilder.append(String.format(Locale.getDefault(), "%02d", month + 1));
        stringBuilder.append("-");
        stringBuilder.append(String.format(Locale.getDefault(), "%02d", dayOfMonth));
        return getformattedDateFromString("yyyy-MM-dd", "yyyy-MM-dd", stringBuilder.toString(), false, null);
    }

    public interface ClickListner {
        void onPositiveClick();

        void onNegativeClick();
    }

    public static void shareContent(Context mContext, final String text) {
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("text/plain");
        i.putExtra(Intent.EXTRA_TEXT, text);
        mContext.startActivity(i);
    }

    public static int getResurceId(Context mContext, String stringId) {
        return mContext.getResources().getIdentifier(stringId, "drawable", mContext.getPackageName());
    }

    public static void shareImage(String image, final Context mContext) {
        if (image != null && !image.isEmpty()) {
            final ProgressDialog show = ProgressDialog.show(mContext, "", "", true, false);
            GlideApp.with(mContext).asBitmap().load(image).into(new SimpleTarget<Bitmap>() {
                @Override
                public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                    show.dismiss();
                    Uri imageUri = getImageUri(mContext, resource);
                    if (imageUri != null) {
                        Intent i = new Intent(Intent.ACTION_SEND);
                        i.setType("image/*");
                        i.putExtra(Intent.EXTRA_STREAM, imageUri);
                        mContext.startActivity(Intent.createChooser(i, "Share Image"));
                    }

                }

                @Override
                public void onLoadFailed(@Nullable Drawable errorDrawable) {
                    super.onLoadFailed(errorDrawable);
                    show.dismiss();

                }
            });
        }
    }

    public static void shareImageWithContent(String image, final String text, final Context mContext) {
        if (image != null && !image.isEmpty() && text != null && !text.isEmpty()) {
            final ProgressDialog show = ProgressDialog.show(mContext, "", "", true, false);
            GlideApp.with(mContext).asBitmap().load(image).into(new SimpleTarget<Bitmap>() {
                @Override
                public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                    Uri imageUri = getImageUri(mContext, resource);
                    show.dismiss();
                    if (imageUri != null) {
                        Intent i = new Intent(Intent.ACTION_SEND);
                        i.setType("*/*");
                        i.putExtra(Intent.EXTRA_STREAM, imageUri);
                        i.putExtra(Intent.EXTRA_TEXT, text);
                        mContext.startActivity(Intent.createChooser(i, "Share Image"));
                    }

                }

                @Override
                public void onLoadFailed(@Nullable Drawable errorDrawable) {
                    super.onLoadFailed(errorDrawable);
                    show.dismiss();
                }
            });
        } else if (image != null && !image.isEmpty()) {
            shareImage(image, mContext);
        } else if (text != null && !text.isEmpty()) {
            shareContent(mContext, text);
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


    public static void showDatePicker(Context activity, DatePickerDialog.OnDateSetListener listener, long minDate) {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        final int day = c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                activity, listener, year, month, day);
        datePickerDialog.getDatePicker().setMinDate(minDate);
        datePickerDialog.show();
    }

    public static void showDatePickerWithMax(Context activity, DatePickerDialog.OnDateSetListener listener, long max) {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        final int day = c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                activity, listener, year, month, day);
        datePickerDialog.getDatePicker().setMaxDate(max);
        datePickerDialog.show();
    }

    public static void showDatePickerWithMinAndMax(Context activity, DatePickerDialog.OnDateSetListener listener, long max, long timeInMilis) {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        final int day = c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                activity, listener, year, month, day);
        datePickerDialog.getDatePicker().setMinDate(timeInMilis);
        datePickerDialog.getDatePicker().setMaxDate(max);
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


    public static List<Date> getDates(String dateString1, String dateString2) {
        ArrayList<Date> dates = new ArrayList<Date>();
        DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

        Date date1 = null;
        Date date2 = null;

        try {
            date1 = df1.parse(dateString1);
            date2 = df1.parse(dateString2);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);


        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);

        while (!cal1.after(cal2)) {
            dates.add(cal1.getTime());
            cal1.add(Calendar.DATE, 1);
        }
        return dates;
    }


    public static Uri[] getRingtoneUris(Context mContext) {
        RingtoneManager ringtoneMgr = new RingtoneManager(mContext);
        ringtoneMgr.setType(RingtoneManager.TYPE_ALARM);
        Cursor alarmsCursor = ringtoneMgr.getCursor();
        int alarmsCount = alarmsCursor.getCount();
        if (alarmsCount == 0 && !alarmsCursor.moveToFirst()) {
            return null;
        }
        Uri[] alarms = new Uri[alarmsCount];
        while (!alarmsCursor.isAfterLast() && alarmsCursor.moveToNext()) {
            int currentPosition = alarmsCursor.getPosition();
            alarms[currentPosition] = ringtoneMgr.getRingtoneUri(currentPosition);
        }
        alarmsCursor.close();
        return alarms;
    }


    public static boolean isPackageExisted(String targetPackage, Context mContext) {
        PackageManager pm = mContext.getPackageManager();
        try {
            PackageInfo info = pm.getPackageInfo(targetPackage, PackageManager.GET_META_DATA);
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
        return true;
    }


    public static String getDuration(String url) {
        try {
            MediaMetadataRetriever retriever = new MediaMetadataRetriever();
            retriever.setDataSource(url);
            String time = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
            long millis = Long.parseLong(time);
            if (millis > 60 * 1000 * 60) {
                return String.format(Locale.getDefault(), "%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(millis),
                        TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
                        TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
            } else {
                return String.format(Locale.getDefault(), "%02d:%02d",
                        TimeUnit.MILLISECONDS.toMinutes(millis),
                        TimeUnit.MILLISECONDS.toSeconds(millis) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
            }

        } catch (Exception e) {

        }
        return "";

    }


    public static String getCurrentDateFormatted(Locale locale) {
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd/MMMM, yyyy", locale);
        String formattedDate = df.format(c);
        return formattedDate;
    }
}
