package com.superlifesecretcode.app.ui.dailyactivities.personalevent;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.superlifesecretcode.app.R;
import com.superlifesecretcode.app.data.model.language.LanguageResponseData;
import com.superlifesecretcode.app.data.model.personalevent.PersonalEventResponseData;
import com.superlifesecretcode.app.data.model.standardevent.StandardEventResponseData;
import com.superlifesecretcode.app.data.model.userdetails.UserDetailResponseData;
import com.superlifesecretcode.app.data.persistance.SuperLifeSecretPreferences;
import com.superlifesecretcode.app.ui.base.BaseActivity;
import com.superlifesecretcode.app.ui.picker.selectiondialog.SelectionListDialog;
import com.superlifesecretcode.app.util.CommonUtils;
import com.superlifesecretcode.app.util.ConstantLib;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class AddNewEventCalendarActivity extends BaseActivity implements View.OnClickListener, AddEventView {


    private LanguageResponseData conversionData;
    private UserDetailResponseData userData;
    AddEventPresenter presenter;
    List<StandardEventResponseData> eventResponseDataList;
    private TextView textViewSelect;
    EditText editTextTitle, editTextReminderMinute;
    private TextView textViewDate, textViewTime;
    String typeId = "";
    String date = "";
    String time = "";
    Button buttonAddEvent;
    String id = "";
    PersonalEventResponseData eventResponseData;
    private boolean isStandard;
    TextView textViewTitleLable, textViewEventTitleLabel, textViewWhen,
            textViewLabelReminder, textViewInMin;

    @Override
    protected int getContentView() {
        return R.layout.activity_add_new_event;
    }

    @Override
    protected void initializeView() {
        userData = SuperLifeSecretPreferences.getInstance().getUserData();
        conversionData = SuperLifeSecretPreferences.getInstance().getConversionData();
        textViewSelect = findViewById(R.id.textVew_select);
        editTextTitle = findViewById(R.id.edit_text_event);
        textViewDate = findViewById(R.id.textView_date);
        textViewTime = findViewById(R.id.textView_time);
        textViewInMin = findViewById(R.id.textVew_minute);
        buttonAddEvent = findViewById(R.id.button_add_event);
        textViewTitleLable = findViewById(R.id.textView_label_event_details);
        textViewEventTitleLabel = findViewById(R.id.textVew_label_event_title);
        textViewWhen = findViewById(R.id.textVew_label_when);
        textViewLabelReminder = findViewById(R.id.textVew_label_reminder);
        editTextReminderMinute = findViewById(R.id.edit_text_reminder);
        Bundle detail = getIntent().getBundleExtra("bundle");
        if (detail != null && detail.getBoolean("isDetails")) {
            eventResponseData = (PersonalEventResponseData) detail.getSerializable("detail");
        }
        textViewSelect.setOnClickListener(this);
        textViewTime.setOnClickListener(this);
        textViewDate.setOnClickListener(this);
        buttonAddEvent.setOnClickListener(this);
        setUpToolbar();
        getStandardActivities();
        editTextTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!isStandard) {
                    if (textViewDate.getVisibility() == View.GONE) {
                        textViewDate.setVisibility(View.VISIBLE);
                        typeId = "";
                    }
                } else {
                    isStandard = false;
                }

            }
        });
        setUpLocalData();
        if (conversionData != null) {
            textViewEventTitleLabel.setText(conversionData.getEvent_title());
            textViewWhen.setText(conversionData.getWhen());
            textViewTitleLable.setText(conversionData.getEnter_event_details());
            textViewSelect.setText(conversionData.getSelect());
            textViewDate.setHint(conversionData.getDate());
            textViewTime.setHint(conversionData.getTime());
            editTextTitle.setHint(conversionData.getEvent_title());
            textViewLabelReminder.setText(conversionData.getReminder_before_event());
            editTextReminderMinute.setHint(conversionData.getReminder_before_event());
            textViewInMin.setText(conversionData.getIn_minute());
            buttonAddEvent.setText(conversionData.getAdd_event());
        }
    }

    @Override
    public void onBackPressed() {
        CommonUtils.startActivity(this, PersonalEventCalendarActivity.class);
        super.onBackPressed();
    }

    private void setUpLocalData() {
        if (eventResponseData != null) {
            id = eventResponseData.getActivity_id();
            if (eventResponseData.getType_id().equalsIgnoreCase("0")) {
                date = eventResponseData.getActivity_date();
                textViewDate.setText(CommonUtils.getformattedDateFromString(ConstantLib.INPUT_DATE_ONLY_FORMATE,
                        ConstantLib.OUTPUT_DATE_FORMATE, eventResponseData.getActivity_date(), false, null));
            } else {
                textViewDate.setVisibility(View.GONE);
                isStandard = true;
                typeId = eventResponseData.getType_id();
            }
            time = eventResponseData.getActivity_time();
            textViewTime.setText(CommonUtils.getformattedDateFromString("HH:mm", "hh:mm a", time, false, null));
            editTextReminderMinute.setText(eventResponseData.getRemind_before());
            editTextTitle.append(eventResponseData.getTitle());
            buttonAddEvent.setText("Update Event");
        }
    }

    private void getStandardActivities() {
        HashMap<String, String> body = new HashMap<>();
        body.put("language_id", SuperLifeSecretPreferences.getInstance().getLanguageId());
        presenter.getStandardevents(body);
    }


    @Override
    protected void initializePresenter() {
        presenter = new AddEventPresenter(this);
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
            case R.id.textVew_select:
                if (eventResponseDataList != null) {
                    showStandardList();
                }
                break;
            case R.id.textView_date:
                showDatePicker(System.currentTimeMillis() - 1000);
                break;
            case R.id.textView_time:
                showTimePicker();
                break;
            case R.id.button_add_event:
                validateAndAdd();
                break;

        }
    }

    private void validateAndAdd() {
        String title = editTextTitle.getText().toString().trim();
        String reminderMinute = editTextReminderMinute.getText().toString().trim();
        if (typeId.isEmpty()) {
            if (title.isEmpty()) {
                editTextTitle.setError(conversionData.getSelect_enter_title());
                return;
            }
            if (date.isEmpty()) {
                CommonUtils.showSnakeBar(this, conversionData.getSelect_date());
                return;
            }
        }
        if (time.isEmpty()) {
            CommonUtils.showSnakeBar(this, conversionData.getSelect_time());
            return;
        }
        if (reminderMinute.isEmpty()) {
            reminderMinute = "0";
//            editTextReminderMinute.setError(conversionData.getEnter_reminder_time_event());
        }
     /*   if (Integer.parseInt(reminderMinute) < 1) {
            editTextReminderMinute.setError(conversionData.getEnter_valid_reminder_time());
            return;
        }*/

        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + userData.getApi_token());
        HashMap<String, String> params = new HashMap<>();
        params.put("type_id", typeId);
        params.put("user_id", userData.getUser_id());
        params.put("title", title);
        params.put("activity_date", date);
        params.put("remind_before", reminderMinute);
        params.put("activity_time", time);
        params.put("sound", "");
        params.put("activity_id", id);
        presenter.addEvent(params, headers);
    }

    private void showDatePicker(long minDate) {
        CommonUtils.showDatePicker(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                String fromatttedDate = CommonUtils.getFromatttedDate(year, month, dayOfMonth);
                date = CommonUtils.getAppendedDate(year, month, dayOfMonth);
                textViewDate.setText(fromatttedDate);
            }
        }, minDate);
    }

    private void showTimePicker() {
        CommonUtils.showTimePicker(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                String hour = String.format(Locale.getDefault(), "%02d", selectedHour);
                String minute = String.format(Locale.getDefault(), "%02d", selectedMinute);
                String t = String.format("%s:%s", hour, minute);
                textViewTime.setText(CommonUtils.getformattedDateFromString("HH:mm", "hh:mm a", t, false, null));
                time = CommonUtils.getformattedDateFromString("HH:mm", "hh:mm a", t, false, null);

            }
        });
    }

    SelectionListDialog dialog;

    private void showStandardList() {
        if (dialog == null) {
            dialog = new SelectionListDialog(this, eventResponseDataList, new SelectionListDialog.SelectedListner<StandardEventResponseData>() {
                @Override
                public void onSelected(int position, StandardEventResponseData object) {
                    editTextTitle.setText(object.getTitle());
                    textViewDate.setVisibility(View.GONE);
                    typeId = object.getType_id();
                    date = "";
                }
            });
        }
        dialog.show();

    }


    @Override
    public void setStandardActivities(List<StandardEventResponseData> data) {
        eventResponseDataList = data;
    }

    @Override
    public void onEventAdded() {
        PersonalEventCalendarActivity.MODIFIEDLIST = true;
        onBackPressed();
    }
}
