package com.superlifesecretcode.app.ui.login;

import android.view.View;

import com.superlifesecretcode.app.R;
import com.superlifesecretcode.app.ui.base.BaseActivity;
import com.superlifesecretcode.app.ui.main.MainActivity;
import com.superlifesecretcode.app.util.CommonUtils;

public class LoginActivity extends BaseActivity implements View.OnClickListener {


    @Override
    protected int getContentView() {
        return R.layout.activity_login;
    }

    @Override
    protected void initializeView() {
        findViewById(R.id.button_login).setOnClickListener(this);
    }

    @Override
    protected void initializePresenter() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_login:
                CommonUtils.startActivity(this, MainActivity.class, null, true);
                break;
        }
    }
}
