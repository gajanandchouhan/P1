package com.superlifesecretcode.app.ui.subcategory;


import android.os.Bundle;
import android.support.v4.view.ViewPager;
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
import com.superlifesecretcode.app.data.model.allmenu.AllMenuResponseData;
import com.superlifesecretcode.app.data.model.category.BannerModel;
import com.superlifesecretcode.app.data.model.category.CategoryResponseData;
import com.superlifesecretcode.app.data.model.category.CategoryResponseModel;
import com.superlifesecretcode.app.data.model.language.LanguageResponseData;
import com.superlifesecretcode.app.data.model.userdetails.UserDetailResponseData;
import com.superlifesecretcode.app.data.persistance.SuperLifeSecretPreferences;
import com.superlifesecretcode.app.ui.adapter.BannerPagerAdapter;
import com.superlifesecretcode.app.ui.adapter.SubListAdapter;
import com.superlifesecretcode.app.ui.base.BaseActivity;
import com.superlifesecretcode.app.ui.book.first.FirstBookActivity;
import com.superlifesecretcode.app.ui.countryactivities.CountryAcitvitiesActivity;
import com.superlifesecretcode.app.ui.dailyactivities.interestedevent.InterestedEventCalendarActivity;
import com.superlifesecretcode.app.ui.dailyactivities.personalevent.PersonalEventCalendarActivity;
import com.superlifesecretcode.app.ui.events.EventActivity;
import com.superlifesecretcode.app.ui.news.NewsActivity;
import com.superlifesecretcode.app.ui.picker.AlertDialog;
import com.superlifesecretcode.app.ui.sharing_latest.LatestActivity;
import com.superlifesecretcode.app.ui.sharing_submit.SubmitListActivity;
import com.superlifesecretcode.app.ui.webview.WebViewActivity;
import com.superlifesecretcode.app.util.CommonUtils;
import com.superlifesecretcode.app.util.ConstantLib;
import com.superlifesecretcode.app.util.ImageLoadUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SubCategoryActivity extends BaseActivity implements SubCaetgoryView, View.OnClickListener {

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
        parentId = getIntent().getBundleExtra("bundle").getString("parent_id");
        colorCode = getIntent().getBundleExtra("bundle").getString("color");
        conversionData = SuperLifeSecretPreferences.getInstance().getConversionData();
        userData = SuperLifeSecretPreferences.getInstance().getUserData();
        setUpToolbar(title);
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        getSubCategory();
        subList = new ArrayList<>();
        subListAdapter = new SubListAdapter(subList, this, colorCode);
        recyclerView.setAdapter(subListAdapter);
        int screenWidth = CommonUtils.getScreenWidth(this);
        int height = screenWidth * 6 / 16;
        autoScrollViewPager.getLayoutParams().width = screenWidth;
        autoScrollViewPager.getLayoutParams().height = height;
        setUpBanner();
        setUpBottomBar2();
        if (SuperLifeSecretPreferences.getInstance().getSelectedBooks()!=null&&SuperLifeSecretPreferences.getInstance().getSelectedBooks().size()>0){
            CommonUtils.startActivity(this, FirstBookActivity.class);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (subListAdapter != null) {
            subListAdapter.notifyDataSetChanged();
        }
    }

    private void setUpBottomBar2() {
        List<AllMenuResponseData> bottomList = new ArrayList<>();
        List<AllMenuResponseData> subMenuList = SuperLifeSecretPreferences.getInstance().getAllCategories();
        if (subMenuList != null && subMenuList.size() > 0) {
            for (AllMenuResponseData subcategoryModel : subMenuList) {
                if (subcategoryModel.isSelected()) {
                    bottomList.add(subcategoryModel);
                }
            }
        } else {

        }
        if (bottomList.size() > 2) {
            textView1.setText(conversionData.getHome());
            tab2.setTag(bottomList.get(0));
            textView2.setText(bottomList.get(0).getTitle());
            tab3.setTag(bottomList.get(1));
            textView3.setText(bottomList.get(1).getTitle());
            tab4.setTag(bottomList.get(2));
            textView4.setText(bottomList.get(2).getTitle());
            ImageLoadUtils.loadImage(bottomList.get(0).getParent_image() != null && !bottomList.get(0).getParent_image().isEmpty() ? bottomList.get(0).getParent_image() : bottomList.get(0).getImage(), imageView2, R.drawable.ic_logo);
            ImageLoadUtils.loadImage(bottomList.get(1).getParent_image() != null && !bottomList.get(1).getParent_image().isEmpty() ? bottomList.get(1).getParent_image() : bottomList.get(1).getImage(), imageView3, R.drawable.ic_logo);
            ImageLoadUtils.loadImage(bottomList.get(2).getParent_image() != null && !bottomList.get(2).getParent_image().isEmpty() ? bottomList.get(2).getParent_image() : bottomList.get(2).getImage(), imageView4, R.drawable.ic_logo);
            imageView1.setImageResource(R.drawable.home);
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
                onBackPressed();
                break;
            case R.id.tab_2:
            case R.id.tab_3:
            case R.id.tab_4:
                handleBottomClick((AllMenuResponseData) view.getTag());
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
        Toast.makeText(this, "f"+tag.getType(), Toast.LENGTH_SHORT).show();
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
            }

            @Override
            public void onNegativeClick() {

            }
        });
    }

    public void handleMainCategoryClicked(AllMenuResponseData data) {

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