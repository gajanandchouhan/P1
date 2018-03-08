package com.superlifesecretcode.app.ui.disclosure;

import android.location.Location;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.superlifesecretcode.app.R;
import com.superlifesecretcode.app.SuperLifeSecretCodeApp;
import com.superlifesecretcode.app.data.model.language.LanguageResponseData;
import com.superlifesecretcode.app.data.persistance.SuperLifeSecretPreferences;
import com.superlifesecretcode.app.ui.base.BaseActivity;
import com.superlifesecretcode.app.ui.language.LanguageActivity;
import com.superlifesecretcode.app.ui.login.LoginActivity;
import com.superlifesecretcode.app.ui.register.RegisterActivity;
import com.superlifesecretcode.app.util.CommonUtils;
import com.superlifesecretcode.app.util.GeoCoderUtils;

public class DiscolsureActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = "DiscolsureActivity";
    private Button buttonAccept, buttonReject;
    private TextView textViewDisclosureText;

    @Override
    protected int getContentView() {
        overridePendingTransition(R.anim.activity_open_translate, R.anim.activity_close_scale);
        return R.layout.activity_discolsure;
    }

    @Override
    protected void initializeView() {
        buttonAccept = findViewById(R.id.button_accept);
        buttonReject = findViewById(R.id.button_reject);
        textViewDisclosureText = findViewById(R.id.textView_disclosure_text);
        buttonAccept.setOnClickListener(this);
        buttonReject.setOnClickListener(this);
        setUpConersion();
    }

    private void setUpConersion() {
        LanguageResponseData conversionData = SuperLifeSecretCodeApp.getInstance().getConversionData();
        if (conversionData != null) {
            buttonReject.setText(conversionData.getReject());
            buttonAccept.setText(conversionData.getAccept());
            textViewDisclosureText.setText(conversionData.getDisclosure_text());
        }
    }

    @Override
    protected void initializePresenter() {
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_accept:
                SuperLifeSecretPreferences.getInstance().putBoolean(SuperLifeSecretPreferences.DISCLOSE_ACCEPTED, true);
                CommonUtils.startActivity(DiscolsureActivity.this, LoginActivity.class);
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

    private void accept() {
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
                        } else {
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
