package com.superlifesecretcode.app.ui.news;

import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.superlifesecretcode.app.R;
import com.superlifesecretcode.app.data.model.language.LanguageResponseData;
import com.superlifesecretcode.app.data.model.news.NewsResponseData;
import com.superlifesecretcode.app.data.model.news.NewsResponseModel;
import com.superlifesecretcode.app.data.model.userdetails.UserDetailResponseData;
import com.superlifesecretcode.app.data.persistance.SuperLifeSecretPreferences;
import com.superlifesecretcode.app.ui.base.BaseActivity;
import com.superlifesecretcode.app.util.CommonUtils;
import com.superlifesecretcode.app.util.PermissionConstant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NewsActivity extends BaseActivity implements NewsView {

    private RecyclerView recyclerView;
    private NewsAapter newsAapter;
    private LanguageResponseData conversionData;
    private NewsPresenter presenter;
    private UserDetailResponseData userDetailResponseData;
    private List<NewsResponseData> newsList;
    private TextView textViewUnread, textViewTopNews, textViewUnreadLabel;

    @Override
    protected int getContentView() {
        return R.layout.activity_news;
    }

    @Override
    protected void initializeView() {
        textViewUnread = findViewById(R.id.textView_unread_count);
        textViewTopNews = findViewById(R.id.textView_top_news);
        textViewUnreadLabel = findViewById(R.id.textView_unread_label);
        userDetailResponseData = SuperLifeSecretPreferences.getInstance().getUserData();
        conversionData = SuperLifeSecretPreferences.getInstance().getConversionData();
        setUpToolbar();
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        newsList = new ArrayList<>();
        newsAapter = new NewsAapter(newsList, this);
        recyclerView.setAdapter(newsAapter);
        textViewTopNews.setText(conversionData.getTop_news());
        textViewUnreadLabel.setText(conversionData.getUnread_news());
    }

    @Override
    protected void onResume() {
        super.onResume();
        getNews();
    }

    private void getNews() {
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + userDetailResponseData.getApi_token());
        HashMap<String, String> params = new HashMap<>();
        params.put("announcement_type", "2");
        presenter.getNews(params, headers);
    }

    @Override
    protected void initializePresenter() {
        presenter = new NewsPresenter(this);
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
            textViewTitle.setText(conversionData.getNews_update());
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setNewsData(NewsResponseModel newsResponseModel) {
        if (newsResponseModel.getData() != null) {
            newsList.clear();
            newsList.addAll(newsResponseModel.getData());
            newsAapter.notifyDataSetChanged();
            SuperLifeSecretPreferences.getInstance().setNewsUndread(newsResponseModel.getUnread());
            textViewUnread.setText(String.valueOf(newsResponseModel.getUnread()));
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
}
