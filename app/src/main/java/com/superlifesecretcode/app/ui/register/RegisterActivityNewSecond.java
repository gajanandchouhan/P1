package com.superlifesecretcode.app.ui.register;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.util.Log;
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
import com.superlifesecretcode.app.ui.main.MainActivity;
import com.superlifesecretcode.app.ui.picker.CountryPicker;
import com.superlifesecretcode.app.ui.picker.CountryStatePicker;
import com.superlifesecretcode.app.ui.picker.DropDownWindow;
import com.superlifesecretcode.app.ui.webview.TcWebViewActivity;
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

public class RegisterActivityNewSecond extends BaseActivity implements View.OnClickListener, RegisterView {

    private String countryCode = "us";
    TextView textViewLabel;
    TextView textViewGender;
    TextView textViewAlreadyAccount;
    EditText editTextName;
    EditText editTextEmail;
    Button buttonRegister, buttonSkipandRegister;
    CheckBox checkBoxTOS;
    TextView textViewTermsOfServices;
    private CountryPicker countryPicker;
    private TextView textViewSiginCotinue;
    private ImageView imageViewprofile;
    private String imagePath;
    private String enterName, selectGender,
            passLength, enterEmail, enterValidEmail;
    private List<String> genderList;
    private RegisterPresenter presenter;
    private String gender="";
    private String city = "";

    @Override
    protected int getContentView() {
        return R.layout.activity_register_new2;
    }

    @Override
    protected void initializeView() {
        editTextName = findViewById(R.id.edi_text_name);
        textViewGender = findViewById(R.id.textView_gender);
        textViewLabel = findViewById(R.id.textView_label);
        buttonRegister = findViewById(R.id.button_register);
        textViewSiginCotinue = findViewById(R.id.textView_siginin);
        checkBoxTOS = findViewById(R.id.checkbox_tc);
        textViewTermsOfServices = findViewById(R.id.textView_tc);
        textViewAlreadyAccount = findViewById(R.id.textview_allready_account);
        editTextEmail = findViewById(R.id.edit_text_email);
        buttonSkipandRegister = findViewById(R.id.button_skipand_register);
        textViewSiginCotinue.setOnClickListener(this);
        buttonRegister.setOnClickListener(this);
        imageViewprofile = findViewById(R.id.imageView_profile);
        imageViewprofile.setOnClickListener(this);
        textViewGender.setOnClickListener(this);
        textViewTermsOfServices.setOnClickListener(this);
        buttonSkipandRegister.setOnClickListener(this);
        setUpConversion();
        Typeface typeface = ResourcesCompat.getFont(this, R.font.reguler);
        checkBoxTOS.setTypeface(typeface);
    }

    private void setUpConversion() {
        LanguageResponseData conversionData = SuperLifeSecretCodeApp.getInstance().getConversionData();
        if (conversionData != null) {
            textViewLabel.setText(conversionData.getSignup());
            buttonSkipandRegister.setText(conversionData.getStart_app());
            editTextName.setHint(conversionData.getName());
            textViewGender.setHint(conversionData.getGender());
            buttonRegister.setText(conversionData.getSignup());
            textViewAlreadyAccount.setText(String.format("%s ", conversionData.getAlready_account()));
            textViewSiginCotinue.setText(conversionData.getSigin_in_cotinue());
            editTextEmail.setHint(conversionData.getEmail());
            enterName = conversionData.getEnter_name();
            passLength = conversionData.getPassword_length();
            selectGender = conversionData.getSelect_gender();
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
            case R.id.button_register:
                validateAndRegister(1);
                break;
            case R.id.button_skipand_register:
                validateAndRegister(2);
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

            case R.id.textView_gender:
                showgGenderSelection();
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

    private void showgGenderSelection() {

        DropDownWindow.show(this, textViewGender, genderList, new DropDownWindow.SelectedListner() {
            @Override
            public void onSelected(String value, int position) {
                textViewGender.setText(value);
                gender = String.valueOf(position + 1);
            }
        });
    }

    private void validateAndRegister(int check) {
        String name = editTextName.getText().toString().trim();
        String gender = textViewGender.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        HashMap<String, String> body = new HashMap<>();
        if (check==1){
            if (name.isEmpty()) {
                editTextName.setError(enterName);
                return;
            }
            if (gender.isEmpty()) {
                CommonUtils.showSnakeBar(this, selectGender);
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
            body.put("name", name);
            body.put("email", email);
            body.put("gender", gender);
        }else {
            body.put("name", "");
            body.put("email", "");
            body.put("gender", "");
        }

        if (!checkBoxTOS.isChecked()) {
            CommonUtils.showSnakeBar(this, SuperLifeSecretPreferences.getInstance().getConversionData().getAgree_tc());
            return;
        }
        String mobileNumber = getIntent().getStringExtra("mobile");
        String countryId = getIntent().getStringExtra("country_id");
        String password = getIntent().getStringExtra("password");
        String phone_code = getIntent().getStringExtra("phone_code");
        String country_code = getIntent().getStringExtra("country_code");
        String state_id = getIntent().getStringExtra("state_id");
        String city_id = getIntent().getStringExtra("city_id");

        if (state_id!=null)
        body.put("state_id", state_id);
        else
            body.put("state_id", "");
        if (city_id!=null)
        body.put("city_id", city_id);
        else
            body.put("city_id","");

        //Toast.makeText(this, "cityid--"+city_id+"   stateid--"+state_id, Toast.LENGTH_SHORT).show();
        body.put("mobile", mobileNumber);
        body.put("country_id", countryId);
        body.put("password", password);
        body.put("phone_code", phone_code);
        body.put("device_token", SuperLifeSecretPreferences.getInstance().getDeviceToken());
        body.put("country_code", country_code);
        body.put("device_type", "1");
        body.put("lang_id",SuperLifeSecretPreferences.getInstance().getLanguageId());

        Log.e("body",body.toString());
//
        HashMap<String, File> fileParams = null;
        if (imagePath != null) {
            File file = new File(imagePath);
            fileParams = new HashMap<>();
            fileParams.put("image", file);
        }
        presenter.registerUser(body, fileParams);
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
    }
    @Override
    public void setStateData(List<CountryResponseData> data) {
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
    }
    @Override
    public void onCountryCodeSuccess() {
    }

    @Override
    public void setCountryDataPicker(List<CountryResponseData> data) {

    }
}
