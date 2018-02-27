package com.superlifesecretcode.app.ui.splash;

import android.content.Context;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.superlifesecretcode.app.R;
import com.superlifesecretcode.app.ui.base.BaseActivity;
import com.superlifesecretcode.app.ui.disclosure.DiscolsureActivity;
import com.superlifesecretcode.app.ui.language.LanguageActivity;
import com.superlifesecretcode.app.ui.main.MainActivity;
import com.superlifesecretcode.app.util.CommonUtils;

public class SplashActivity extends BaseActivity implements SplashView {


    private SplashPresenter presenter;
    String TAG="SplashActivity";

    @Override
    protected int getContentView() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initializeView() {
        presenter.delaySplash();
    }

    @Override
    protected void initializePresenter() {
        presenter = new SplashPresenter(this);
        presenter.setView(this);
    }

    @Override
    public void navigateToNextScreen() {
        CommonUtils.startActivity(this, LanguageActivity.class);
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        presenter.cancelDelay();
    }
}
