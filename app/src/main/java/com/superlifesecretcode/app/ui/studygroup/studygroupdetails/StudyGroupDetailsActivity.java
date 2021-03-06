package com.superlifesecretcode.app.ui.studygroup.studygroupdetails;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.superlifesecretcode.app.R;
import com.superlifesecretcode.app.data.model.language.LanguageResponseData;
import com.superlifesecretcode.app.data.model.studygroups.StudyGroupDetails;
import com.superlifesecretcode.app.data.model.studygroups.studygroupitem.StudyGroupItemData;
import com.superlifesecretcode.app.data.model.userdetails.UserDetailResponseData;
import com.superlifesecretcode.app.data.persistance.SuperLifeSecretPreferences;
import com.superlifesecretcode.app.ui.base.BaseActivity;
import com.superlifesecretcode.app.ui.picker.AlertDialog;
import com.superlifesecretcode.app.ui.studygroup.StudyGroupActivity;
import com.superlifesecretcode.app.ui.studygroup.planlist.SubscriptionPlanListActivity;
import com.superlifesecretcode.app.util.CommonUtils;
import com.superlifesecretcode.app.util.ConstantLib;
import com.superlifesecretcode.app.util.ImageLoadUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StudyGroupDetailsActivity extends BaseActivity implements StudyGroupDetailView, View.OnClickListener {


    private LanguageResponseData conversionData;
    private UserDetailResponseData userData;
    private RecyclerView recyclerView;
    private List<StudyGroupItemData> list;
    private StudyGroupDetails studyGroupDetails;
    private TextView textViewTitle, textViewDesc, textViewReason, textViewExpiry;
    private ImageView imageView;
    private Button buttonSubscribe;
    private StudyGroupItemAdapter adapter;
    StudyGroupDetailPresenter presenter;

    //private static StudyGroupDetailsActivity activity;

    @Override
    protected int getContentView() {
        return R.layout.activity_study_group_details;
    }

    @Override
    protected void initializeView() {
        // activity = this;
        conversionData = SuperLifeSecretPreferences.getInstance().getConversionData();
        userData = SuperLifeSecretPreferences.getInstance().getUserData();
        studyGroupDetails = (StudyGroupDetails) getIntent().getBundleExtra("bundle").getSerializable("data");
        setUpToolbar();
        textViewTitle = findViewById(R.id.textView_group_title);
        textViewDesc = findViewById(R.id.text_view_desc);
        buttonSubscribe = findViewById(R.id.button_subscribe);
        textViewReason = findViewById(R.id.text_view_reason);
        imageView = findViewById(R.id.image_view_group);
        textViewExpiry = findViewById(R.id.text_view_expiry);
        buttonSubscribe.setOnClickListener(this);
        findViewById(R.id.text_view_reason).setOnClickListener(this);
        list = new ArrayList<>();
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new StudyGroupItemAdapter(this, list);
        recyclerView.setAdapter(adapter);
        if (studyGroupDetails != null) {
            setDetails();
            Map<String, String> headers = new HashMap<>();
            headers.put("Authorization", "Bearer " + userData.getApi_token());
            HashMap<String, String> params = new HashMap<>();
            params.put("study_group_id", studyGroupDetails.getGroup_id());
            Log.e("getGroup_id", "" + studyGroupDetails.getGroup_id());

            presenter.getGroupItems(params, headers);
            adapter.setSubscirptionStatus(studyGroupDetails.getSubcription_status());
        }

    }


//    public static void finishActivity() {
//        if (activity != null) {
//            activity.finish();
//        }
//    }

    private void setDetails() {
        textViewTitle.setText(studyGroupDetails.getGroup_name());
        textViewDesc.setText(studyGroupDetails.getGroup_description());
        ImageLoadUtils.loadImage(studyGroupDetails.getGroup_image(), imageView);
        switch (studyGroupDetails.getSubcription_status()) {
            case ConstantLib.STATUS_GROUP_NEW:
                buttonSubscribe.setText(conversionData.getSubscribe());
                textViewReason.setVisibility(View.GONE);
                textViewExpiry.setVisibility(View.GONE);
                break;
            case ConstantLib.STATUS_GROUP_SUBSCRIBED:
                buttonSubscribe.setText(conversionData.getSubscribed());
                textViewReason.setVisibility(View.GONE);
                textViewExpiry.setVisibility(View.VISIBLE);
                textViewExpiry.setText(String.format("%s: %s", conversionData.getExpire_on(), CommonUtils.getformattedDateFromString(ConstantLib.INPUT_DATE_TIME_FORMATE, "dd, MMM, yyyy", studyGroupDetails.getExpiry_date(), false, null)));
                break;
            case ConstantLib.STATUS_GROUP_PENDING:
                buttonSubscribe.setText(conversionData.getPending());
                textViewExpiry.setVisibility(View.GONE);
                break;
            case ConstantLib.STATUS_GROUP_EXPIRED:
                buttonSubscribe.setText(conversionData.getRenew());
                textViewReason.setVisibility(View.GONE);
                textViewExpiry.setVisibility(View.VISIBLE);
                textViewExpiry.setText(String.format("%s: %s", conversionData.getExpire_on(), CommonUtils.getformattedDateFromString(ConstantLib.INPUT_DATE_TIME_FORMATE, "dd, MMM, yyyy", studyGroupDetails.getExpiry_date(), false, null)));
                break;
            case ConstantLib.STATUS_GROUP_REJECTED:
                buttonSubscribe.setText(conversionData.getSubscribe());
                textViewReason.setVisibility(View.VISIBLE);
                textViewExpiry.setVisibility(View.VISIBLE);
                textViewExpiry.setText(conversionData.getRejected());
                break;
        }
    }

    @Override
    protected void initializePresenter() {
        presenter = new StudyGroupDetailPresenter(this);
        presenter.setView(this);
    }

    private void setUpToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        TextView textViewTitle = findViewById(R.id.textView_title);
        if (conversionData != null)
            textViewTitle.setText(conversionData.getStudy_group());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setItemList(List<StudyGroupItemData> data) {
        if (data != null) {
            list.clear();
            list.addAll(data);
            adapter.notifyDataSetChanged();
            //setDuration();
        }
    }


    private void setDuration() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (StudyGroupItemData studyGroupItemData : list) {
                    if (studyGroupItemData.getItem_type_id().equals(ConstantLib.TYPE_AUDIO_ITEM) || studyGroupItemData.getItem_type_id().equals(ConstantLib.TYPE_VIDEO_ITEM)) {
                        studyGroupItemData.setDuration(CommonUtils.getDuration(studyGroupItemData.getItem_url()));
                    }
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.notifyDataSetChanged();
                    }
                });
            }
        }).start();


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_subscribe:
                if (studyGroupDetails.getSubcription_status().equals(ConstantLib.STATUS_GROUP_PENDING) || studyGroupDetails.getSubcription_status().equals(ConstantLib.STATUS_GROUP_SUBSCRIBED)) {
                    return;
                }
                openPlanSelectionActivity();
                break;
            case R.id.text_view_reason:
                CommonUtils.showAlert(this, studyGroupDetails.getReason(), SuperLifeSecretPreferences.getInstance().getConversionData().getOk(), null, new AlertDialog.OnClickListner() {
                    @Override
                    public void onPositiveClick() {

                    }

                    @Override
                    public void onNegativeClick() {

                    }
                });
                break;
        }
    }

    private void openPlanSelectionActivity() {
        if (studyGroupDetails != null) {
            Intent intent = new Intent(this, SubscriptionPlanListActivity.class);
            intent.putExtra("title", studyGroupDetails.getGroup_name());
            intent.putExtra("id", studyGroupDetails.getGroup_id());
            startActivity(intent);
        }
    }

    public void showAlertSubscriptionStatus() {
        if (studyGroupDetails.getSubcription_status().equals(ConstantLib.STATUS_GROUP_NEW)) {
            showAlert(conversionData.getSubscribe(), conversionData.getCancel(), conversionData.getNeed_subscribe_first());
        } else if (studyGroupDetails.getSubcription_status().equals(ConstantLib.STATUS_GROUP_PENDING)) {
            showAlert(conversionData.getOk(), null, conversionData.getSubscription_pending_for_approval());
        } else if (studyGroupDetails.getSubcription_status().equals(ConstantLib.STATUS_GROUP_EXPIRED)) {
            showAlert(conversionData.getRenew(), conversionData.getCancel(), conversionData.getSubscription_plan_expired());
        } else if (studyGroupDetails.getSubcription_status().equals(ConstantLib.STATUS_GROUP_REJECTED)) {
            showAlert(conversionData.getSubscribe(), conversionData.getCancel(), conversionData.getSubscription_plan_rejected());
        }
    }

    private void showAlert(String positive, final String negative, String message) {
        CommonUtils.showAlert(this, message, positive, negative, new AlertDialog.OnClickListner() {
            @Override
            public void onPositiveClick() {
                if (negative != null) {
                    openPlanSelectionActivity();
                }
            }

            @Override
            public void onNegativeClick() {

            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (StudyGroupActivity.shouldRefresh == true) {
            finish();
        }
    }
}
