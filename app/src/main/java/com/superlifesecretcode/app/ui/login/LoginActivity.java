package com.superlifesecretcode.app.ui.login;

import android.view.View;
import android.widget.EditText;

import com.superlifesecretcode.app.R;
import com.superlifesecretcode.app.ui.base.BaseActivity;
import com.superlifesecretcode.app.ui.main.MainActivity;
import com.superlifesecretcode.app.util.CommonUtils;

import java.util.HashMap;

public class LoginActivity extends BaseActivity implements View.OnClickListener {
    private EditText editTextMobileNumber, editTextPassword;

    @Override
    protected int getContentView() {
        overridePendingTransition(R.anim.activity_open_translate, R.anim.activity_close_scale);
        return R.layout.activity_login;
    }

    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(R.anim.activity_open_scale, R.anim.activity_close_translate);
    }

    @Override
    protected void initializeView() {
        editTextMobileNumber = findViewById(R.id.edi_text_phone);
        editTextPassword = findViewById(R.id.edit_text_password);
        findViewById(R.id.button_login).setOnClickListener(this);
    }

    @Override
    protected void initializePresenter() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_login:
                validateAndLogin();
                CommonUtils.startActivity(this, MainActivity.class, null, true);
                break;
        }
    }

    private void validateAndLogin() {
        String mobileNumber = editTextMobileNumber.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        if (mobileNumber.isEmpty()){
            editTextMobileNumber.setError("");
            return;
        }
        if (password.isEmpty()){
            editTextPassword.setError("");
            return;
        }

    }
}
