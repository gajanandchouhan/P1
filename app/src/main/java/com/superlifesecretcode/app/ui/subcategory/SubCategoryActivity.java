package com.superlifesecretcode.app.ui.subcategory;


import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
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
import com.superlifesecretcode.app.util.ConstantLib;
import com.superlifesecretcode.app.util.SpacesItemDecoration;
import com.superlifesecretcode.app.util.SpacesItemDecorationGridLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SubCategoryActivity extends BaseActivity implements SubCaetgoryView {

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

    @Override
    protected int getContentView() {
        overridePendingTransition(R.anim.activity_open_translate, R.anim.activity_close_scale);
        return R.layout.activity_sub_category;
    }

    @Override
    protected void initializeView() {
        autoScrollViewPager = findViewById(R.id.pager_banner);
        String title = getIntent().getBundleExtra("bundle").getString("title");
        positon = getIntent().getBundleExtra("bundle").getInt("pos");
        parentId = getIntent().getBundleExtra("bundle").getString("parent_id");
        conversionData = SuperLifeSecretPreferences.getInstance().getConversionData();
        userData = SuperLifeSecretPreferences.getInstance().getUserData();
        setUpToolbar(title);
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        recyclerView.addItemDecoration(new SpacesItemDecorationGridLayout(3, 30, true));
        if (parentId == null) {
            recyclerView.setAdapter(new SubacategoryListAdapter(getList(positon), this));
        } else {
            getSubCategory();
            subList = new ArrayList<>();
            subListAdapter = new SubListAdapter(subList, this);
            recyclerView.setAdapter(subListAdapter);
        }
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
                    list.add(new SubcategoryModel(android.R.drawable.ic_menu_camera, conversionData.getNews_update(), ""));
                    list.add(new SubcategoryModel(android.R.drawable.ic_menu_camera, conversionData.getNews_update(), ""));
                } else {
                    list.add(new SubcategoryModel(android.R.drawable.ic_menu_camera, "News Update", ""));
                    list.add(new SubcategoryModel(android.R.drawable.ic_menu_camera, "Event+Activities", ""));

                }
                return list;

            case 6:
                if (conversionData != null) {
                    list.add(new SubcategoryModel(android.R.drawable.ic_menu_camera, conversionData.getLatest(), ""));
                    list.add(new SubcategoryModel(android.R.drawable.ic_menu_camera, conversionData.getSubmit(), ""));
                } else {
                    list.add(new SubcategoryModel(android.R.drawable.ic_menu_camera, "Latest", ""));
                    list.add(new SubcategoryModel(android.R.drawable.ic_menu_camera, "Submit", ""));
                }
                return list;
            case 7:
                if (conversionData != null) {
                    list.add(new SubcategoryModel(android.R.drawable.ic_menu_camera, conversionData.getPersonal_calendar(), ""));
                } else {
                    list.add(new SubcategoryModel(android.R.drawable.ic_menu_camera, "Personal+Event Calendar", ""));
                }
                return list;
           /* case 7:
                list.add(new SubcategoryModel(android.R.drawable.ic_menu_camera, "Songs", " https://www.richestlife.com/music-downloads/"));
//                list.add(new SubcategoryModel(android.R.drawable.ic_menu_camera, "Wallpapers","https://m.facebook.com/SuperLifeCode/?refid=46"));
//                list.add(new SubcategoryModel(android.R.drawable.ic_menu_camera, "Sun Emoji","https://www.youtube.com/watch?v=PKNSIpl1aYQ&feature=youtu.be%20Inbox%20x"));
                return list;*/

            case 8:
                if (conversionData != null) {
                    list.add(new SubcategoryModel(android.R.drawable.ic_menu_camera, conversionData.getStudy_group(), ""));
                    list.add(new SubcategoryModel(android.R.drawable.ic_menu_camera, conversionData.getOnsite(), ""));
                } else {
                    list.add(new SubcategoryModel(android.R.drawable.ic_menu_camera, "Study Group", ""));
                    list.add(new SubcategoryModel(android.R.drawable.ic_menu_camera, "On-site sharing", ""));
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
        autoScrollViewPager.startAutoScroll();
        autoScrollViewPager.setCycle(true);
        autoScrollViewPager.setStopScrollWhenTouch(true);
//        autoScrollViewPager.setAutoScrollDurationFactor(10);
        bannerList = new ArrayList<>();
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

}