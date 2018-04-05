package com.superlifesecretcode.app.ui.forgotpassword;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.superlifesecretcode.app.R;
import com.superlifesecretcode.app.SuperLifeSecretCodeApp;
import com.superlifesecretcode.app.data.model.language.LanguageResponseData;
import com.superlifesecretcode.app.ui.base.BaseActivity;
import com.superlifesecretcode.app.util.CommonUtils;

import java.util.HashMap;

public class ForgotPaswordActivity extends BaseActivity implements View.OnClickListener, ForgotPasswordView {
    private EditText editTextEmail;
    Button buttonSendCode;
    private String endterEmail, endterValidEmail;
    private ForgotPasswordPresenter presenter;
    private LanguageResponseData conversionData;
    static ForgotPaswordActivity activity;
    private String email;

    @Override
    protected int getContentView() {
        return R.layout.activity_forgot_password;
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void initializeView() {
        activity = this;
        editTextEmail = findViewById(R.id.edit_text_email);
        buttonSendCode = findViewById(R.id.button_get_code);
        buttonSendCode.setOnClickListener(this);
        setUpConversion();
        setUpToolbar();
    }

    public static void finishActivty() {
        if (activity != null) {
            activity.finish();
        }
    }

    private void setUpToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        TextView textViewTitle = findViewById(R.id.textView_title);
        textViewTitle.setText(conversionData != null ? conversionData.getForgot_password() : "Forgot Password");
    }

    private void setUpConversion() {
        conversionData = SuperLifeSecretCodeApp.getInstance().getConversionData();
        if (conversionData != null) {
            editTextEmail.setHint(conversionData.getEmail());
            endterEmail = conversionData.getEnter_email();
            endterValidEmail = conversionData.getEnter_valid_email();
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void initializePresenter() {
        presenter = new ForgotPasswordPresenter(this);
        presenter.setView(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_get_code:
                validateAndGetCode();
//                CommonUtils.startActivity(this, MainActivity.class, null, true);
                break;
        }
    }

    private void validateAndGetCode() {
        email = editTextEmail.getText().toString().trim();
        if (email.isEmpty()) {
            editTextEmail.setError(endterEmail);
            return;
        }
        if (!CommonUtils.isValidEmail(email)) {
            CommonUtils.showSnakeBar(this, endterValidEmail);
            return;
        }
        HashMap<String, String> body = new HashMap<>();
        body.put("email", email);
        presenter.getResetCode(body);
    }


    @Override
    public void onTempPassSuccess() {
        Bundle bundle=new Bundle();
        bundle.putString("email",email);
        CommonUtils.startActivity(this,ResetPaswordActivity.class,bundle,false);
    }

    @Override
    public void onPasswrodChanged() {

    }
}
