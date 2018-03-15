package com.superlifesecretcode.app.ui.events;

import android.support.design.widget.TabLayout;
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
import com.superlifesecretcode.app.ui.news.NewsAapter;
import com.superlifesecretcode.app.ui.news.NewsPresenter;

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

    @Override
    protected int getContentView() {
        overridePendingTransition(R.anim.activity_open_translate, R.anim.activity_close_scale);
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
        newsAapter = new EventAapter(list, this);
        recyclerView.setAdapter(newsAapter);
        tabLayout.addOnTabSelectedListener(listener);
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
                    newsAapter.setToday(true);
                    newsAapter.notifyDataSetChanged();
                    break;
                case 1:
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

    @Override
    public void setEventData(EventResponseModel newsResponseModel) {
        if (newsResponseModel.getData() != null) {
            todayList = newsResponseModel.getData().getToday();
            upcomingList = newsResponseModel.getData().getUpcoming();
            list.clear();
            if (todayList != null) {
                list.addAll(todayList);
                newsAapter.setToday(true);
                newsAapter.notifyDataSetChanged();
            }
        }
    }

    private void getEvents() {
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + userData.getApi_token());
        HashMap<String, String> params = new HashMap<>();
        params.put("announcement_type", "1");
        presenter.getEvents(params, headers);
    }

}
