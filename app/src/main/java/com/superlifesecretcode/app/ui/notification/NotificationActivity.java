package com.superlifesecretcode.app.ui.notification;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.superlifesecretcode.app.R;
import com.superlifesecretcode.app.data.model.language.LanguageResponseData;
import com.superlifesecretcode.app.data.model.news.NewsResponseData;
import com.superlifesecretcode.app.data.model.userdetails.UserDetailResponseData;
import com.superlifesecretcode.app.data.persistance.SuperLifeSecretPreferences;
import com.superlifesecretcode.app.ui.base.BaseActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NotificationActivity extends BaseActivity implements NotificationView {

    private RecyclerView recyclerView;
    private NotificationAapter newsAapter;
    private LanguageResponseData conversionData;
    private NotificationPresenter presenter;
    private UserDetailResponseData userDetailResponseData;
    private List<NewsResponseData> newsList;

    @Override
    protected int getContentView() {
        return R.layout.activity_notification;
    }

    @Override
    protected void initializeView() {
        userDetailResponseData = SuperLifeSecretPreferences.getInstance().getUserData();
        conversionData = SuperLifeSecretPreferences.getInstance().getConversionData();
        setUpToolbar();
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        newsList = new ArrayList<>();
        newsAapter = new NotificationAapter(newsList, this);
        recyclerView.setAdapter(newsAapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
//        getNews();
    }

    private void getNews() {
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + userDetailResponseData.getApi_token());
        HashMap<String, String> params = new HashMap<>();
        params.put("announcement_type", "2");
        presenter.getNotification(params, headers);
    }

    @Override
    protected void initializePresenter() {
        presenter = new NotificationPresenter(this);
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
            textViewTitle.setText("Notifications");
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


}
