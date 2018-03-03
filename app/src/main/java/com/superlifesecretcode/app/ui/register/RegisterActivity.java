package com.superlifesecretcode.app.ui.register;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.Button;
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
import com.superlifesecretcode.app.util.CommonUtils;
import com.superlifesecretcode.app.util.ImageLoadUtils;
import com.superlifesecretcode.app.util.ImagePickerUtils;
import com.superlifesecretcode.app.util.PermissionConstant;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RegisterActivity extends BaseActivity implements View.OnClickListener, RegisterView {

    private String countryCode = "us";
    TextView textViewDialCode;
    TextView textViewCountry;
    TextView textViewLabel;
    TextView textViewGender;
    TextView textState;
    TextView textViewAlreadyAccount;
    EditText editTextName;
    EditText editTextMobileNumber;
    EditText editTextPassword;
    Button buttonRegister;
    ImageView imageViewFlag;
    private CountryPicker countryPicker;
    private TextView textViewSiginCotinue;
    private ImageView imageViewprofile;
    private String imagePath;
    private String enterName, selectGender, selecrCountry, selectState, eneterMobileNo, passLength;
    private List<String> genderList;
    private RegisterPresenter presenter;

    private CountryStatePicker countryStatePicker;
    private String countryId;
    private String stateId;

    @Override
    protected int getContentView() {
        overridePendingTransition(R.anim.activity_open_translate, R.anim.activity_close_scale);
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
        editTextMobileNumber = findViewById(R.id.edi_text_phone);
        textViewSiginCotinue = findViewById(R.id.textView_siginin);
        textViewAlreadyAccount = findViewById(R.id.textview_allready_account);
        textViewSiginCotinue.setOnClickListener(this);
        buttonRegister.setOnClickListener(this);
        imageViewprofile = findViewById(R.id.imageView_profile);
        imageViewprofile.setOnClickListener(this);
        textViewGender.setOnClickListener(this);
        textState.setOnClickListener(this);
        textViewCountry.setOnClickListener(this);
        setUpConversion();

    }

    private void setUpConversion() {
        LanguageResponseData conversionData = SuperLifeSecretCodeApp.getInstance().getConversionData();
        if (conversionData != null) {
            textViewLabel.setText(conversionData.getSignup());
            editTextName.setHint(conversionData.getName());
            editTextMobileNumber.setHint(conversionData.getMobile_no());
            textViewGender.setHint(conversionData.getGender());
            textViewCountry.setHint(conversionData.getCountry());
            textState.setHint(conversionData.getState());
            editTextPassword.setHint(conversionData.getPassword());
            buttonRegister.setText(conversionData.getRegister());
            textViewAlreadyAccount.setText(String.format("%s ", conversionData.getAlready_account()));
            textViewSiginCotinue.setText(conversionData.getSigin_in_cotinue());
            enterName = conversionData.getEnter_name();
            passLength = conversionData.getPassword_length();
            eneterMobileNo = conversionData.getEnter_mobile_number();
            selectGender = conversionData.getSelect_gender();
            selecrCountry = conversionData.getSelect_country();
            selectState = conversionData.getSelect_state();
            genderList = new ArrayList<>();
            genderList.add(conversionData.getMale());
            genderList.add(conversionData.getFemale());
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
        overridePendingTransition(R.anim.activity_open_scale, R.anim.activity_close_translate);
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
                CommonUtils.startActivity(this, LoginActivity.class);
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

        }

    }

    private void getCountry() {
        presenter.getCountry();
    }

    private void showgGenderSelection() {

        DropDownWindow.show(this, textViewGender, genderList, new DropDownWindow.SelectedListner() {
            @Override
            public void onSelected(String value) {
                textViewGender.setText(value);
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
        if (password.isEmpty()) {
            editTextPassword.setError(passLength);
            return;
        }
        HashMap<String, String> body = new HashMap<>();
        body.put("name", name);
        body.put("gender", gender);
        body.put("mobile", mobileNumber);
        body.put("country_id", countryId);
        body.put("state_id", stateId);
        body.put("password", password);
        body.put("phone_code", dialCode);
        body.put("country_code", countryCode.toLowerCase());
        body.put("email", "");
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
                countryCode = country.getCode().toLowerCase();
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
                countryId = country.getId();
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

    private void getState() {
        HashMap<String, String> body = new HashMap<>();
        body.put("country_id", countryId);
        presenter.getStates(body);
    }


}
