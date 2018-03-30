package com.superlifesecretcode.app.ui.picker;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;


import com.superlifesecretcode.app.R;
import com.superlifesecretcode.app.util.CommonUtils;

/**
 * Created by Divya on 22-11-2017.
 */

public class DateRangePicker extends Dialog implements View.OnClickListener {
    private final Context context;
    private Button buttonOk;
    private String fromDate;
    private String toDate;
    private OnClickListner onClickListner;
    private String id = "";
    private TextView textViewFromDate, textViewToDate;
    private EditText editTextName;


    public DateRangePicker(@NonNull Context context) {
        super(context);
        this.context = context;
        initView();
    }

    public void setOnClickListner(OnClickListner onClickListner) {
        this.onClickListner = onClickListner;
    }

    private void initView() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_date_range);
        getWindow().setBackgroundDrawableResource(R.drawable.white_rounded_corner);
        getWindow().setLayout(CommonUtils.getScreenWidth(context) - 100, WindowManager.LayoutParams.WRAP_CONTENT);
        buttonOk = findViewById(R.id.button_ok);
        buttonOk.setOnClickListener(this);
        textViewFromDate = findViewById(R.id.textView_start_date);
        textViewToDate = findViewById(R.id.textView_end_date);
        textViewFromDate.setOnClickListener(this);
        textViewToDate.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_ok:
                if (fromDate == null) {
                    CommonUtils.showToast(context, "Select start date");
                    return;
                }
                if (toDate == null) {
                    CommonUtils.showToast(context, "Select end date");
                    return;
                }

                onClickListner.onDateRnageSelected(fromDate, toDate);
                break;
            case R.id.textView_start_date:
                if (toDate != null) {
                    textViewToDate.setText("");
                    toDate = null;
                }
                showDatePicker(textViewFromDate, 1, fromDate == null ? System.currentTimeMillis() - 1000 : CommonUtils.getDateInMilis(fromDate));
                break;
            case R.id.textView_end_date:
                if (fromDate == null) {
                    CommonUtils.showToast(context, "Please select start date.");
                    return;
                }
                showDatePicker(textViewToDate, 2, CommonUtils.getDateInMilis(fromDate));
                break;
        }
    }


    public interface OnClickListner {
        void onDateRnageSelected(String startDate, String endDate);
    }

    private void showDatePicker(final TextView textView, final int type, long minDate) {
        CommonUtils.showDatePicker(context, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                if (type == 1) {
                    fromDate = CommonUtils.getAppendedDate(i, i1, i2);
                } else {
                    toDate = CommonUtils.getAppendedDate(i, i1, i2);
                }
                textView.setText(CommonUtils.getFromatttedDate(i, i1, i2));
            }
        }, minDate);
    }
}
