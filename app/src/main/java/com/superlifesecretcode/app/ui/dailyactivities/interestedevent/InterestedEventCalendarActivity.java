package com.superlifesecretcode.app.ui.dailyactivities.interestedevent;

import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;
import com.superlifesecretcode.app.R;
import com.superlifesecretcode.app.data.model.events.EventsInfoModel;
import com.superlifesecretcode.app.data.model.interesetdevent.InterestedEventdata;
import com.superlifesecretcode.app.data.model.language.LanguageResponseData;
import com.superlifesecretcode.app.data.model.news.SingleNewsResponseModel;
import com.superlifesecretcode.app.data.model.userdetails.UserDetailResponseData;
import com.superlifesecretcode.app.data.persistance.SuperLifeSecretPreferences;
import com.superlifesecretcode.app.ui.base.BaseActivity;
import com.superlifesecretcode.app.ui.dailyactivities.personalevent.PersonalEventCalendarActivity;
import com.superlifesecretcode.app.util.CommonUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class InterestedEventCalendarActivity extends BaseActivity implements InterestedEventView, View.OnClickListener {


    public static boolean UPDATED = false;
    private LanguageResponseData conversionData;
    private CompactCalendarView compactCalendarView;
    private SimpleDateFormat dateFormatForMonth = new SimpleDateFormat("MMMM yyyy", Locale.getDefault());
    private SimpleDateFormat dateFormatForDay = new SimpleDateFormat("EEEE, dd MMMM", Locale.getDefault());
    private UserDetailResponseData userData;
    private InterestedEventPresenter presenter;
    private TextView textViewMonth, textViewDay;
    RecyclerView recyclerView;
    InterestedEventAapter adapter;
    List<Event> eventList;
    private Date date;
    //  private ImageView imageViewProfile;

    @Override
    protected int getContentView() {
        return R.layout.activity_interested_event;
    }

    @Override
    protected void initializeView() {
        userData = SuperLifeSecretPreferences.getInstance().getUserData();
        conversionData = SuperLifeSecretPreferences.getInstance().getConversionData();
        compactCalendarView = findViewById(R.id.compactcalendar_view);
        final ImageView imageViewPre = findViewById(R.id.imageView_previous);
        final ImageView imageViewNext = findViewById(R.id.imageView_next);
        imageViewNext.setOnClickListener(this);
        imageViewPre.setOnClickListener(this);
        textViewDay = findViewById(R.id.textView_day);
        recyclerView = findViewById(R.id.recycler_view);
//        imageViewProfile = findViewById(R.id.imageView_profile);
//        imageViewProfile.setVisibility(View.VISIBLE);
//        imageViewProfile.setImageResource(R.drawable.date);
//        imageViewProfile.setOnClickListener(this);
        date = new Date();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        compactCalendarView.setUseThreeLetterAbbreviation(true);
        final Date firstDayOfCurrentMonth = compactCalendarView.getFirstDayOfCurrentMonth();
        String format = dateFormatForMonth.format(firstDayOfCurrentMonth);
        textViewMonth = findViewById(R.id.textView_month_year);
        textViewMonth.setText(format);
        compactCalendarView.setListener(listener);
        eventList = new ArrayList<>();
        adapter = new InterestedEventAapter(eventList, this, conversionData);
        recyclerView.setAdapter(adapter);
        setUpToolbar();
        getInterestedEvent();
        listener.onDayClick(date);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (UPDATED) {
            UPDATED = false;
            getInterestedEvent();
        }
    }

    CompactCalendarView.CompactCalendarViewListener listener = new CompactCalendarView.CompactCalendarViewListener() {
        @Override
        public void onDayClick(Date dateClicked) {
            date = dateClicked;
            textViewDay.setText(dateFormatForDay.format(dateClicked));
            List<Event> events = compactCalendarView.getEvents(dateClicked);
            eventList.clear();
            eventList.addAll(events);
            adapter.notifyDataSetChanged();
        }

        @Override

        public void onMonthScroll(Date firstDayOfNewMonth) {
            String format = dateFormatForMonth.format(firstDayOfNewMonth);
            textViewMonth.setText(format);
        }
    };

    private void getInterestedEvent() {
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + userData.getApi_token());
        HashMap<String, String> params = new HashMap<>();
        params.put("user_id", userData.getUser_id());
        presenter.getInterestedEvent(params, headers);
    }

    @Override
    protected void initializePresenter() {
        presenter = new InterestedEventPresenter(this);
        presenter.setView(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    private void setUpToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        TextView textViewTitle = findViewById(R.id.textView_title);
        if (conversionData != null) {
            textViewTitle.setText(conversionData.getEvent_cal());
        }
    }

    @Override
    public void setEventData(List<InterestedEventdata> interestedEventResponseDataList) {
        compactCalendarView.removeAllEvents();
        if (interestedEventResponseDataList != null) {
            for (InterestedEventdata interestedEventResponseData : interestedEventResponseDataList) {
                compactCalendarView.addEvent(new Event(Color.RED, CommonUtils.getTimeInMilis(interestedEventResponseData.getEvent_date() + " " + interestedEventResponseData.getEvent_time()), interestedEventResponseData));
            }
        }
        listener.onDayClick(date);
    }

    @Override
    public void setDetails(SingleNewsResponseModel newsResponseModel) {

    }

    @Override
    public void onUpdateInteresed() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imageView_profile:
                CommonUtils.startActivity(this, PersonalEventCalendarActivity.class);
                break;
            case R.id.imageView_next:
                compactCalendarView.showNextMonth();
                break;
            case R.id.imageView_previous:
                compactCalendarView.showPreviousMonth();
                break;
        }
    }
}
