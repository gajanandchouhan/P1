package com.superlifesecretcode.app.ui.main;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.superlifesecretcode.app.R;
import com.superlifesecretcode.app.custom.AutoScrollViewPager;
import com.superlifesecretcode.app.data.model.DrawerItem;
import com.superlifesecretcode.app.data.model.SubcategoryModel;
import com.superlifesecretcode.app.data.model.category.BannerModel;
import com.superlifesecretcode.app.data.model.category.CategoryResponseData;
import com.superlifesecretcode.app.data.model.category.CategoryResponseModel;
import com.superlifesecretcode.app.data.model.language.LanguageResponseData;
import com.superlifesecretcode.app.data.model.userdetails.UserDetailResponseData;
import com.superlifesecretcode.app.data.persistance.SuperLifeSecretPreferences;
import com.superlifesecretcode.app.ui.adapter.BannerPagerAdapter;
import com.superlifesecretcode.app.ui.adapter.MainListAdapter;
import com.superlifesecretcode.app.ui.base.BaseActivity;
import com.superlifesecretcode.app.ui.profile.ProfileActivity;
import com.superlifesecretcode.app.ui.subcategory.SubCategoryActivity;
import com.superlifesecretcode.app.util.CommonUtils;
import com.superlifesecretcode.app.util.ImageLoadUtils;
import com.superlifesecretcode.app.util.ItemClickListner;
import com.superlifesecretcode.app.util.SpacesItemDecoration;
import com.superlifesecretcode.app.util.SpacesItemDecorationGridLayout;

import org.w3c.dom.Text;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends BaseActivity implements View.OnClickListener, MainView {
    public static boolean LANGAUE_CHANGED;
    DrawerLayout mDrawerLayout;
    private LinearLayout layoutDrawer, mainLayout;
    private RecyclerView recyclerView;
    private AutoScrollViewPager autoScrollViewPager;
    private BannerPagerAdapter bannerPagerAdapter;
    private TextView textViewName;
    private TextView textViewEditProfile;
    private ImageView imageViewUser;
    private UserDetailResponseData userDetailResponseData;
    private RecyclerView recyclerViewMain;
    private List<CategoryResponseData> list;
    private DrawerAdapter drawerAdapter;
    private MainListAdapter mainAdapter;
    private MainPresenter presenter;
    private LanguageResponseData conversionData;
    private ArrayList<BannerModel> bannerList;

    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(R.anim.activity_open_scale, R.anim.activity_close_translate);
    }

    @Override
    protected int getContentView() {
        overridePendingTransition(R.anim.activity_open_translate, R.anim.activity_close_scale);
        return R.layout.activity_main;
    }

    @Override
    protected void initializeView() {
        setUpToolbar();
        userDetailResponseData = SuperLifeSecretPreferences.getInstance().getUserData();
        conversionData = SuperLifeSecretPreferences.getInstance().getConversionData();
        mDrawerLayout = findViewById(R.id.drawer);
        layoutDrawer = findViewById(R.id.layout_drawer);
        mainLayout = findViewById(R.id.main_layout);
        recyclerView = findViewById(R.id.recycler_view);
        recyclerViewMain = findViewById(R.id.recycler_view_main);
        textViewName = findViewById(R.id.textView_name);
        textViewEditProfile = findViewById(R.id.textView_edit);
        imageViewUser = findViewById(R.id.imageView_user);
        textViewEditProfile.setOnClickListener(this);
        autoScrollViewPager = findViewById(R.id.pager_banner);
        findViewById(R.id.imageView_profile).setOnClickListener(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<>();
        drawerAdapter = new DrawerAdapter(list);
        drawerAdapter.setListner(new ItemClickListner() {
            @Override
            public void onItemClick(Object object, int position) {
                openNextScreen(list.get(position).getPosition(), list.get(position).getTitle(), list.get(position).getId());
                  /*  case 0:
                        mDrawerLayout.closeDrawer(layoutDrawer);
                        break;
                    case 2:
                        openNextScreen(2, getString(R.string.product_shopping));
                        break;
                    case 1:
                        openNextScreen(1, getString(R.string.abourt));
                        break;
                    case 3:
                        openNextScreen(3, getString(R.string.learing));
                        break;
                    case 4:
                        openNextScreen(4, getString(R.string.annoucement));
                        break;

                    case 5:
                        openNextScreen(5, getString(R.string.sharing));
                        break;

                    case 6:
                        openNextScreen(6, getString(R.string.daily_activities));
                        break;
                    case 7:
                        openNextScreen(7, getString(R.string.free_downloads));
                        break;
                    case 8:
                        openNextScreen(8, getString(R.string.country_activities));
                        break;*/
            }

        });
        recyclerView.setAdapter(drawerAdapter);
        recyclerView.addItemDecoration(new SpacesItemDecoration(2));
        mDrawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float offset) {
                mainLayout.setTranslationX(offset * drawerView.getWidth());
                mDrawerLayout.bringChildToFront(drawerView);
                mDrawerLayout.requestLayout();
            }

            @Override
            public void onDrawerOpened(View drawerView) {

            }

            @Override
            public void onDrawerClosed(View drawerView) {

            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });
        setUpBanner();
      /*  findViewById(R.id.cardview_about).setOnClickListener(this);
        findViewById(R.id.cardview_learning).setOnClickListener(this);
        findViewById(R.id.cardview_product_shoping).setOnClickListener(this);
        findViewById(R.id.cardvview_freedownloads).setOnClickListener(this);*/
        setUpUserdetails();
        recyclerViewMain.setLayoutManager(new GridLayoutManager(this, 3));
        List<DrawerItem> drawerItems = getDrawerList();
        drawerItems.remove(0);
        mainAdapter = new MainListAdapter(list, this);
        recyclerViewMain.setAdapter(mainAdapter);
        recyclerViewMain.addItemDecoration(new SpacesItemDecorationGridLayout(3, 30, true));
        getMainCategories();
    }



    @Override
    protected void onResume() {
        super.onResume();
        if (LANGAUE_CHANGED) {
            getMainCategories();
            LANGAUE_CHANGED = false;
            conversionData = SuperLifeSecretPreferences.getInstance().getConversionData();

        }
    }

    private void getMainCategories() {

        if (userDetailResponseData.getCountry() != null) {
            HashMap<String, String> body = new HashMap<>();
            body.put("country_id", userDetailResponseData.getCountry());
            body.put("language_id", SuperLifeSecretPreferences.getInstance().getLanguageId());
            presenter.getHomeCategories(body);
        } else {
            Bundle bundle = new Bundle();
            bundle.putBoolean("update", true);
            CommonUtils.startActivity(this, ProfileActivity.class, bundle, false);
            finish();
        }

    }

    private void setUpUserdetails() {
        if (userDetailResponseData != null) {
            textViewName.setText(userDetailResponseData.getUsername());
            ImageLoadUtils.loadImage(userDetailResponseData.getImage(), imageViewUser);
        }
    }

    private void setUpBanner() {
        autoScrollViewPager.startAutoScroll();
        autoScrollViewPager.setInterval(3000);
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

    private List<DrawerItem> getDrawerList() {
        List<DrawerItem> list = new ArrayList<>();
        list.add(new DrawerItem(getString(R.string.home), android.R.drawable.ic_menu_camera));
        list.add(new DrawerItem(getString(R.string.abourt), android.R.drawable.ic_menu_camera));
        list.add(new DrawerItem(getString(R.string.product_shopping), android.R.drawable.ic_menu_camera));
        list.add(new DrawerItem(getString(R.string.learing), android.R.drawable.ic_menu_camera));
        list.add(new DrawerItem(getString(R.string.annoucement), android.R.drawable.ic_menu_camera));
        list.add(new DrawerItem(getString(R.string.sharing), android.R.drawable.ic_menu_camera));
        list.add(new DrawerItem(getString(R.string.daily_activities), android.R.drawable.ic_menu_camera));
        list.add(new DrawerItem(getString(R.string.free_downloads), android.R.drawable.ic_menu_camera));
        list.add(new DrawerItem(getString(R.string.country_activities), android.R.drawable.ic_menu_camera));
        return list;
    }

    private void setUpToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(android.R.drawable.ic_menu_agenda);
    }

    @Override
    protected void initializePresenter() {
        presenter = new MainPresenter(this);
        presenter.setView(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            mDrawerLayout.openDrawer(layoutDrawer);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
         /*   case R.id.cardview_about:
                openNextScreen(1, getString(R.string.abourt));
                break;
            case R.id.cardview_product_shoping:
                openNextScreen(2, getString(R.string.product_shopping));
                break;
            case R.id.cardview_learning:
                openNextScreen(3, getString(R.string.learing));
                break;
            case R.id.cardvview_freedownloads:
                openNextScreen(7, getString(R.string.free_downloads));
                break;*/
            case R.id.textView_edit:
            case R.id.imageView_profile:
                Bundle bundle = new Bundle();
                bundle.putBoolean("update", false);
                CommonUtils.startActivity(this, ProfileActivity.class, bundle, false);
                break;
        }

    }

    public void openNextScreen(int position, String title, String parentId) {
        Bundle bundle = new Bundle();
        bundle.putString("title", title);
        bundle.putInt("pos", position);
        bundle.putString("parent_id", parentId);
        bundle.putSerializable("banner", bannerList);
        CommonUtils.startActivity(this, SubCategoryActivity.class, bundle, false);
    }

    @Override
    public void setHomeData(CategoryResponseModel categoryResponseModel) {
        if (categoryResponseModel.getData() != null) {
            list.clear();
            list.addAll(categoryResponseModel.getData());
        }
        if (categoryResponseModel.getBanners() != null) {
            if (bannerList.size() > 0) {
                autoScrollViewPager.setInterval(Long.parseLong(bannerList.get(0).getTransition_time()));
            }
            bannerList.clear();
            bannerList.addAll(categoryResponseModel.getBanners());
            bannerPagerAdapter.notifyDataSetChanged();
        }
        if (conversionData != null) {
            CategoryResponseData categoryResponseData = new CategoryResponseData();
            categoryResponseData.setTitle(conversionData.getAnnouncement());
            categoryResponseData.setIcon(android.R.drawable.ic_menu_camera);
            categoryResponseData.setPosition(5);
            list.add(categoryResponseData);

            CategoryResponseData categoryResponseData2 = new CategoryResponseData();
            categoryResponseData2.setTitle(conversionData.getSharing());
            categoryResponseData2.setIcon(android.R.drawable.ic_menu_camera);
            categoryResponseData2.setPosition(6);
            list.add(categoryResponseData2);


            CategoryResponseData categoryResponseData3 = new CategoryResponseData();
            categoryResponseData3.setTitle(conversionData.getDaily_activities());
            categoryResponseData3.setIcon(android.R.drawable.ic_menu_camera);
            categoryResponseData3.setPosition(7);
            list.add(categoryResponseData3);

            CategoryResponseData categoryResponseData4 = new CategoryResponseData();
            categoryResponseData4.setTitle(conversionData.getCountry_activities());
            categoryResponseData4.setIcon(android.R.drawable.ic_menu_camera);
            categoryResponseData4.setPosition(8);
            list.add(categoryResponseData4);
            textViewEditProfile.setText(conversionData.getEdit_profile());
        }
        drawerAdapter.notifyDataSetChanged();
        mainAdapter.notifyDataSetChanged();
    }


}
