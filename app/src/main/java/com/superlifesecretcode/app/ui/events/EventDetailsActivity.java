package com.superlifesecretcode.app.ui.events;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewPager;
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
import com.superlifesecretcode.app.util.PermissionConstant;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EventDetailsActivity extends BaseActivity implements EventView {


    private ViewPager pager;
    private EventPagerAdapter newsAapter;
    private LanguageResponseData conversionData;
    private List<EventsInfoModel> list;
    private UserDetailResponseData userData;
    private String interested;
    private EventPresenter presenter;
    private int postion;

    @Override
    protected int getContentView() {
        return R.layout.activity_event_details;
    }

    @Override
    protected void initializeView() {
        conversionData = SuperLifeSecretPreferences.getInstance().getConversionData();
        userData = SuperLifeSecretPreferences.getInstance().getUserData();
        Bundle bundle = getIntent().getBundleExtra("bundle");
        postion = bundle.getInt("position");
        list = (List<EventsInfoModel>) bundle.getSerializable("events");
        setUpToolbar();
        pager = findViewById(R.id.pager);
        if (list != null) {
            newsAapter = new EventPagerAdapter(this, list, conversionData);
            pager.setAdapter(newsAapter);
            pager.setCurrentItem(postion);
        }
        String readed_by_user = list.get(postion).getReaded_by_user();
        if (readed_by_user.equalsIgnoreCase("0")) {
            markRead();
        }

        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                String readed_by_user1 = list.get(position).getReaded_by_user();
                if (readed_by_user1.equalsIgnoreCase("0")) {
                    markRead();
                }
                postion = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    protected void initializePresenter() {
        presenter = new EventPresenter(this);
        presenter.setView(this);
    }

    private void markRead() {
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + userData.getApi_token());
        HashMap<String, String> params = new HashMap<>();
        params.put("announcement_id", list.get(postion).getAnnouncement_id());
        presenter.readMark(params, headers);
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
        this.postion = position;
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
        list.get(postion).setUserIntrested(interested);
        if (interested.equalsIgnoreCase("1")) {
            setAlarm(list.get(postion));
        } else {
            removeAlarm(list.get(postion));
        }
        newsAapter.notifyDataSetChanged();
    }

    private void setAlarm(EventsInfoModel eventsInfoModel) {
        AlarmUtility.getInstance(this).setAlarm(Integer.parseInt(eventsInfoModel.getAnnouncement_id()), "RichestLifeReminder", "Hi one new event is near- " + eventsInfoModel.getAnnouncement_name(), CommonUtils.getTimeInMilis(eventsInfoModel.getAnnouncement_date() + " " + eventsInfoModel.getAnnouncement_time()) - 60 * 1000 * 30, false);
    }

    private void removeAlarm(EventsInfoModel eventsInfoModel) {
        AlarmUtility.getInstance(this).removeAlarm(Integer.parseInt(eventsInfoModel.getAnnouncement_id()));
    }

    @Override
    public void onEvendReaded() {
        list.get(postion).setReaded_by_user("1");
        newsAapter.notifyDataSetChanged();
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
