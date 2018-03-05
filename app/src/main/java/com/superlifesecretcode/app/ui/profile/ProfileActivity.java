package com.superlifesecretcode.app.ui.profile;


import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.superlifesecretcode.app.R;
import com.superlifesecretcode.app.data.model.language.LanguageResponseData;
import com.superlifesecretcode.app.data.model.userdetails.UserDetailResponseData;
import com.superlifesecretcode.app.data.persistance.SuperLifeSecretPreferences;
import com.superlifesecretcode.app.ui.base.BaseActivity;
import com.superlifesecretcode.app.ui.picker.DropDownWindow;
import com.superlifesecretcode.app.util.ConstantLib;
import com.superlifesecretcode.app.util.ImageLoadUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ProfileActivity extends BaseActivity implements View.OnClickListener, ProfileView {


    private ImageView imageViewProfile;
    private EditText editTextName;
    private EditText editTextMobileNumber;
    private TextView textViewGender, textViewCountry, textViewState, textViewLanguage, textViewName;
    UserDetailResponseData userDetailResponseData;
    ImageView imageViewUser, imageViewFlag;
    private LanguageResponseData conversionData;
    private ArrayList<String> genderList;
    private List<String> langauageList;
    private String currentLanguag;
    private String languageId;
    private ProfilePresenter presenter;
    private TextView textViewNameLabel, textViewGenderLabel, textViewMobileLabel,
            textViewCountryLabel, textViewStateLabel, textViewLanguageLabel;

    @Override
    protected int getContentView() {
        overridePendingTransition(R.anim.activity_open_translate, R.anim.activity_close_scale);
        return R.layout.activity_profile;
    }

    @Override
    protected void initializeView() {
        userDetailResponseData = SuperLifeSecretPreferences.getInstance().getUserData();
        conversionData = SuperLifeSecretPreferences.getInstance().getConversionData();
        setUpToolbar("Profile");
        editTextName = findViewById(R.id.edi_text_name);
        editTextMobileNumber = findViewById(R.id.edi_text_phone);
        textViewGender = findViewById(R.id.textView_gender);
        textViewCountry = findViewById(R.id.textView_country);
        textViewState = findViewById(R.id.textView_state);
        textViewLanguage = findViewById(R.id.textView_language);
        textViewName = findViewById(R.id.textView_name);
        imageViewUser = findViewById(R.id.imageView_user);
        imageViewFlag = findViewById(R.id.imageView_flag);

        textViewNameLabel = findViewById(R.id.textView_name_label);
        textViewMobileLabel = findViewById(R.id.textView_mobile_label);
        textViewGenderLabel = findViewById(R.id.textView_gender_label);
        textViewCountryLabel = findViewById(R.id.textView_country_label);
        textViewStateLabel = findViewById(R.id.textView_state_label);
        textViewLanguageLabel = findViewById(R.id.textView_language_label);
        textViewGender.setOnClickListener(this);
        textViewLanguage.setOnClickListener(this);
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
    }

    private void setUpLocalConversion() {
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
    }

    private void setUpUi() {
        if (userDetailResponseData != null) {
            String countrCode = userDetailResponseData.getCountry_code().toLowerCase();
            if (!countrCode.isEmpty()) {
                int id = getResources().getIdentifier("flag_" + countrCode, "drawable", getPackageName());
                imageViewFlag.setImageResource(id);
            }
            editTextName.setText(userDetailResponseData.getUsername());
            textViewName.setText(userDetailResponseData.getUsername());
            editTextMobileNumber.setText(userDetailResponseData.getMobile());
            textViewCountry.setText(userDetailResponseData.getCountryName());
            textViewState.setText(userDetailResponseData.getStateName());
            textViewGender.setText(userDetailResponseData.getGender());
            currentLanguag = getLanguage(SuperLifeSecretPreferences.getInstance().getLanguageId());
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
        imageViewProfile.setImageResource(android.R.drawable.ic_menu_edit);
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
        overridePendingTransition(R.anim.activity_open_scale, R.anim.activity_close_translate);
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
        DropDownWindow.show(this, textViewGender, langauageList, new DropDownWindow.SelectedListner() {
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
            case R.id.textView_language:
                showLanguageSelection();
                break;
        }
    }

    @Override
    public void onProfileUpdated(UserDetailResponseData data) {

    }


    @Override
    public void setConversionContent(LanguageResponseData data) {
        conversionData = data;
        SuperLifeSecretPreferences.getInstance().setConversionData(data);
        setUpLocalConversion();
    }
}
