package com.superlifesecretcode.app.ui.news;

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
    private TextView textViewUnread;

    @Override
    protected int getContentView() {
        overridePendingTransition(R.anim.activity_open_translate, R.anim.activity_close_scale);
        return R.layout.activity_news;
    }

    @Override
    protected void initializeView() {
        textViewUnread = findViewById(R.id.textView_unread_count);
        userDetailResponseData = SuperLifeSecretPreferences.getInstance().getUserData();
        conversionData = SuperLifeSecretPreferences.getInstance().getConversionData();
        setUpToolbar();
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        newsList = new ArrayList<>();
        newsAapter = new NewsAapter(newsList, this);
        recyclerView.setAdapter(newsAapter);

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
        overridePendingTransition(R.anim.activity_open_scale, R.anim.activity_close_translate);
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
            textViewUnread.setText(String.valueOf(newsResponseModel.getUnread()));
        }
    }
}
