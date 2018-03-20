package com.superlifesecretcode.app.ui.sharing_latest;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.superlifesecretcode.app.R;
import com.superlifesecretcode.app.data.model.language.LanguageResponseData;
import com.superlifesecretcode.app.data.model.shares.ShareListResponseData;
import com.superlifesecretcode.app.data.model.userdetails.UserDetailResponseData;
import com.superlifesecretcode.app.data.persistance.SuperLifeSecretPreferences;
import com.superlifesecretcode.app.ui.base.BaseActivity;
import com.superlifesecretcode.app.util.CommonUtils;
import com.superlifesecretcode.app.util.ConstantLib;
import com.superlifesecretcode.app.util.ImageLoadUtils;

import java.util.ArrayList;

public class LatestDetailsActivity extends BaseActivity {


    private UserDetailResponseData userData;
    private LanguageResponseData conversionData;
    private ViewPager pager;
    TextView textViewName;
    TextView textViewDateTime;
    TextView textViewCountryName;
    TextView textViewStatus;
    TextView textViewDesc;
    ImageView imageView;
    RelativeLayout layoutLikeShare;
    private ShareListResponseData data;
    private boolean from_submit;

    @Override
    protected int getContentView() {
        overridePendingTransition(R.anim.activity_open_translate, R.anim.activity_close_scale);
        return R.layout.activity_latest_details;
    }

    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(R.anim.activity_open_scale, R.anim.activity_close_translate);
    }

    @Override
    protected void initializeView() {
        Bundle bundle = getIntent().getBundleExtra("bundle");
        data = (ShareListResponseData) bundle.getSerializable("share");
        from_submit = bundle.getBoolean("from_submit");
        pager = findViewById(R.id.pager);
        layoutLikeShare = findViewById(R.id.like_share_layout);
        textViewCountryName = findViewById(R.id.textView_country);
        textViewStatus = findViewById(R.id.textView_status);
        textViewDateTime = findViewById(R.id.textView_date_time);
        textViewDesc = findViewById(R.id.textView_desc);
        textViewName = findViewById(R.id.textView_name);
        imageView = findViewById(R.id.imageView_user);
        pager.getLayoutParams().width = CommonUtils.getScreenWidth(this);
        pager.getLayoutParams().height = CommonUtils.getScreenWidth(this) * 9 / 16;


        userData = SuperLifeSecretPreferences.getInstance().getUserData();
        conversionData = SuperLifeSecretPreferences.getInstance().getConversionData();
        setUpToolbar();
        setUpDetails();
    }

    private void setUpDetails() {
        if (data.getSharing_files() != null) {
            pager.setVisibility(View.VISIBLE);
            pager.setAdapter(new LatestPagerAdapter(this, data.getSharing_files()));
        } else {
            pager.setVisibility(View.GONE);
        }
        if (from_submit) {
            textViewStatus.setVisibility(View.VISIBLE);
            layoutLikeShare.setVisibility(View.GONE);
            textViewStatus.setText(data.getStatus().equals("2") ? "Rejected" : "Published");
        } else {
            textViewStatus.setVisibility(View.GONE);
            layoutLikeShare.setVisibility(View.VISIBLE);
        }

        textViewName.setText(data.getUsername());
        textViewDateTime.setText(CommonUtils.getformattedDateFromString(ConstantLib.INPUT_DATE_TIME_FORMATE, ConstantLib.OUTPUT_DATE_TIME_FORMATE, data.getCreated_at()));
        textViewDesc.setText(data.getContent());
        textViewCountryName.setText(data.getCountryName());
        ImageLoadUtils.loadImage(data.getUser_image(), imageView);
    }

    private void setUpToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        TextView textViewTitle = findViewById(R.id.textView_title);
        textViewTitle.setText(conversionData != null ? conversionData.getLatest() : "Latest");
    }

    @Override
    protected void initializePresenter() {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}
