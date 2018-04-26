package com.superlifesecretcode.app.ui.profile;


import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dining.countrypicker.Country;
import com.superlifesecretcode.app.R;
import com.superlifesecretcode.app.data.model.SubcategoryModel;
import com.superlifesecretcode.app.data.model.country.CountryResponseData;
import com.superlifesecretcode.app.data.model.language.LanguageResponseData;
import com.superlifesecretcode.app.data.model.userdetails.UserDetailResponseData;
import com.superlifesecretcode.app.data.persistance.SuperLifeSecretPreferences;
import com.superlifesecretcode.app.ui.base.BaseActivity;
import com.superlifesecretcode.app.ui.customizebar.CustomizeBarActivity;
import com.superlifesecretcode.app.ui.main.MainActivity;
import com.superlifesecretcode.app.ui.picker.CountryPicker;
import com.superlifesecretcode.app.ui.picker.CountryStatePicker;
import com.superlifesecretcode.app.ui.picker.DropDownWindow;
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
import java.util.Map;

public class ProfileActivity extends BaseActivity implements View.OnClickListener, ProfileView {


    private ImageView imageViewProfile;
    private EditText editTextName;
    private EditText editTextMobileNumber, editTextEmail;
    private TextView textViewGender, textViewCountry, textViewDialCode,
            textViewState, textViewLanguage, textViewName;
    UserDetailResponseData userDetailResponseData;
    ImageView imageViewUser, imageViewFlag;
    private LanguageResponseData conversionData;
    private ArrayList<String> genderList;
    private List<String> langauageList;
    private String currentLanguag;
    private String languageId;
    private ProfilePresenter presenter;
    private TextView textViewNameLabel, textViewGenderLabel, textViewMobileLabel,
            textViewCountryLabel, textViewStateLabel, textViewLanguageLabel, textViewEmailLabel;
    private boolean isEnabled;
    String countryId, stateId, dialCode, countryCode;
    private String imagePath;
    private CountryPicker countryPicker;
    private CountryStatePicker countryStatePicker;
    private boolean isUpdate;
    private boolean lanuguageChanged;
    private Button buttonCustomize;
    ImageView imageViewCamera;

    @Override
    protected int getContentView() {
        return R.layout.activity_profile;
    }

    @Override
    protected void initializeView() {
        userDetailResponseData = SuperLifeSecretPreferences.getInstance().getUserData();
        conversionData = SuperLifeSecretPreferences.getInstance().getConversionData();
        setUpToolbar("Profile");
        isUpdate = getIntent().getBundleExtra("bundle").getBoolean("update");
        editTextName = findViewById(R.id.edi_text_name);
        editTextMobileNumber = findViewById(R.id.edi_text_phone);
        textViewGender = findViewById(R.id.textView_gender);
        textViewCountry = findViewById(R.id.textView_country);
        textViewState = findViewById(R.id.textView_state);
        textViewLanguage = findViewById(R.id.textView_language);
        textViewName = findViewById(R.id.textView_name);
        textViewDialCode = findViewById(R.id.textView_phonecode);
        imageViewUser = findViewById(R.id.imageView_user);
        imageViewFlag = findViewById(R.id.imageView_flag);
        imageViewCamera=findViewById(R.id.imageView_camera);
        textViewNameLabel = findViewById(R.id.textView_name_label);
        textViewMobileLabel = findViewById(R.id.textView_mobile_label);
        textViewGenderLabel = findViewById(R.id.textView_gender_label);
        textViewCountryLabel = findViewById(R.id.textView_country_label);
        textViewStateLabel = findViewById(R.id.textView_state_label);
        textViewLanguageLabel = findViewById(R.id.textView_language_label);
        editTextEmail = findViewById(R.id.edit_text_email);
        textViewEmailLabel = findViewById(R.id.textView_email_label);
        textViewGender.setOnClickListener(this);
        textViewLanguage.setOnClickListener(this);
        imageViewUser.setOnClickListener(this);
        imageViewFlag.setOnClickListener(this);
        textViewDialCode.setOnClickListener(this);
        textViewCountry.setOnClickListener(this);
        textViewState.setOnClickListener(this);
        buttonCustomize = findViewById(R.id.customize_bar);
        buttonCustomize.setOnClickListener(this);
        if (conversionData != null) {
            genderList = new ArrayList<>();
            genderList.add(conversionData.getMale());
            genderList.add(conversionData.getFemale());
        }
        langauageList = new ArrayList<>();
        langauageList.add(ConstantLib.STRING_ENGLISH);
        langauageList.add(ConstantLib.STRING_TRADITIONAL);
        langauageList.add(ConstantLib.STRING_SIMPLIFIED);
        setUpLocalConversion();
        setUpUi();
        enableDisableView(false);

    }

    private void setUpLocalConversion() {
        if (conversionData != null) {
            genderList = new ArrayList<>();
            genderList.add(conversionData.getMale());
            genderList.add(conversionData.getFemale());
        }
        textViewNameLabel.setText(conversionData.getName());
        editTextName.setHint(conversionData.getName());
        textViewMobileLabel.setText(conversionData.getMobile_no());
        editTextMobileNumber.setHint(conversionData.getMobile_no());
        textViewGenderLabel.setText(conversionData.getGender());
        textViewGender.setHint(conversionData.getGender());
        textViewCountryLabel.setText(conversionData.getCountry());
        textViewCountry.setHint(conversionData.getCountry());
        textViewStateLabel.setText(conversionData.getState());
        textViewState.setHint(conversionData.getState());
        textViewLanguageLabel.setText(conversionData.getSelect_language());
        textViewLanguage.setHint(conversionData.getSelect_language());
        textViewEmailLabel.setText(conversionData.getEmail());
        editTextEmail.setHint(conversionData.getEmail());
        buttonCustomize.setText(conversionData.getCustmize_bar());
    }

    private void setUpUi() {
        if (userDetailResponseData != null) {
            if (userDetailResponseData.getCountry_code() != null) {
                String countrCode = userDetailResponseData.getCountry_code().toLowerCase();
                if (!countrCode.isEmpty()) {
                    int id = getResources().getIdentifier("flag_" + countrCode, "drawable", getPackageName());
                    imageViewFlag.setImageResource(id);
                    dialCode = userDetailResponseData.getPhone_code();
                    countryCode = userDetailResponseData.getCountry_code();
                    textViewDialCode.setText(dialCode);
                }
            }
            editTextName.setText(userDetailResponseData.getUsername());
            textViewName.setText(userDetailResponseData.getUsername());
            editTextMobileNumber.setText(userDetailResponseData.getMobile());
            textViewCountry.setText(userDetailResponseData.getCountryName());
            textViewState.setText(userDetailResponseData.getStateName());
            textViewGender.setText(userDetailResponseData.getGender());
            countryId = userDetailResponseData.getCountry();
            stateId = userDetailResponseData.getState();
            editTextEmail.setText(userDetailResponseData.getEmail());
            languageId=SuperLifeSecretPreferences.getInstance().getLanguageId();
            currentLanguag = getLanguage(languageId);
            textViewLanguage.setText(currentLanguag);
            ImageLoadUtils.loadImage(userDetailResponseData.getImage(), imageViewUser);
        }

    }

    private String getLanguage(String languageId) {
        switch (languageId) {
            case ConstantLib.LANGUAGE_ENGLISH:
                return ConstantLib.STRING_ENGLISH;
            case ConstantLib.LANGUAGE_SIMPLIFIED:
                return ConstantLib.STRING_SIMPLIFIED;
            case ConstantLib.LANGUAGE_TRADITIONAL:
                return ConstantLib.STRING_TRADITIONAL;
        }
        return ConstantLib.STRING_ENGLISH;
    }

    private void setUpToolbar(String title) {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        TextView textViewTitle = findViewById(R.id.textView_title);
        imageViewProfile = findViewById(R.id.imageView_profile);
        imageViewProfile.setVisibility(View.VISIBLE);
        imageViewProfile.setImageResource(R.drawable.edit);
        imageViewProfile.setOnClickListener(this);
        textViewTitle.setText(title);
    }

    @Override
    protected void initializePresenter() {
        presenter = new ProfilePresenter(this);
        presenter.setView(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    private void showgGenderSelection() {
        DropDownWindow.show(this, textViewGender, genderList, new DropDownWindow.SelectedListner() {
            @Override
            public void onSelected(String value) {
                textViewGender.setText(value);
            }
        });
    }

    private void showLanguageSelection() {
        DropDownWindow.show(this, textViewLanguage, langauageList, new DropDownWindow.SelectedListner() {
            @Override
            public void onSelected(String value) {
                if (!value.equalsIgnoreCase(currentLanguag)) {
                    textViewLanguage.setText(value);
                    currentLanguag = value;
                    switch (value) {
                        case ConstantLib.STRING_ENGLISH:
                            languageId = ConstantLib.LANGUAGE_ENGLISH;
                            break;
                        case ConstantLib.STRING_SIMPLIFIED:
                            languageId = ConstantLib.LANGUAGE_SIMPLIFIED;
                            break;
                        case ConstantLib.STRING_TRADITIONAL:
                            languageId = ConstantLib.LANGUAGE_TRADITIONAL;
                            break;
                    }
                    HashMap<String, String> body = new HashMap<>();
                    body.put("language_id", languageId);
                    presenter.getConversion(body);
                    lanuguageChanged = true;
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.textView_gender:
                showgGenderSelection();
                break;
            case R.id.textView_phonecode:
            case R.id.imageView_flag:
                showDialCodePicker();
                break;
            case R.id.textView_language:
                showLanguageSelection();
                break;
            case R.id.customize_bar:
                CommonUtils.startActivity(this, CustomizeBarActivity.class);
                break;
            case R.id.imageView_profile:
                if (isEnabled) {
                    updateProfile();
                } else {
                    enableDisableView(true);
                }

                break;
            case R.id.textView_country:
                getCountry();
                break;
            case R.id.textView_state:
                if (countryId == null) {
                    CommonUtils.showSnakeBar(this, conversionData.getSelect_country());
                    return;
                }
                getState();
                break;

            case R.id.imageView_user:
                if (CommonUtils.hasPermissions(this, PermissionConstant.PERMISSION_PROFILE)) {
                    pickImage();
                } else {
                    ActivityCompat.requestPermissions(this, PermissionConstant.PERMISSION_PROFILE, PermissionConstant.CODE_PROFILE);
                }
                break;
        }
    }

    private void updateProfile() {
        String name = editTextName.getText().toString().trim();
        String mobileNumber = editTextMobileNumber.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String gender = textViewGender.getText().toString().trim();
        String country = textViewCountry.getText().toString().trim();
        String state = textViewState.getText().toString().trim();

        if (name.isEmpty()) {
            editTextName.setError(conversionData.getEnter_name());
            return;
        }
        if (dialCode == null || countryCode == null) {
            CommonUtils.showSnakeBar(this, "Please select dial code.");
            return;
        }

        if (mobileNumber.isEmpty()) {
            editTextMobileNumber.setError(conversionData.getEnter_mobile_number());
            return;
        }
        if (email.isEmpty()) {
            editTextEmail.setError(conversionData.getEnter_email());
            return;
        }
        if (!CommonUtils.isValidEmail(email)) {
            editTextEmail.setError(conversionData.getEnter_valid_email());
            return;
        }
        if (gender.isEmpty()) {
            CommonUtils.showSnakeBar(this, conversionData.getSelect_gender());
            return;
        }
        if (countryId == null) {
            CommonUtils.showSnakeBar(this, conversionData.getSelect_country());
            return;
        }
        if (stateId == null) {
            CommonUtils.showSnakeBar(this, conversionData.getSelect_state());
            return;
        }
        HashMap<String, String> body = new HashMap<>();
        body.put("name", name);
        body.put("gender", gender);
        body.put("mobile", mobileNumber.startsWith("0")?mobileNumber:"0"+mobileNumber);
        body.put("country_id", countryId);
        body.put("state_id", stateId);
        body.put("phone_code", dialCode);
        body.put("email", email);
        body.put("country_code", countryCode.toLowerCase());
        body.put("user_id", userDetailResponseData.getUser_id());
        HashMap<String, File> fileParams = null;
        if (imagePath != null) {
            File file = new File(imagePath);
            fileParams = new HashMap<>();
            fileParams.put("image", file);
        }
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + userDetailResponseData.getApi_token());
        presenter.updateUser(body, fileParams, headers);
    }

    private void enableDisableView(boolean enbale) {
        isEnabled = enbale;
        if (enbale) {
            imageViewCamera.setVisibility(View.VISIBLE);
            imageViewProfile.setImageResource(R.drawable.right);
        } else {
            imageViewProfile.setImageResource(R.drawable.edit);
            imageViewCamera.setVisibility(View.GONE);
        }
        editTextName.setEnabled(enbale);
        imageViewUser.setEnabled(enbale);
        imageViewFlag.setEnabled(enbale);
        textViewDialCode.setEnabled(enbale);
        editTextMobileNumber.setEnabled(enbale && editTextMobileNumber.getText().toString().isEmpty());
        editTextEmail.setEnabled(enbale && editTextEmail.getText().toString().isEmpty());
        textViewGender.setEnabled(enbale);
        textViewCountry.setEnabled(enbale);
        textViewState.setEnabled(enbale);
        textViewLanguage.setEnabled(enbale);
    }


    @Override
    public void setConversionContent(LanguageResponseData data) {
        conversionData = data;
        setUpLocalConversion();
    }


    private void showDialCodePicker() {
        countryPicker = new CountryPicker(this, new CountryPicker.PickerListner() {
            @Override
            public void onPick(Country country) {
                imageViewFlag.setImageResource(country.getFlag());
                dialCode = country.getDialCode();
                countryCode = country.getCode().toLowerCase();
                countryPicker.dismiss();
                textViewDialCode.setText(dialCode);
            }
        });
        countryPicker.show();
    }

    private void getCountry() {
        presenter.getCountry();
    }

    private void getState() {
        HashMap<String, String> body = new HashMap<>();
        body.put("country_id", countryId);
        presenter.getStates(body);
    }


    private void pickImage() {
        CropImage.activity()
                .setCropShape(CropImageView.CropShape.OVAL)
                .start(this);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                imageViewUser.setImageURI(result.getUri());
                imagePath = ImagePickerUtils.getPath(this, result.getUri());
                if (imagePath != null) {
                    ImageLoadUtils.loadImage(imagePath, imageViewUser);
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
                textViewState.setText(country.getName());
                countryStatePicker.dismiss();
                stateId = country.getId();
            }
        }, data);
        countryStatePicker.show();
    }

    @Override
    public void setUserData(UserDetailResponseData data) {
        if (data != null) {
            this.userDetailResponseData = data;
            SuperLifeSecretPreferences.getInstance().setUserDetails(data);
            enableDisableView(false);
            SuperLifeSecretPreferences.getInstance().setConversionData(conversionData);
            SuperLifeSecretPreferences.getInstance().setLanguageId(languageId);
            MainActivity.LANGAUE_CHANGED = lanuguageChanged;
            MainActivity.PROFILE_UPDATED = true;
            if (isUpdate) {
                CommonUtils.startActivity(this, MainActivity.class);
            }
          /*  if (lanuguageChanged) {
                List<SubcategoryModel> subMenuList = SuperLifeSecretPreferences.getInstance().getSubMenuList();
                for (SubcategoryModel subcategoryModel : subMenuList) {
                    upadateContent(subcategoryModel);
                }
                SuperLifeSecretPreferences.getInstance().setSubMenuList(subMenuList);
            }*/
            finish();
        }
    }

  /*  private void upadateContent(SubcategoryModel subcategoryModel) {
        switch (subcategoryModel.getType()) {
            case ConstantLib.TYPE_HOME:
                subcategoryModel.setTitle(conversionData.getHome());
                break;
            case ConstantLib.TYPE_NEWS:
                subcategoryModel.setTitle(conversionData.getNews_update());
                break;
            case ConstantLib.TYPE_EVENT:
                subcategoryModel.setTitle(conversionData.getEvent_activity());
                break;
            case ConstantLib.TYPE_LATEST:
                subcategoryModel.setTitle(conversionData.getLatest());
                break;
            case ConstantLib.TYPE_SUBMIT:
                subcategoryModel.setTitle(conversionData.getSubmit());
                break;
            case ConstantLib.TYPE_PERSONAL_CALENDAR:
                subcategoryModel.setTitle(conversionData.getPersonal_cal());
                break;
            case ConstantLib.TYPE_EVENT_CALENDAR:
                subcategoryModel.setTitle(conversionData.getEvent_cal());
                break;
            case ConstantLib.TYPE_STUDY_GROUP:
                subcategoryModel.setTitle(conversionData.getStudy_group());
                break;
            case ConstantLib.TYPE_ONSITE:
                subcategoryModel.setTitle(conversionData.getOnsite());
                break;
        }
    }*/

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

}
