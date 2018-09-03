package com.superlifesecretcode.app.ui.myannouncement.addannouncement;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
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

import com.kbeanie.multipicker.api.ImagePicker;
import com.kbeanie.multipicker.api.Picker;
import com.kbeanie.multipicker.api.callbacks.ImagePickerCallback;
import com.kbeanie.multipicker.api.entity.ChosenImage;
import com.superlifesecretcode.app.R;
import com.superlifesecretcode.app.data.model.country.CountryResponseData;
import com.superlifesecretcode.app.data.model.language.LanguageResponseData;
import com.superlifesecretcode.app.data.model.userdetails.UserDetailResponseData;
import com.superlifesecretcode.app.data.persistance.SuperLifeSecretPreferences;
import com.superlifesecretcode.app.ui.base.BaseActivity;
import com.superlifesecretcode.app.ui.myannouncement.MyAnnouncementActivity;
import com.superlifesecretcode.app.ui.picker.CountryStatePicker;
import com.superlifesecretcode.app.ui.picker.DropDownWindow;
import com.superlifesecretcode.app.util.CommonUtils;
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

public class AddAnnouncementActivity extends BaseActivity implements AddAnnouncementView, View.OnClickListener {


    private LanguageResponseData conversionData;
    private UserDetailResponseData userData;
    private CountryStatePicker countryStatePicker;
    private List<CountryResponseData> countryList;

    private TextView textViewViewCountry, textViewAnnounType, textViewPickImage;
    private TextView textViewStartDate, textViewEndDate, textViewStartTime, textViewEndTime;

    private EditText editTextName, editTextCCMail, editTextDesc, editTextVenue;
    private AddAnnouncementPresenter presenter;

    List<String> announceTypeList;
    private List<String> imageList;
    private HorizontalImageAapter submitAapter;
    private RecyclerView recyclerView;
    private ImagePicker imagePicker;
    private String toDate;
    private String fromDate;
    private String startTime;
    private String endTime;
    private Button button;
    private String countryId;
    private String announcmentType;

    TextInputLayout venueInputLayout;


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
        textViewPickImage = findViewById(R.id.text_view_pick_image);
        recyclerView = findViewById(R.id.recycler_view);
        textViewStartDate = findViewById(R.id.text_view_start_date);
        textViewEndDate = findViewById(R.id.text_view_end_date);
        textViewStartTime = findViewById(R.id.text_view_start_time);
        textViewEndTime = findViewById(R.id.text_view_end_time);
        editTextName = findViewById(R.id.input_name);
        editTextCCMail = findViewById(R.id.input_ccmail);
        editTextDesc = findViewById(R.id.input_desc);
        editTextVenue = findViewById(R.id.input_venue);
        venueInputLayout = findViewById(R.id.input_layout_venue);
        button = findViewById(R.id.button_add);
        button.setOnClickListener(this);
        textViewViewCountry.setOnClickListener(this);
        textViewPickImage.setOnClickListener(this);
        textViewAnnounType.setOnClickListener(this);
        textViewStartDate.setOnClickListener(this);
        textViewEndDate.setOnClickListener(this);
        textViewStartTime.setOnClickListener(this);
        textViewEndTime.setOnClickListener(this);
        announceTypeList = new ArrayList<>();
        announceTypeList.add(conversionData.getEvent_activity());
        announceTypeList.add(conversionData.getNews_update());
        imageList = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        submitAapter = new HorizontalImageAapter(imageList, this);
        recyclerView.setAdapter(submitAapter);
        announcmentType = "1";
        textViewAnnounType.setText(conversionData.getEvent_activity());
        setUpVisibilityOfFields();
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

    @Override
    public void onAdded() {
        MyAnnouncementActivity.shouldRefresh=true;
        onBackPressed();
    }

    private void showCountryPicker() {
        countryStatePicker = new CountryStatePicker(this, new CountryStatePicker.PickerListner() {
            @Override
            public void onPick(CountryResponseData country) {
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
                if (toDate != null) {
                    textViewEndDate.setText("");
                    toDate = null;
                }
                showDatePicker(textViewStartDate, 1, fromDate == null ? System.currentTimeMillis() - 1000 : CommonUtils.getDateInMilis(fromDate));
                break;
            case R.id.text_view_end_date:
                if (fromDate == null) {
                    CommonUtils.showToast(this, "Please select start date.");
                    return;
                }
                showDatePicker(textViewEndDate, 2, CommonUtils.getDateInMilis(fromDate));
                break;
            case R.id.text_view_start_time:
                showTimePicker(1);
                break;
            case R.id.text_view_end_time:
                showTimePicker(2);
                break;

            case R.id.button_add:
                addAnnouncement();
                break;

        }
    }


    private void showTypePicker() {
        DropDownWindow.show(this, textViewAnnounType, announceTypeList, new DropDownWindow.SelectedListner() {
            @Override
            public void onSelected(String value, int position) {
                textViewAnnounType.setText(value);
                announcmentType = String.valueOf(position + 1);
                setUpVisibilityOfFields();
            }
        });
    }

    private void setUpVisibilityOfFields() {
        if (announcmentType != null && announcmentType.equals("1")) {
            venueInputLayout.setVisibility(View.VISIBLE);
            textViewStartDate.setVisibility(View.VISIBLE);
            textViewEndDate.setVisibility(View.VISIBLE);
            textViewStartTime.setVisibility(View.VISIBLE);
            textViewEndTime.setVisibility(View.VISIBLE);
        } else {
            venueInputLayout.setVisibility(View.GONE);
            textViewStartDate.setVisibility(View.GONE);
            textViewEndDate.setVisibility(View.GONE);
            textViewStartTime.setVisibility(View.GONE);
            textViewEndTime.setVisibility(View.GONE);
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
            pickImage();
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
                }
            }
        }
        if (imageList != null) {
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
                } else {
                    toDate = CommonUtils.getAppendedDate(i, i1, i2);
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
                } else {
                    textViewEndTime.setText(CommonUtils.getformattedDateFromString("HH:mm", "hh:mm a", t, false, null));
                    endTime = CommonUtils.getformattedDateFromString("HH:mm", "hh:mm a", t, false, null);
                }


            }
        });
    }


    private void addAnnouncement() {
        String name = editTextName.getText().toString().trim();
        String ccEmail = editTextCCMail.getText().toString().trim();
        String desc = editTextDesc.getText().toString().trim();
        String venue = editTextVenue.getText().toString().trim();

        if (countryId == null || countryId.isEmpty()) {
            CommonUtils.showSnakeBar(this, conversionData.getSelect_country());
            return;
        }

        if (announcmentType == null || announcmentType.isEmpty()) {
            CommonUtils.showSnakeBar(this, "Please select type");
            return;
        }
        if (name.isEmpty()) {
            CommonUtils.showSnakeBar(this, "Please enter event name");
            return;
        }
        if (!ccEmail.isEmpty() && !CommonUtils.isValidEmail(ccEmail)) {
            CommonUtils.showSnakeBar(this, conversionData.getEnter_valid_email());
            return;
        }
        if (desc.isEmpty()) {
            CommonUtils.showSnakeBar(this, "Please enter description");
            return;
        }
        if (imageList == null || imageList.isEmpty()) {
            CommonUtils.showSnakeBar(this, "Please select at least one image");
            return;
        }
        if (imageList.size() > 5) {
            CommonUtils.showSnakeBar(this, "You can upload maximum 5 images");
            return;
        }
        if (imageList.size() > 5) {
            CommonUtils.showSnakeBar(this, "You can upload maximum 5 images");
            return;
        }
        if (venue.isEmpty()) {
            CommonUtils.showSnakeBar(this, "Please enter venue details");
            return;
        }
        if (announcmentType.equals("1")) {
            if (fromDate == null || fromDate.isEmpty()) {
                CommonUtils.showSnakeBar(this, "Please select start date.");
                return;
            }
            if (startTime == null || startTime.isEmpty()) {
                CommonUtils.showSnakeBar(this, "Please select start time.");
                return;
            }

            if (toDate != null && !toDate.isEmpty()) {
                if (endTime == null || endTime.isEmpty()) {
                    CommonUtils.showSnakeBar(this, "Please select end time.");
                    return;
                }

            }

        }

        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM);
        builder.addFormDataPart("announcement_type", announcmentType);
        builder.addFormDataPart("announcement_name", name);
        builder.addFormDataPart("announcement_description", desc);
        builder.addFormDataPart("announcement_country", countryId);
        if (announcmentType.equals("1")) {
            builder.addFormDataPart("announcement_date", fromDate);
            builder.addFormDataPart("announcement_time", startTime);
            builder.addFormDataPart("announcement_venue", venue);

            if (toDate != null && !toDate.isEmpty()) {
                builder.addFormDataPart("announcement_end_date", toDate);
                builder.addFormDataPart("announcement_end_time", endTime);
            }
        }


        for (int i = 0; i < imageList.size(); i++) {
            try {
                if (imageList.get(i) != null) {
                    File file = new File(imageList.get(i));
                    RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                    builder.addFormDataPart("announcement_images[]", file.getName(), requestBody);
                }
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }
        }
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + userData.getApi_token());
        RequestBody finalRequestBody = builder.build();
        presenter.addAnnouncement(finalRequestBody, headers);
    }
}