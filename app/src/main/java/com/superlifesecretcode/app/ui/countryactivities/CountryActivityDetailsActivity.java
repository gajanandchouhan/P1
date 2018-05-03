package com.superlifesecretcode.app.ui.countryactivities;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.Spanned;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.superlifesecretcode.app.R;
import com.superlifesecretcode.app.data.model.country.CountryResponseData;
import com.superlifesecretcode.app.data.model.countryactivities.CounActivtyResponseData;
import com.superlifesecretcode.app.data.model.countryactivities.CountryActivityInfoModel;
import com.superlifesecretcode.app.data.model.language.LanguageResponseData;
import com.superlifesecretcode.app.data.model.userdetails.UserDetailResponseData;
import com.superlifesecretcode.app.data.persistance.SuperLifeSecretPreferences;
import com.superlifesecretcode.app.ui.base.BaseActivity;
import com.superlifesecretcode.app.ui.dailyactivities.interestedevent.InterestedEventCalendarActivity;
import com.superlifesecretcode.app.ui.picker.selectiondialog.SelectionListDialog;
import com.superlifesecretcode.app.util.AlarmUtility;
import com.superlifesecretcode.app.util.CommonUtils;
import com.superlifesecretcode.app.util.ConstantLib;
import com.superlifesecretcode.app.util.ImageLoadUtils;
import com.superlifesecretcode.app.util.PermissionConstant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class CountryActivityDetailsActivity extends BaseActivity implements View.OnClickListener, CountryActivitiesView {


    private UserDetailResponseData userData;
    private LanguageResponseData conversionData;
    private CountryAcivitiesPresenter presenter;
    private String id;
    ImageView imageViewShare, imageView, imageViewMap;
    RelativeLayout relativeLayout;
    private TextView textViewTitle1, textViewTime, textViewInterested,
            textViewAddr, textViewDesc;
    private TextView textViewCountry, textViewCityState,
            textViewName, textViewPhone, textViewEmail;
    private boolean fromCalendar;
    private String interested;
    private CountryActivityInfoModel countryActivityInfoModel;
    private String lat, lng;
    TextView textViewContactDetails;
    private String googlePackage = "com.google.android.apps.maps";
    private String wazePackage = "com.waze";
    SelectionListDialog dialog;
    List<String> directionApps = new ArrayList<>();

    @Override
    protected int getContentView() {
        return R.layout.activity_country_activity_details;
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void onBackPressed() {
        if (fromCalendar) {
            CommonUtils.startActivity(this, InterestedEventCalendarActivity.class);
        }
        super.onBackPressed();
    }

    private void showDirection() {
        if (lat != null && !lat.isEmpty() && lng != null && !lng.isEmpty()) {
//            Uri gmmIntentUri = Uri.parse("google.navigation:q=" + lat + "," + lng);
            String s = String.format(Locale.getDefault(), "geo:0,0?q=") + Uri.encode(String.format(Locale.getDefault(), "%s@%f,%f", "", Double.parseDouble(lat), Double.parseDouble(lng)), "UTF-8");
            Uri gmmIntentUri = Uri.parse(s);
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
//        mapIntent.setPackage("com.google.android.apps.maps");
            startActivity(mapIntent);
        }
    }

    @Override
    protected void initializeView() {
        imageView = findViewById(R.id.imageView);
        relativeLayout = findViewById(R.id.button_interested);
        textViewTitle1 = findViewById(R.id.textView_title_event);
        textViewTime = findViewById(R.id.textView_time);
        textViewInterested = findViewById(R.id.textview_interested);
        textViewAddr = findViewById(R.id.textView_addr);
        imageViewShare = findViewById(R.id.imageView_share);
        textViewDesc = findViewById(R.id.textView_desc);
        textViewCountry = findViewById(R.id.textView_country);
        textViewCityState = findViewById(R.id.textView_city_state);
        textViewName = findViewById(R.id.textView_name);
        textViewPhone = findViewById(R.id.textView_phone);
        textViewEmail = findViewById(R.id.textView_email);
        imageViewMap = findViewById(R.id.imageView_map);
        textViewContactDetails = findViewById(R.id.textView_contact_details);
        imageViewMap.setOnClickListener(this);
        userData = SuperLifeSecretPreferences.getInstance().getUserData();
        conversionData = SuperLifeSecretPreferences.getInstance().getConversionData();
        id = getIntent().getBundleExtra("bundle").getString("id");
        fromCalendar = getIntent().getBundleExtra("bundle").getBoolean("from_calendar");
        setUpToolbar();
        getDetails();
        if (conversionData != null) {
            textViewContactDetails.setText(conversionData.getContact_details());
        }
        if (CommonUtils.isPackageExisted(googlePackage, this)) {
            directionApps.add("Google Map");
        }
        if (CommonUtils.isPackageExisted(wazePackage, this)) {
            directionApps.add("Waze");
        }
    }

    private void getDetails() {
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + userData.getApi_token());
        HashMap<String, String> params = new HashMap<>();
        params.put("activity_id", id);
        presenter.getDetails(params, headers);
    }

    private void setUpToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        TextView textViewTitle = findViewById(R.id.textView_title);
        textViewTitle.setText(conversionData != null ? conversionData.getCountry_activities() : "Country Activities");
    }

    @Override
    protected void initializePresenter() {
        presenter = new CountryAcivitiesPresenter(this);
        presenter.setView(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imageView_map:
                showDirection();
                break;
        }
    }


    @Override
    public void setCountyAcivityData(CounActivtyResponseData data) {

    }

    @Override
    public void setCountryData(List<CountryResponseData> data) {

    }

    @Override
    public void setStateData(List<CountryResponseData> data) {

    }

    @Override
    public void onUpdateInteresed() {
        if (interested.equalsIgnoreCase("1")) {
            setAlarm(countryActivityInfoModel);
        } else {
            removeAlarm(countryActivityInfoModel);
        }
        if (fromCalendar) {
            InterestedEventCalendarActivity.UPDATED = true;
            onBackPressed();
        } else {
            CountryAcitvitiesActivity.UPDATED = true;
        }
        relativeLayout.setSelected(interested.equalsIgnoreCase("1"));
        countryActivityInfoModel.setUserIntrested(interested);
    }

    private void setAlarm(CountryActivityInfoModel eventsInfoModel) {
        AlarmUtility.getInstance(this).setAlarm(Integer.parseInt(eventsInfoModel.getActivity_id()), "RichestLifeReminder", "" + eventsInfoModel.getTitle(), CommonUtils.getTimeInMilis(eventsInfoModel.getActivity_date() + " " + eventsInfoModel.getActivity_time()) - 60 * 1000 * 30, false);
    }

    private void removeAlarm(CountryActivityInfoModel eventsInfoModel) {
        AlarmUtility.getInstance(this).removeAlarm(Integer.parseInt(eventsInfoModel.getActivity_id()));
    }

    @Override
    public void setActivtyDetails(CountryActivityInfoModel data) {
        setUpData(data);
    }

    @Override
    public void setCities(List<CountryResponseData> data) {

    }

    private void setUpData(final CountryActivityInfoModel countryActivityInfoModel) {
        if (countryActivityInfoModel != null) {
            this.countryActivityInfoModel = countryActivityInfoModel;
            textViewTitle1.setText(countryActivityInfoModel.getTitle());
           /* if (newsResponseModel.getEnd_date() != null && !newsResponseModel.getEnd_date().isEmpty()) {
                String startDateTime = CommonUtils.getformattedDateFromString(ConstantLib.INPUT_DATE_TIME_FORMATE, ConstantLib.OUTPUT_DATE_TIME_FORMATE, newsResponseModel.getAnnouncement_date() + " " + newsResponseModel.getAnnouncement_time());
                String endDateTime = CommonUtils.getformattedDateFromString(ConstantLib.INPUT_DATE_TIME_FORMATE, ConstantLib.OUTPUT_DATE_TIME_FORMATE, newsResponseModel.getEnd_date() + " " + newsResponseModel.getEnd_time());
                textViewTime.setText(String.format("%s-%s", startDateTime, endDateTime));

            } else {*//*
                textViewTime.setText(CommonUtils.getformattedDateFromString(ConstantLib.INPUT_DATE_TIME_FORMATE, ConstantLib.OUTPUT_DATE_TIME_FORMATE, newsResponseModel.getAnnouncement_date() + " " + newsResponseModel.getAnnouncement_time()));
            }*/
            textViewTime.setText(CommonUtils.getformattedDateFromString(ConstantLib.INPUT_DATE_TIME_FORMATE, ConstantLib.OUTPUT_DATE_TIME_FORMATE, countryActivityInfoModel.getActivity_date() + " " + countryActivityInfoModel.getActivity_time(), true, countryActivityInfoModel.getTimezone()));
            ImageLoadUtils.loadImage(countryActivityInfoModel.getImage(), imageView);
            textViewAddr.setText(countryActivityInfoModel.getVenue());
            textViewInterested.setText(conversionData.getInterested());
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                Spanned spanned = Html.fromHtml(countryActivityInfoModel.getDescription(), Html.FROM_HTML_MODE_LEGACY);
                textViewDesc.setText(spanned);
            } else {
                Spanned spanned = Html.fromHtml(countryActivityInfoModel.getDescription());
                textViewDesc.setText(spanned);
            }
            textViewName.setText(countryActivityInfoModel.getContact_name());
            textViewCountry.setText(String.format("(%s)", countryActivityInfoModel.getCountryName()));
            textViewCityState.setText(String.format("%s(%s)", countryActivityInfoModel.getCityName(), countryActivityInfoModel.getStateName()));
            textViewEmail.setText(countryActivityInfoModel.getContact_email());
            textViewPhone.setText(countryActivityInfoModel.getContact_no());
            relativeLayout.setSelected(countryActivityInfoModel.getUserIntrested() != null && countryActivityInfoModel.getUserIntrested().equalsIgnoreCase("1"));
            relativeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String userIntrested = countryActivityInfoModel.getUserIntrested();
                    if (userIntrested != null && userIntrested.equalsIgnoreCase("1")) {
                        updateEventInterest("0", countryActivityInfoModel.getActivity_id());
                    } else {
                        updateEventInterest("1", countryActivityInfoModel.getActivity_id());
                    }
                }
            });
            String url = "http://maps.google.com/maps/api/staticmap?markers=color:blue%7C" + countryActivityInfoModel.getLatitude() + "," + countryActivityInfoModel.getLongitude() + "&zoom=13&size=640x116&sensor=true";
            ImageLoadUtils.loadImage(url, imageViewMap);
            imageViewShare.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    shareImageAndText(countryActivityInfoModel.getImage(), Html.fromHtml(countryActivityInfoModel.getDescription()).toString());
                }
            });
            lat = countryActivityInfoModel.getLatitude();
            lng = countryActivityInfoModel.getLongitude();
        }
    }

    public void updateEventInterest(String interested, String id) {
        this.interested = interested;
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + userData.getApi_token());
        HashMap<String, String> params = new HashMap<>();
        params.put("activity_id", id);
        params.put("interest", interested);
        params.put("user_id", userData.getUser_id());
        presenter.makeInterested(params, headers);
    }


    public void shareImageAndText(String image, String text) {
        if (CommonUtils.hasPermissions(this, PermissionConstant.PERMISSION_PROFILE)) {
            CommonUtils.shareImageWithContent(image, text, this);
        } else {
            ActivityCompat.requestPermissions(this, PermissionConstant.PERMISSION_PROFILE, PermissionConstant.CODE_PROFILE);
        }

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PermissionConstant.CODE_PROFILE) {
            for (int grantResult : grantResults) {
                if (grantResult != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
            }
        }
    }

    private void showDirectionPopup() {
        if (directionApps != null && directionApps.size() > 0) {
            dialog = new SelectionListDialog(this, directionApps, new SelectionListDialog.SelectedListner<String>() {
                @Override
                public void onSelected(int position, String object) {

                }
            });
        }

    }
}
