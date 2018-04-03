package com.superlifesecretcode.app.ui.picker;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.superlifesecretcode.app.R;
import com.superlifesecretcode.app.util.CommonUtils;

/**
 * Created by Divya on 22-11-2017.
 */

public class TimeEditPicker extends Dialog implements View.OnClickListener {
    private final Context context;
    private Button buttonOk;
    private EditText editTextTime;
    private OnClickListner onClickListner;
    private String time;


    public TimeEditPicker(@NonNull Context context, String time) {
        super(context);
        this.context = context;
        this.time = time;
        initView();
    }

    public void setOnClickListner(OnClickListner onClickListner) {
        this.onClickListner = onClickListner;
    }

    private void initView() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_time_edit);
        getWindow().setBackgroundDrawableResource(R.drawable.alert_bg);
        getWindow().setLayout(CommonUtils.getScreenWidth(context) - 100, WindowManager.LayoutParams.WRAP_CONTENT);
        buttonOk = findViewById(R.id.button_update);
        buttonOk.setOnClickListener(this);
        editTextTime = findViewById(R.id.edit_text_reminder);
        editTextTime.setText(time);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_update:
                String time = editTextTime.getText().toString().trim();
                if (time.isEmpty()) {
                    dismiss();
                    return;
                }
                dismiss();
                onClickListner.onInputDone(time);
                break;

        }
    }


    public interface OnClickListner {
        void onInputDone(String time);
    }


}
