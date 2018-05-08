package com.superlifesecretcode.app.ui.picker;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.superlifesecretcode.app.R;
import com.superlifesecretcode.app.data.persistance.SuperLifeSecretPreferences;
import com.superlifesecretcode.app.util.CommonUtils;

/**
 * Created by Divya on 22-11-2017.
 */

public class AlertDialog extends Dialog implements View.OnClickListener {
    private final Context context;
    private final String message;
    private final String positive;
    private final String negatve;
    private Button buttonPositive, buttonNegative, buttonNeutral;
    RelativeLayout layoutPositiveNegative;
    TextView textViewMessage;
    private OnClickListner onClickListner;


    public AlertDialog(@NonNull Context context, String message, String positive, String negative, OnClickListner clickListner) {
        super(context);
        this.context = context;
        this.message = message;
        this.positive = positive;
        this.negatve = negative;
        this.onClickListner = clickListner;
        initView();
    }


    private void initView() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_alert);
        getWindow().setBackgroundDrawableResource(R.drawable.alert_bg);
        getWindow().setLayout(CommonUtils.getScreenWidth(context) - 100, WindowManager.LayoutParams.WRAP_CONTENT);
        buttonPositive = findViewById(R.id.button_positive);
        buttonNegative = findViewById(R.id.button_negative);
        textViewMessage = findViewById(R.id.textView_message);
        TextView textViewTitle = findViewById(R.id.textView_title);
        textViewTitle.setText(SuperLifeSecretPreferences.getInstance().getConversionData().getRichest_life());
        layoutPositiveNegative = findViewById(R.id.layout_positive_negative);
        buttonNeutral = findViewById(R.id.button_neutral);
        buttonPositive.setOnClickListener(this);
        buttonNegative.setOnClickListener(this);
        buttonNeutral.setOnClickListener(this);

        if (negatve != null && !negatve.isEmpty()) {
            buttonPositive.setText(positive);
            buttonNegative.setText(negatve);
        } else {
            layoutPositiveNegative.setVisibility(View.GONE);
            buttonNeutral.setVisibility(View.VISIBLE);
            buttonNeutral.setText(positive);
        }
        textViewMessage.setText(message);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_positive:
            case R.id.button_neutral:
                onClickListner.onPositiveClick();
                break;
            case R.id.button_negative:
                onClickListner.onNegativeClick();
                break;

        }
        dismiss();
    }


    public interface OnClickListner {
        void onPositiveClick();

        void onNegativeClick();
    }


}
