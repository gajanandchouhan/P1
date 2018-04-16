package com.superlifesecretcode.app.ui.dailyactivities.personalevent;

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
import com.superlifesecretcode.app.data.model.language.LanguageResponseData;
import com.superlifesecretcode.app.data.model.personalevent.PersonalEventResponseData;
import com.superlifesecretcode.app.data.model.userdetails.UserDetailResponseData;
import com.superlifesecretcode.app.data.persistance.SuperLifeSecretPreferences;
import com.superlifesecretcode.app.ui.base.BaseActivity;
import com.superlifesecretcode.app.ui.dailyactivities.interestedevent.InterestedEventCalendarActivity;
import com.superlifesecretcode.app.util.AlarmUtility;
import com.superlifesecretcode.app.util.CommonUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class PersonalEventCalendarActivity extends BaseActivity implements View.OnClickListener, PersonalEventView {


    public static boolean MODIFIEDLIST = false;
    private LanguageResponseData conversionData;
    private CompactCalendarView compactCalendarView;
    private SimpleDateFormat dateFormatForMonth = new SimpleDateFormat("MMMM yyyy", Locale.getDefault());
    private SimpleDateFormat dateFormatForDay = new SimpleDateFormat("EEEE, dd MMMM", Locale.getDefault());
    private UserDetailResponseData userData;
    private PersonalEventPresenter presenter;
    private TextView textViewMonth, textViewDay;
    RecyclerView recyclerView;
    PersonalEventAapter adapter;
    List<Event> eventList;
    private ImageView imageViewProfile;
    private PersonalEventResponseData object;
    private int position;
    private String status;
    private Date date;
    private Event event;

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
        findViewById(R.id.imageView_add_event).setOnClickListener(this);
        recyclerView = findViewById(R.id.recycler_view);
        imageViewProfile = findViewById(R.id.imageView_profile);
        imageViewProfile.setVisibility(View.VISIBLE);
        imageViewProfile.setImageResource(R.drawable.date);
        imageViewProfile.setOnClickListener(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        compactCalendarView.setUseThreeLetterAbbreviation(true);
        compactCalendarView.shouldDrawIndicatorsBelowSelectedDays(true);
        date = new Date();
        final Date firstDayOfCurrentMonth = compactCalendarView.getFirstDayOfCurrentMonth();
        String format = dateFormatForMonth.format(firstDayOfCurrentMonth);
        textViewMonth = findViewById(R.id.textView_month_year);
        textViewMonth.setText(format);
        compactCalendarView.setListener(listener);
        eventList = new ArrayList<>();
        adapter = new PersonalEventAapter(eventList, this, conversionData);
        recyclerView.setAdapter(adapter);
        setUpToolbar();
        getInterestedEvent();
        listener.onDayClick(date);
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

    @Override
    protected void onResume() {
        super.onResume();
        if (MODIFIEDLIST) {
            MODIFIEDLIST = false;
            getInterestedEvent();
        }
    }

    private void getInterestedEvent() {
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + userData.getApi_token());
        HashMap<String, String> params = new HashMap<>();
        params.put("user_id", userData.getUser_id());
        presenter.getPersonalEvent(params, headers);
    }


    @Override
    protected void initializePresenter() {
        presenter = new PersonalEventPresenter(this);
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
            textViewTitle.setText(conversionData.getPersonal_cal());
        }
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imageView_profile:
                CommonUtils.startActivity(this, InterestedEventCalendarActivity.class);
                break;
            case R.id.imageView_add_event:
                CommonUtils.startActivity(this, AddNewEventCalendarActivity.class);
                break;
            case R.id.imageView_next:
                compactCalendarView.showNextMonth();
                break;
            case R.id.imageView_previous:
                compactCalendarView.showPreviousMonth();
                break;
        }
    }

    @Override
    public void setEventData(List<PersonalEventResponseData> data) {
        if (data != null) {
            compactCalendarView.removeAllEvents();
            for (PersonalEventResponseData interestedEventResponseData : data) {
                if (interestedEventResponseData.getStatus() != null && interestedEventResponseData.getStatus().equalsIgnoreCase("1")) {
                    setAlarm(interestedEventResponseData);
                }
                compactCalendarView.addEvent(new Event(Color.RED, CommonUtils.getTimeInMilis(interestedEventResponseData.getActivity_date() + " " + interestedEventResponseData.getActivity_time()), interestedEventResponseData));
            }
        }
        listener.onDayClick(
                date);
    }

    private void setAlarm(PersonalEventResponseData interestedEventResponseData) {
        AlarmUtility.getInstance(this).setAlarm(Integer.parseInt(interestedEventResponseData.getActivity_id()), "RichestLifeReminder", "Hi one new event is near- " + interestedEventResponseData.getTitle(), CommonUtils.getTimeInMilis(interestedEventResponseData.getActivity_date() + " " + interestedEventResponseData.getActivity_time()) - (Long.parseLong(interestedEventResponseData.getRemind_before())*1000*60), interestedEventResponseData.getTypeName()!=null&&!interestedEventResponseData.getTypeName().isEmpty());
    }

    private void removeAlarm(PersonalEventResponseData interestedEventResponseData) {
        AlarmUtility.getInstance(this).removeAlarm(Integer.parseInt(interestedEventResponseData.getActivity_id()));
    }

    @Override
    public void onStatusUpdated() {
        object.setStatus(status);
        if (status.equalsIgnoreCase("1")) {
            setAlarm(object);
        } else {
            removeAlarm(object);
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onStatusFailed() {
        object.setStatus(status.equalsIgnoreCase("1") ? "0" : "1");
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onRemoveSuccess() {
        eventList.remove(position);
        compactCalendarView.removeEvent(event);
        if (object!=null){
            removeAlarm(object);
        }
        adapter.notifyDataSetChanged();
    }

    public void updateState(int adapterPosition) {
        this.position = adapterPosition;
        this.object = (PersonalEventResponseData) eventList.get(adapterPosition).getData();
        if (object.getStatus().equalsIgnoreCase("1")) {
            this.status = "0";
        } else {
            this.status = "1";
        }
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + userData.getApi_token());
        HashMap<String, String> params = new HashMap<>();
        params.put("activity_id", object.getActivity_id());
        params.put("status", status);
        presenter.updateEventStatus(params, headers);

    }


    public void removeActivity(int position) {
        this.position = position;
        event = eventList.get(position);
        this.object = (PersonalEventResponseData) eventList.get(position).getData();
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + userData.getApi_token());
        HashMap<String, String> params = new HashMap<>();
        params.put("activity_id", object.getActivity_id());
        presenter.removeActivity(params, headers);
    }

    @Override
    public void noData() {
        compactCalendarView.removeAllEvents();
        eventList.clear();
        adapter.notifyDataSetChanged();
    }


}
