package com.superlifesecretcode.app.ui.splash;

import com.superlifesecretcode.app.R;
import com.superlifesecretcode.app.data.model.userdetails.UserDetailResponseData;
import com.superlifesecretcode.app.data.persistance.SuperLifeSecretPreferences;
import com.superlifesecretcode.app.ui.base.BaseActivity;
import com.superlifesecretcode.app.ui.disclosure.DiscolsureActivity;
import com.superlifesecretcode.app.ui.language.LanguageActivity;
import com.superlifesecretcode.app.ui.login.LoginActivity;
import com.superlifesecretcode.app.ui.main.MainActivity;
import com.superlifesecretcode.app.util.CommonUtils;

public class SplashActivity extends BaseActivity implements SplashView {


    private SplashPresenter presenter;
    String TAG = "SplashActivity";

    @Override
    protected int getContentView() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initializeView() {
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
        if (userData != null) {
            CommonUtils.startActivity(this, MainActivity.class);
        } else if (SuperLifeSecretPreferences.getInstance().getBoolean(SuperLifeSecretPreferences.LANGUAGE_SETTED) && SuperLifeSecretPreferences.getInstance().getBoolean(SuperLifeSecretPreferences.DISCLOSE_ACCEPTED)) {
            CommonUtils.startActivity(this, LoginActivity.class);
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
        overridePendingTransition(R.anim.activity_open_scale, R.anim.activity_close_translate);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        presenter.cancelDelay();
    }
}
