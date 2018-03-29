package com.superlifesecretcode.app.ui.countryactivities;

import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import com.superlifesecretcode.app.R;
import com.superlifesecretcode.app.data.model.countryactivities.CounActivtyResponseData;
import com.superlifesecretcode.app.data.model.countryactivities.CountryActivityInfoModel;
import com.superlifesecretcode.app.data.model.events.EventsInfoModel;
import com.superlifesecretcode.app.data.model.language.LanguageResponseData;
import com.superlifesecretcode.app.data.model.userdetails.UserDetailResponseData;
import com.superlifesecretcode.app.data.persistance.SuperLifeSecretPreferences;
import com.superlifesecretcode.app.ui.base.BaseActivity;
import com.superlifesecretcode.app.ui.events.EventAapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class CountryAcitvitiesActivity extends BaseActivity implements CountryActivitiesView {


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
    private boolean isUpdated = false;
    EditText editTextSearch;


    @Override
    protected int getContentView() {
        overridePendingTransition(R.anim.activity_open_translate, R.anim.activity_close_scale);
        return R.layout.activity_country_activities;
    }

    @Override
    protected void initializeView() {
        conversionData = SuperLifeSecretPreferences.getInstance().getConversionData();
        userData = SuperLifeSecretPreferences.getInstance().getUserData();
        editTextSearch = findViewById(R.id.edit_text_search);
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
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isUpdated) {
            isUpdated = false;
            getEvents("", "", "", true);
        }
    }

    TabLayout.OnTabSelectedListener listener = new TabLayout.OnTabSelectedListener() {
        @Override
        public void onTabSelected(TabLayout.Tab tab) {
            switch (tab.getPosition()) {
                case 0:
                    list.clear();
                    if (todayList != null) {
                        list.addAll(todayList);
                    }
                    countryActivityAapter.setToday(true);
                    countryActivityAapter.notifyDataSetChanged();
                    break;
                case 1:
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
        overridePendingTransition(R.anim.activity_open_scale, R.anim.activity_close_translate);
        super.onPause();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

   /* @Override
    public void setEventData(EventResponseModel newsResponseModel) {
        if (newsResponseModel.getData() != null) {
            todayList = newsResponseModel.getData().getToday();
            upcomingList = newsResponseModel.getData().getUpcoming();
            list.clear();
            if (tabLayout.getSelectedTabPosition() == 0) {
                if (todayList != null) {
                    list.addAll(todayList);
                    newsAapter.setToday(true);
                    newsAapter.notifyDataSetChanged();
                }
            } else {
                if (upcomingList != null) {
                    list.addAll(upcomingList);
                    newsAapter.setToday(true);
                    newsAapter.notifyDataSetChanged();
                }
            }
        }
    }

    @Override
    public void onUpdateInteresed() {
        list.get(position).setUserIntrested(interested);
        newsAapter.notifyDataSetChanged();
    }*/

    private void getEvents(String keyword, String startDate, String endDate, boolean showProgress) {
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + userData.getApi_token());
        HashMap<String, String> params = new HashMap<>();
        params.put("type", isStudyGroup ? "1" : "2");
        params.put("keyword", keyword);
        params.put("startDate", startDate);
        params.put("endDate", endDate);
        presenter.getAcivities(params, headers, showProgress);
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
                    countryActivityAapter.setToday(true);
                    countryActivityAapter.notifyDataSetChanged();
                }
            }
        }
    }
}
