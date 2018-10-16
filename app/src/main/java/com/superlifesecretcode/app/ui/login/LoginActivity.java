package com.superlifesecretcode.app.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.superlifesecretcode.app.R;
import com.superlifesecretcode.app.SuperLifeSecretCodeApp;
import com.superlifesecretcode.app.data.model.country.CountryResponseData;
import com.superlifesecretcode.app.data.model.language.LanguageResponseData;
import com.superlifesecretcode.app.data.model.userdetails.UserDetailResponseData;
import com.superlifesecretcode.app.data.persistance.SuperLifeSecretPreferences;
import com.superlifesecretcode.app.ui.base.BaseActivity;
import com.superlifesecretcode.app.ui.forgotpassword.ForgotPaswordActivity;
import com.superlifesecretcode.app.ui.main.MainActivity;
import com.superlifesecretcode.app.ui.profile.ProfileActivity;
import com.superlifesecretcode.app.ui.register.RegisterActivity;
import com.superlifesecretcode.app.ui.register.RegisterActivityNewFirst;
import com.superlifesecretcode.app.util.CommonUtils;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterAuthClient;
import com.twitter.sdk.android.core.models.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class LoginActivity extends BaseActivity implements View.OnClickListener, LoginView {
    private EditText editTextMobileNumber, editTextPassword;
    TextView textViewDontHaveAnAccount, textViewSignup, textViewForgot , textViewMin;
    Button buttonLogin;
    private String eneterMobileNo, enterPassword, enterValidEmail;
    private LoginPresenter presenter;
    private CallbackManager callbackManager;
    private int RC_SIGN_IN = 114;
    private GoogleApiClient mGoogleApiClient;
    private TwitterAuthClient client;
    List<CountryResponseData> countryResponseDataArrayList;
    String regex = "\\d+";

    @Override
    protected int getContentView() {
        return R.layout.activity_login;
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void initializeView() {

        editTextMobileNumber = findViewById(R.id.edi_text_phone);
        editTextPassword = findViewById(R.id.edit_text_password);
        buttonLogin = findViewById(R.id.button_login);
        buttonLogin.setOnClickListener(this);
        textViewDontHaveAnAccount = findViewById(R.id.textView_dont_have);
        textViewForgot = findViewById(R.id.textView_forgot);
        textViewSignup = findViewById(R.id.textView_signup);
        textViewMin = findViewById(R.id.textview_min);
        //textViewLoginWith = findViewById(R.id.textView_login_with);
        textViewSignup.setOnClickListener(this);
        textViewDontHaveAnAccount.setOnClickListener(this);
        textViewForgot.setOnClickListener(this);
        setUpConversion();
    }

    private void setUpConversion() {
        LanguageResponseData conversionData = SuperLifeSecretCodeApp.getInstance().getConversionData();
        if (conversionData != null) {
            editTextMobileNumber.setHint(conversionData.getMobile_no()+"/"+conversionData.getEmail());
            editTextPassword.setHint(conversionData.getPassword());
            buttonLogin.setText(conversionData.getSigin_in_cotinue());
            textViewSignup.setText(conversionData.getSignup());
            textViewDontHaveAnAccount.setText(String.format("%s ", conversionData.getDont_have_acnt()));
            eneterMobileNo = conversionData.getEnter_mobile_number();
            enterValidEmail = conversionData.getEnter_valid_email();
            enterPassword = conversionData.getEnter_password();
            textViewForgot.setText(conversionData.getForgot_password());
            textViewMin.setText(conversionData.getMin_password_character());
           // textViewLoginWith.setText(conversionData.getLogin_with());
        }
    }

    @Override
    protected void initializePresenter() {
        presenter = new LoginPresenter(this);
        presenter.setView(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_login:
                validateAndLogin();
//                CommonUtils.startActivity(this, MainActivity.class, null, true);
                break;
            case R.id.textView_dont_have:
            case R.id.textView_signup:
                Intent intent = new Intent(this, RegisterActivityNewFirst.class);
                intent.putExtra("countryList", (Serializable) countryResponseDataArrayList);
                startActivity(intent);
//                CommonUtils.startActivity(this, RegisterActivityNewFirst.class);
                break;
            case R.id.textView_forgot:
                CommonUtils.startActivity(this, ForgotPaswordActivity.class);
                break;
        }
    }

    private void validateAndLogin() {
        String mobileNumber = editTextMobileNumber.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        if (mobileNumber.isEmpty()) {
            editTextMobileNumber.setError(eneterMobileNo);
            return;
        }
        HashMap<String, String> body = new HashMap<>();
        if (mobileNumber.matches(regex)) {
            body.put("mobile", mobileNumber.startsWith("0") ? mobileNumber : "0" + mobileNumber);
        } else if (!CommonUtils.isValidEmail(mobileNumber)) {
            editTextMobileNumber.setError(enterValidEmail);
            return;
        } else if (CommonUtils.isValidEmail(mobileNumber)) {
            body.put("mobile", mobileNumber);
        }
        if (password.isEmpty()) {
            editTextPassword.setError(enterPassword);
            return;
        }
        //body.put("mobile", mobileNumber.startsWith("0")?mobileNumber:"0"+mobileNumber);
        body.put("password", password);
        body.put("device_token", SuperLifeSecretPreferences.getInstance().getDeviceToken());
        body.put("device_type", "1");
        body.put("lang_id",SuperLifeSecretPreferences.getInstance().getLanguageId());
        presenter.loginUser(body);
    }

    @Override
    public void setUserData(UserDetailResponseData data) {
        if (data != null) {
            SuperLifeSecretPreferences.getInstance().setUserDetails(data);
            Bundle bundle = new Bundle();
            bundle.putBoolean("isFromLogin",true);
            CommonUtils.startActivity(this, MainActivity.class, bundle, true);
        }
    }

    @Override
    public void setCountryData(List<CountryResponseData> data) {
        countryResponseDataArrayList = data;
    }

}
