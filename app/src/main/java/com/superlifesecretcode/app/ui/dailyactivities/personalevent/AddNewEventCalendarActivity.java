package com.superlifesecretcode.app.ui.dailyactivities.personalevent;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.superlifesecretcode.app.R;
import com.superlifesecretcode.app.data.model.language.LanguageResponseData;
import com.superlifesecretcode.app.data.model.standardevent.StandardEventResponseData;
import com.superlifesecretcode.app.data.model.userdetails.UserDetailResponseData;
import com.superlifesecretcode.app.data.persistance.SuperLifeSecretPreferences;
import com.superlifesecretcode.app.ui.base.BaseActivity;
import com.superlifesecretcode.app.ui.picker.selectiondialog.SelectionListDialog;
import com.superlifesecretcode.app.util.CommonUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;

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
        editTextReminderMinute = findViewById(R.id.edit_text_reminder);
        textViewSelect.setOnClickListener(this);
        textViewTime.setOnClickListener(this);
        textViewDate.setOnClickListener(this);
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
                if (textViewDate.getVisibility() == View.GONE) {
                    textViewDate.setVisibility(View.VISIBLE);
                    typeId = "";
                }
            }
        });
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
                showDatePicker();
                break;
            case R.id.textView_time:
                showTimePicker();
                break;

        }
    }

    private void showDatePicker() {
        CommonUtils.showDatePicker(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                String fromatttedDate = CommonUtils.getFromatttedDate(year, month, dayOfMonth);
                date = CommonUtils.getAppendedDate(year, month, dayOfMonth);
                textViewDate.setText(fromatttedDate);
            }
        });
    }

    private void showTimePicker() {
        CommonUtils.showTimePicker(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                String hour = String.format(Locale.getDefault(), "%02d", selectedHour);
                String minute = String.format(Locale.getDefault(), "%02d", selectedMinute);
                String t = String.format("%s:%s", hour, minute);
                textViewTime.setText(t);
                time = t;
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
}
