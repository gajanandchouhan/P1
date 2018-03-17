package com.superlifesecretcode.app.ui.subcategory;


import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.superlifesecretcode.app.R;
import com.superlifesecretcode.app.custom.AutoScrollViewPager;
import com.superlifesecretcode.app.data.model.SubcategoryModel;
import com.superlifesecretcode.app.data.model.category.BannerModel;
import com.superlifesecretcode.app.data.model.category.CategoryResponseData;
import com.superlifesecretcode.app.data.model.category.CategoryResponseModel;
import com.superlifesecretcode.app.data.model.language.LanguageResponseData;
import com.superlifesecretcode.app.data.model.userdetails.UserDetailResponseData;
import com.superlifesecretcode.app.data.persistance.SuperLifeSecretPreferences;
import com.superlifesecretcode.app.ui.adapter.BannerPagerAdapter;
import com.superlifesecretcode.app.ui.adapter.SubListAdapter;
import com.superlifesecretcode.app.ui.adapter.SubacategoryListAdapter;
import com.superlifesecretcode.app.ui.base.BaseActivity;
import com.superlifesecretcode.app.ui.events.EventActivity;
import com.superlifesecretcode.app.ui.news.NewsActivity;
import com.superlifesecretcode.app.util.CommonUtils;
import com.superlifesecretcode.app.util.ConstantLib;
import com.superlifesecretcode.app.util.SpacesItemDecoration;
import com.superlifesecretcode.app.util.SpacesItemDecorationGridLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SubCategoryActivity extends BaseActivity implements SubCaetgoryView, View.OnClickListener {

    private int positon;
    private LanguageResponseData conversionData;
    private SubCategoryPresenter presenter;
    private UserDetailResponseData userData;
    private String parentId;
    private List<CategoryResponseData> subList;
    private SubListAdapter subListAdapter;
    private List<BannerModel> bannerList;
    private AutoScrollViewPager autoScrollViewPager;
    private BannerPagerAdapter bannerPagerAdapter;
    private TextView textViewHome, textViewNewds, textViewEvent, textViewSharing;

    @Override
    protected int getContentView() {
        overridePendingTransition(R.anim.activity_open_translate, R.anim.activity_close_scale);
        return R.layout.activity_sub_category;
    }

    @Override
    protected void initializeView() {
        autoScrollViewPager = findViewById(R.id.pager_banner);
        textViewHome = findViewById(R.id.textView_home);
        textViewSharing = findViewById(R.id.textView_share);
        textViewNewds = findViewById(R.id.textView_news);
        textViewEvent = findViewById(R.id.textView_event);
        findViewById(R.id.tab_home).setOnClickListener(this);
        findViewById(R.id.tab_news).setOnClickListener(this);
        findViewById(R.id.tab_share).setOnClickListener(this);
        findViewById(R.id.tab_events).setOnClickListener(this);
        String title = getIntent().getBundleExtra("bundle").getString("title");
        positon = getIntent().getBundleExtra("bundle").getInt("pos");
        parentId = getIntent().getBundleExtra("bundle").getString("parent_id");
        conversionData = SuperLifeSecretPreferences.getInstance().getConversionData();
        userData = SuperLifeSecretPreferences.getInstance().getUserData();
        setUpToolbar(title);
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        recyclerView.addItemDecoration(new SpacesItemDecorationGridLayout(3, 25, true));
        if (positon == 5 || positon == 6 || positon == 7 || positon == 8) {
            bannerList = (ArrayList<BannerModel>) getIntent().getBundleExtra("bundle").getSerializable("banner");
            recyclerView.setAdapter(new SubacategoryListAdapter(getList(positon), this));
        } else {
            getSubCategory();
            subList = new ArrayList<>();
            subListAdapter = new SubListAdapter(subList, this);
            recyclerView.setAdapter(subListAdapter);
        }
        if (conversionData != null) {
            textViewHome.setText(conversionData.getHome());
            textViewSharing.setText(conversionData.getSharing());
            textViewEvent.setText(conversionData.getEvent_activity());
            textViewNewds.setText(conversionData.getNews_update());
        }
        int screenWidth = CommonUtils.getScreenWidth(this);
        int height = screenWidth * 6 / 16;
        autoScrollViewPager.getLayoutParams().width = screenWidth;
        autoScrollViewPager.getLayoutParams().height = height;
        setUpBanner();
    }

    private void setUpToolbar(String title) {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        TextView textViewTitle = findViewById(R.id.textView_title);
        textViewTitle.setText(title);
    }

    private void getSubCategory() {
        HashMap<String, String> body = new HashMap<>();
        body.put("country_id", userData.getCountry());
        body.put("parent_id", parentId);
        presenter.getSubCategories(body);
    }

    @Override
    protected void initializePresenter() {
        presenter = new SubCategoryPresenter(this);
        presenter.setView(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(R.anim.activity_open_scale, R.anim.activity_close_translate);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    public List<SubcategoryModel> getList(int position) {
        List<SubcategoryModel> list = new ArrayList<>();
        switch (position) {
            case 5:
                if (conversionData != null) {
                    list.add(new SubcategoryModel(android.R.drawable.ic_menu_camera, conversionData.getNews_update(), "", position));
                    list.add(new SubcategoryModel(android.R.drawable.ic_menu_camera, conversionData.getEvent_activity(), "", position));
                } else {
                    list.add(new SubcategoryModel(android.R.drawable.ic_menu_camera, "News Update", "", position));
                    list.add(new SubcategoryModel(android.R.drawable.ic_menu_camera, "Event+Activities", "", position));

                }
                return list;

            case 6:
                if (conversionData != null) {
                    list.add(new SubcategoryModel(android.R.drawable.ic_menu_camera, conversionData.getLatest(), "", position));
                    list.add(new SubcategoryModel(android.R.drawable.ic_menu_camera, conversionData.getSubmit(), "", position));
                } else {
                    list.add(new SubcategoryModel(android.R.drawable.ic_menu_camera, "Latest", "", position));
                    list.add(new SubcategoryModel(android.R.drawable.ic_menu_camera, "Submit", "", position));
                }
                return list;
            case 7:
                if (conversionData != null) {
                    list.add(new SubcategoryModel(android.R.drawable.ic_menu_camera, conversionData.getPersonal_calendar(), "", position));
                } else {
                    list.add(new SubcategoryModel(android.R.drawable.ic_menu_camera, "Personal+Event Calendar", "", position));
                }
                return list;
           /* case 7:
                list.add(new SubcategoryModel(android.R.drawable.ic_menu_camera, "Songs", " https://www.richestlife.com/music-downloads/"));
//                list.add(new SubcategoryModel(android.R.drawable.ic_menu_camera, "Wallpapers","https://m.facebook.com/SuperLifeCode/?refid=46"));
//                list.add(new SubcategoryModel(android.R.drawable.ic_menu_camera, "Sun Emoji","https://www.youtube.com/watch?v=PKNSIpl1aYQ&feature=youtu.be%20Inbox%20x"));
                return list;*/

            case 8:
                if (conversionData != null) {
                    list.add(new SubcategoryModel(android.R.drawable.ic_menu_camera, conversionData.getStudy_group(), "", position));
                    list.add(new SubcategoryModel(android.R.drawable.ic_menu_camera, conversionData.getOnsite(), "", position));
                } else {
                    list.add(new SubcategoryModel(android.R.drawable.ic_menu_camera, "Study Group", "", position));
                    list.add(new SubcategoryModel(android.R.drawable.ic_menu_camera, "On-site sharing", "", position));
                }
                return list;
        }
        return list;
    }

    @Override
    public void setSubCaetgiryData(CategoryResponseModel categoryResponseModel) {
        if (categoryResponseModel.getData() != null) {
            subList.clear();
            if (subListAdapter != null) {
                subList.addAll(categoryResponseModel.getData());
                subListAdapter.notifyDataSetChanged();
            }
        }

        if (categoryResponseModel.getBanners() != null) {
            if (bannerList.size() > 0) {
                autoScrollViewPager.setInterval(Long.parseLong(bannerList.get(0).getTransition_time()));
            }
            bannerList.clear();
            bannerList.addAll(categoryResponseModel.getBanners());
            bannerPagerAdapter.notifyDataSetChanged();
        }
    }

    private void setUpBanner() {
        if (bannerList == null) {
            bannerList = new ArrayList<>();
        }
        autoScrollViewPager.startAutoScroll();
        autoScrollViewPager.setCycle(true);
        autoScrollViewPager.setStopScrollWhenTouch(true);
//        autoScrollViewPager.setAutoScrollDurationFactor(10);
        bannerPagerAdapter = new BannerPagerAdapter(this, bannerList);
        autoScrollViewPager.setAdapter(bannerPagerAdapter);
        autoScrollViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                autoScrollViewPager.setInterval(Long.parseLong(bannerList.get(position).getTransition_time()));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tab_home:
                onBackPressed();
                break;
            case R.id.tab_events:
                CommonUtils.startActivity(this, EventActivity.class);
                break;
            case R.id.tab_news:
                CommonUtils.startActivity(this, NewsActivity.class);
                break;
            case R.id.tab_share:
                break;
        }
    }
}