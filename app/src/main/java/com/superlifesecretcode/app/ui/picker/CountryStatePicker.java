package com.superlifesecretcode.app.ui.picker;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;

import com.dining.countrypicker.Country;
import com.superlifesecretcode.app.R;
import com.superlifesecretcode.app.data.model.country.CountryResponseData;
import com.superlifesecretcode.app.ui.adapter.CountryListAdapter;
import com.superlifesecretcode.app.ui.adapter.CountryStateListAdapter;
import com.superlifesecretcode.app.util.SpacesItemDecoration;

import java.util.List;

/**
 * Created by Divya on 04-12-2017.
 */

public class CountryStatePicker extends Dialog {
    private final Context context;
    private final List<CountryResponseData> list;
    private PickerListner pickerListner;
    private EditText editText;

    public CountryStatePicker(@NonNull Context context, PickerListner pickerListner, List<CountryResponseData> list) {
        super(context, R.style.AppTheme);
        this.context = context;
        this.pickerListner = pickerListner;
        this.list=list;
        init(context);
    }

    private void init(Context context) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.layout_country_picker);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        editText = findViewById(R.id.country_code_picker_search);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.addItemDecoration(new SpacesItemDecoration(4));
        final CountryStateListAdapter countryListAdapter = new CountryStateListAdapter(context, pickerListner,list);
        recyclerView.setAdapter(countryListAdapter);

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String trim = s.toString().trim();
                countryListAdapter.getFilter().filter(trim.toLowerCase());

            }
        });

    }

    public interface PickerListner {
        void onPick(CountryResponseData country);
    }
}
