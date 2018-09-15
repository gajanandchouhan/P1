package com.superlifesecretcode.app.ui.studygroup;

import android.view.View;

import com.superlifesecretcode.app.R;
import com.superlifesecretcode.app.ui.base.BaseActivity;
import com.superlifesecretcode.app.ui.studygroup.planlist.SubscriptionPlanListActivity;
import com.superlifesecretcode.app.ui.studygroup.studygroupdetails.StudyGroupDetailsActivity;

public class ThanksActivity extends BaseActivity {
    @Override
    protected int getContentView() {
        return R.layout.activity_thanks;
    }

    @Override
    protected void initializeView() {
        findViewById(R.id.textview_continue).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SubscriptionPlanListActivity.finishActivity();
                StudyGroupDetailsActivity.finishActivity();
                StudyGroupActivity.shouldRefresh = true;
                finish();
            }
        });

    }

    @Override
    protected void initializePresenter() {

    }
}
