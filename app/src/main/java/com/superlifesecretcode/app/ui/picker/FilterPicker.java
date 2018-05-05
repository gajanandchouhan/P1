package com.superlifesecretcode.app.ui.picker;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.superlifesecretcode.app.R;
import com.superlifesecretcode.app.data.model.WeekDayModel;
import com.superlifesecretcode.app.data.model.country.CountryResponseData;
import com.superlifesecretcode.app.data.model.countryactivities.CounActivtyResponseData;
import com.superlifesecretcode.app.data.model.countryactivities.CountryActivityInfoModel;
import com.superlifesecretcode.app.data.model.language.LanguageResponseData;
import com.superlifesecretcode.app.data.persistance.SuperLifeSecretPreferences;
import com.superlifesecretcode.app.ui.countryactivities.CountryAcivitiesPresenter;
import com.superlifesecretcode.app.ui.countryactivities.CountryActivitiesView;
import com.superlifesecretcode.app.ui.picker.selectiondialog.SelectionListDialog;
import com.superlifesecretcode.app.util.CommonUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Divya on 04-12-2017.
 */

public class FilterPicker extends Dialog implements CountryActivitiesView, View.OnClickListener {
    private final Context context;
    private PickerListner pickerListner;
    private EditText editText;
    private ProgressDialog progressDialog;
    private CountryAcivitiesPresenter presenter;
    TextView textViewCountry, textViewState, textViewCity, textViewDay,
            textViewTitle;
    private String country = "", state = "", city = "", day = "", countryCode, contryName;
    private LanguageResponseData languageResponseData;
    private Button buttonDone;
    private ArrayList<WeekDayModel> weekDayModelList;
    private SelectionListDialog dialog;
    private CountryStatePicker countryStatePicker;

    public FilterPicker(@NonNull Context context, PickerListner pickerListner, String countryId, String countryCode, String countryName) {
        super(context, R.style.AppTheme);
        this.context = context;
        this.pickerListner = pickerListner;
        this.country = countryId;
        this.countryCode = countryCode;
        this.contryName = countryName;
        init(context);
    }

    private void init(Context context) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.layout_fliter_picker);
        languageResponseData = SuperLifeSecretPreferences.getInstance().getConversionData();
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        presenter = new CountryAcivitiesPresenter(context);
        presenter.setView(this);
        textViewCity = findViewById(R.id.textView_city);
        textViewCountry = findViewById(R.id.textView_country);
        textViewState = findViewById(R.id.textView_state);
        textViewDay = findViewById(R.id.textView_day);
        textViewTitle = findViewById(R.id.textView_title);
        buttonDone = findViewById(R.id.button_ok);
        textViewCountry.setText(contryName);
        buttonDone.setOnClickListener(this);
        textViewDay.setOnClickListener(this);
        textViewCountry.setOnClickListener(this);
        textViewCity.setOnClickListener(this);
        textViewState.setOnClickListener(this);
        setUpLocalConverson();
    }

    private void setUpLocalConverson() {
        textViewDay.setHint(languageResponseData.getDay());
        textViewState.setHint(languageResponseData.getState());
        textViewCountry.setHint(languageResponseData.getCountry());
        textViewCity.setHint(languageResponseData.getCity());
        textViewTitle.setText(languageResponseData.getFilter());
        buttonDone.setText(languageResponseData.getDone());
        weekDayModelList = new ArrayList<>();
        weekDayModelList.add(new WeekDayModel(languageResponseData.getMon(), "2"));
        weekDayModelList.add(new WeekDayModel(languageResponseData.getTue(), "3"));
        weekDayModelList.add(new WeekDayModel(languageResponseData.getWed(), "4"));
        weekDayModelList.add(new WeekDayModel(languageResponseData.getThu(), "5"));
        weekDayModelList.add(new WeekDayModel(languageResponseData.getFri(), "6"));
        weekDayModelList.add(new WeekDayModel(languageResponseData.getSat(), "7"));
        weekDayModelList.add(new WeekDayModel(languageResponseData.getSun(), "1"));
    }


    @Override
    public void showProgress() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(context);
            progressDialog.setCancelable(false);
        }
        progressDialog.show();
    }

    @Override
    public void hideProgress() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    @Override
    public void setCountyAcivityData(CounActivtyResponseData data) {

    }

    @Override
    public void setCountryData(final List<CountryResponseData> data) {
        countryStatePicker = new CountryStatePicker(context, new CountryStatePicker.PickerListner() {
            @Override
            public void onPick(CountryResponseData country) {
                textViewCountry.setText(country.getName());
                if (!country.getId().equalsIgnoreCase(FilterPicker.this.country)) {
                    textViewState.setText("");
                    textViewCity.setText("");
                    state = "";
                    city = "";
                }
                FilterPicker.this.country = country.getId();
                countryCode = country.getCountrycode();
                contryName = country.getName();
                countryStatePicker.dismiss();
            }
        }, data);
        countryStatePicker.show();
    }

    @Override
    public void setStateData(List<CountryResponseData> data) {
        countryStatePicker = new CountryStatePicker(context, new CountryStatePicker.PickerListner() {
            @Override
            public void onPick(CountryResponseData country) {
                countryStatePicker.dismiss();
                textViewState.setText(country.getName());

                if (!country.getId().equalsIgnoreCase(FilterPicker.this.state)) {
                    textViewCity.setText("");
                    city = "";
                }
                state = country.getId();
            }
        }, data);
        countryStatePicker.show();
    }

    @Override
    public void onUpdateInteresed() {

    }

    @Override
    public void setActivtyDetails(CountryActivityInfoModel data) {

    }

    @Override
    public void setCities(List<CountryResponseData> data) {
        countryStatePicker = new CountryStatePicker(context, new CountryStatePicker.PickerListner() {
            @Override
            public void onPick(CountryResponseData country) {
                textViewCity.setText(country.getName());
                FilterPicker.this.city = country.getId();
                countryStatePicker.dismiss();
            }
        }, data);
        countryStatePicker.show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.textView_country:
                presenter.getCountry();
                break;
            case R.id.textView_city:
                if (country.isEmpty()) {
                    CommonUtils.showToast(context, languageResponseData.getSelect_country());
                    return;
                }
                if (state.isEmpty()) {
                    CommonUtils.showToast(context, languageResponseData.getSelect_state());
                    return;
                }
                HashMap<String, String> map = new HashMap();
                map.put("state_id", state);
                presenter.getCities(map);
                break;
            case R.id.textView_state:
                if (country.isEmpty()) {
                    CommonUtils.showToast(context, languageResponseData.getSelect_country());
                    return;
                }
                HashMap<String, String> map2 = new HashMap();
                map2.put("country_id", country);
                presenter.getStates(map2);
                break;
            case R.id.textView_day:
                showWeekDayPicker();
                break;
            case R.id.button_ok:
                dismiss();
                pickerListner.onPick(country, state, city, day, contryName, countryCode);
                break;
        }
    }

    public interface PickerListner {
        void onPick(String countryId, String stateId, String cityId, String day, String cName, String cCode);
    }

    private void showWeekDayPicker() {
        if (dialog == null) {
            dialog = new SelectionListDialog(context, weekDayModelList, new SelectionListDialog.SelectedListner<WeekDayModel>() {
                @Override
                public void onSelected(int position, WeekDayModel object) {
                    textViewDay.setText(object.getDay());
                    day = object.getIndex();
                }
            });
        }
        dialog.show();
    }

}
