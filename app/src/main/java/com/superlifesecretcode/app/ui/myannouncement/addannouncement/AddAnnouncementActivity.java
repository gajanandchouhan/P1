package com.superlifesecretcode.app.ui.myannouncement.addannouncement;

import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.superlifesecretcode.app.R;
import com.superlifesecretcode.app.data.model.country.CountryResponseData;
import com.superlifesecretcode.app.data.model.language.LanguageResponseData;
import com.superlifesecretcode.app.data.model.userdetails.UserDetailResponseData;
import com.superlifesecretcode.app.data.persistance.SuperLifeSecretPreferences;
import com.superlifesecretcode.app.ui.base.BaseActivity;
import com.superlifesecretcode.app.ui.picker.CountryStatePicker;
import com.superlifesecretcode.app.ui.picker.DropDownWindow;

import java.util.ArrayList;
import java.util.List;

public class AddAnnouncementActivity extends BaseActivity implements AddAnnouncementView, View.OnClickListener {


    private LanguageResponseData conversionData;
    private UserDetailResponseData userData;
    private CountryStatePicker countryStatePicker;
    private List<CountryResponseData> countryList;

    private TextView textViewViewCountry, textViewAnnounType;
    private AddAnnouncementPresenter presenter;

    List<String> announceTypeList;

    @Override
    protected int getContentView() {
        return R.layout.activity_add_announcement;
    }

    @Override
    protected void initializeView() {
        conversionData = SuperLifeSecretPreferences.getInstance().getConversionData();
        userData = SuperLifeSecretPreferences.getInstance().getUserData();
        setUpToolbar();
        textViewViewCountry = findViewById(R.id.text_view_country);
        textViewAnnounType = findViewById(R.id.text_view_announcment_type);
        textViewViewCountry.setOnClickListener(this);
        textViewAnnounType.setOnClickListener(this);
        announceTypeList = new ArrayList<>();
        announceTypeList.add(conversionData.getNews_update());
        announceTypeList.add(conversionData.getEvent_activity());
    }

    @Override
    protected void initializePresenter() {
        presenter = new AddAnnouncementPresenter(this);
        presenter.setView(this);

    }

    private void setUpToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        TextView textViewTitle = findViewById(R.id.textView_title);
        if (conversionData != null)
            textViewTitle.setText(conversionData.getAnnouncement());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setCountryData(List<CountryResponseData> data) {

        if (data != null && data.size() > 0) {
            countryList = data;
            showCountryPicker();
        }

    }

    private void showCountryPicker() {
        countryStatePicker = new CountryStatePicker(this, new CountryStatePicker.PickerListner() {
            @Override
            public void onPick(CountryResponseData country) {
                textViewViewCountry.setText(country.getName());
                countryStatePicker.dismiss();
            }
        }, countryList);
        countryStatePicker.show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.text_view_country:
                if (countryList != null && countryList.size() > 0) {
                    showCountryPicker();
                } else {
                    presenter.getCountry();
                }
                break;
            case R.id.text_view_announcment_type:
                showTypePicker();
                break;
        }
    }

    private void showTypePicker() {
        DropDownWindow.show(this, textViewAnnounType, announceTypeList, new DropDownWindow.SelectedListner() {
            @Override
            public void onSelected(String value, int position) {
                textViewAnnounType.setText(value);
            }
        });
    }
}
