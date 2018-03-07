package com.superlifesecretcode.app.ui.splash;

import com.superlifesecretcode.app.R;
import com.superlifesecretcode.app.ui.base.BaseActivity;
import com.superlifesecretcode.app.ui.language.LanguageActivity;
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
        CommonUtils.startActivity(this, LanguageActivity.class);
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
