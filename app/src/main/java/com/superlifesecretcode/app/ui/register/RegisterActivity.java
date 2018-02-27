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
import com.superlifesecretcode.app.ui.base.BaseActivity;
import com.superlifesecretcode.app.ui.login.LoginActivity;
import com.superlifesecretcode.app.ui.picker.CountryPicker;
import com.superlifesecretcode.app.util.CommonUtils;
import com.superlifesecretcode.app.util.PermissionConstant;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

public class RegisterActivity extends BaseActivity implements View.OnClickListener {

    private String countryCode;
    TextView textViewDialCode;
    TextView textViewCountry;
    EditText editTextName;
    TextView textViewGender;
    TextView textState;
    EditText editTextPassword;
    Button buttonRegister;

    ImageView imageViewFlag;
    private CountryPicker countryPicker;
    private TextView textViewSiginCotinue;
    private ImageView imageViewprofile;

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
        buttonRegister = findViewById(R.id.button_register);
        textViewSiginCotinue = findViewById(R.id.textView_siginin);
        textViewSiginCotinue.setOnClickListener(this);
        buttonRegister.setOnClickListener(this);
        imageViewprofile=findViewById(R.id.imageView_profile);
        imageViewprofile.setOnClickListener(this);
    }

    @Override
    protected void initializePresenter() {

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
        }

    }

    private void validateAndRegister() {

    }

    private void showDialCodePicker() {
        countryPicker = new CountryPicker(this, new CountryPicker.PickerListner() {
            @Override
            public void onPick(Country country) {
                textViewDialCode.setText(country.getDialCode());
                imageViewFlag.setImageResource(country.getFlag());
                textViewCountry.setText(country.getName());
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
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Toast.makeText(this, "Cropping failed: " + result.getError(), Toast.LENGTH_LONG).show();
            }
        }
    }
}
