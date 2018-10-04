package com.superlifesecretcode.app.ui.main;

import android.os.Bundle;
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
import android.widget.Toast;
import com.superlifesecretcode.app.R;
import com.superlifesecretcode.app.custom.AutoScrollViewPager;
import com.superlifesecretcode.app.data.model.AlertModel;
import com.superlifesecretcode.app.data.model.DrawerItem;
import com.superlifesecretcode.app.data.model.allmenu.AllMenuResponseData;
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
import com.superlifesecretcode.app.ui.countryactivities.CountryAcitvitiesActivity;
import com.superlifesecretcode.app.ui.customizebar.CustomizeBarActivity;
import com.superlifesecretcode.app.ui.dailyactivities.interestedevent.InterestedEventCalendarActivity;
import com.superlifesecretcode.app.ui.dailyactivities.personalevent.PersonalEventCalendarActivity;
import com.superlifesecretcode.app.ui.events.EventActivity;
import com.superlifesecretcode.app.ui.kpi_summery.KpiActivity;
import com.superlifesecretcode.app.ui.news.NewsActivity;
import com.superlifesecretcode.app.ui.notification.NotificationActivity;
import com.superlifesecretcode.app.ui.picker.AlertDialog;
import com.superlifesecretcode.app.ui.profile.ProfileActivity;
import com.superlifesecretcode.app.ui.sharing_latest.LatestActivity;
import com.superlifesecretcode.app.ui.sharing_submit.SubmitListActivity;
import com.superlifesecretcode.app.ui.subcategory.SubCategoryActivity;
import com.superlifesecretcode.app.ui.webview.WebViewActivity;
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
    private TextView textViewTitle;

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
        userDetailResponseData = SuperLifeSecretPreferences.getInstance().getUserData();
        conversionData = SuperLifeSecretPreferences.getInstance().getConversionData();
        setUpToolbar();
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
        View viewSummeryKPI = findViewById(R.id.imageView_summery);
        viewSummeryKPI.setVisibility(View.VISIBLE);
        viewSummeryKPI.setOnClickListener(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<>();
        drawerAdapter = new DrawerAdapter(list);
        drawerAdapter.setListner(new ItemClickListner() {
            @Override
            public void onItemClick(Object object, int position) {
                openNextScreen(position, list.get(position).getPosition(), list.get(position).getTitle(), list.get(position).getId(), list.get(position).getColor());
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
        setUpUserdetails();
        recyclerViewMain.setLayoutManager(new GridLayoutManager(this, 3));
        List<DrawerItem> drawerItems = getDrawerList();
        drawerItems.remove(0);
        mainAdapter = new MainListAdapter(list, this);
        recyclerViewMain.setAdapter(mainAdapter);
        recyclerViewMain.addItemDecoration(new SpacesItemDecorationGridLayout(3, 25, true));
        HashMap<String, String> body = new HashMap<>();
        body.put("language_id", SuperLifeSecretPreferences.getInstance().getLanguageId());
        presenter.getConversion(body);
        setUpBottomBar2();
    }

    private void setUpBottomBar2() {
        if (userDetailResponseData.getCountry() != null && !userDetailResponseData.getCountry().isEmpty()) {
            List<AllMenuResponseData> bottomList = new ArrayList<>();
            List<AllMenuResponseData> subMenuList = SuperLifeSecretPreferences.getInstance().getAllCategories();
            if (subMenuList != null && subMenuList.size() > 0) {
                for (AllMenuResponseData subcategoryModel : subMenuList) {
                    if (subcategoryModel.isSelected()) {
                        bottomList.add(subcategoryModel);
                    }
                }
            } else {
                getAllMenu();
            }
            if (bottomList.size() > 2) {
                textView1.setText(conversionData.getHome());
                tab2.setTag(bottomList.get(0));
                textView2.setText(bottomList.get(0).getTitle());
                tab3.setTag(bottomList.get(1));
                textView3.setText(bottomList.get(1).getTitle());
                tab4.setTag(bottomList.get(2));
                textView4.setText(bottomList.get(2).getTitle());
                imageView1.setImageResource(R.drawable.home);
                ImageLoadUtils.loadImage(bottomList.get(0).getParent_image() != null && !bottomList.get(0).getParent_image().isEmpty() ? bottomList.get(0).getParent_image() : bottomList.get(0).getImage(), imageView2, R.drawable.ic_logo);
                ImageLoadUtils.loadImage(bottomList.get(1).getParent_image() != null && !bottomList.get(1).getParent_image().isEmpty() ? bottomList.get(1).getParent_image() : bottomList.get(1).getImage(), imageView3, R.drawable.ic_logo);
                ImageLoadUtils.loadImage(bottomList.get(2).getParent_image() != null && !bottomList.get(2).getParent_image().isEmpty() ? bottomList.get(2).getParent_image() : bottomList.get(2).getImage(), imageView4, R.drawable.ic_logo);
            }
        }
    }

    private void getAllMenu() {
        HashMap<String, String> body = new HashMap<>();
        body.put("language_id", SuperLifeSecretPreferences.getInstance().getLanguageId());
        body.put("country_id", SuperLifeSecretPreferences.getInstance().getUserData().getCountry());
        presenter.getAllMenu(body);
    }

    @Override
    protected void onResume() {
        super.onResume();
//        getBanner();
        getMainCategories();
        if (mainAdapter != null) {
            mainAdapter.notifyDataSetChanged();
        }
        if (LANGAUE_CHANGED) {
//            getMainCategories();
            LANGAUE_CHANGED = false;
            conversionData = SuperLifeSecretPreferences.getInstance().getConversionData();
            SuperLifeSecretPreferences.getInstance().clearAllCategories();
            setUpBottomBar2();
            textViewTitle.setText(conversionData.getRichest_life());
        }
        if (BOTTOM_BAR_CHANGED) {
            setUpBottomBar2();
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

        if (userDetailResponseData.getCountry() == null || userDetailResponseData.getCountry().equals("null") || userDetailResponseData.getCountry().isEmpty()
                || userDetailResponseData.getCity() == null || userDetailResponseData.getCity().equals("null") || userDetailResponseData.getCity().isEmpty()
                || userDetailResponseData.getState() == null || userDetailResponseData.getState().equals("null") || userDetailResponseData.getState().isEmpty()) {
            Bundle bundle = new Bundle();
            bundle.putBoolean("update", true);
//            Bundle  bundle3 = getIntent().getBundleExtra("bundle");
//            if (bundle3!=null){
//                boolean islogin = bundle3.getBoolean("isFromLogin");
//                if (islogin){
//                    bundle.putBoolean("isFromLogin", true);
//                }
//            }
            CommonUtils.startActivity(this, ProfileActivity.class, bundle, false);
            finish();
        } else {
            HashMap<String, String> body = new HashMap<>();
            body.put("country_id", userDetailResponseData.getCountry());
            body.put("language_id", SuperLifeSecretPreferences.getInstance().getLanguageId());
            presenter.getHomeCategories(body);
        }
    }

    private void getAnnouementCount() {
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + userDetailResponseData.getApi_token());
        HashMap<String, String> params = new HashMap<>();
        params.put("user_id", userDetailResponseData.getUser_id());
        params.put("country", userDetailResponseData.getCountry());
        presenter.getAnnouncementCount(params, headers);
    }

    private void setUpUserdetails() {
        if (userDetailResponseData != null) {
            textViewName.setText(userDetailResponseData.getUsername());
            if (!isFinishing())
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
        textViewTitle = findViewById(R.id.textView_title);
        textViewTitle.setText(conversionData.getRichest_life());
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
            case R.id.textView_edit:
                Bundle bundle2 = new Bundle();
                bundle2.putBoolean("update", false);
                CommonUtils.startActivity(this, ProfileActivity.class, bundle2, false);
                break;

            case R.id.imageView_summery:
                CommonUtils.startActivity(this, KpiActivity.class);
                break;
            case R.id.imageView_profile:
                Bundle bundle = new Bundle();
                bundle.putBoolean("update", false);
                CommonUtils.startActivity(this, ProfileActivity.class, bundle, false);
                break;
            case R.id.tab_1:
                break;
            case R.id.tab_2:
            case R.id.tab_3:
            case R.id.tab_4:
                handleBottomClick((AllMenuResponseData) v.getTag());
                break;
        }

    }

    private void handleBottomClick(AllMenuResponseData tag) {
        if (tag.getParent_id().equalsIgnoreCase("0")) {
            handleMainCategoryClicked(tag);
        } else {
            handleSubCaetgoryClicked(tag);
        }
    }

    private void handleSubCaetgoryClicked(AllMenuResponseData tag) {
        if (tag != null && tag.getAlert().equalsIgnoreCase("1")) {
            AlertModel alertModel = new AlertModel();
            alertModel.setCount(Integer.parseInt(tag.getAlert_count()));
            alertModel.setId(tag.getId());
            if (!SuperLifeSecretPreferences.getInstance().getAcceptedIds().contains(alertModel)) {
                showAlert2(tag, null, null);
            } else {
                List<AlertModel> alertModelList = SuperLifeSecretPreferences.getInstance().getAcceptedIds();
                int index = alertModelList.indexOf(alertModel);
                AlertModel alertModel1 = alertModelList.get(index);
                if (alertModel.getCount() > alertModel1.getShowCount()) {
                    showAlert2(tag, alertModel1, alertModelList);
                } else {
                    openNext(tag);
                }
            }
        } else {
            openNext(tag);
        }
    }


    public void openNextScreen(int clickedPostion, int position, String title, String parentId, String color) {
        if (list.get(clickedPostion).getType().equals(ConstantLib.MAIN_MENU_CHAT)) {
            createCometChatUser(userDetailResponseData.getUsername(), userDetailResponseData.getUser_id());
            return;
        }

        if (list.get(clickedPostion).getType().equals(ConstantLib.MAIN_MENU_SETTING)) {
            CommonUtils.startActivity(this, CustomizeBarActivity.class);
            return;
        }

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

    @Override
    public void setAllCaetgoryData(List<AllMenuResponseData> data) {
        if (data != null) {
            if (data.size() > 3) {
                data.get(1).setSelected(true);
                data.get(2).setSelected(true);
                data.get(3).setSelected(true);
                SuperLifeSecretPreferences.getInstance().setAllCategories(data);
                setUpBottomBar2();
            }
        }
    }

    @Override
    public void setConversionContent(LanguageResponseData data) {
        conversionData = data;
        SuperLifeSecretPreferences.getInstance().setConversionData(data);
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
//                openNext(clikedPostion, list.get(clikedPostion).getPosition(), list.get(clikedPostion).getTitle(), list.get(clikedPostion).getId(), list.get(clikedPostion).getColor());
            }

            @Override
            public void onNegativeClick() {

            }
        });
    }

    private void showAlert2(final AllMenuResponseData data, final AlertModel alertModel1, final List<AlertModel> alertModelList) {
        String positive_resp = data.getPositive_resp();
        String negative_resp = data.getNegative_resp();
        CommonUtils.showAlert(this, data.getAlert_text(), positive_resp, negative_resp, new AlertDialog.OnClickListner() {
            @Override
            public void onPositiveClick() {
                if (alertModel1 != null) {
                    alertModel1.setShowCount(alertModel1.getShowCount() + 1);
                    SuperLifeSecretPreferences.getInstance().updateAlertList(alertModelList);
                } else {
                    AlertModel alertModel = new AlertModel();
                    alertModel.setShowCount(1);
                    alertModel.setId(data.getId());
                    alertModel.setCount(Integer.parseInt(data.getAlert_count()) - 1);
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
//                openNext(clikedPostion, list.get(clikedPostion).getPosition(), list.get(clikedPostion).getTitle(), list.get(clikedPostion).getId(), list.get(clikedPostion).getColor());
            }

            @Override
            public void onNegativeClick() {

            }
        });
    }


    private void getBanner() {
        if (userDetailResponseData.getCountry() != null) {
            HashMap<String, String> body = new HashMap<>();
            body.put("country_id", userDetailResponseData.getCountry());
            presenter.getBanners(body);
        }
    }


    public void handleMainCategoryClicked(AllMenuResponseData data) {
        Toast.makeText(this, "dfs", Toast.LENGTH_SHORT).show();
        if (data.getAlert() != null && data.getAlert().equalsIgnoreCase("1")) {
            AlertModel alertModel = new AlertModel();
            alertModel.setCount(Integer.parseInt(data.getAlert_count()));
            alertModel.setId(data.getId());
            if (!SuperLifeSecretPreferences.getInstance().getAcceptedIds().contains(alertModel)) {
                showAlert2(data, null, null);
            } else {
                List<AlertModel> alertModelList = SuperLifeSecretPreferences.getInstance().getAcceptedIds();
                int index = alertModelList.indexOf(alertModel);
                AlertModel alertModel1 = alertModelList.get(index);
                if (alertModel.getCount() > alertModel1.getShowCount()) {
                    showAlert2(data, alertModel1, alertModelList);
                } else {
                    Bundle bundle = new Bundle();
                    bundle.putString("title", data.getTitle());
                    bundle.putString("parent_id", data.getId());
                    bundle.putString("color", data.getColor());
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
                    showAlert(data.getAlert_text(), clickedPostion, alertModel1, alertModelList);
                }*/

            }
        } else {
            Bundle bundle = new Bundle();
            bundle.putString("title", data.getTitle());
            bundle.putString("parent_id", data.getId());
            bundle.putString("color", data.getColor());
            CommonUtils.startActivity(this, SubCategoryActivity.class, bundle, false);
        }

    }


    private void openNext(AllMenuResponseData tag) {
        if (tag.getType().equalsIgnoreCase("1") || tag.getType().equalsIgnoreCase("2")) {
            Bundle bundle = new Bundle();
            bundle.putString("title", tag.getTitle());
            bundle.putBoolean("is_link", tag.getType().equalsIgnoreCase("1"));
            bundle.putString("url", tag.getLink());
            bundle.putString("content", tag.getContent());
            CommonUtils.startActivity(this, WebViewActivity.class, bundle, false);
        } else {
            switch (tag.getType()) {
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
                    CommonUtils.startActivity(this, PersonalEventCalendarActivity.class);
                    break;
                case ConstantLib.TYPE_EVENT_CALENDAR:
                    CommonUtils.startActivity(this, InterestedEventCalendarActivity.class);
                    break;
                case ConstantLib.TYPE_STUDY_GROUP:
                    Bundle bundle = new Bundle();
                    bundle.putString("title", SuperLifeSecretPreferences.getInstance().getConversionData().getStudy_group());
                    bundle.putBoolean("isStudyGroup", true);
                    CommonUtils.startActivity(this, CountryAcitvitiesActivity.class, bundle, false);
                    break;
                case ConstantLib.TYPE_ONSITE:
                    Bundle bundle2 = new Bundle();
                    bundle2.putString("title", SuperLifeSecretPreferences.getInstance().getConversionData().getOnsite());
                    bundle2.putBoolean("isStudyGroup", false);
                    CommonUtils.startActivity(this, CountryAcitvitiesActivity.class, bundle2, false);
                    break;
            }
        }
    }


}
