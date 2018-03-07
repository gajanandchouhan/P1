package com.superlifesecretcode.app.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.superlifesecretcode.app.R;
import com.superlifesecretcode.app.SuperLifeSecretCodeApp;
import com.superlifesecretcode.app.data.model.language.LanguageResponseData;
import com.superlifesecretcode.app.data.model.userdetails.UserDetailResponseData;
import com.superlifesecretcode.app.data.persistance.SuperLifeSecretPreferences;
import com.superlifesecretcode.app.ui.base.BaseActivity;
import com.superlifesecretcode.app.ui.main.MainActivity;
import com.superlifesecretcode.app.util.CommonUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;

public class LoginActivity extends BaseActivity implements View.OnClickListener, LoginView {
    private EditText editTextMobileNumber, editTextPassword;
    TextView textViewDontHaveAnAccount, textViewSignup, textViewForgot;
    Button buttonLogin;
    private String eneterMobileNo, enterPassword;
    private LoginPresenter presenter;
    private CallbackManager callbackManager;

    @Override
    protected int getContentView() {
        overridePendingTransition(R.anim.activity_open_translate, R.anim.activity_close_scale);
        return R.layout.activity_login;
    }

    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(R.anim.activity_open_scale, R.anim.activity_close_translate);
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
        findViewById(R.id.imageView_f).setOnClickListener(this);
        textViewSignup.setOnClickListener(this);
        textViewForgot.setOnClickListener(this);
        setUpConversion();
    }

    private void setUpConversion() {
        LanguageResponseData conversionData = SuperLifeSecretCodeApp.getInstance().getConversionData();
        if (conversionData != null) {
            editTextMobileNumber.setHint(conversionData.getMobile_no());
            editTextPassword.setHint(conversionData.getPassword());
            buttonLogin.setText(conversionData.getSigin_in_cotinue());
            textViewSignup.setText(conversionData.getSignup());
            textViewDontHaveAnAccount.setText(String.format("%s ", conversionData.getDont_have_acnt()));
            eneterMobileNo = conversionData.getEnter_mobile_number();
            enterPassword = conversionData.getEnter_password();
            textViewForgot.setText(conversionData.getForgot_password());
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
            case R.id.textView_signup:
                onBackPressed();
                break;
            case R.id.imageView_f:
                loginWithFb();
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
        if (password.isEmpty()) {
            editTextPassword.setError(enterPassword);
            return;
        }
        HashMap<String, String> body = new HashMap<>();
        body.put("mobile", mobileNumber);
        body.put("password", password);
        presenter.loginUser(body);

    }

    @Override
    public void setUserData(UserDetailResponseData data) {
        if (data != null) {
            SuperLifeSecretPreferences.getInstance().setUserDetails(data);
            CommonUtils.startActivity(this, MainActivity.class, null, true);
        }
    }


    private void loginWithFb() {
        callbackManager = CallbackManager.Factory.create();

        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        // App code
                        Log.v("FACEBOOK", "Suceess" + loginResult.getAccessToken().getToken());
                        setFacebookData(loginResult);
                    }

                    @Override
                    public void onCancel() {
                        // App code
                        Log.v("FACEBOOK", "Cancel");
                    }

                    @Override
                    public void onError(FacebookException exc) {
                        // App code
                        Log.v("ERROR", "ERROR" + exc.getMessage());
                    }
                });
        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile", "email"));
    }

    private void setFacebookData(final LoginResult loginResult) {
        GraphRequest request = GraphRequest.newMeRequest(
                loginResult.getAccessToken(),
                new GraphRequest.GraphJSONObjectCallback() {

                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        // Application code
                        try {
                            Log.i("Response", response.toString());

                            String email = response.getJSONObject().getString("email");
                            String firstName = response.getJSONObject().getString("first_name");
                            String lastName = response.getJSONObject().getString("last_name");
                            String id = response.getJSONObject().getString("id");

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,email,name,gender");
        request.setParameters(parameters);
        request.executeAsync();
    }

    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
      /*  if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        } else {*/
            callbackManager.onActivityResult(requestCode, resultCode, data);
//        }
    }
}
