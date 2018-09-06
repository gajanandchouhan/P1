package com.superlifesecretcode.app.ui.mycountryactivities.addcountryactivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.facebook.internal.Utility;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLng;
import com.kbeanie.multipicker.api.ImagePicker;
import com.kbeanie.multipicker.api.Picker;
import com.kbeanie.multipicker.api.callbacks.ImagePickerCallback;
import com.kbeanie.multipicker.api.entity.ChosenImage;
import com.superlifesecretcode.app.R;
import com.superlifesecretcode.app.data.model.WeekDayModel;
import com.superlifesecretcode.app.data.model.country.CountryResponseData;
import com.superlifesecretcode.app.data.model.language.LanguageResponseData;
import com.superlifesecretcode.app.data.model.myannoucement.MyAnnouncementResponseData;
import com.superlifesecretcode.app.data.model.userdetails.UserDetailResponseData;
import com.superlifesecretcode.app.data.persistance.SuperLifeSecretPreferences;
import com.superlifesecretcode.app.ui.base.BaseActivity;
import com.superlifesecretcode.app.ui.myannouncement.MyAnnouncementActivity;
import com.superlifesecretcode.app.ui.myannouncement.addannouncement.HorizontalImageAapter;
import com.superlifesecretcode.app.ui.picker.CountryStatePicker;
import com.superlifesecretcode.app.ui.picker.DropDownWindow;
import com.superlifesecretcode.app.ui.picker.selectiondialog.SelectionListDialog;
import com.superlifesecretcode.app.util.CommonUtils;
import com.superlifesecretcode.app.util.ConstantLib;
import com.superlifesecretcode.app.util.PermissionConstant;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class AddCountryActivityActivity extends BaseActivity implements AddCountryActivityView, View.OnClickListener {


    private LanguageResponseData conversionData;
    private UserDetailResponseData userData;
    private CountryStatePicker countryStatePicker;
    private List<CountryResponseData> countryList;

    private TextView textViewViewCountry, textViewAnnounType, textViewPickImage;
    private TextView textViewState, textViewCity, textViewDay, textViewLocation;
    private TextView textViewStartDate, textViewStartTime;

    private EditText editTextName, editTextCCMail, editTextDesc, editTextVenue, editTextContactName, edittextContactNumber, editTextContatEmail;
    private AddCountryActivityPresenter presenter;

    List<String> announceTypeList;
    private List<String> imageList;
    private HorizontalImageAapter submitAapter;
    private RecyclerView recyclerView;
    private ImagePicker imagePicker;
    private String fromDate;
    private String startTime;
    private Button button;
    private String countryId;
    private String announcmentType;
    private List<Object> remoteImageList;

    MyAnnouncementResponseData data;
    private int position;
    private SelectionListDialog dialog;
    private List<WeekDayModel> weekDayModelList;
    private String day;
    private String stateId;
    private String city;

    int PLACE_PICKER_REQUEST = 1;
    private String lat;
    private String lon;

    @Override
    protected int getContentView() {
        return R.layout.activity_add_country_activities;
    }

    @Override
    protected void initializeView() {
        conversionData = SuperLifeSecretPreferences.getInstance().getConversionData();
        userData = SuperLifeSecretPreferences.getInstance().getUserData();
        Bundle bundle = getIntent().getBundleExtra("bundle");
        if (bundle != null && bundle.getSerializable("data") != null) {
            data = (MyAnnouncementResponseData) bundle.getSerializable("data");
        }
        setUpToolbar();
        textViewViewCountry = findViewById(R.id.text_view_country);
        textViewAnnounType = findViewById(R.id.text_view_announcment_type);
        textViewPickImage = findViewById(R.id.text_view_pick_image);
        textViewState = findViewById(R.id.text_view_state);
        textViewCity = findViewById(R.id.text_view_city);
        textViewDay = findViewById(R.id.text_view_day);
        textViewLocation = findViewById(R.id.text_view_location);
        recyclerView = findViewById(R.id.recycler_view);
        textViewStartDate = findViewById(R.id.text_view_start_date);
        textViewStartTime = findViewById(R.id.text_view_start_time);
        editTextName = findViewById(R.id.input_name);
        editTextCCMail = findViewById(R.id.input_ccmail);
        editTextDesc = findViewById(R.id.input_desc);
        editTextVenue = findViewById(R.id.input_venue);
        editTextContactName = findViewById(R.id.input_contact_name);
        edittextContactNumber = findViewById(R.id.input_contact_no);
        editTextContatEmail = findViewById(R.id.input_contact_email);
        button = findViewById(R.id.button_add);
        button.setOnClickListener(this);
        textViewViewCountry.setOnClickListener(this);
        textViewPickImage.setOnClickListener(this);
        textViewAnnounType.setOnClickListener(this);
        textViewStartDate.setOnClickListener(this);
        textViewStartTime.setOnClickListener(this);
        textViewState.setOnClickListener(this);
        textViewCity.setOnClickListener(this);
        textViewDay.setOnClickListener(this);
        textViewLocation.setOnClickListener(this);
        announceTypeList = new ArrayList<>();
        announceTypeList.add(conversionData.getStudy_group());
        announceTypeList.add(conversionData.getOnsite());
        imageList = new ArrayList<>();
        remoteImageList = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        submitAapter = new HorizontalImageAapter(remoteImageList, this);
        recyclerView.setAdapter(submitAapter);
        announcmentType = "1";
        textViewAnnounType.setText(conversionData.getStudy_group());
        setWeekDayList();
        if (data != null) {
            setUpUi();
        }
    }

    private void setWeekDayList() {
        weekDayModelList = new ArrayList<>();
        weekDayModelList.add(new WeekDayModel(conversionData.getMon(), "2"));
        weekDayModelList.add(new WeekDayModel(conversionData.getTue(), "3"));
        weekDayModelList.add(new WeekDayModel(conversionData.getWed(), "4"));
        weekDayModelList.add(new WeekDayModel(conversionData.getThu(), "5"));
        weekDayModelList.add(new WeekDayModel(conversionData.getFri(), "6"));
        weekDayModelList.add(new WeekDayModel(conversionData.getSat(), "7"));
        weekDayModelList.add(new WeekDayModel(conversionData.getSun(), "1"));
    }

    private void setUpUi() {
        button.setText("Update Announcement");
        announcmentType = data.getAnnouncement_type();
        countryId = data.getCountry();
        fromDate = data.getStart_date();
        startTime = data.getStart_time();
        remoteImageList.addAll(data.getAnnouncement_images());
        if (remoteImageList.size() > 0) {
            recyclerView.setVisibility(View.VISIBLE);
        }
        submitAapter.notifyDataSetChanged();
        textViewViewCountry.setText(data.getCountryName());
        textViewAnnounType.setText(announcmentType != null && announcmentType.equals("1") ? conversionData.getEvent_activity() : conversionData.getNews_update());
        editTextName.append(data.getAnnouncement_name());
        editTextDesc.append(data.getAnnouncement_description());
        if (announcmentType != null && announcmentType.equals("1")) {
            editTextVenue.setText(data.getVenue());
            textViewStartDate.setText(CommonUtils.getformattedDateFromString(ConstantLib.INPUT_DATE_ONLY_FORMATE, ConstantLib.OUTPUT_DATE_FORMATE, fromDate, false, null));
        }

    }

    @Override
    protected void initializePresenter() {
        presenter = new AddCountryActivityPresenter(this);
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
            textViewTitle.setText(conversionData.getCountry_activities());
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

    @Override
    public void onAdded() {
        MyAnnouncementActivity.shouldRefresh = true;
        onBackPressed();
    }

    @Override
    public void imageDelete() {
        remoteImageList.remove(position);
        submitAapter.notifyItemRemoved(position);
        MyAnnouncementActivity.shouldRefresh = true;
    }

    private void showCountryPicker() {
        countryStatePicker = new CountryStatePicker(this, new CountryStatePicker.PickerListner() {
            @Override
            public void onPick(CountryResponseData country) {
                if (!country.getId().equalsIgnoreCase(countryId)) {
                    stateId = null;
                    city = "";
                    textViewCity.setText("");
                    textViewState.setText("");
                }
                textViewViewCountry.setText(country.getName());
                countryStatePicker.dismiss();
                countryId = country.getId();
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
            case R.id.text_view_state:
                if (countryId == null || countryId.isEmpty()) {
                    CommonUtils.showSnakeBar(this, conversionData.getSelect_country());
                    return;
                }
                getState();
                break;
            case R.id.text_view_city:
                if (stateId == null || stateId.isEmpty()) {
                    CommonUtils.showSnakeBar(this, conversionData.getSelect_state());
                    return;
                }
                getCity();
                break;
            case R.id.text_view_day:
                showWeekDayPicker();
                break;
            case R.id.text_view_location:
                if (CommonUtils.hasPermissions(this, PermissionConstant.PERMISSION_LOCATION)) {
                    openPlacePicker();
                } else {
                    ActivityCompat.requestPermissions(this, PermissionConstant.PERMISSION_LOCATION, PermissionConstant.CODE_LOCATION);
                }

                break;
            case R.id.text_view_announcment_type:
                showTypePicker();
                break;
            case R.id.text_view_pick_image:
                if (CommonUtils.hasPermissions(this, PermissionConstant.PERMISSION_PROFILE)) {
                    pickImage();
                } else {
                    ActivityCompat.requestPermissions(this, PermissionConstant.PERMISSION_PROFILE, PermissionConstant.CODE_PROFILE);
                }
                break;

            case R.id.text_view_start_date:
                showDatePicker(textViewStartDate, 1, System.currentTimeMillis() - 1000);
                break;

            case R.id.text_view_start_time:
                showTimePicker(1);
                break;


            case R.id.button_add:
                addContryActivity();
                break;

        }
    }


    private void showTypePicker() {
        DropDownWindow.show(this, textViewAnnounType, announceTypeList, new DropDownWindow.SelectedListner() {
            @Override
            public void onSelected(String value, int position) {
                textViewAnnounType.setText(value);
                announcmentType = String.valueOf(position + 1);
            }
        });
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
        if (requestCode == PermissionConstant.CODE_LOCATION) {
            for (int grantResult : grantResults) {
                if (grantResult != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
            }
            openPlacePicker();
        }
    }

    private void pickImage() {
        if (imagePicker == null) {
            imagePicker = new ImagePicker(this);
        }
        imagePicker.setImagePickerCallback(new ImagePickerCallback() {
            @Override
            public void onImagesChosen(List<ChosenImage> list) {
                setPathArray(list);
                for (ChosenImage chosenImage : list) {
                    long size = chosenImage.getSize();
                    Log.v("SIZE", size + "");
                }

            }

            @Override
            public void onError(String s) {

            }
        });
        imagePicker.allowMultiple(); // Default is false
        imagePicker.shouldGenerateMetadata(true); // Default is true
        imagePicker.shouldGenerateThumbnails(false); // Default is true
        imagePicker.pickImage();
    }

    private void setPathArray(List<ChosenImage> uris) {
        for (ChosenImage uri : uris) {
            if (uri != null) {
                String path = uri.getOriginalPath();
                if (path != null) {
                    imageList.add(path);
                    remoteImageList.add(path);
                }
            }
        }
        if (remoteImageList != null && remoteImageList.size() > 0) {
            submitAapter.notifyDataSetChanged();
            recyclerView.setVisibility(View.VISIBLE);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Picker.PICK_IMAGE_DEVICE) {
            imagePicker.submit(data);
        }
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(this, data);
                if (place != null) {
                    lat = String.valueOf(place.getLatLng().latitude);
                    lon = String.valueOf(place.getLatLng().longitude);
                }

            }
        }

    }

    public void hideReccylerView() {
        recyclerView.setVisibility(View.GONE);
    }

    private void showDatePicker(final TextView textView, final int type, long minDate) {
        CommonUtils.showDatePicker(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                if (type == 1) {
                    fromDate = CommonUtils.getAppendedDate(i, i1, i2);
                }
                textView.setText(CommonUtils.getFromatttedDate(i, i1, i2));
            }
        }, minDate);
    }

    private void showTimePicker(final int type) {
        CommonUtils.showTimePicker(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                String hour = String.format(Locale.getDefault(), "%02d", selectedHour);
                String minute = String.format(Locale.getDefault(), "%02d", selectedMinute);
                String t = String.format("%s:%s", hour, minute);
                if (type == 1) {
                    textViewStartTime.setText(CommonUtils.getformattedDateFromString("HH:mm", "hh:mm a", t, false, null));
                    startTime = CommonUtils.getformattedDateFromString("HH:mm", "hh:mm a", t, false, null);
                }

            }
        });
    }


    private void addContryActivity() {
        String name = editTextName.getText().toString().trim();
        String desc = editTextDesc.getText().toString().trim();
        String venue = editTextVenue.getText().toString().trim();
        String contactName = editTextContactName.getText().toString().trim();
        String contactNo = edittextContactNumber.getText().toString().trim();
        String contanctEmail = editTextContatEmail.getText().toString().trim();
        if (announcmentType == null || announcmentType.isEmpty()) {
            CommonUtils.showSnakeBar(this, "Please select type");
            return;
        }

        if (name.isEmpty()) {
            CommonUtils.showSnakeBar(this, "Please enter event title");
            return;
        }
        if (desc.isEmpty()) {
            CommonUtils.showSnakeBar(this, "Please enter description");
            return;
        }
        if (!(remoteImageList != null && remoteImageList.size() > 0)) {
            if (imageList == null || imageList.isEmpty()) {
                CommonUtils.showSnakeBar(this, "Please select at least one image");
                return;
            }
        }

        if (remoteImageList.size() > 5) {
            CommonUtils.showSnakeBar(this, "You can upload maximum 5 images");
            return;
        }
        if (contactName.isEmpty()) {
            CommonUtils.showSnakeBar(this, "Please enter contact person name");
            return;
        }
        if (contactNo.isEmpty()) {
            CommonUtils.showSnakeBar(this, "Please enter contact person number");
            return;
        }
        if (!contanctEmail.isEmpty()) {
            if (!CommonUtils.isValidEmail(contanctEmail)) {
                CommonUtils.showSnakeBar(this, conversionData.getEnter_valid_email());
                return;
            }
        }
        if (countryId == null || countryId.isEmpty()) {
            CommonUtils.showSnakeBar(this, conversionData.getSelect_country());
            return;
        }

        if (stateId == null || stateId.isEmpty()) {
            CommonUtils.showSnakeBar(this, conversionData.getSelect_state());
            return;
        }
        if (city == null || city.isEmpty()) {
            CommonUtils.showSnakeBar(this, conversionData.getSelect_city());
            return;
        }
        if (venue.isEmpty()) {
            CommonUtils.showSnakeBar(this, "Please enter venue details");
            return;
        }
        if (fromDate == null || fromDate.isEmpty()) {
            CommonUtils.showSnakeBar(this, conversionData.getSelect_date());
            return;
        }
        if (startTime == null || startTime.isEmpty()) {
            CommonUtils.showSnakeBar(this, conversionData.getSelect_time());
            return;
        }
        if (day == null || day.isEmpty()) {
            CommonUtils.showSnakeBar(this, "Please select day");
            return;
        }
        if (lat == null || lat.isEmpty() || lat.equals("0.0") | lon == null || lon.isEmpty() || lon.equals("0.0")) {
            CommonUtils.showSnakeBar(this, "Please select map location");
            return;
        }
        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM);
        builder.addFormDataPart("activity_type", announcmentType);
        builder.addFormDataPart("activity_title", name);
        builder.addFormDataPart("activity_discription", desc);
        builder.addFormDataPart("activity_contact_number", contactNo);
        builder.addFormDataPart("activity_contact_name", contactName);
        builder.addFormDataPart("activity_contact_email", contanctEmail);
        builder.addFormDataPart("activity_country", countryId);
        builder.addFormDataPart("activity_id", data != null ? data.getAnnouncement_id() : "");
        builder.addFormDataPart("activity_date", fromDate);
        builder.addFormDataPart("activity_time", startTime);
        builder.addFormDataPart("activity_venue", venue);
        builder.addFormDataPart("activity_state", stateId);
        builder.addFormDataPart("activity_city", city);
        builder.addFormDataPart("activity_day", day);
        builder.addFormDataPart("activity_location_lat", lat);
        builder.addFormDataPart("activity_location_long", lon);

        for (int i = 0; i < imageList.size(); i++) {
            try {
                if (imageList.get(i) != null) {
                    File file = new File(imageList.get(i));
                    RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                    builder.addFormDataPart("activity_images[]", file.getName(), requestBody);
                }
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }
        }
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + userData.getApi_token());
        RequestBody finalRequestBody = builder.build();
        presenter.addCountryActivity(finalRequestBody, headers);
    }

    public void deleteImage(int position) {
        this.position = position;
        Object o = remoteImageList.get(position);
        if (o instanceof MyAnnouncementResponseData.ImageData) {
            MyAnnouncementResponseData.ImageData image = (MyAnnouncementResponseData.ImageData) o;
            Map<String, String> headers = new HashMap<>();
            headers.put("Authorization", "Bearer " + userData.getApi_token());
            HashMap<String, String> params = new HashMap<>();
            params.put("id", image.getId());
            presenter.deleleteCountryActivityImage(params, headers);

        }
    }

    private void showWeekDayPicker() {
        if (dialog == null) {
            dialog = new SelectionListDialog(this, weekDayModelList, new SelectionListDialog.SelectedListner<WeekDayModel>() {
                @Override
                public void onSelected(int position, WeekDayModel object) {
                    textViewDay.setText(object.getDay());
                    day = object.getIndex();
                }
            });
        }
        dialog.show();
    }

    private void getState() {
        HashMap<String, String> body = new HashMap<>();
        body.put("country_id", countryId);
        presenter.getStates(body);
    }


    private void getCity() {
        HashMap<String, String> map = new HashMap();
        map.put("state_id", stateId);
        presenter.getCities(map);
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
    public void setStateData(List<CountryResponseData> data) {
        countryStatePicker = new CountryStatePicker(this, new CountryStatePicker.PickerListner() {
            @Override
            public void onPick(CountryResponseData country) {
                textViewState.setText(country.getName());
                if (!country.getId().equalsIgnoreCase(city)) {
                    city = "";
                    textViewCity.setText("");
                }
                countryStatePicker.dismiss();
                stateId = country.getId();
            }
        }, data);
        countryStatePicker.show();
    }

    private void openPlacePicker() {
        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
        try {
            startActivityForResult(builder.build(this), PLACE_PICKER_REQUEST);
        } catch (GooglePlayServicesRepairableException e) {
            e.printStackTrace();
        } catch (GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        }
    }


}
