package com.superlifesecretcode.app.ui.splash;

import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.Gson;
import com.superlifesecretcode.app.R;
import com.superlifesecretcode.app.data.model.userdetails.UserDetailResponseData;
import com.superlifesecretcode.app.data.persistance.SuperLifeSecretPreferences;
import com.superlifesecretcode.app.ui.base.BaseActivity;
import com.superlifesecretcode.app.ui.disclosure.DiscolsureActivity;
import com.superlifesecretcode.app.ui.language.LanguageActivity;
import com.superlifesecretcode.app.ui.login.LoginActivity;
import com.superlifesecretcode.app.ui.main.MainActivity;
import com.superlifesecretcode.app.ui.register.RegisterActivityNewFirst;
import com.superlifesecretcode.app.util.CommonUtils;
import com.superlifesecretcode.app.util.PermissionConstant;

import org.json.JSONException;
import org.json.JSONObject;

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
        presenter.delaySplash();
    }

    @Override
    protected void initializePresenter() {
        presenter = new SplashPresenter(this);
        presenter.setView(this);
    }

    @Override
    public void navigateToNextScreen() {
        UserDetailResponseData userData = SuperLifeSecretPreferences.getInstance().getUserData();
        Log.e("userdata2",""+new Gson().toJson(userData));
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
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        presenter.cancelDelay();
    }
}
