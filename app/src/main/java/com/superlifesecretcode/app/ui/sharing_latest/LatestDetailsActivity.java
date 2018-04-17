package com.superlifesecretcode.app.ui.sharing_latest;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.superlifesecretcode.app.R;
import com.superlifesecretcode.app.data.model.language.LanguageResponseData;
import com.superlifesecretcode.app.data.model.shares.FileResponseData;
import com.superlifesecretcode.app.data.model.shares.ShareListResponseData;
import com.superlifesecretcode.app.data.model.userdetails.UserDetailResponseData;
import com.superlifesecretcode.app.data.persistance.SuperLifeSecretPreferences;
import com.superlifesecretcode.app.ui.base.BaseActivity;
import com.superlifesecretcode.app.ui.sharing_submit.ShareListPresenter;
import com.superlifesecretcode.app.ui.sharing_submit.ShareListView;
import com.superlifesecretcode.app.util.CommonUtils;
import com.superlifesecretcode.app.util.ConstantLib;
import com.superlifesecretcode.app.util.ImageLoadUtils;
import com.superlifesecretcode.app.util.PermissionConstant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LatestDetailsActivity extends BaseActivity implements View.OnClickListener, ShareListView {


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
    private ImageView imageViewLike;
    private TextView textViewLike;
    private TextView textViewShare;
    private ShareListPresenter presenter;
    private String like;

    @Override
    protected int getContentView() {
        return R.layout.activity_latest_details;
    }

    @Override
    protected void onPause() {
        super.onPause();
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
        imageViewLike = findViewById(R.id.imageView_like);
        textViewLike = findViewById(R.id.textView_like_count);
        textViewShare = findViewById(R.id.textView_share);
        imageView = findViewById(R.id.imageView_user);
        textViewShare.setOnClickListener(this);
        imageViewLike.setOnClickListener(this);
        findViewById(R.id.imageView_share).setOnClickListener(this);
        pager.getLayoutParams().width = CommonUtils.getScreenWidth(this);
        pager.getLayoutParams().height = CommonUtils.getScreenWidth(this) * 9 / 16;
        userData = SuperLifeSecretPreferences.getInstance().getUserData();
        conversionData = SuperLifeSecretPreferences.getInstance().getConversionData();
        setUpToolbar();
        setUpDetails();
        textViewShare.setText(conversionData.getShare());
    }

    private void setUpDetails() {
        if (data.getSharing_files() != null && data.getSharing_files().size() > 0) {
            pager.setVisibility(View.VISIBLE);
            pager.setAdapter(new LatestPagerAdapter(this, data.getSharing_files()));
        } else {
            pager.setVisibility(View.GONE);
        }
        if (from_submit) {
            textViewStatus.setVisibility(View.VISIBLE);
            findViewById(R.id.devider).setVisibility(View.GONE);
            layoutLikeShare.setVisibility(View.GONE);
            textViewStatus.setText(data.getStatus().equals("2") ? "Rejected" : "Published");
        } else {
            textViewStatus.setVisibility(View.GONE);
            findViewById(R.id.devider).setVisibility(View.VISIBLE);
            layoutLikeShare.setVisibility(View.VISIBLE);
        }

        textViewName.setText(data.getUsername());
        textViewDateTime.setText(CommonUtils.getformattedDateFromString(ConstantLib.INPUT_DATE_TIME_FORMATE, ConstantLib.OUTPUT_DATE_TIME_FORMATE, data.getCreated_at(),false,null));
        textViewDesc.setText(data.getContent());
        textViewCountryName.setText(data.getCountryName());
        textViewLike.setText(String.format("%s " + conversionData.getLikes(), data.getLiked_by()));
        imageViewLike.setSelected(data.getLiked_by_user().equalsIgnoreCase("1"));
        ImageLoadUtils.loadImage(data.getUser_image(), imageView);
    }

    private void setUpToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        TextView textViewTitle = findViewById(R.id.textView_title);
        if (from_submit) {
            textViewTitle.setText(conversionData != null ? conversionData.getSubmit() : "Submit");
        } else {
            textViewTitle.setText(conversionData != null ? conversionData.getLatest() : "Latest");
        }
    }

    @Override
    protected void initializePresenter() {
        presenter = new ShareListPresenter(this);
        presenter.setView(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imageView_like:
                likeShare();
                break;
            case R.id.textView_share:
            case R.id.imageView_share:
                List<FileResponseData> sharing_files = data.getSharing_files();
                if (sharing_files != null && sharing_files.size() > 0) {
                    String type = sharing_files.get(pager.getCurrentItem()).getType();
                    if (type.equalsIgnoreCase(ConstantLib.TYPE_IMAGE)) {
                        shareImage(sharing_files.get(pager.getCurrentItem()).getFile());
                    }
                } else {
                    CommonUtils.shareContent(this, data.getContent());
                }
                break;
        }
    }

    public void shareImage(String file) {
        if (CommonUtils.hasPermissions(this, PermissionConstant.PERMISSION_PROFILE)) {
            CommonUtils.shareImage(file, this);
        } else {
            ActivityCompat.requestPermissions(this, PermissionConstant.PERMISSION_PROFILE, PermissionConstant.CODE_PROFILE);
        }

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PermissionConstant.CODE_PROFILE) {
            for (int grantResult : grantResults) {
                if (grantResult != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
            }
        }
    }

    public void likeShare() {
        String liked_by_user = data.getLiked_by_user();
        if (liked_by_user != null && liked_by_user.equalsIgnoreCase("1")) {
            like = "0";
        } else {
            like = "1";

        }
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + userData.getApi_token());
        HashMap<String, String> params = new HashMap<>();
        params.put("sharing_id", data.getSharing_id());
        params.put("liked_by", userData.getUser_id());
        params.put("like", like);
        presenter.likeShare(params, headers);
    }

    @Override
    public void setShareListData(List<ShareListResponseData> listData) {

    }

    @Override
    public void onLiked() {
        if (like != null && like.equalsIgnoreCase("1")) {
            String liked_by = data.getLiked_by();
            if (liked_by != null) {
                int likedBy = (Integer.parseInt(liked_by)) + 1;
                data.setLiked_by(String.valueOf(likedBy));
            }
        } else {
            String liked_by = data.getLiked_by();
            if (liked_by != null) {
                int likedBy = (Integer.parseInt(liked_by)) - 1;
                data.setLiked_by(String.valueOf(likedBy));
            }
        }
        data.setLiked_by_user(like);
        textViewLike.setText(String.format("%s Likes", data.getLiked_by()));
        imageViewLike.setSelected(data.getLiked_by_user().equalsIgnoreCase("1"));
        LatestActivity.isUpdated = true;
    }
}
