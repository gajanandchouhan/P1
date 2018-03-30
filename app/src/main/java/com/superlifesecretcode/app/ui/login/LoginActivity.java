package com.superlifesecretcode.app.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.superlifesecretcode.app.R;
import com.superlifesecretcode.app.SuperLifeSecretCodeApp;
import com.superlifesecretcode.app.data.model.language.LanguageResponseData;
import com.superlifesecretcode.app.data.model.userdetails.UserDetailResponseData;
import com.superlifesecretcode.app.data.persistance.SuperLifeSecretPreferences;
import com.superlifesecretcode.app.ui.base.BaseActivity;
import com.superlifesecretcode.app.ui.main.MainActivity;
import com.superlifesecretcode.app.ui.register.RegisterActivity;
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

import java.util.Arrays;
import java.util.HashMap;

public class LoginActivity extends BaseActivity implements View.OnClickListener, LoginView, GoogleApiClient.OnConnectionFailedListener {
    private EditText editTextMobileNumber, editTextPassword;
    TextView textViewDontHaveAnAccount, textViewSignup, textViewForgot;
    Button buttonLogin;
    private String eneterMobileNo, enterPassword;
    private LoginPresenter presenter;
    private CallbackManager callbackManager;
    private int RC_SIGN_IN = 114;
    private GoogleApiClient mGoogleApiClient;
    private TwitterAuthClient client;
    private TextView textViewLoginWith;

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
        textViewLoginWith = findViewById(R.id.textView_login_with);
        findViewById(R.id.imageView_f).setOnClickListener(this);
        findViewById(R.id.imageView_g).setOnClickListener(this);
        findViewById(R.id.imageView_t).setOnClickListener(this);
        textViewSignup.setOnClickListener(this);
        textViewDontHaveAnAccount.setOnClickListener(this);
        textViewForgot.setOnClickListener(this);
        setUpConversion();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                //    .requestIdToken("625831639845-2v4uc03o9d98k9bld9m6rdhefoepa4gj.apps.googleusercontent.com")
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
    }

    private void setUpConversion() {
        LanguageResponseData conversionData = SuperLifeSecretCodeApp.getInstance().getConversionData();
        if (conversionData != null) {
            editTextMobileNumber.setHint(conversionData.getMobile_no());
            editTextPassword.setHint(conversionData.getPassword());
            buttonLogin.setText(conversionData.getSigin_in_cotinue());
            textViewSignup.setText(conversionData.getSignup());
            textViewDontHaveAnAccount.setText(String.format("%s ", conversionData.getNo_account_signup()));
            eneterMobileNo = conversionData.getEnter_mobile_number();
            enterPassword = conversionData.getEnter_password();
            textViewForgot.setText(conversionData.getForgot_password());
            textViewLoginWith.setText(conversionData.getLogin_with());
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
                CommonUtils.startActivity(this, RegisterActivity.class);
                break;
            case R.id.imageView_f:
                loginWithFb();
                break;
            case R.id.imageView_g:
                signInWithGoogle();
                break;
            case R.id.imageView_t:
                signinWithTwitter();
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
        body.put("device_token", SuperLifeSecretPreferences.getInstance().getDeviceToken());
        body.put("device_type", "1");
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
        showProgress();
        GraphRequest request = GraphRequest.newMeRequest(
                loginResult.getAccessToken(),
                new GraphRequest.GraphJSONObjectCallback() {

                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        // Application code
                        hideProgress();
                        try {
                            Log.i("Response", response.toString());
                            String email = "";
                            if (response.getJSONObject().has("email")) {
                                email = response.getJSONObject().getString("email");
                            }
                            String name = response.getJSONObject().getString("name");
                            String id = response.getJSONObject().getString("id");
                            registerSocialUser(name, email, id);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            CommonUtils.showSnakeBar(LoginActivity.this, getString(R.string.server_error));
                        }
                    }
                });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,email,name");
        request.setParameters(parameters);
        request.executeAsync();
    }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    private void signInWithGoogle() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void handleSignInResult(GoogleSignInResult result) {
        if (result.isSuccess()) {
            GoogleSignInAccount acct = result.getSignInAccount();
            Log.v("Google", acct.getDisplayName());
            String fullame = acct.getDisplayName();
            String email = acct.getEmail();
            String id = acct.getId();
            registerSocialUser(fullame, email, id);
        } else {
            CommonUtils.showSnakeBar(LoginActivity.this, getString(R.string.server_error));
        }

    }


    private void signinWithTwitter() {
        client = new TwitterAuthClient();
        client.authorize(LoginActivity.this, new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> twitterSessionResult) {
                TwitterSession user = twitterSessionResult.data;
                getUserDetails(user);
            }

            @Override
            public void failure(TwitterException e) {
                CommonUtils.showSnakeBar(LoginActivity.this, getString(R.string.server_error));
            }
        });
    }

    private void getUserDetails(TwitterSession user) {
        showProgress();
        TwitterCore.getInstance().getApiClient(user).getAccountService().verifyCredentials(true, false, true).enqueue(new Callback<User>() {
            @Override
            public void success(Result<User> result) {
                hideProgress();
                registerSocialUser(result.data.name, result.data.email, String.valueOf(result.data.getId()));
            }

            @Override
            public void failure(TwitterException exception) {
                hideProgress();
                CommonUtils.showSnakeBar(LoginActivity.this, getString(R.string.server_error));
            }
        });
    }

    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (client != null) {
            client.onActivityResult(requestCode, resultCode, data);
        }
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
        if (callbackManager != null) {
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }


    private void registerSocialUser(String name, String email, String socialId) {
        HashMap<String, String> body = new HashMap<>();
        body.put("social_id", socialId);
        body.put("name", name);
        body.put("email", email != null ? email : "");
        body.put("device_token", SuperLifeSecretPreferences.getInstance().getDeviceToken());
        body.put("device_type", "1");
        presenter.loginSocialUser(body);
    }
}
