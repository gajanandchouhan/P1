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
import com.superlifesecretcode.app.data.model.language.LanguageResponseData;
import com.superlifesecretcode.app.ui.base.BaseActivity;
import com.superlifesecretcode.app.ui.login.LoginActivity;
import com.superlifesecretcode.app.ui.picker.CountryPicker;
import com.superlifesecretcode.app.util.CommonUtils;
import com.superlifesecretcode.app.util.ImageLoadUtils;
import com.superlifesecretcode.app.util.ImagePickerUtils;
import com.superlifesecretcode.app.util.PermissionConstant;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

public class RegisterActivity extends BaseActivity implements View.OnClickListener {

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
    private String enterName,selectGender,selecrCountry,selectState,eneterMobileNo,passLength;


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
        }
    }

    @Override
    protected void initializePresenter() {

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
                break;
            case R.id.textView_gender:
                break;
            case R.id.textView_state:
                break;

        }

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
    }

    private void showDialCodePicker() {
        countryPicker = new CountryPicker(this, new CountryPicker.PickerListner() {
            @Override
            public void onPick(Country country) {
                textViewDialCode.setText(country.getDialCode());
                imageViewFlag.setImageResource(country.getFlag());
//                textViewCountry.setText(country.getName());
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
}
