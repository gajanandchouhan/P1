package com.superlifesecretcode.app.ui.myannouncement.addannouncement;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
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
import com.superlifesecretcode.app.ui.picker.CountryStatePicker;
import com.superlifesecretcode.app.ui.picker.DropDownWindow;
import com.superlifesecretcode.app.ui.sharing_submit.ImageAapter;
import com.superlifesecretcode.app.util.CommonUtils;
import com.superlifesecretcode.app.util.PermissionConstant;
import com.superlifesecretcode.app.util.SpacesItemDecorationGridLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class AddAnnouncementActivity extends BaseActivity implements AddAnnouncementView, View.OnClickListener {


    private LanguageResponseData conversionData;
    private UserDetailResponseData userData;
    private CountryStatePicker countryStatePicker;
    private List<CountryResponseData> countryList;

    private TextView textViewViewCountry, textViewAnnounType, textViewPickImage;
    private TextView textViewStartDate, textViewEndDate, textViewStartTime, textViewEndTime;
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
        textViewViewCountry.setOnClickListener(this);
        textViewPickImage.setOnClickListener(this);
        textViewAnnounType.setOnClickListener(this);
        textViewStartDate.setOnClickListener(this);
        textViewEndDate.setOnClickListener(this);
        textViewStartTime.setOnClickListener(this);
        textViewEndTime.setOnClickListener(this);
        announceTypeList = new ArrayList<>();
        announceTypeList.add(conversionData.getNews_update());
        announceTypeList.add(conversionData.getEvent_activity());
        imageList = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        submitAapter = new HorizontalImageAapter(imageList, this);
        recyclerView.setAdapter(submitAapter);
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
}
