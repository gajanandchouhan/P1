package com.superlifesecretcode.app.ui.sharing_latest;

import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.superlifesecretcode.app.R;
import com.superlifesecretcode.app.data.model.country.CountryResponseData;
import com.superlifesecretcode.app.data.model.language.LanguageResponseData;
import com.superlifesecretcode.app.data.model.shares.ShareListResponseData;
import com.superlifesecretcode.app.data.model.userdetails.UserDetailResponseData;
import com.superlifesecretcode.app.data.persistance.SuperLifeSecretPreferences;
import com.superlifesecretcode.app.ui.base.BaseActivity;
import com.superlifesecretcode.app.ui.picker.CountryStatePicker;
import com.superlifesecretcode.app.ui.sharing_submit.ShareListPresenter;
import com.superlifesecretcode.app.ui.sharing_submit.ShareListView;
import com.superlifesecretcode.app.util.CommonUtils;
import com.superlifesecretcode.app.util.OnLoadMoreListener;
import com.superlifesecretcode.app.util.PermissionConstant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LatestActivity extends BaseActivity implements ShareListView, View.OnClickListener {


    public static String liked;
    public static String likedCOunt;
    private LatestAapter latestAapter;
    private UserDetailResponseData userData;
    private LanguageResponseData conversionData;
    private ShareListPresenter presenter;
    private List<ShareListResponseData> shareList;
    private int position;
    private String like;
    public static boolean isUpdated = false;
    private ImageView imageViewProfile;
    private String countryId = "";
    CountryStatePicker countryStatePicker;
    private boolean isLoadMore = false;
    private RecyclerView recyclerView;
    private int nextPage;
    private int clickedPos;

    @Override
    protected int getContentView() {
        return R.layout.activity_latest;
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void initializeView() {
        userData = SuperLifeSecretPreferences.getInstance().getUserData();
        conversionData = SuperLifeSecretPreferences.getInstance().getConversionData();
        setUpToolbar();
        recyclerView = findViewById(R.id.recycler_view);
        imageViewProfile = findViewById(R.id.imageView_profile);
        imageViewProfile.setVisibility(View.VISIBLE);
        imageViewProfile.setImageResource(R.drawable.filter);
        imageViewProfile.setOnClickListener(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        shareList = new ArrayList<>();
        latestAapter = new LatestAapter(shareList, this, conversionData);
        recyclerView.setAdapter(latestAapter);
        latestAapter.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                if (nextPage != -1) {
                    isLoadMore = true;
                    shareList.add(null);
                    recyclerView.post(new Runnable() {
                        public void run() {
                            latestAapter.notifyItemInserted(shareList.size() - 1);
                            getAllLatestShare(nextPage);
                        }
                    });

                }
            }
        });
        nextPage = 1;
        getAllLatestShare(nextPage);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isUpdated) {
            isUpdated = false;
            isLoadMore = false;
            shareList.get(clickedPos).setLiked_by_user(liked);
            shareList.get(clickedPos).setLiked_by(likedCOunt);
            latestAapter.notifyItemChanged(clickedPos);
           /* nextPage = 1;
            getAllLatestShare(nextPage);*/
        }
    }

    private void getAllLatestShare(int index) {
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + userData.getApi_token());
        presenter.getAllLatestShare(headers, countryId, isLoadMore, String.valueOf(index));
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

    public void likeShare(int position, String like, String id) {
        this.position = position;
        this.like = like;
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + userData.getApi_token());
        HashMap<String, String> params = new HashMap<>();
        params.put("sharing_id", id);
        params.put("liked_by", userData.getUser_id());
        params.put("like", like);
        presenter.likeShare(params, headers);
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
    public void setShareListData(List<ShareListResponseData> listData, String nextPage) {
        if (listData != null) {
            if (nextPage != null) {
                this.nextPage = Integer.parseInt(Uri.parse(nextPage).getQueryParameter("page"));
            } else {
                this.nextPage = -1;
            }
            if (isLoadMore) {
                latestAapter.setLoaded();
                shareList.remove(shareList.size() - 1);
                latestAapter.notifyItemRemoved(shareList.size());
            } else {
                latestAapter.setLoaded();
                shareList.clear();
                latestAapter.setUpLoadMore(recyclerView);
            }
            shareList.addAll(listData);
            latestAapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onLiked() {
        if (like != null && like.equalsIgnoreCase("1")) {
            String liked_by = shareList.get(position).getLiked_by();
            if (liked_by != null) {
                int likedBy = (Integer.parseInt(liked_by)) + 1;
                shareList.get(position).setLiked_by(String.valueOf(likedBy));
            }
        } else {
            String liked_by = shareList.get(position).getLiked_by();
            if (liked_by != null) {
                int likedBy = (Integer.parseInt(liked_by)) - 1;
                shareList.get(position).setLiked_by(String.valueOf(likedBy));
            }
        }
        shareList.get(position).setLiked_by_user(like);
        latestAapter.notifyDataSetChanged();
    }


    @Override
    public void setCountryData(List<CountryResponseData> data) {
        if (data != null) {
            countryStatePicker = new CountryStatePicker(this, new CountryStatePicker.PickerListner() {
                @Override
                public void onPick(CountryResponseData country) {
                    countryId = country.getId();
                    countryStatePicker.dismiss();
                    isLoadMore = false;
                    nextPage = 1;
                    getAllLatestShare(nextPage);
                }
            }, data);
            countryStatePicker.show();
        }
    }

    public void shareImage(String file) {
        if (CommonUtils.hasPermissions(this, PermissionConstant.PERMISSION_PROFILE)) {
            CommonUtils.shareImage(file, this);
        } else {
            ActivityCompat.requestPermissions(this, PermissionConstant.PERMISSION_PROFILE, PermissionConstant.CODE_PROFILE);
        }

    }

    public void shareImageAndText(String image, String text) {
        if (CommonUtils.hasPermissions(this, PermissionConstant.PERMISSION_PROFILE)) {
            CommonUtils.shareImageWithContent(image, text, this);
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imageView_profile:
                presenter.getCountry();
                break;
        }
    }

    public void setClickedPosition(int adapterPosition) {
        clickedPos=adapterPosition;
    }
}
