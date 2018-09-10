package com.superlifesecretcode.app.ui.studygroup.studygroupdetails;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.superlifesecretcode.app.R;
import com.superlifesecretcode.app.data.model.language.LanguageResponseData;
import com.superlifesecretcode.app.data.model.studygroups.StudyGroupDetails;
import com.superlifesecretcode.app.data.model.userdetails.UserDetailResponseData;
import com.superlifesecretcode.app.data.persistance.SuperLifeSecretPreferences;
import com.superlifesecretcode.app.ui.base.BaseActivity;
import com.superlifesecretcode.app.ui.studygroup.StudyGroupListAdapter;
import com.superlifesecretcode.app.util.ConstantLib;
import com.superlifesecretcode.app.util.ImageLoadUtils;

import java.util.ArrayList;
import java.util.List;

public class StudyGroupDetailsActivity extends BaseActivity {


    private LanguageResponseData conversionData;
    private UserDetailResponseData userData;
    private RecyclerView recyclerView;
    private List list;
    private StudyGroupDetails studyGroupDetails;
    private TextView textViewTitle, textViewDesc, textViewReason, textViewStatus;
    LinearLayout layoutStatus;
    private ImageView imageView;
    private Button buttonSubscribe;

    @Override
    protected int getContentView() {
        return R.layout.activity_study_group_details;
    }

    @Override
    protected void initializeView() {
        conversionData = SuperLifeSecretPreferences.getInstance().getConversionData();
        userData = SuperLifeSecretPreferences.getInstance().getUserData();
        studyGroupDetails = (StudyGroupDetails) getIntent().getBundleExtra("bundle").getSerializable("data");
        setUpToolbar();
        textViewTitle = findViewById(R.id.textView_group_title);
        textViewDesc = findViewById(R.id.text_view_desc);
        buttonSubscribe = findViewById(R.id.button_subscribe);
        textViewStatus = findViewById(R.id.text_view_status);
        textViewReason = findViewById(R.id.text_view_reason);
        imageView = findViewById(R.id.image_view_group);
        layoutStatus = findViewById(R.id.layout_status);
        list = new ArrayList();
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        if (studyGroupDetails != null) {
            setDetails();
        }

    }

    private void setDetails() {
        textViewTitle.setText(studyGroupDetails.getGroup_name());
        textViewDesc.setText(studyGroupDetails.getGroup_description());
        ImageLoadUtils.loadImage(studyGroupDetails.getGroup_image(), imageView);
        switch (studyGroupDetails.getSubcription_status()) {
            case ConstantLib.STATUS_GROUP_NEW:
                layoutStatus.setVisibility(View.GONE);
                buttonSubscribe.setVisibility(View.VISIBLE);
                textViewReason.setVisibility(View.GONE);
                break;
            case ConstantLib.STATUS_GROUP_SUBSCRIBED:
                layoutStatus.setVisibility(View.VISIBLE);
                textViewStatus.setText("Subscribed");
                textViewStatus.setBackgroundResource(R.drawable.bg_published);
                buttonSubscribe.setVisibility(View.GONE);
                textViewReason.setVisibility(View.GONE);
                break;
            case ConstantLib.STATUS_GROUP_PENDING:
                layoutStatus.setVisibility(View.VISIBLE);
                textViewStatus.setText("PENDING");
                textViewStatus.setBackgroundResource(R.drawable.bg_pending);
                buttonSubscribe.setVisibility(View.GONE);
                textViewReason.setVisibility(View.GONE);
                break;
            case ConstantLib.STATUS_GROUP_EXPIRED:
                layoutStatus.setVisibility(View.VISIBLE);
                textViewStatus.setText("Expired");
                textViewStatus.setText("Renew");
                textViewStatus.setBackgroundResource(R.drawable.bg_declined);
                buttonSubscribe.setVisibility(View.VISIBLE);
                textViewReason.setVisibility(View.GONE);
                break;
            case ConstantLib.STATUS_GROUP_REJECTED:
                layoutStatus.setVisibility(View.VISIBLE);
                textViewStatus.setText("Reject");
                textViewStatus.setBackgroundResource(R.drawable.bg_declined);
                buttonSubscribe.setVisibility(View.GONE);
                textViewReason.setVisibility(View.VISIBLE);
                break;
        }
    }

    @Override
    protected void initializePresenter() {

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
}
