package com.superlifesecretcode.app.ui.splash;

import android.content.Context;
import android.os.Handler;

import com.superlifesecretcode.app.ui.base.BasePresenter;


/**
 * Created by Divya on 21-02-2018.
 */

public class SplashPresenter extends BasePresenter<SplashView> {
    private SplashView view;

    public SplashPresenter(Context mContext) {
        super(mContext);
    }

    @Override
    protected void setView(SplashView splashView) {
        view = splashView;
    }

    public void delaySplash() {
        mHandler.postDelayed(mRunnable, 3000);
    }

    Handler mHandler = new Handler();
    Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            view.navigateToNextScreen();
        }
    };

    public void cancelDelay() {
        mHandler.removeCallbacks(mRunnable);
    }
}
