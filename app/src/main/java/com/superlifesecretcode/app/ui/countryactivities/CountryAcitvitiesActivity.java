package com.superlifesecretcode.app.ui.countryactivities;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.superlifesecretcode.app.R;
import com.superlifesecretcode.app.data.model.country.CountryResponseData;
import com.superlifesecretcode.app.data.model.countryactivities.CounActivtyResponseData;
import com.superlifesecretcode.app.data.model.countryactivities.CountryActivityInfoModel;
import com.superlifesecretcode.app.data.model.events.EventsInfoModel;
import com.superlifesecretcode.app.data.model.language.LanguageResponseData;
import com.superlifesecretcode.app.data.model.userdetails.UserDetailResponseData;
import com.superlifesecretcode.app.data.persistance.SuperLifeSecretPreferences;
import com.superlifesecretcode.app.ui.base.BaseActivity;
import com.superlifesecretcode.app.ui.picker.CountryStatePicker;
import com.superlifesecretcode.app.ui.picker.DateRangePicker;
import com.superlifesecretcode.app.util.AlarmUtility;
import com.superlifesecretcode.app.util.CommonUtils;
import com.superlifesecretcode.app.util.PermissionConstant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class CountryAcitvitiesActivity extends BaseActivity implements CountryActivitiesView, View.OnClickListener {


    public static boolean UPDATED;
    private RecyclerView recyclerView;
    private CountryActivityAapter countryActivityAapter;
    private LanguageResponseData conversionData;
    private CountryAcivitiesPresenter presenter;
    private List<CountryActivityInfoModel> list;
    private TabLayout tabLayout;
    private List<CountryActivityInfoModel> todayList;
    private List<CountryActivityInfoModel> upcomingList;
    private UserDetailResponseData userData;
    private int position;
    private String interested;
    private boolean isStudyGroup;
    private String title;
    EditText editTextSearch;
    private DateRangePicker rangePicker;
    private ImageView imageViewDate;
    private TextView textViewCountry;
    private CountryStatePicker countryStatePicker;
    private String stateId = "";
    private String countryId = "";

    @Override
    protected int getContentView() {
        return R.layout.activity_country_activities;
    }

    @Override
    protected void initializeView() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        conversionData = SuperLifeSecretPreferences.getInstance().getConversionData();
        userData = SuperLifeSecretPreferences.getInstance().getUserData();
        editTextSearch = findViewById(R.id.edit_text_search);
        imageViewDate = findViewById(R.id.imageView_date);
        textViewCountry = findViewById(R.id.textView_country);
        textViewCountry.setOnClickListener(this);
        textViewCountry.setText(userData.getCountry_code().toUpperCase());
        imageViewDate.setOnClickListener(this);
        imageViewDate.setVisibility(View.GONE);
        Bundle bundle = getIntent().getBundleExtra("bundle");
        title = bundle.getString("title");
        isStudyGroup = bundle.getBoolean("isStudyGroup");
        setUpToolbar();
        recyclerView = findViewById(R.id.recycler_view);
        tabLayout = findViewById(R.id.tab_layout);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<>();
        countryActivityAapter = new CountryActivityAapter(list, this, conversionData);
        recyclerView.setAdapter(countryActivityAapter);
        tabLayout.addOnTabSelectedListener(listener);
        tabLayout.getTabAt(0).setText(conversionData.getToday());
        tabLayout.getTabAt(1).setText(conversionData.getUpcoming());
        countryId = userData.getCountry();
        getEvents("", "", "", true);
        editTextSearch.addTextChangedListener(new TextWatcher() {
            private Timer timer = new Timer();
            private final long DELAY = 500; // milliseconds

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(final Editable s) {
                timer.cancel();
                timer = new Timer();
                timer.schedule(
                        new TimerTask() {
                            @Override
                            public void run() {
                                // TODO: do what you need here (refresh list)
                                // you will probably need to use runOnUiThread(Runnable action) for some specific actions
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        getEvents(s.toString(), "", "", false);
                                    }
                                });
                            }
                        },
                        DELAY
                );

            }
        });
        editTextSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    CommonUtils.hideKeyboard(CountryAcitvitiesActivity.this);
                    return true;
                }
                return false;
            }
        });
        editTextSearch.setHint(conversionData.getSearch());
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (UPDATED) {
            UPDATED = false;
            getEvents("", "", "", true);
        }
    }

    TabLayout.OnTabSelectedListener listener = new TabLayout.OnTabSelectedListener() {
        @Override
        public void onTabSelected(TabLayout.Tab tab) {
            switch (tab.getPosition()) {
                case 0:
                    imageViewDate.setVisibility(View.GONE);
                    list.clear();
                    if (todayList != null) {
                        list.addAll(todayList);
                    }
                    countryActivityAapter.setToday(true);
                    countryActivityAapter.notifyDataSetChanged();
                    break;
                case 1:
                    imageViewDate.setVisibility(View.VISIBLE);
                    list.clear();
                    if (upcomingList != null) {
                        list.addAll(upcomingList);
                    }
                    countryActivityAapter.setToday(false);
                    countryActivityAapter.notifyDataSetChanged();
                    break;
            }
        }

        @Override
        public void onTabUnselected(TabLayout.Tab tab) {

        }

        @Override
        public void onTabReselected(TabLayout.Tab tab) {

        }
    };

    @Override
    protected void initializePresenter() {
        presenter = new CountryAcivitiesPresenter(this);
        presenter.setView(this);
    }

    private void setUpToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        TextView textViewTitle = findViewById(R.id.textView_title);
        textViewTitle.setText(title);
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

    @Override
    public void onUpdateInteresed() {
        list.get(position).setUserIntrested(interested);
        if (interested.equalsIgnoreCase("1")){
            setAlarm(list.get(position));
        }
        else{
            removeAlarm(list.get(position));
        }
        countryActivityAapter.notifyDataSetChanged();
    }

    @Override
    public void setActivtyDetails(CountryActivityInfoModel data) {

    }

    private void getEvents(String keyword, String startDate, String endDate, boolean showProgress) {
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + userData.getApi_token());
        HashMap<String, String> params = new HashMap<>();
        params.put("type", isStudyGroup ? "1" : "2");
        params.put("keyword", keyword);
        params.put("startDate", startDate);
        params.put("endDate", endDate);
        params.put("country_id", countryId);
        params.put("state_id", stateId);
        presenter.getAcivities(params, headers, showProgress);
    }

    private void getCountry() {
        presenter.getCountry();
    }

    public void updateEventInterest(int position, String interested, String id) {
        this.position = position;
        this.interested = interested;
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + userData.getApi_token());
        HashMap<String, String> params = new HashMap<>();
        params.put("activity_id", id);
        params.put("interest", interested);
        params.put("user_id", userData.getUser_id());
        presenter.makeInterested(params, headers);
    }

    @Override
    public void setCountyAcivityData(CounActivtyResponseData data) {
        if (data != null) {
            todayList = data.getToday();
            upcomingList = data.getUpcoming();
            list.clear();
            if (tabLayout.getSelectedTabPosition() == 0) {
                if (todayList != null) {
                    list.addAll(todayList);
                    countryActivityAapter.setToday(true);
                    countryActivityAapter.notifyDataSetChanged();
                }
            } else {
                if (upcomingList != null) {
                    list.addAll(upcomingList);
                    countryActivityAapter.setToday(false);
                    countryActivityAapter.notifyDataSetChanged();
                }
            }
        }
    }

    @Override
    public void setCountryData(List<CountryResponseData> data) {
        countryStatePicker = new CountryStatePicker(this, new CountryStatePicker.PickerListner() {
            @Override
            public void onPick(CountryResponseData country) {
                textViewCountry.setText(country.getCountrycode());
                countryId = country.getId();
                countryStatePicker.dismiss();
                getState(countryId);
            }
        }, data);
        countryStatePicker.show();
    }

    private void getState(String countryId) {
        HashMap<String, String> body = new HashMap<>();
        body.put("country_id", countryId);
        presenter.getStates(body);
    }

    @Override
    public void setStateData(List<CountryResponseData> data) {
        countryStatePicker = new CountryStatePicker(this, new CountryStatePicker.PickerListner() {
            @Override
            public void onPick(CountryResponseData country) {
                countryStatePicker.dismiss();
                stateId = country.getId();
                getEvents("", "", "", true);
            }
        }, data);
        countryStatePicker.show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imageView_date:
                showDateRnagePicker();
                break;
            case R.id.textView_country:
                getCountry();
                break;
        }
    }

    private void showDateRnagePicker() {
        if (rangePicker == null) {
            rangePicker = new DateRangePicker(this);
            rangePicker.setOnClickListner(new DateRangePicker.OnClickListner() {
                @Override
                public void onDateRnageSelected(String startDate, String endDate) {
                    CommonUtils.showLog("DATE", startDate + ", " + endDate);
                    getEvents("", startDate, endDate, true);
                    rangePicker.dismiss();
                }
            });
        }
        rangePicker.show();
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
    private void setAlarm(CountryActivityInfoModel eventsInfoModel) {
        AlarmUtility.getInstance(this).setAlarm(Integer.parseInt(eventsInfoModel.getActivity_id()), "RichestLifeReminder", "Hi one new event is near- " + eventsInfoModel.getTitle(), CommonUtils.getTimeInMilis(eventsInfoModel.getActivity_date() + " " + eventsInfoModel.getActivity_time()) - 60 * 1000 * 30,false);
    }

    private void removeAlarm(CountryActivityInfoModel eventsInfoModel) {
        AlarmUtility.getInstance(this).removeAlarm(Integer.parseInt(eventsInfoModel.getActivity_id()));
    }
}
