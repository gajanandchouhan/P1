package com.superlifesecretcode.app.ui.forgotpassword;

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

public class ResetPaswordActivity extends BaseActivity implements View.OnClickListener, ForgotPasswordView {
    private EditText editTextCode, editTextPassword, editTextConfirmPassword;
    Button buttonResetPassword;
    private ForgotPasswordPresenter presenter;
    private LanguageResponseData conversionData;
    private String email;

    @Override
    protected int getContentView() {
        return R.layout.activity_reset_password;
    }


    @Override
    protected void initializeView() {
        editTextCode = findViewById(R.id.edit_text_code);
        editTextPassword = findViewById(R.id.edit_text_password);
        editTextConfirmPassword = findViewById(R.id.edit_text_cpassword);
        buttonResetPassword = findViewById(R.id.button_reset_password);
        buttonResetPassword.setOnClickListener(this);
        email = getIntent().getBundleExtra("bundle").getString("email");
        setUpConversion();
        setUpToolbar();
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
            case R.id.button_reset_password:
                validateAndGetCode();
//                CommonUtils.startActivity(this, MainActivity.class, null, true);
                break;
        }
    }

    private void validateAndGetCode() {
        String code = editTextCode.getText().toString().trim();
        String newPass = editTextPassword.getText().toString().trim();
        String confirmPass = editTextConfirmPassword.getText().toString().trim();
        if (code.isEmpty()) {
            editTextCode.setError("Please enter reset code.");
            return;
        }
        if (newPass.length() < 6) {
            editTextPassword.setError(conversionData.getPassword_length());
            return;
        }
        if (!newPass.equalsIgnoreCase(confirmPass)) {
            editTextConfirmPassword.setError("Password not matching");
            return;
        }
        HashMap<String, String> body = new HashMap<>();
        body.put("email", email);
        body.put("password", newPass);
        body.put("code", code);
        presenter.resetPassword(body);
    }


    @Override
    public void onTempPassSuccess() {

    }

    @Override
    public void onPasswrodChanged() {
        finish();
        ForgotPaswordActivity.finishActivty();
    }
}
