package com.superlifesecretcode.app.ui.base;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.google.firebase.messaging.FirebaseMessaging;
import com.inscripts.interfaces.Callbacks;
import com.inscripts.interfaces.LaunchCallbacks;
import com.superlifesecretcode.app.R;
import com.superlifesecretcode.app.SuperLifeSecretCodeApp;
import com.superlifesecretcode.app.data.persistance.SuperLifeSecretPreferences;
import com.superlifesecretcode.app.ui.picker.AlertDialog;
import com.superlifesecretcode.app.util.CommonUtils;
import com.superlifesecretcode.app.util.MyLocationManager;
import com.superlifesecretcode.app.util.PermissionConstant;

import org.json.JSONException;
import org.json.JSONObject;

import cometchat.inscripts.com.cometchatcore.coresdk.CometChat;
import utils.CCNotificationHelper;

/**
 * Created by Divya on 21-02-2018.
 */

public abstract class BaseActivity extends AppCompatActivity implements BaseView {
    private static final int REQUEST_CHECK_SETTINGS = 102;
    private LocationCallBack callback;
    private MyLocationManager locationManager;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentView());
        initializePresenter();
        initializeView();
    }

    public void getCurrentLocation(LocationCallBack callBack) {
        this.callback = callBack;
        if (!CommonUtils.hasPermissions(this, PermissionConstant.PERMISSION_LOCATION)) {
            ActivityCompat.requestPermissions(this, PermissionConstant.PERMISSION_LOCATION, PermissionConstant.CODE_LOCATION);
        } else {
            locationManager = new MyLocationManager(this) {
                @Override
                protected void locationUpdate(Location location) {
                    callback.onLocationSuccess(location);
                    locationManager.stopLocationUpdates();
                }

                @Override
                protected void getOnActivityResultCode(int REQUEST_CHECK_SETTINGS) {
                }

                @Override
                protected void userDeniedLocationSettingChange(boolean isDenied) {
                    showAlert();
                }
            };
        }

    }

    public interface LocationCallBack {
        void onLocationSuccess(Location location);
    }

    @Override
    public void showProgress() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setCancelable(false);
        }
        progressDialog.show();
    }

    @Override
    public void hideProgress() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PermissionConstant.CODE_LOCATION) {
            for (int grantResult : grantResults) {
                if (grantResult != PackageManager.PERMISSION_GRANTED) {
                    showAlert();
                    return;
                }
            }
            getCurrentLocation(callback);
        }
    }

    protected void showAlert() {
        CommonUtils.showAlert(this, "You have to allow location to use this app.", "Allow", "Cancel", new AlertDialog.OnClickListner() {
            @Override
            public void onPositiveClick() {
                getCurrentLocation(callback);
            }

            @Override
            public void onNegativeClick() {
                finish();
            }
        });
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        boolean ret = false;
        try {
            View view = getCurrentFocus();
            ret = super.dispatchTouchEvent(event);

            if (view instanceof EditText) {
                View w = getCurrentFocus();
                int scrcoords[] = new int[2];
                w.getLocationOnScreen(scrcoords);
                float x = event.getRawX() + w.getLeft() - scrcoords[0];
                float y = event.getRawY() + w.getTop() - scrcoords[1];

                if (event.getAction() == MotionEvent.ACTION_UP
                        && (x < w.getLeft() || x >= w.getRight()
                        || y < w.getTop() || y > w.getBottom())) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getWindow().getCurrentFocus().getWindowToken(), 0);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ret;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CHECK_SETTINGS) {
            locationManager.onActivityResult(requestCode, resultCode, data);
        }
    }

    protected abstract int getContentView();

    protected abstract void initializeView();

    protected abstract void initializePresenter();


    @Override
    protected void onDestroy() {
        hideProgress();
        super.onDestroy();
    }
}
