package com.superlifesecretcode.app.ui.disclosure;

import android.location.Location;
import android.os.Build;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.superlifesecretcode.app.R;
import com.superlifesecretcode.app.SuperLifeSecretCodeApp;
import com.superlifesecretcode.app.data.model.disclosure.DisclosureResponseData;
import com.superlifesecretcode.app.data.model.language.LanguageResponseData;
import com.superlifesecretcode.app.data.persistance.SuperLifeSecretPreferences;
import com.superlifesecretcode.app.ui.base.BaseActivity;
import com.superlifesecretcode.app.ui.language.LanguageActivity;
import com.superlifesecretcode.app.ui.login.LoginActivity;
import com.superlifesecretcode.app.ui.picker.AlertDialog;
import com.superlifesecretcode.app.ui.register.RegisterActivity;
import com.superlifesecretcode.app.ui.register.RegisterActivityNewFirst;
import com.superlifesecretcode.app.util.CommonUtils;
import com.superlifesecretcode.app.util.GeoCoderUtils;

import java.util.HashMap;

public class DiscolsureActivity extends BaseActivity implements View.OnClickListener, DisclosureView {
    private static final String TAG = "DiscolsureActivity";
    private Button buttonAccept, buttonReject;
    private TextView textViewDisclosureText;
    private DisclosurePresenter presenter;

    @Override
    protected int getContentView() {
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
        getDisclosureText();
    }

    private void getDisclosureText() {
        HashMap<String, String> requestBody = new HashMap<>();
        requestBody.put("disclosure_id", SuperLifeSecretPreferences.getInstance().getLanguageId());
        presenter.getDisclosure(requestBody);
    }

    private void setUpConersion() {
        LanguageResponseData conversionData = SuperLifeSecretCodeApp.getInstance().getConversionData();
        if (conversionData != null) {
            buttonReject.setText(conversionData.getReject());
            buttonAccept.setText(conversionData.getAccept());
        }
    }

    @Override
    protected void initializePresenter() {
        presenter = new DisclosurePresenter(this);
        presenter.setView(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_accept:
                SuperLifeSecretPreferences.getInstance().putBoolean(SuperLifeSecretPreferences.DISCLOSE_ACCEPTED, true);
                CommonUtils.startActivity(DiscolsureActivity.this, RegisterActivityNewFirst.class);
                finish();
                break;
            case R.id.button_reject:
                SuperLifeSecretPreferences.getInstance().putBoolean(SuperLifeSecretPreferences.DISCLOSE_ACCEPTED, false);
                finish();
                break;
        }
    }

    protected void showInternetAlert() {
        CommonUtils.showAlert(this, getString(R.string.no_internet), "OK", "CANCEL", new AlertDialog.OnClickListner() {
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
                    public void onGetCode(String countryName,String countryCode,String state,String city) {
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
    }

    @Override
    public void setDisclosureData(DisclosureResponseData data) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Spanned spanned = Html.fromHtml(data.getDisclosure(), Html.FROM_HTML_MODE_LEGACY);
            textViewDisclosureText.setText(spanned);
        } else {
            Spanned spanned = Html.fromHtml(data.getDisclosure());
            textViewDisclosureText.setText(spanned);
        }
    }
}
