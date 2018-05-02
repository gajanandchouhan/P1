package com.superlifesecretcode.app.ui.events;

import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.superlifesecretcode.app.R;
import com.superlifesecretcode.app.data.model.events.EventResponseModel;
import com.superlifesecretcode.app.data.model.events.EventsInfoModel;
import com.superlifesecretcode.app.data.model.language.LanguageResponseData;
import com.superlifesecretcode.app.data.model.userdetails.UserDetailResponseData;
import com.superlifesecretcode.app.data.persistance.SuperLifeSecretPreferences;
import com.superlifesecretcode.app.ui.base.BaseActivity;
import com.superlifesecretcode.app.util.AlarmUtility;
import com.superlifesecretcode.app.util.CommonUtils;
import com.superlifesecretcode.app.util.ConstantLib;
import com.superlifesecretcode.app.util.PermissionConstant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EventActivity extends BaseActivity implements EventView {


    private RecyclerView recyclerView;
    private EventAapter newsAapter;
    private LanguageResponseData conversionData;
    private EventPresenter presenter;
    private List<EventsInfoModel> list;
    private TabLayout tabLayout;
    private List<EventsInfoModel> todayList;
    private List<EventsInfoModel> upcomingList;
    private UserDetailResponseData userData;
    private int position;
    private String interested;

    @Override
    protected int getContentView() {
        return R.layout.activity_event;
    }

    @Override
    protected void initializeView() {
        conversionData = SuperLifeSecretPreferences.getInstance().getConversionData();
        userData = SuperLifeSecretPreferences.getInstance().getUserData();
        setUpToolbar();
        recyclerView = findViewById(R.id.recycler_view);
        tabLayout = findViewById(R.id.tab_layout);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<>();
        newsAapter = new EventAapter(list, this, conversionData);
        recyclerView.setAdapter(newsAapter);
        tabLayout.addOnTabSelectedListener(listener);
        tabLayout.getTabAt(0).setText(conversionData.getAll());
        tabLayout.getTabAt(1).setText(conversionData.getToday());
        tabLayout.getTabAt(2).setText(conversionData.getUpcoming());
    }

    @Override
    protected void onResume() {
        super.onResume();
        getEvents();
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
                    if (upcomingList != null) {
                        list.addAll(upcomingList);
                    }
                    newsAapter.setToday(false);
                    newsAapter.notifyDataSetChanged();
                    break;
                case 1:
                    list.clear();
                    if (todayList != null) {
                        list.addAll(todayList);
                    }
                    newsAapter.setToday(true);
                    newsAapter.notifyDataSetChanged();
                    break;
                case 2:
                    list.clear();
                    if (upcomingList != null) {
                        list.addAll(upcomingList);
                    }
                    newsAapter.setToday(false);
                    newsAapter.notifyDataSetChanged();
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
        presenter = new EventPresenter(this);
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
            textViewTitle.setText(conversionData.getEvent_activity());
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
    public void setEventData(EventResponseModel newsResponseModel) {
        if (newsResponseModel.getData() != null) {
            int unread = newsResponseModel.getUnread();
            SuperLifeSecretPreferences.getInstance().setEventUndread(unread);
            todayList = newsResponseModel.getData().getToday();
            upcomingList = newsResponseModel.getData().getUpcoming();
            list.clear();
            if (tabLayout.getSelectedTabPosition() == 0) {
                if (todayList != null) {
                    list.addAll(todayList);
                    newsAapter.setToday(false);
                    newsAapter.notifyDataSetChanged();
                }
                if (upcomingList != null) {
                    list.addAll(upcomingList);
                    newsAapter.setToday(false);
                    newsAapter.notifyDataSetChanged();
                }
            } else if (tabLayout.getSelectedTabPosition() == 1) {
                if (todayList != null) {
                    list.addAll(todayList);
                    newsAapter.setToday(true);
                    newsAapter.notifyDataSetChanged();
                }
            } else {
                if (upcomingList != null) {
                    list.addAll(upcomingList);
                    newsAapter.setToday(false);
                    newsAapter.notifyDataSetChanged();
                }
            }
        }
    }

    @Override
    public void onUpdateInteresed() {
        list.get(position).setUserIntrested(interested);
        if (interested.equalsIgnoreCase("1")) {
            setAlarm(list.get(position));
        } else {
            removeAlarm(list.get(position));
        }
        newsAapter.notifyDataSetChanged();
    }

    private void setAlarm(EventsInfoModel eventsInfoModel) {
        String dateTime = eventsInfoModel.getAnnouncement_date() + " " + eventsInfoModel.getAnnouncement_time();
        long timeInMilis = CommonUtils.getTimeInMilis(CommonUtils.getformattedDateFromString(ConstantLib.INPUT_DATE_TIME_FORMATE, ConstantLib.INPUT_DATE_TIME_FORMATE, dateTime, true, eventsInfoModel.getTimezone()));
        AlarmUtility.getInstance(this).setAlarm(Integer.parseInt(eventsInfoModel.getAnnouncement_id()), "RichestLifeReminder", "" + eventsInfoModel.getAnnouncement_name(), timeInMilis - 60 * 1000 * 30, false);
    }

    private void removeAlarm(EventsInfoModel eventsInfoModel) {
        AlarmUtility.getInstance(this).removeAlarm(Integer.parseInt(eventsInfoModel.getAnnouncement_id()));
    }

    @Override
    public void onEvendReaded() {

    }

    private void getEvents() {
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + userData.getApi_token());
        HashMap<String, String> params = new HashMap<>();
        params.put("announcement_type", "1");
        params.put("country", userData.getCountry());
        presenter.getEvents(params, headers);
    }

    public void updateEventInterest(int position, String interested, String id) {
        this.position = position;
        this.interested = interested;
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + userData.getApi_token());
        HashMap<String, String> params = new HashMap<>();
        params.put("announcement_id", id);
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
}
