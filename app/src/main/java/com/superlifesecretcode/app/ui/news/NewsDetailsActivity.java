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
import com.superlifesecretcode.app.data.model.userdetails.UserDetailResponseData;
import com.superlifesecretcode.app.data.persistance.SuperLifeSecretPreferences;
import com.superlifesecretcode.app.ui.base.BaseActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NewsDetailsActivity extends BaseActivity implements NewsDetailsView {


    private ViewPager pager;
    private NewsPagerAdapter newsAapter;
    private LanguageResponseData conversionData;
    private List<NewsResponseData> list;
    private UserDetailResponseData userDetailsResponseData;
    private int postion;
    private NewsDetailsPresenter presenter;
    private int position;
    private String like;

    @Override
    protected int getContentView() {
        return R.layout.activity_news_details;
    }

    @Override
    protected void initializeView() {
        conversionData = SuperLifeSecretPreferences.getInstance().getConversionData();
        userDetailsResponseData = SuperLifeSecretPreferences.getInstance().getUserData();
        Bundle bundle = getIntent().getBundleExtra("bundle");
        postion = bundle.getInt("position");
        list = (List<NewsResponseData>) bundle.getSerializable("news");
        setUpToolbar();
        pager = findViewById(R.id.pager);
        if (list != null) {
            newsAapter = new NewsPagerAdapter(this, list, conversionData);
            pager.setAdapter(newsAapter);
            pager.setCurrentItem(postion);
        }
        String readed_by_user = list.get(postion).getReaded_by_user();
        if (readed_by_user.equalsIgnoreCase("0")) {
            markRead();
        }

        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                String readed_by_user1 = list.get(position).getReaded_by_user();
                if (readed_by_user1.equalsIgnoreCase("0")) {
                    markRead();
                }
                postion = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void markRead() {
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + userDetailsResponseData.getApi_token());
        HashMap<String, String> params = new HashMap<>();
        params.put("announcement_id", list.get(postion).getAnnouncement_id());
        presenter.readMark(params, headers);
    }

    public void likeNews(int position, String like, String id) {
        this.position = position;
        this.like = like;
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + userDetailsResponseData.getApi_token());
        HashMap<String, String> params = new HashMap<>();
        params.put("announcement_id", id);
        params.put("liked_by", userDetailsResponseData.getUser_id());
        params.put("like", like);
        presenter.likeMark(params, headers);
    }

    @Override
    protected void initializePresenter() {
        presenter = new NewsDetailsPresenter(this);
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
    public void onNewsReaded() {
        list.get(postion).setReaded_by_user("1");
        newsAapter.notifyDataSetChanged();
    }

    @Override
    public void onLiked() {
        if (like != null && like.equalsIgnoreCase("1")) {
            String liked_by = list.get(position).getLiked_by();
            if (liked_by != null) {
                int likedBy = (Integer.parseInt(liked_by)) + 1;
                list.get(position).setLiked_by(String.valueOf(likedBy));
            }
        } else {
            String liked_by = list.get(position).getLiked_by();
            if (liked_by != null) {
                int likedBy = (Integer.parseInt(liked_by)) - 1;
                list.get(position).setLiked_by(String.valueOf(likedBy));
            }
        }
        list.get(postion).setLiked_by_user(like);
        newsAapter.notifyDataSetChanged();
    }
}
