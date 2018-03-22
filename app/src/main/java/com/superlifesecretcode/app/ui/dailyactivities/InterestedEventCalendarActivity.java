package com.superlifesecretcode.app.ui.dailyactivities;

import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;
import com.superlifesecretcode.app.R;
import com.superlifesecretcode.app.data.model.events.EventsInfoModel;
import com.superlifesecretcode.app.data.model.language.LanguageResponseData;
import com.superlifesecretcode.app.data.model.userdetails.UserDetailResponseData;
import com.superlifesecretcode.app.data.persistance.SuperLifeSecretPreferences;
import com.superlifesecretcode.app.ui.base.BaseActivity;
import com.superlifesecretcode.app.util.CommonUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class InterestedEventCalendarActivity extends BaseActivity implements InterestedEventView {


    private LanguageResponseData conversionData;
    private CompactCalendarView compactCalendarView;
    private SimpleDateFormat dateFormatForMonth = new SimpleDateFormat("MMMM yyyy", Locale.getDefault());
    private UserDetailResponseData userData;
    private InterestedEventPresenter presenter;
    private TextView textViewMonth;
    RecyclerView recyclerView;
    InterestedEventAapter adapter;
    List<Event> eventList;

    @Override
    protected int getContentView() {
        return R.layout.activity_interested_event;
    }

    @Override
    protected void initializeView() {
        userData = SuperLifeSecretPreferences.getInstance().getUserData();
        conversionData = SuperLifeSecretPreferences.getInstance().getConversionData();
        compactCalendarView = findViewById(R.id.compactcalendar_view);
        recyclerView = findViewById(R.id.recycler_view);
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
    }

    CompactCalendarView.CompactCalendarViewListener listener = new CompactCalendarView.CompactCalendarViewListener() {
        @Override
        public void onDayClick(Date dateClicked) {
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
            textViewTitle.setText(conversionData.getPersonal_calendar());
        }
    }

    @Override
    public void setEventData(List<EventsInfoModel> interestedEventResponseDataList) {
        if (interestedEventResponseDataList != null) {
            for (EventsInfoModel interestedEventResponseData : interestedEventResponseDataList) {
                compactCalendarView.addEvent(new Event(Color.RED, CommonUtils.getTimeInMilis(interestedEventResponseData.getAnnouncement_date() + " " + interestedEventResponseData.getAnnouncement_time()), interestedEventResponseData));
            }
        }
        listener.onDayClick(new Date());
    }
}
