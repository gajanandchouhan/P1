package com.superlifesecretcode.app.ui.subcategory;


import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.superlifesecretcode.app.ui.sharing_latest.LatestActivity;
import com.superlifesecretcode.app.ui.sharing_submit.SubmitActivity;
import com.superlifesecretcode.app.ui.sharing_submit.SubmitListActivity;
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
    private TextView textView1, textView2, textView3, textView4;
    private String colorCode;
    LinearLayout tab1, tab2, tab3, tab4;
    ImageView imageView1, imageView2, imageView3, imageView4;
    private SubacategoryListAdapter subacategoryListAdapter;

    @Override
    protected int getContentView() {
        return R.layout.activity_sub_category;
    }

    @Override
    protected void initializeView() {
        autoScrollViewPager = findViewById(R.id.pager_banner);
        textView1 = findViewById(R.id.textView_1);
        textView2 = findViewById(R.id.textView_2);
        textView3 = findViewById(R.id.textView_3);
        textView4 = findViewById(R.id.textView_4);
        tab1 = findViewById(R.id.tab_1);
        tab2 = findViewById(R.id.tab_2);
        tab3 = findViewById(R.id.tab_3);
        tab4 = findViewById(R.id.tab_4);
        imageView1 = findViewById(R.id.imageView1);
        imageView2 = findViewById(R.id.imageView2);
        imageView3 = findViewById(R.id.imageView3);
        imageView4 = findViewById(R.id.imageView4);
        tab1.setOnClickListener(this);
        tab2.setOnClickListener(this);
        tab3.setOnClickListener(this);
        tab4.setOnClickListener(this);
        String title = getIntent().getBundleExtra("bundle").getString("title");
        positon = getIntent().getBundleExtra("bundle").getInt("pos");
        parentId = getIntent().getBundleExtra("bundle").getString("parent_id");
        colorCode = getIntent().getBundleExtra("bundle").getString("color");
        conversionData = SuperLifeSecretPreferences.getInstance().getConversionData();
        userData = SuperLifeSecretPreferences.getInstance().getUserData();
        setUpToolbar(title);
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        recyclerView.addItemDecoration(new SpacesItemDecorationGridLayout(3, 25, true));
        if (positon == 5 || positon == 6 || positon == 7 || positon == 8) {
            bannerList = (ArrayList<BannerModel>) getIntent().getBundleExtra("bundle").getSerializable("banner");
            subacategoryListAdapter = new SubacategoryListAdapter(getList(positon), this, colorCode);
            recyclerView.setAdapter(subacategoryListAdapter);
        } else {
            getSubCategory();
            subList = new ArrayList<>();
            subListAdapter = new SubListAdapter(subList, this, colorCode);
            recyclerView.setAdapter(subListAdapter);
        }
      /*  if (conversionData != null) {
            textViewHome.setText(conversionData.getHome());
            textViewSharing.setText(conversionData.getSharing());
            textViewEvent.setText(conversionData.getEvent_activity());
            textViewNewds.setText(conversionData.getNews_update());
        }*/
        int screenWidth = CommonUtils.getScreenWidth(this);
        int height = screenWidth * 6 / 16;
        autoScrollViewPager.getLayoutParams().width = screenWidth;
        autoScrollViewPager.getLayoutParams().height = height;
        setUpBanner();
        setUpBottomBar();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (subacategoryListAdapter != null) {
            subacategoryListAdapter.notifyDataSetChanged();
        }
    }

    private void setUpBottomBar() {
        List<SubcategoryModel> bottomList = new ArrayList<>();
        List<SubcategoryModel> subMenuList = SuperLifeSecretPreferences.getInstance().getSubMenuList();
        if (subMenuList != null && subMenuList.size() > 0) {
            for (SubcategoryModel subcategoryModel : subMenuList) {
                if (subcategoryModel.isSelected()) {
                    bottomList.add(subcategoryModel);
                }
            }
        } else {
            List<SubcategoryModel> list = getList();
            SuperLifeSecretPreferences.getInstance().setSubMenuList(list);
            for (SubcategoryModel subcategoryModel : list) {
                if (subcategoryModel.isSelected()) {
                    bottomList.add(subcategoryModel);
                }
            }

        }
        if (bottomList.size() > 3) {
            tab1.setTag(bottomList.get(0).getType());
            textView1.setText(bottomList.get(0).getTitle());
            tab2.setTag(bottomList.get(1).getType());
            textView2.setText(bottomList.get(1).getTitle());
            tab3.setTag(bottomList.get(2).getType());
            textView3.setText(bottomList.get(2).getTitle());
            tab4.setTag(bottomList.get(3).getType());
            textView4.setText(bottomList.get(3).getTitle());
            imageView1.setImageResource(CommonUtils.getResurceId(this, bottomList.get(0).getIcon()));
            imageView2.setImageResource(CommonUtils.getResurceId(this, bottomList.get(1).getIcon()));
            imageView3.setImageResource(CommonUtils.getResurceId(this, bottomList.get(2).getIcon()));
            imageView4.setImageResource(CommonUtils.getResurceId(this, bottomList.get(3).getIcon()));
        }
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
                    list.add(new SubcategoryModel("announcement", conversionData.getNews_update(), "", position, true));
                    list.add(new SubcategoryModel("announcement", conversionData.getEvent_activity(), "", position, true));
                } else {
                    list.add(new SubcategoryModel("announcement", "News Update", "", position, true));
                    list.add(new SubcategoryModel("announcement", "Event+Activities", "", position, true));

                }
                return list;

            case 6:
                if (conversionData != null) {
                    list.add(new SubcategoryModel("sharing", conversionData.getLatest(), "", position, true));
                    list.add(new SubcategoryModel("sharing", conversionData.getSubmit(), "", position, true));
                } else {
                    list.add(new SubcategoryModel("sharing", "Latest", "", position, true));
                    list.add(new SubcategoryModel("sharing", "Submit", "", position, true));
                }
                return list;
            case 7:
                if (conversionData != null) {
                    list.add(new SubcategoryModel("activities", conversionData.getPersonal_cal(), "", position, false));
                    list.add(new SubcategoryModel("activities", conversionData.getEvent_cal(), "", position, false));
                } else {
                    list.add(new SubcategoryModel("activities", "Personal Calendar", "", position, false));
                    list.add(new SubcategoryModel("activities", "Event Calendar", "", position, false));
                }
                return list;
           /* case 7:
                list.add(new SubcategoryModel(android.ic_menu_camera, "Songs", " https://www.richestlife.com/music-downloads/"));
//                list.add(new SubcategoryModel(android.ic_menu_camera, "Wallpapers","https://m.facebook.com/SuperLifeCode/?refid=46"));
//                list.add(new SubcategoryModel(android.ic_menu_camera, "Sun Emoji","https://www.youtube.com/watch?v=PKNSIpl1aYQ&feature=youtu.be%20Inbox%20x"));
                return list;*/

            case 8:
                if (conversionData != null) {
                    list.add(new SubcategoryModel("country_globe", conversionData.getStudy_group(), "", position, false));
                    list.add(new SubcategoryModel("country_globe", conversionData.getOnsite(), "", position, false));
                } else {
                    list.add(new SubcategoryModel("country_globe", "Study Group", "", position, false));
                    list.add(new SubcategoryModel("country_globe", "On-site sharing", "", position, false));
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
            case R.id.tab_1:
            case R.id.tab_2:
            case R.id.tab_3:
            case R.id.tab_4:
                handleBottomClick((Integer) view.getTag());
                break;
        }

    }

    private void handleBottomClick(int tag) {
        switch (tag) {
            case ConstantLib.TYPE_HOME:
                onBackPressed();
                break;
            case ConstantLib.TYPE_NEWS:
                CommonUtils.startActivity(this, NewsActivity.class);
                break;
            case ConstantLib.TYPE_EVENT:
                CommonUtils.startActivity(this, EventActivity.class);
                break;
            case ConstantLib.TYPE_LATEST:
                CommonUtils.startActivity(this, LatestActivity.class);
                break;
            case ConstantLib.TYPE_SUBMIT:
                CommonUtils.startActivity(this, SubmitListActivity.class);
                break;
            case ConstantLib.TYPE_PERSONAL_CALENDAR:
                break;
            case ConstantLib.TYPE_EVENT_CALENDAR:
                break;
            case ConstantLib.TYPE_STUDY_GROUP:
                break;
            case ConstantLib.TYPE_ONSITE:
                break;

        }
    }

    public List<SubcategoryModel> getList() {
        List<SubcategoryModel> list = new ArrayList<>();
        if (conversionData != null) {
            list.add(new SubcategoryModel("home", conversionData.getHome(), "", 0, true));
            list.add(new SubcategoryModel("announcement", conversionData.getNews_update(), "", 1, true));
            list.add(new SubcategoryModel("announcement", conversionData.getEvent_activity(), "", 2, true));
            list.add(new SubcategoryModel("sharing", conversionData.getLatest(), "", 3, true));
            list.add(new SubcategoryModel("sharing", conversionData.getSubmit(), "", 4, false));
            list.add(new SubcategoryModel("activities", conversionData.getPersonal_cal(), "", 5, false));
            list.add(new SubcategoryModel("activities", conversionData.getEvent_cal(), "", 6, false));
            list.add(new SubcategoryModel("country_globe", conversionData.getStudy_group(), "", 7, false));
            list.add(new SubcategoryModel("country_globe", conversionData.getOnsite(), "", 8, false));
        } else {
            list.add(new SubcategoryModel("home", "Home", "", 0, true));
            list.add(new SubcategoryModel("announcement", "News Update", "", 1, true));
            list.add(new SubcategoryModel("announcement", "Event+Activities", "", 2, true));
            list.add(new SubcategoryModel("sharing", "Latest", "", 3, true));
            list.add(new SubcategoryModel("sharing", "Submit", "", 4, false));
            list.add(new SubcategoryModel("activities", "Personal Calendar", "", 5, false));
            list.add(new SubcategoryModel("activities", "Event Calendar", "", 6, false));
            list.add(new SubcategoryModel("country_globe", "Study Group", "", 7, false));
            list.add(new SubcategoryModel("country_globe", "On-site sharing", "", 8, false));
        }
        return list;
    }


}