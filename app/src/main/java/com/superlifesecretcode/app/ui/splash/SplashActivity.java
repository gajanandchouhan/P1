package com.superlifesecretcode.app.ui.splash;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.Gson;
import com.superlifesecretcode.app.BuildConfig;
import com.superlifesecretcode.app.R;
import com.superlifesecretcode.app.data.model.BaseResponseModel;
import com.superlifesecretcode.app.data.model.userdetails.UserDetailResponseData;
import com.superlifesecretcode.app.data.persistance.SuperLifeSecretPreferences;
import com.superlifesecretcode.app.ui.base.BaseActivity;
import com.superlifesecretcode.app.ui.disclosure.DiscolsureActivity;
import com.superlifesecretcode.app.ui.language.LanguageActivity;
import com.superlifesecretcode.app.ui.login.LoginActivity;
import com.superlifesecretcode.app.ui.main.MainActivity;
import com.superlifesecretcode.app.ui.picker.AlertDialog;
import com.superlifesecretcode.app.ui.register.RegisterActivityNewFirst;
import com.superlifesecretcode.app.util.CommonUtils;
import com.superlifesecretcode.app.util.ConstantLib;
import com.superlifesecretcode.app.util.PermissionConstant;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class SplashActivity extends BaseActivity implements SplashView {


    private SplashPresenter presenter;
    String TAG = "SplashActivity";

    @Override
    protected int getContentView() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initializeView() {
//        Log.v("TOKE", FirebaseInstanceId.getInstance().getToken());
        CommonUtils.printHashKey(this);
        //presenter.delaySplash();
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("appversion", BuildConfig.VERSION_NAME);
        hashMap.put("type", "1");
        presenter.validateAppVersion(hashMap);
    }

    @Override
    protected void initializePresenter() {
        presenter = new SplashPresenter(this);
        presenter.setView(this);
    }

    @Override
    public void navigateToNextScreen() {
        UserDetailResponseData userData = SuperLifeSecretPreferences.getInstance().getUserData();
        Log.e("userdata2", "" + new Gson().toJson(userData));
        if (userData != null) {
            CommonUtils.startActivity(this, MainActivity.class);
        } else if (SuperLifeSecretPreferences.getInstance().getBoolean(SuperLifeSecretPreferences.LANGUAGE_SETTED) && SuperLifeSecretPreferences.getInstance().getBoolean(SuperLifeSecretPreferences.DISCLOSE_ACCEPTED)) {
            CommonUtils.startActivity(this, RegisterActivityNewFirst.class);
        } else if (SuperLifeSecretPreferences.getInstance().getBoolean(SuperLifeSecretPreferences.LANGUAGE_SETTED)) {
            CommonUtils.startActivity(this, DiscolsureActivity.class);
        } else {
            CommonUtils.startActivity(this, LanguageActivity.class);
        }
        finish();

    }

    @Override
    public void validateVersion(BaseResponseModel baseResponseModel) {
        if (baseResponseModel.getStatus().equalsIgnoreCase(ConstantLib.RESPONSE_SUCCESS)) {
            navigateToNextScreen();
        } else {
            if (baseResponseModel.getValidate() != null && baseResponseModel.getValidate().equalsIgnoreCase("2")) {
                showAlertWarning(baseResponseModel.getMessage());
            } else if (baseResponseModel.getValidate() != null && baseResponseModel.getValidate().equalsIgnoreCase("3")) {
                showAlertForcefullyUpdate(baseResponseModel.getMessage());
            }
        }

    }

    private void showAlertWarning(String message) {
        CommonUtils.showAlert(this, message, "Update", "Cancel", new AlertDialog.OnClickListner() {
            @Override
            public void onPositiveClick() {
                final String appPackageName = getPackageName(); // getPackageName() from Context or Activity object
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                } catch (android.content.ActivityNotFoundException anfe) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                }
                finish();
            }

            @Override
            public void onNegativeClick() {
                navigateToNextScreen();
            }
        });
    }

    private void showAlertForcefullyUpdate(String message) {
        CommonUtils.showAlert(this, message, "Update", null, new AlertDialog.OnClickListner() {
            @Override
            public void onPositiveClick() {

                final String appPackageName = getPackageName(); // getPackageName() from Context or Activity object
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                } catch (android.content.ActivityNotFoundException anfe) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                }
                finish();
            }

            @Override
            public void onNegativeClick() {
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
