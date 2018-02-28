package com.superlifesecretcode.app.ui.disclosure;

import android.location.Location;
import android.view.View;

import com.superlifesecretcode.app.R;
import com.superlifesecretcode.app.data.persistance.SuperLifeSecretPreferences;
import com.superlifesecretcode.app.ui.base.BaseActivity;
import com.superlifesecretcode.app.ui.language.LanguageActivity;
import com.superlifesecretcode.app.ui.register.RegisterActivity;
import com.superlifesecretcode.app.util.CommonUtils;
import com.superlifesecretcode.app.util.GeoCoderUtils;

public class DiscolsureActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = "DiscolsureActivity";

    @Override
    protected int getContentView() {
        overridePendingTransition(R.anim.activity_open_translate, R.anim.activity_close_scale);
        return R.layout.activity_discolsure;
    }

    @Override
    protected void initializeView() {
        findViewById(R.id.button_accept).setOnClickListener(this);
        findViewById(R.id.button_reject).setOnClickListener(this);
    }

    @Override
    protected void initializePresenter() {
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_accept:
                SuperLifeSecretPreferences.getInstance().putBoolean(SuperLifeSecretPreferences.DISCLOSE_ACCEPTED, true);
                CommonUtils.startActivity(DiscolsureActivity.this, RegisterActivity.class);
                finish();
                break;
            case R.id.button_reject:
                SuperLifeSecretPreferences.getInstance().putBoolean(SuperLifeSecretPreferences.DISCLOSE_ACCEPTED, false);
                finish();
                break;
        }
    }

    protected void showInternetAlert() {
        CommonUtils.showAlert(this, getString(R.string.no_internet), "OK", "CANCEL", new CommonUtils.ClickListner() {
            @Override
            public void onPositiveClick() {
            }

            @Override
            public void onNegativeClick() {
            }
        });
    }

    private void accept(){
        getCurrentLocation(new LocationCallBack() {
            @Override
            public void onLocationSuccess(Location location) {
                GeoCoderUtils.getCountryCode(location.getLatitude(), location.getLongitude(), DiscolsureActivity.this, new GeoCoderUtils.GeocoderListner() {
                    @Override
                    public void onGetCode(String countryCode) {
                        if (countryCode != null) {
                            CommonUtils.showLog(TAG, "Code is :" + countryCode);
                            SuperLifeSecretPreferences.getInstance().putBoolean(SuperLifeSecretPreferences.DISCLOSE_ACCEPTED, true);
                            CommonUtils.startActivity(DiscolsureActivity.this, RegisterActivity.class);
                            finish();
                        }else {
                            showInternetAlert();
                        }
                    }
                });
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(R.anim.activity_open_scale, R.anim.activity_close_translate);
    }
}
