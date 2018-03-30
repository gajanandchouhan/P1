package com.superlifesecretcode.app.ui.events;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.superlifesecretcode.app.R;
import com.superlifesecretcode.app.data.model.events.EventResponseModel;
import com.superlifesecretcode.app.data.model.events.EventsInfoModel;
import com.superlifesecretcode.app.data.model.language.LanguageResponseData;
import com.superlifesecretcode.app.data.model.news.NewsResponseData;
import com.superlifesecretcode.app.data.model.userdetails.UserDetailResponseData;
import com.superlifesecretcode.app.data.persistance.SuperLifeSecretPreferences;
import com.superlifesecretcode.app.ui.base.BaseActivity;
import com.superlifesecretcode.app.ui.news.NewsPagerAdapter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EventDetailsActivity extends BaseActivity implements EventView{


    private ViewPager pager;
    private EventPagerAdapter newsAapter;
    private LanguageResponseData conversionData;
    private List<EventsInfoModel> list;
    private UserDetailResponseData userData;
    private int position;
    private String interested;
    private EventPresenter presenter;

    @Override
    protected int getContentView() {
        return R.layout.activity_event_details;
    }

    @Override
    protected void initializeView() {
        conversionData = SuperLifeSecretPreferences.getInstance().getConversionData();
        userData=SuperLifeSecretPreferences.getInstance().getUserData();
        Bundle bundle = getIntent().getBundleExtra("bundle");
        int postion = bundle.getInt("position");
        list = (List<EventsInfoModel>) bundle.getSerializable("events");
        setUpToolbar();
        pager = findViewById(R.id.pager);
        if (list != null) {
            newsAapter = new EventPagerAdapter(this, list,conversionData);
            pager.setAdapter(newsAapter);
            pager.setCurrentItem(postion);
        }
    }

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

    @Override
    public void setEventData(EventResponseModel newsResponseModel) {

    }

    @Override
    public void onUpdateInteresed() {
        list.get(position).setUserIntrested(interested);
        newsAapter.notifyDataSetChanged();
    }
}
