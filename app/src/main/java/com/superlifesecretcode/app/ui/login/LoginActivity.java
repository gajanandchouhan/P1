package com.superlifesecretcode.app.ui.login;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.superlifesecretcode.app.R;
import com.superlifesecretcode.app.SuperLifeSecretCodeApp;
import com.superlifesecretcode.app.data.model.language.LanguageResponseData;
import com.superlifesecretcode.app.data.model.userdetails.UserDetailResponseData;
import com.superlifesecretcode.app.data.persistance.SuperLifeSecretPreferences;
import com.superlifesecretcode.app.ui.base.BaseActivity;
import com.superlifesecretcode.app.ui.main.MainActivity;
import com.superlifesecretcode.app.util.CommonUtils;

import java.util.HashMap;

public class LoginActivity extends BaseActivity implements View.OnClickListener, LoginView {
    private EditText editTextMobileNumber, editTextPassword;
    TextView textViewLabel, textViewDontHaveAnAccount, textViewSignup;
    Button buttonLogin;
    private String eneterMobileNo, enterPassword;
    private LoginPresenter presenter;

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
        buttonLogin = findViewById(R.id.button_login);
        buttonLogin.setOnClickListener(this);
        textViewLabel = findViewById(R.id.textView_label);
        textViewDontHaveAnAccount = findViewById(R.id.textView_dont_have);
        textViewSignup = findViewById(R.id.textView_signup);
        textViewSignup.setOnClickListener(this);
        setUpConversion();
    }

    private void setUpConversion() {
        LanguageResponseData conversionData = SuperLifeSecretCodeApp.getInstance().getConversionData();
        if (conversionData != null) {
            textViewLabel.setText(conversionData.getSigin_in());
            editTextMobileNumber.setHint(conversionData.getMobile_no());
            editTextPassword.setHint(conversionData.getPassword());
            buttonLogin.setText(conversionData.getSigin_in_cotinue());
            textViewSignup.setText(conversionData.getSignup());
            textViewDontHaveAnAccount.setText(String.format("%s ", conversionData.getDont_have_acnt()));
            eneterMobileNo = conversionData.getEnter_mobile_number();
            enterPassword = conversionData.getEnter_password();
        }
    }

    @Override
    protected void initializePresenter() {
        presenter = new LoginPresenter(this);
        presenter.setView(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_login:
                validateAndLogin();
//                CommonUtils.startActivity(this, MainActivity.class, null, true);
                break;
            case R.id.textView_signup:
                onBackPressed();
                break;
        }
    }

    private void validateAndLogin() {
        String mobileNumber = editTextMobileNumber.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        if (mobileNumber.isEmpty()) {
            editTextMobileNumber.setError(eneterMobileNo);
            return;
        }
        if (password.isEmpty()) {
            editTextPassword.setError(enterPassword);
            return;
        }
        HashMap<String,String> body=new HashMap<>();
        body.put("mobile",mobileNumber);
        body.put("password",password);
        presenter.loginUser(body);

    }

    @Override
    public void setUserData(UserDetailResponseData data) {
        if (data != null) {
            SuperLifeSecretPreferences.getInstance().setUserDetails(data);
            CommonUtils.startActivity(this, MainActivity.class, null, true);
        }
    }
}
