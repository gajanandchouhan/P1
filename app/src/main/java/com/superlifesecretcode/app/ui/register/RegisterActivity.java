package com.superlifesecretcode.app.ui.register;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dining.countrypicker.Country;
import com.superlifesecretcode.app.R;
import com.superlifesecretcode.app.SuperLifeSecretCodeApp;
import com.superlifesecretcode.app.data.model.country.CountryResponseData;
import com.superlifesecretcode.app.data.model.language.LanguageResponseData;
import com.superlifesecretcode.app.data.model.userdetails.UserDetailResponseData;
import com.superlifesecretcode.app.data.persistance.SuperLifeSecretPreferences;
import com.superlifesecretcode.app.ui.base.BaseActivity;
import com.superlifesecretcode.app.ui.login.LoginActivity;
import com.superlifesecretcode.app.ui.main.MainActivity;
import com.superlifesecretcode.app.ui.picker.CountryPicker;
import com.superlifesecretcode.app.ui.picker.CountryStatePicker;
import com.superlifesecretcode.app.ui.picker.DropDownWindow;
import com.superlifesecretcode.app.ui.picker.FilterPicker;
import com.superlifesecretcode.app.ui.webview.TcWebViewActivity;
import com.superlifesecretcode.app.ui.webview.WebViewActivity;
import com.superlifesecretcode.app.util.CommonUtils;
import com.superlifesecretcode.app.util.ConstantLib;
import com.superlifesecretcode.app.util.ImageLoadUtils;
import com.superlifesecretcode.app.util.ImagePickerUtils;
import com.superlifesecretcode.app.util.PermissionConstant;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Deprecated
public class RegisterActivity extends BaseActivity implements View.OnClickListener, RegisterView {

    private String countryCode = "us";
    TextView textViewDialCode;
    TextView textViewCountry;
    TextView textViewLabel;
    TextView textViewGender;
    TextView textState;
    TextView textViewCity;
    TextView textViewAlreadyAccount;
    EditText editTextName;
    EditText editTextMobileNumber;
    EditText editTextPassword;
    EditText editTextEmail;
    Button buttonRegister;
    CheckBox checkBoxTOS;
    TextView textViewTermsOfServices;
    ImageView imageViewFlag;
    private CountryPicker countryPicker;
    private TextView textViewSiginCotinue;
    private ImageView imageViewprofile;
    private String imagePath;
    private String enterName, selectGender, selecrCountry,
            selectState, eneterMobileNo, passLength, enterEmail, enterValidEmail;
    private List<String> genderList;
    private RegisterPresenter presenter;

    private CountryStatePicker countryStatePicker;
    private String countryId;
    private String stateId;
    private String gender;
    private String city = "";

    @Override
    protected int getContentView() {
        return R.layout.activity_register;
    }

    @Override
    protected void initializeView() {
        findViewById(R.id.layoout_dial_code).setOnClickListener(this);
        imageViewFlag = findViewById(R.id.imageView_flag);
        textViewDialCode = findViewById(R.id.textView_dial_code);
        textViewCountry = findViewById(R.id.textView_country);
        editTextName = findViewById(R.id.edi_text_name);
        editTextPassword = findViewById(R.id.edit_text_password);
        textViewGender = findViewById(R.id.textView_gender);
        textState = findViewById(R.id.textView_state);
        textViewLabel = findViewById(R.id.textView_label);
        buttonRegister = findViewById(R.id.button_register);
        textViewCity = findViewById(R.id.textView_city);
        editTextMobileNumber = findViewById(R.id.edi_text_phone);
        textViewSiginCotinue = findViewById(R.id.textView_siginin);
        checkBoxTOS = findViewById(R.id.checkbox_tc);
        textViewTermsOfServices = findViewById(R.id.textView_tc);
        textViewAlreadyAccount = findViewById(R.id.textview_allready_account);
        editTextEmail = findViewById(R.id.edit_text_email);
        textViewSiginCotinue.setOnClickListener(this);
        buttonRegister.setOnClickListener(this);
        imageViewprofile = findViewById(R.id.imageView_profile);
        imageViewprofile.setOnClickListener(this);
        textViewGender.setOnClickListener(this);
        textState.setOnClickListener(this);
        textViewCountry.setOnClickListener(this);
        textViewTermsOfServices.setOnClickListener(this);
        textViewCity.setOnClickListener(this);
        setUpConversion();
        Typeface typeface = ResourcesCompat.getFont(this, R.font.reguler);
        checkBoxTOS.setTypeface(typeface);

    }

    private void setUpConversion() {
        LanguageResponseData conversionData = SuperLifeSecretCodeApp.getInstance().getConversionData();
        if (conversionData != null) {
            textViewLabel.setText(conversionData.getSignup());
            editTextName.setHint(conversionData.getName());
            editTextMobileNumber.setHint(conversionData.getMobile_no() + "(Ex. 012345678)");
            textViewGender.setHint(conversionData.getGender());
            textViewCountry.setHint(conversionData.getCountry());
            textState.setHint(conversionData.getState());
            editTextPassword.setHint(conversionData.getPassword());
            textViewCity.setHint(conversionData.getCity());
            buttonRegister.setText(conversionData.getRegister());
            textViewAlreadyAccount.setText(String.format("%s ", conversionData.getAlready_account()));
            textViewSiginCotinue.setText(conversionData.getSigin_in_cotinue());
            editTextEmail.setHint(conversionData.getEmail());
            enterName = conversionData.getEnter_name();
            passLength = conversionData.getPassword_length();
            eneterMobileNo = conversionData.getEnter_mobile_number();
            selectGender = conversionData.getSelect_gender();
            selecrCountry = conversionData.getSelect_country();
            selectState = conversionData.getSelect_state();
            enterEmail = conversionData.getEnter_email();
            enterValidEmail = conversionData.getEnter_valid_email();
            genderList = new ArrayList<>();
            genderList.add(conversionData.getMale());
            genderList.add(conversionData.getFemale());
            checkBoxTOS.setText(conversionData.getI_agree());
            textViewTermsOfServices.setText(conversionData.getTerms_of_service());
        }
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
//                CommonUtils.startActivity(this, MainActivity.class, null, true);
                validateAndRegister();
                break;
            case R.id.textView_siginin:
                onBackPressed();
                break;
            case R.id.imageView_profile:
                if (CommonUtils.hasPermissions(this, PermissionConstant.PERMISSION_PROFILE)) {
                    pickImage();
                } else {
                    ActivityCompat.requestPermissions(this, PermissionConstant.PERMISSION_PROFILE, PermissionConstant.CODE_PROFILE);
                }
                break;
            case R.id.textView_country:
                getCountry();
                break;
            case R.id.textView_gender:
                showgGenderSelection();
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
                    CommonUtils.showSnakeBar(this, SuperLifeSecretPreferences.getInstance().
                            getConversionData().getSelect_state());
                    return;
                }
                getCity();
                break;
            case R.id.textView_tc:
                Bundle bundle = new Bundle();
                bundle.putString("title", "Terms Of Services");
                bundle.putString("url", ConstantLib.TC_URL);
                bundle.putBoolean("is_link", true);
                CommonUtils.startActivity(this, TcWebViewActivity.class, bundle, false);
                break;

        }

    }

    private void getCity() {
        HashMap<String, String> map = new HashMap();
        map.put("state_id", stateId);
        presenter.getCities(map);
    }

    private void getCountry() {
        presenter.getCountry();
    }

    private void showgGenderSelection() {

        DropDownWindow.show(this, textViewGender, genderList, new DropDownWindow.SelectedListner() {
            @Override
            public void onSelected(String value, int position) {
                textViewGender.setText(value);
                gender = String.valueOf(position + 1);
            }
        });
    }

    private void validateAndRegister() {
        String name = editTextName.getText().toString().trim();
        String dialCode = textViewDialCode.getText().toString();
        String mobileNumber = editTextMobileNumber.getText().toString().trim();
        String gender = textViewGender.getText().toString().trim();
        String country = textViewCountry.getText().toString().trim();
        String state = textState.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        if (name.isEmpty()) {
            editTextName.setError(enterName);
            return;
        }
        if (mobileNumber.isEmpty()) {
            editTextMobileNumber.setError(eneterMobileNo);
            return;
        }
        if (gender.isEmpty()) {
            CommonUtils.showSnakeBar(this, selectGender);
            return;
        }
        if (country.isEmpty()) {
            CommonUtils.showSnakeBar(this, selecrCountry);
            return;
        }
        if (state.isEmpty()) {
            CommonUtils.showSnakeBar(this, selectState);
            return;
        }
        if (email.isEmpty()) {
            editTextEmail.setError(enterEmail);
            return;
        }
        if (!CommonUtils.isValidEmail(email)) {
            editTextEmail.setError(enterValidEmail);
            return;
        }
        if (password.isEmpty() || password.length() < 6) {
            editTextPassword.setError(passLength);
            return;
        }
        if (!checkBoxTOS.isChecked()) {
            CommonUtils.showSnakeBar(this, SuperLifeSecretPreferences.getInstance().getConversionData().getAgree_tc());
            return;
        }
        HashMap<String, String> body = new HashMap<>();
        body.put("name", name);
        body.put("gender", this.gender);
        body.put("mobile", mobileNumber.startsWith("0") ? mobileNumber : "0" + mobileNumber);
        body.put("country_id", countryId);
        body.put("state_id", stateId);
        body.put("city_id", city);
        body.put("password", password);
        body.put("phone_code", dialCode);
        body.put("device_token", SuperLifeSecretPreferences.getInstance().getDeviceToken());
        body.put("country_code", countryCode.toLowerCase());
        body.put("device_type", "1");
        body.put("email", email);
        HashMap<String, File> fileParams = null;
        if (imagePath != null) {
            File file = new File(imagePath);
            fileParams = new HashMap<>();
            fileParams.put("image", file);
        }
        presenter.registerUser(body, fileParams);
    }

    private void showDialCodePicker() {
        countryPicker = new CountryPicker(this, new CountryPicker.PickerListner() {
            @Override
            public void onPick(Country country) {
                textViewDialCode.setText(country.getDialCode());
                imageViewFlag.setImageResource(country.getFlag());
//                countryCode = country.getCode().toLowerCase();
                countryPicker.dismiss();
            }
        });
        countryPicker.show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PermissionConstant.CODE_PROFILE) {
            for (int grantResult : grantResults) {
                if (grantResult != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
            }
            pickImage();
        }
    }

    private void pickImage() {
        CropImage.activity()
                .setCropShape(CropImageView.CropShape.RECTANGLE)
                .start(this);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                imageViewprofile.setImageURI(result.getUri());
                imagePath = ImagePickerUtils.getPath(this, result.getUri());
                if (imagePath != null) {
                    ImageLoadUtils.loadImage(imagePath, imageViewprofile);
                }
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Toast.makeText(this, "Cropping failed: " + result.getError(), Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void setCountryData(List<CountryResponseData> data) {
        countryStatePicker = new CountryStatePicker(this, new CountryStatePicker.PickerListner() {
            @Override
            public void onPick(CountryResponseData country) {
                textViewCountry.setText(country.getName());
                if (!country.getId().equalsIgnoreCase(countryId)) {
                    stateId = null;
                    city = "";
                    textState.setText("");
                    textViewCity.setText("");
                }
                countryId = country.getId();
                countryCode = country.getCountrycode();
                countryStatePicker.dismiss();
            }
        }, data);
        countryStatePicker.show();
    }

    @Override
    public void setStateData(List<CountryResponseData> data) {
        countryStatePicker = new CountryStatePicker(this, new CountryStatePicker.PickerListner() {
            @Override
            public void onPick(CountryResponseData country) {
                textState.setText(country.getName());
                if (!country.getId().equalsIgnoreCase(stateId)) {
                    city = "";
                    textViewCity.setText("");
                }
                countryStatePicker.dismiss();
                stateId = country.getId();
            }
        }, data);
        countryStatePicker.show();
    }

    @Override
    public void setUserData(UserDetailResponseData data) {
        if (data != null) {
            SuperLifeSecretPreferences.getInstance().setUserDetails(data);
            CommonUtils.startActivity(this, MainActivity.class, null, true);
        }
    }

    @Override
    public void setCities(List<CountryResponseData> data) {
        countryStatePicker = new CountryStatePicker(this, new CountryStatePicker.PickerListner() {
            @Override
            public void onPick(CountryResponseData country) {
                textViewCity.setText(country.getName());
                city = country.getId();
                countryStatePicker.dismiss();
            }
        }, data);
        countryStatePicker.show();
    }

    @Override
    public void onCountryCodeSuccess() {

    }

    @Override
    public void setCountryDataPicker(List<CountryResponseData> data) {

    }

    private void getState() {
        HashMap<String, String> body = new HashMap<>();
        body.put("country_id", countryId);
        presenter.getStates(body);
    }


}
