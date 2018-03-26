package com.superlifesecretcode.app.ui.dailyactivities.personalevent;

import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.superlifesecretcode.app.R;
import com.superlifesecretcode.app.data.model.language.LanguageResponseData;
import com.superlifesecretcode.app.data.model.standardevent.StandardEventResponseData;
import com.superlifesecretcode.app.data.model.userdetails.UserDetailResponseData;
import com.superlifesecretcode.app.data.persistance.SuperLifeSecretPreferences;
import com.superlifesecretcode.app.ui.base.BaseActivity;
import com.superlifesecretcode.app.ui.picker.selectiondialog.SelectionListDialog;

import java.util.HashMap;
import java.util.List;

public class AddNewEventCalendarActivity extends BaseActivity implements View.OnClickListener, AddEventView {


    private LanguageResponseData conversionData;
    private UserDetailResponseData userData;
    AddEventPresenter presenter;
    List<StandardEventResponseData> eventResponseDataList;
    private TextView textViewSelect;
    EditText editTextTitle;
    private TextView textViewDate;
    String typeId = "";


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
        textViewSelect.setOnClickListener(this);
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
        }
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
