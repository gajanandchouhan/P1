package com.superlifesecretcode.app.ui.studygroup;

import android.view.View;
import android.widget.TextView;
import android.view.View;
import android.widget.TextView;
import com.superlifesecretcode.app.R;
import com.superlifesecretcode.app.data.model.language.LanguageResponseData;
import com.superlifesecretcode.app.data.persistance.SuperLifeSecretPreferences;
import com.superlifesecretcode.app.ui.base.BaseActivity;
import com.superlifesecretcode.app.ui.studygroup.planlist.SubscriptionPlanListActivity;
import com.superlifesecretcode.app.ui.studygroup.studygroupdetails.StudyGroupDetailsActivity;

        public class ThanksActivity extends BaseActivity {
            TextView textview_thankyou, textview_ty_line;
            LanguageResponseData languageResponseData;

            @Override
            protected int getContentView() {
                return R.layout.activity_thanks;
            }

            @Override
            protected void initializeView() {
                languageResponseData = SuperLifeSecretPreferences.getInstance().getConversionData();
                textview_thankyou = findViewById(R.id.textview_thankyou);
                textview_ty_line = findViewById(R.id.textview_ty_line);
                textview_thankyou.setText(languageResponseData.getThank_you());
                textview_ty_line.setText(languageResponseData.getThankyou_groupstudy_msg());

                findViewById(R.id.textview_continue).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //SubscriptionPlanListActivity.finishActivity();
                        //StudyGroupDetailsActivity.finishActivity();
                        StudyGroupActivity.shouldRefresh = true;
                        finish();
                    }
                });

            }

            @Override
            protected void initializePresenter() {

            }
        }
