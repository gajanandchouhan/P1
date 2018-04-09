package com.superlifesecretcode.app.ui.main;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
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
import com.superlifesecretcode.app.data.model.AlertModel;
import com.superlifesecretcode.app.data.model.DrawerItem;
import com.superlifesecretcode.app.data.model.SubcategoryModel;
import com.superlifesecretcode.app.data.model.category.BannerModel;
import com.superlifesecretcode.app.data.model.category.CategoryResponseData;
import com.superlifesecretcode.app.data.model.category.CategoryResponseModel;
import com.superlifesecretcode.app.data.model.language.LanguageResponseData;
import com.superlifesecretcode.app.data.model.unreadannouncement.AnnouncementCounData;
import com.superlifesecretcode.app.data.model.userdetails.UserDetailResponseData;
import com.superlifesecretcode.app.data.persistance.SuperLifeSecretPreferences;
import com.superlifesecretcode.app.ui.adapter.BannerPagerAdapter;
import com.superlifesecretcode.app.ui.adapter.MainListAdapter;
import com.superlifesecretcode.app.ui.base.BaseActivity;
import com.superlifesecretcode.app.ui.events.EventActivity;
import com.superlifesecretcode.app.ui.news.NewsActivity;
import com.superlifesecretcode.app.ui.notification.NotificationActivity;
import com.superlifesecretcode.app.ui.picker.AlertDialog;
import com.superlifesecretcode.app.ui.profile.ProfileActivity;
import com.superlifesecretcode.app.ui.sharing_latest.LatestActivity;
import com.superlifesecretcode.app.ui.sharing_submit.SubmitActivity;
import com.superlifesecretcode.app.ui.sharing_submit.SubmitListActivity;
import com.superlifesecretcode.app.ui.subcategory.SubCategoryActivity;
import com.superlifesecretcode.app.util.CommonUtils;
import com.superlifesecretcode.app.util.ConstantLib;
import com.superlifesecretcode.app.util.ImageLoadUtils;
import com.superlifesecretcode.app.util.ItemClickListner;
import com.superlifesecretcode.app.util.SpacesItemDecoration;
import com.superlifesecretcode.app.util.SpacesItemDecorationGridLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends BaseActivity implements View.OnClickListener, MainView {
    public static boolean LANGAUE_CHANGED;
    public static boolean PROFILE_UPDATED;
    public static boolean BOTTOM_BAR_CHANGED = false;
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
    private TextView textView1, textView2, textView3, textView4;
    LinearLayout tab1, tab2, tab3, tab4;
    ImageView imageView1, imageView2, imageView3, imageView4;


    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    protected void initializeView() {
        setUpToolbar();
        userDetailResponseData = SuperLifeSecretPreferences.getInstance().getUserData();
        conversionData = SuperLifeSecretPreferences.getInstance().getConversionData();
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
        textView1 = findViewById(R.id.textView_1);
        textView2 = findViewById(R.id.textView_2);
        textView3 = findViewById(R.id.textView_3);
        textView4 = findViewById(R.id.textView_4);
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
        int screenWidth = CommonUtils.getScreenWidth(this);
        int height = screenWidth * 6 / 16;
        autoScrollViewPager.getLayoutParams().width = screenWidth;
        autoScrollViewPager.getLayoutParams().height = height;
        View viewById = findViewById(R.id.imageView_profile);
        viewById.setVisibility(View.VISIBLE);
        viewById.setOnClickListener(this);
        View viewNotifcation = findViewById(R.id.imageView_notification);
        viewNotifcation.setVisibility(View.VISIBLE);
        viewNotifcation.setOnClickListener(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<>();
        drawerAdapter = new DrawerAdapter(list);
        drawerAdapter.setListner(new ItemClickListner() {
            @Override
            public void onItemClick(Object object, int position) {
                openNextScreen(position, list.get(position).getPosition(), list.get(position).getTitle(), list.get(position).getId(), list.get(position).getColor());
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
        recyclerViewMain.addItemDecoration(new SpacesItemDecorationGridLayout(3, 25, true));
        setUpBottomBar();
        getMainCategories();
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


    @Override
    protected void onResume() {
        super.onResume();
        getBanner();
        if (mainAdapter != null) {
            mainAdapter.notifyDataSetChanged();
        }
        if (LANGAUE_CHANGED) {
            getMainCategories();
            LANGAUE_CHANGED = false;
            conversionData = SuperLifeSecretPreferences.getInstance().getConversionData();
            setUpBottomBar();
        }
        if (BOTTOM_BAR_CHANGED) {
            setUpBottomBar();
            BOTTOM_BAR_CHANGED = false;
        }
        if (PROFILE_UPDATED) {
            userDetailResponseData = SuperLifeSecretPreferences.getInstance().getUserData();
            setUpUserdetails();
        }

    }

    @Override
    protected void onDestroy() {
        // Unregister since the activity is about to be closed.
        super.onDestroy();
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

    private void getAnnouementCount() {
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + userDetailResponseData.getApi_token());
        HashMap<String, String> params = new HashMap<>();
        params.put("announcement_type", "1");
        params.put("user_id", userDetailResponseData.getUser_id());
        presenter.getAnnouncementCount(params, headers);
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
        actionBar.setHomeAsUpIndicator(R.drawable.ic_action_menu);
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
            case R.id.imageView_notification:
                CommonUtils.startActivity(this, NotificationActivity.class);
                break;
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
            case R.id.tab_1:
            case R.id.tab_2:
            case R.id.tab_3:
            case R.id.tab_4:
                handleBottomClick((Integer) v.getTag());
                break;
        }

    }

    private void handleBottomClick(int tag) {
        switch (tag) {
            case ConstantLib.TYPE_HOME:
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

    public void openNextScreen(int clickedPostion, int position, String title, String parentId, String color) {
        if (list.get(clickedPostion).getAlert() != null && list.get(clickedPostion).getAlert().equalsIgnoreCase("1")) {
            AlertModel alertModel = new AlertModel();
            alertModel.setCount(Integer.parseInt(list.get(clickedPostion).getAlert_count()));
            alertModel.setId(list.get(clickedPostion).getId());
            if (!SuperLifeSecretPreferences.getInstance().getAcceptedIds().contains(alertModel)) {
                showAlert(list.get(clickedPostion).getAlert_text(), clickedPostion, null, null);
            } else {
                List<AlertModel> alertModelList = SuperLifeSecretPreferences.getInstance().getAcceptedIds();
                int index = alertModelList.indexOf(alertModel);
                AlertModel alertModel1 = alertModelList.get(index);
                if (alertModel.getCount() > alertModel1.getShowCount()) {
                    showAlert(list.get(clickedPostion).getAlert_text(), clickedPostion, alertModel1, alertModelList);
                } else {
                    Bundle bundle = new Bundle();
                    bundle.putString("title", title);
                    bundle.putInt("pos", position);
                    bundle.putString("parent_id", parentId);
                    bundle.putString("color", color);
                    bundle.putSerializable("banner", bannerList);
                    CommonUtils.startActivity(this, SubCategoryActivity.class, bundle, false);
                }
               /* if (alertModel1.getCount() == 0) {
                    Bundle bundle = new Bundle();
                    bundle.putString("title", title);
                    bundle.putInt("pos", position);
                    bundle.putString("parent_id", parentId);
                    bundle.putString("color", color);
                    bundle.putSerializable("banner", bannerList);
                    CommonUtils.startActivity(this, SubCategoryActivity.class, bundle, false);
                } else {
                    showAlert(list.get(clickedPostion).getAlert_text(), clickedPostion, alertModel1, alertModelList);
                }*/

            }
        } else {
            Bundle bundle = new Bundle();
            bundle.putString("title", title);
            bundle.putInt("pos", position);
            bundle.putString("parent_id", parentId);
            bundle.putString("color", color);
            bundle.putSerializable("banner", bannerList);
            CommonUtils.startActivity(this, SubCategoryActivity.class, bundle, false);
        }

    }

    public void openNext(int clickedPostion, int position, String title, String parentId, String color) {
        Bundle bundle = new Bundle();
        bundle.putString("title", title);
        bundle.putInt("pos", position);
        bundle.putString("parent_id", parentId);
        bundle.putString("color", color);
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
            textViewEditProfile.setText(conversionData.getEdit_profile());
        }
        getAnnouementCount();
        drawerAdapter.notifyDataSetChanged();
        mainAdapter.notifyDataSetChanged();
    }

    @Override
    public void setAnnounceMentCount(AnnouncementCounData announcementCountResponseModel) {
        if (announcementCountResponseModel != null) {
            SuperLifeSecretPreferences.getInstance().setEventUndread(announcementCountResponseModel.getUnreadEvents());
            SuperLifeSecretPreferences.getInstance().setNewsUndread(announcementCountResponseModel.getUnreadNews());
            if (mainAdapter != null) {
                mainAdapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void setBanners(List<BannerModel> banners) {
        if (banners != null) {
            bannerList.clear();
            bannerList.addAll(banners);
            bannerPagerAdapter.notifyDataSetChanged();
        }
    }

    private void showAlert(final String alert_text, final int clikedPostion, final AlertModel alertModel1, final List<AlertModel> alertModelList) {
        String positive_resp = list.get(clikedPostion).getPositive_resp();
        String negative_resp = list.get(clikedPostion).getNegative_resp();
        CommonUtils.showAlert(this, alert_text, positive_resp, negative_resp, new AlertDialog.OnClickListner() {
            @Override
            public void onPositiveClick() {
                if (alertModel1 != null) {
                    alertModel1.setShowCount(alertModel1.getShowCount() + 1);
                    SuperLifeSecretPreferences.getInstance().updateAlertList(alertModelList);
                } else {
                    AlertModel alertModel = new AlertModel();
                    alertModel.setShowCount(1);
                    alertModel.setId(list.get(clikedPostion).getId());
                    alertModel.setCount(Integer.parseInt(list.get(clikedPostion).getAlert_count()) - 1);
                    SuperLifeSecretPreferences.getInstance().setAlertAccepted(alertModel);
                }
                /*if (alertModel1 != null) {
                    alertModel1.setCount(alertModel1.getCount() - 1);
                    SuperLifeSecretPreferences.getInstance().updateAlertList(alertModelList);
                } else {
                    AlertModel alertModel = new AlertModel();
                    alertModel.setId(list.get(clikedPostion).getId());
                    alertModel.setCount(Integer.parseInt(list.get(clikedPostion).getAlert_count()) - 1);
                    SuperLifeSecretPreferences.getInstance().setAlertAccepted(alertModel);
                }*/
                openNext(clikedPostion, list.get(clikedPostion).getPosition(), list.get(clikedPostion).getTitle(), list.get(clikedPostion).getId(), list.get(clikedPostion).getColor());
            }

            @Override
            public void onNegativeClick() {

            }
        });
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

    private void getBanner() {
        if (userDetailResponseData.getCountry() != null) {
            HashMap<String, String> body = new HashMap<>();
            body.put("country_id", userDetailResponseData.getCountry());
            presenter.getBanners(body);
        }
    }
}
