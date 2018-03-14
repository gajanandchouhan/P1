package com.superlifesecretcode.app.ui.news;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.superlifesecretcode.app.R;
import com.superlifesecretcode.app.data.model.language.LanguageResponseData;
import com.superlifesecretcode.app.data.model.news.NewsResponseData;
import com.superlifesecretcode.app.data.persistance.SuperLifeSecretPreferences;
import com.superlifesecretcode.app.ui.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

public class NewsDetailsActivity extends BaseActivity {


    private ViewPager pager;
    private NewsPagerAdapter newsAapter;
    private LanguageResponseData conversionData;
    private List<NewsResponseData> list;

    @Override
    protected int getContentView() {
        overridePendingTransition(R.anim.activity_open_translate, R.anim.activity_close_scale);
        return R.layout.activity_news_details;
    }

    @Override
    protected void initializeView() {
        conversionData = SuperLifeSecretPreferences.getInstance().getConversionData();
        Bundle bundle = getIntent().getBundleExtra("bundle");
        int postion = bundle.getInt("position");
        list = (List<NewsResponseData>) bundle.getSerializable("news");
        setUpToolbar();
        pager = findViewById(R.id.pager);
        if (list != null) {
            newsAapter = new NewsPagerAdapter(this, list);
            pager.setAdapter(newsAapter);
            pager.setCurrentItem(postion);
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
}
