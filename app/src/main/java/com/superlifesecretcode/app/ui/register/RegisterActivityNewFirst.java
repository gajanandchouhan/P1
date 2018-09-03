package com.superlifesecretcode.app.ui.register;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.dining.countrypicker.Country;
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
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.tasks.OnSuccessListener;
import com.superlifesecretcode.app.R;
import com.superlifesecretcode.app.SuperLifeSecretCodeApp;
import com.superlifesecretcode.app.data.model.country.CountryResponseData;
import com.superlifesecretcode.app.data.model.language.LanguageResponseData;
import com.superlifesecretcode.app.data.model.userdetails.UserDetailResponseData;
import com.superlifesecretcode.app.data.persistance.SuperLifeSecretPreferences;
import com.superlifesecretcode.app.ui.base.BaseActivity;
import com.superlifesecretcode.app.ui.disclosure.DiscolsureActivity;
import com.superlifesecretcode.app.ui.login.LoginActivity;
import com.superlifesecretcode.app.ui.main.MainActivity;
import com.superlifesecretcode.app.ui.picker.AlertDialog;
import com.superlifesecretcode.app.ui.picker.CountryPicker;
import com.superlifesecretcode.app.ui.picker.CountryStatePicker;
import com.superlifesecretcode.app.ui.webview.TcWebViewActivity;
import com.superlifesecretcode.app.util.CommonUtils;
import com.superlifesecretcode.app.util.ConstantLib;
import com.superlifesecretcode.app.util.GeoCoderUtils;
import com.superlifesecretcode.app.util.PermissionConstant;
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
import java.util.List;

public class RegisterActivityNewFirst extends BaseActivity implements View.OnClickListener, RegisterView , GoogleApiClient.OnConnectionFailedListener{

    private String countryCode = "us";
    TextView textViewDialCode;
    TextView textViewCountry;
    TextView textViewLabel;
    TextView textViewAlreadyAccount , textViewMin;
    EditText editTextMobileNumber;
    EditText editTextPassword;
    Button buttonRegister;
    ImageView imageViewFlag;
    private CountryPicker countryPicker;
    private TextView textViewSiginCotinue;
    private String selecrCountry, eneterMobileNo, passLength;
    private RegisterPresenter presenter;
    private CountryStatePicker countryStatePicker;
    private String countryId;
    private String stateId;
    private String cityId;
    private String city = "";
    List<CountryResponseData> countryList;
    private FusedLocationProviderClient mFusedLocationClient;
    String stateNamefromGeo, cityNameFromGeo;
    String matchedStateName, matchedCityName , countryName;
    private TextView textViewLoginWith;
    private CallbackManager callbackManager;
    private int RC_SIGN_IN = 114;
    private GoogleApiClient mGoogleApiClient;
    private TwitterAuthClient client;
    String dialCode;
    public static final int REQUEST_CHECK_SETTINGS = 101;
    public String sendCity , sendState;

    @Override
    protected int getContentView() {
        return R.layout.activity_register_new1;
    }

    @Override
    protected void initializeView() {
        presenter.getCountry();
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        //displayLocationSettingsRequest(RegisterActivityNewFirst.this);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                //    .requestIdToken("625831639845-2v4uc03o9d98k9bld9m6rdhefoepa4gj.apps.googleusercontent.com")
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        CommonUtils.hideKeyboard(RegisterActivityNewFirst.this);
        imageViewFlag = findViewById(R.id.imageView_flag);
        textViewDialCode = findViewById(R.id.textView_dial_code);
        textViewCountry = findViewById(R.id.textView_country);
        editTextPassword = findViewById(R.id.edit_text_password);
        textViewLabel = findViewById(R.id.textView_label);
        buttonRegister = findViewById(R.id.button_register);
        editTextMobileNumber = findViewById(R.id.edi_text_phone);
        textViewSiginCotinue = findViewById(R.id.textView_siginin);
        textViewMin = findViewById(R.id.textview_min);
        textViewAlreadyAccount = findViewById(R.id.textview_allready_account);
        textViewSiginCotinue.setOnClickListener(this);
        textViewLoginWith = findViewById(R.id.textView_login_with);
        buttonRegister.setOnClickListener(this);
        textViewCountry.setOnClickListener(this);
        findViewById(R.id.imageView_f).setOnClickListener(this);
        findViewById(R.id.imageView_g).setOnClickListener(this);
        findViewById(R.id.imageView_t).setOnClickListener(this);
        setUpConversion();
    }

    private void setUpConversion() {
        LanguageResponseData conversionData = SuperLifeSecretCodeApp.getInstance().getConversionData();
        if (conversionData != null) {
            textViewLabel.setText(conversionData.getSignup());
            editTextMobileNumber.setHint(conversionData.getMobile_no() + "(Ex. 012345678)");
            textViewCountry.setHint(conversionData.getCountry());
            editTextPassword.setHint(conversionData.getPassword());
            buttonRegister.setText(conversionData.getContinuee());
            textViewAlreadyAccount.setText(String.format("%s ", conversionData.getAlready_account()));
            textViewSiginCotinue.setText(conversionData.getExisting_user());
            passLength = conversionData.getPassword_length();
            eneterMobileNo = conversionData.getEnter_mobile_number();
            selecrCountry = conversionData.getSelect_country();
            textViewLoginWith.setText(conversionData.getLogin_with());
            textViewMin.setText(conversionData.getMin_password_character());
        }
    }
    private void displayLocationSettingsRequest(Context context) {
        GoogleApiClient googleApiClient = new GoogleApiClient.Builder(context)
                .addApi(LocationServices.API).build();
        googleApiClient.connect();

        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(10000 / 2);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);
        builder.setAlwaysShow(true);
        PendingResult<LocationSettingsResult> result = LocationServices.SettingsApi.checkLocationSettings(googleApiClient, builder.build());

        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(LocationSettingsResult result) {
                final Status status = result.getStatus();
                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:
                        getcurrentlocaiton();
                        if (!CommonUtils.hasPermissions(RegisterActivityNewFirst.this, PermissionConstant.PERMISSION_LOCATION)) {
//                            Toast.makeText(RegisterActivityNewFirst.this, "has permission", Toast.LENGTH_SHORT).show();
                            ActivityCompat.requestPermissions(RegisterActivityNewFirst.this, PermissionConstant.PERMISSION_LOCATION, PermissionConstant.CODE_LOCATION);
                        } else {
//                            Toast.makeText(RegisterActivityNewFirst.this, "does not has permission", Toast.LENGTH_SHORT).show();
                            getcurrentlocaiton();
                        }
                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        try {
                            status.startResolutionForResult(RegisterActivityNewFirst.this, REQUEST_CHECK_SETTINGS);
                        } catch (IntentSender.SendIntentException e) {
                            Log.i("pending intent", "PendingIntent unable to execute request.");
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        getcurrentlocaiton();
                        break;
                }
            }
        });
    }

    @Override
    protected void initializePresenter() {
        presenter = new RegisterPresenter(this);
        presenter.setView(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layoout_dial_code:
                showDialCodePicker();
                break;

            case R.id.button_register:
                dialCode = textViewDialCode.getText().toString();
                String mobileNumber = editTextMobileNumber.getText().toString().trim();
                String country = textViewCountry.getText().toString().trim();
                String password = editTextPassword.getText().toString().trim();

                if (mobileNumber.isEmpty()) {
                    editTextMobileNumber.setError(eneterMobileNo);
                    return;
                }

                if (country.isEmpty()) {
                    CommonUtils.showSnakeBar(this, selecrCountry);
                    return;
                }

                if (password.isEmpty() || password.length() < 6) {
                    editTextPassword.setError(passLength);
                    return;
                }

                Intent bundle = new Intent(this, RegisterActivityNewSecond.class);
                bundle.putExtra("country_id", "" + countryId);
                bundle.putExtra("country_code", "" + countryCode.toLowerCase());
                bundle.putExtra("password", "" + password);
                bundle.putExtra("phone_code", "" + dialCode);
                Log.e("stateId",""+stateId);
                Log.e("city_id",""+cityId);
            if (stateId!=null )
                bundle.putExtra("state_id", "" + stateId);
            else
                bundle.putExtra("state_id", "" + stateNamefromGeo);

                if (cityId!=null)
                    bundle.putExtra("city_id", "" + cityId);
                else
                    bundle.putExtra("city_id", "" + cityNameFromGeo);
//                bundle.putExtra("city_id", "" + cityId);
                bundle.putExtra("mobile", mobileNumber.startsWith("0") ? mobileNumber : "0" + mobileNumber);
                startActivity(bundle);
                break;

            case R.id.textView_siginin:
                UserDetailResponseData userDetailResponseData = new UserDetailResponseData();
                dialCode = textViewDialCode.getText().toString();

                userDetailResponseData.setCountry_code(countryCode);
                if (countryId==null || countryId.equals("")){
                    userDetailResponseData.setCountry(SuperLifeSecretPreferences.getInstance().getConversionData().getCountry());
                }else {
                    userDetailResponseData.setCountry(countryId);
                }

                userDetailResponseData.setCountryName(countryName);
                userDetailResponseData.setPhone_code(dialCode);
                if(stateId==null || stateId.equals("") || stateId.equals(null) || stateId.equals("null")){
                    userDetailResponseData.setState(stateNamefromGeo);
                }else {
                    userDetailResponseData.setState(stateId);
                    userDetailResponseData.setStateName(matchedStateName);
                }

                if(cityId==null || cityId.equals("") || cityId.equals(null) || cityId.equals("null")){
                    userDetailResponseData.setCity(cityNameFromGeo);
                }else {
                    userDetailResponseData.setCity(cityId);
                    userDetailResponseData.setCityName(matchedCityName);
                }
                SuperLifeSecretPreferences.getInstance().setLocationData(userDetailResponseData);
                CommonUtils.startActivity(this, LoginActivity.class);
                break;

            case R.id.textView_country:
                if (countryList != null) {
                    showCountryPicker(countryList);
                } else {
                    getCountry();
                }
                break;

            case R.id.textView_state:
                if (countryId == null) {
                    CommonUtils.showSnakeBar(this, selecrCountry);
                    return;
                }
                getState();
                break;

            case R.id.textView_city:
                if (stateId == null) {
                    CommonUtils.showSnakeBar(this, SuperLifeSecretPreferences.getInstance().getConversionData().getSelect_state());
                    return;
                }
                getCity();
                break;
            case R.id.textView_tc:
                Bundle bundleSign = new Bundle();
                bundleSign.putString("title", "Terms Of Services");
                bundleSign.putString("url", ConstantLib.TC_URL);
                bundleSign.putBoolean("is_link", true);
                CommonUtils.startActivity(this, TcWebViewActivity.class, bundleSign, false);
                break;
            case R.id.imageView_f:
                UserDetailResponseData userDetailResponseData2 = new UserDetailResponseData();
                dialCode = textViewDialCode.getText().toString();

                userDetailResponseData2.setCountry_code(countryCode);
                if (countryId==null || countryId.equals("")){
                    userDetailResponseData2.setCountry("");
                }else {
                    userDetailResponseData2.setCountry(countryId);
                }

                userDetailResponseData2.setCountryName(countryName);
                userDetailResponseData2.setPhone_code(dialCode);
                if(stateId==null || stateId.equals("") || stateId.equals(null) || stateId.equals("null")){
                    userDetailResponseData2.setState(stateNamefromGeo);
                }else {
                    userDetailResponseData2.setState(stateId);
                    userDetailResponseData2.setStateName(matchedStateName);
                }

                if(cityId==null || cityId.equals("") || cityId.equals(null) || cityId.equals("null")){
                    userDetailResponseData2.setCity(cityNameFromGeo);
                }else {
                    userDetailResponseData2.setCity(cityId);
                    userDetailResponseData2.setCityName(matchedCityName);
                }
                SuperLifeSecretPreferences.getInstance().setLocationData(userDetailResponseData2);
                loginWithFb();
                break;
            case R.id.imageView_g:
                UserDetailResponseData userDetailResponseData3 = new UserDetailResponseData();
                dialCode = textViewDialCode.getText().toString();

                userDetailResponseData3.setCountry_code(countryCode);
                if (countryId==null || countryId.equals("")){
                    userDetailResponseData3.setCountry("");
                }else {
                    userDetailResponseData3.setCountry(countryId);
                }

                userDetailResponseData3.setCountryName(countryName);
                userDetailResponseData3.setPhone_code(dialCode);
                if(stateId==null || stateId.equals("") || stateId.equals(null) || stateId.equals("null")){
                    userDetailResponseData3.setState(stateNamefromGeo);
                }else {
                    userDetailResponseData3.setState(stateId);
                    userDetailResponseData3.setStateName(matchedStateName);
                }

                if(cityId==null || cityId.equals("") || cityId.equals(null) || cityId.equals("null")){
                    userDetailResponseData3.setCity(cityNameFromGeo);
                }else {
                    userDetailResponseData3.setCity(cityId);
                    userDetailResponseData3.setCityName(matchedCityName);
                }
                SuperLifeSecretPreferences.getInstance().setLocationData(userDetailResponseData3);
                signInWithGoogle();
                break;
            case R.id.imageView_t:
                UserDetailResponseData userDetailResponseData4 = new UserDetailResponseData();
                dialCode = textViewDialCode.getText().toString();

                userDetailResponseData4.setCountry_code(countryCode);
                if (countryId==null || countryId.equals("")){
                    userDetailResponseData4.setCountry("");
                }else {
                    userDetailResponseData4.setCountry(countryId);
                }

                userDetailResponseData4.setCountryName(countryName);
                userDetailResponseData4.setPhone_code(dialCode);
                if(stateId==null || stateId.equals("") || stateId.equals(null) || stateId.equals("null")){
                    userDetailResponseData4.setState(stateNamefromGeo);
                }else {
                    userDetailResponseData4.setState(stateId);
                    userDetailResponseData4.setStateName(matchedStateName);
                }

                if(cityId==null || cityId.equals("") || cityId.equals(null) || cityId.equals("null")){
                    userDetailResponseData4.setCity(cityNameFromGeo);
                }else {
                    userDetailResponseData4.setCity(cityId);
                    userDetailResponseData4.setCityName(matchedCityName);
                }
                SuperLifeSecretPreferences.getInstance().setLocationData(userDetailResponseData4);
                signinWithTwitter();
                break;
        }
    }

    private void getCity() {
        HashMap<String, String> map = new HashMap();
        map.put("state_id", stateId);
        presenter.getCities(map);
    }

    private void getCountry() {
        presenter.getCountryPicker();
    }

    private void showDialCodePicker() {
        countryPicker = new CountryPicker(this, new CountryPicker.PickerListner() {
            @Override
            public void onPick(Country country) {
                textViewDialCode.setText(country.getDialCode());
                imageViewFlag.setImageResource(country.getFlag());
                countryPicker.dismiss();
            }
        });
        countryPicker.show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PermissionConstant.CODE_LOCATION) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getcurrentlocaiton();
//                displayLocationSettingsRequest(RegisterActivityNewFirst.this);
            }
            return;
        }
    }
    @Override
    public void setCountryData(List<CountryResponseData> data) {
        countryList = data;
        displayLocationSettingsRequest(RegisterActivityNewFirst.this);
        //countryList = (List<CountryResponseData>) getIntent().getSerializableExtra("countryList");

    }

    public void showCountryPicker(List<CountryResponseData> data) {
        countryStatePicker = new CountryStatePicker(this, new CountryStatePicker.PickerListner() {
            @Override
            public void onPick(CountryResponseData country) {
                textViewCountry.setText(country.getName());
                if (!country.getId().equalsIgnoreCase(countryId)) {
                    stateId = null;
                    city = "";
                }
                countryId = country.getId();
                countryCode = country.getCountrycode();
                textViewDialCode.setText("+" + country.getPhonecode());
                for (int j = 0; j < Country.getAllCountries().size(); j++) {
                    {
                        if (country.getCountrycode().equalsIgnoreCase(Country.getAllCountries().get(j).getCode())) {
                            imageViewFlag.setImageResource(Country.getAllCountries().get(j).getFlag());
                            countryStatePicker.dismiss();
                            return;
                        }
                    }
                }
                countryStatePicker.dismiss();
            }
        }, data);
        countryStatePicker.show();
    }

    @Override
    public void setStateData(List<CountryResponseData> data) {

        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).getName().equalsIgnoreCase(stateNamefromGeo)) {
                stateId = data.get(i).getId();
                matchedStateName = data.get(i).getName();
                getCity();
                return;
            }
        }
    }

    @Override
    public void setUserData(UserDetailResponseData data) {
        if (data != null) {
            SuperLifeSecretPreferences.getInstance().setUserDetails(data);
            CommonUtils.startActivity(this, MainActivity.class, null, true);
        }
    }

    @Override
    public void setCities(List<CountryResponseData> cities) {
        for (int j = 0; j < cities.size(); j++) {
            if (cities.get(j).getName().equalsIgnoreCase(cityNameFromGeo)) {
                cityId = cities.get(j).getId();
                matchedCityName = cities.get(j).getName();
                //Toast.makeText(this, "cityMatched", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onCountryCodeSuccess() {
    }

    @Override
    public void setCountryDataPicker(List<CountryResponseData> data) {
        showCountryPicker(data);
    }

    private void getState() {
        HashMap<String, String> body = new HashMap<>();
        body.put("country_id", countryId);
        presenter.getStates(body);
    }

    public void getcurrentlocaiton() {
//        Toast.makeText(this, "RESULT_OK2", Toast.LENGTH_SHORT).show();
        mFusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {


                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {

//                            GeoCoderUtils.getCountryCode(3.152194, 101.778446, RegisterActivityNewFirst.this, new GeoCoderUtils.GeocoderListner() {
//                            GeoCoderUtils.getCountryCode(location.getLatitude(), location.getLongitude(), RegisterActivityNewFirst.this, new GeoCoderUtils.GeocoderListner() {

                            GeoCoderUtils.getCountryCode(location.getLatitude(), location.getLongitude(), RegisterActivityNewFirst.this, new GeoCoderUtils.GeocoderListner() {
// GeoCoderUtils.getCountryCode(25.105497, 121.597366, RegisterActivityNewFirst.this, new GeoCoderUtils.GeocoderListner() {
//                     malasia           GeoCoderUtils.getCountryCode(3.152194, 101.778446, RegisterActivityNewFirst.this, new GeoCoderUtils.GeocoderListner() {
                                @Override
                                public void onGetCode(String country_name,String country_code, String stateNamefromeo, String cityNamefrogeo) {

                                    countryName = country_name;
                                    stateNamefromGeo = stateNamefromeo;
                                    cityNameFromGeo = cityNamefrogeo;
                                    CommonUtils.showLog("location1", "Code is :" + country_code);
                                    if (country_code != null) {
                                        String dialcode = "";
                                        if (countryCode != null && countryList != null) {
                                            for (int i = 0; i < countryList.size(); i++) {
                                                if (country_code.equals(countryList.get(i).getCountrycode())) {
                                                    CommonUtils.showLog("location2", "Code is :" + country_code);
                                                    textViewCountry.setText("" + countryList.get(i).getName());
                                                    countryId = countryList.get(i).getId();
                                                    countryCode = country_code;
                                                    getState();
                                                    textViewDialCode.setText("+" + countryList.get(i).getPhonecode());
                                                    dialCode = countryList.get(i).getPhonecode();

                                                    for (int j = 0; j < Country.getAllCountries().size(); j++) {
                                                        {
                                                            if (countryList.get(i).getName().equals(Country.getAllCountries().get(j).getName())) {
                                                                imageViewFlag.setImageResource(Country.getAllCountries().get(j).getFlag());
                                                                return;
                                                            }
                                                        }
                                                    }
                                                    return;
                                                }
                                            }
                                        }
                                    }
                                }
                            });
                        }
                    }
                });
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

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
                            CommonUtils.showSnakeBar(RegisterActivityNewFirst.this, getString(R.string.server_error));
                        }
                    }
                });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,email,name");
        request.setParameters(parameters);
        request.executeAsync();
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
            CommonUtils.showSnakeBar(RegisterActivityNewFirst.this, getString(R.string.server_error));
        }
    }

    private void signinWithTwitter() {
        client = new TwitterAuthClient();
        client.authorize(RegisterActivityNewFirst.this, new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> twitterSessionResult) {
                TwitterSession user = twitterSessionResult.data;
                getUserDetails(user);
            }

            @Override
            public void failure(TwitterException e) {
                CommonUtils.showSnakeBar(RegisterActivityNewFirst.this, getString(R.string.server_error));
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
                CommonUtils.showSnakeBar(RegisterActivityNewFirst.this, getString(R.string.server_error));
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
        if (requestCode == REQUEST_CHECK_SETTINGS) {
            if (resultCode == Activity.RESULT_OK) {
//                Toast.makeText(this, "RESULT_OK", Toast.LENGTH_SHORT).show();
                getcurrentlocaiton();
                if (!CommonUtils.hasPermissions(this, PermissionConstant.PERMISSION_LOCATION)) {
                    ActivityCompat.requestPermissions(this, PermissionConstant.PERMISSION_LOCATION, PermissionConstant.CODE_LOCATION);
                } else {
                    getcurrentlocaiton();
                }
               // startLocationUpdates();
               // allowedLocation();
            }else{
                getcurrentlocaiton();
                //deniedLocation();
            }
        }
    }


    private void registerSocialUser(String name, String email, String socialId) {
        HashMap<String, String> body = new HashMap<>();
        body.put("social_id", socialId);
        body.put("name", name);
        body.put("email", email != null ? email : "");
        body.put("device_token", SuperLifeSecretPreferences.getInstance().getDeviceToken());
        body.put("device_type", "1");
        if (cityId!=null && !city.isEmpty())
        body.put("city", cityId);
        else
            body.put("city",cityNameFromGeo!=null?cityNameFromGeo:"");
        Log.e("city_id",""+cityId);

        if (stateId!=null && !stateId.isEmpty())
        body.put("state", stateId);
        else
            body.put("state",stateNamefromGeo!=null?stateNamefromGeo:"");
        if (countryCode!=null && !countryCode.isEmpty())
        body.put("country_code",countryCode);
        if (dialCode!=null && !dialCode.isEmpty())
        body.put("phone_code",dialCode);
        body.put("country",countryId!=null?countryId:"");
        body.put("lang_id",SuperLifeSecretPreferences.getInstance().getLanguageId());

        presenter.loginSocialUser(body);
    }
}
