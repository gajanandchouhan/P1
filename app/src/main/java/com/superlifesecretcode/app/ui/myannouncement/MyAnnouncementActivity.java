package com.superlifesecretcode.app.ui.myannouncement;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.superlifesecretcode.app.R;
import com.superlifesecretcode.app.data.model.language.LanguageResponseData;
import com.superlifesecretcode.app.data.model.userdetails.UserDetailResponseData;
import com.superlifesecretcode.app.data.persistance.SuperLifeSecretPreferences;
import com.superlifesecretcode.app.ui.base.BaseActivity;
import com.superlifesecretcode.app.ui.myannouncement.addannouncement.AddAnnouncementActivity;
import com.superlifesecretcode.app.util.CommonUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyAnnouncementActivity extends BaseActivity implements View.OnClickListener, MyAnnouncementView {

    public static boolean shouldRefresh = false;
    private UserDetailResponseData userData;
    private LanguageResponseData conversionData;
    private RecyclerView recyclerView;

    private List<Object> announcementList;
    private MyAnnouncementAdapter adapter;
    private MyAnnouncementPresenter presenter;


    @Override

    protected int getContentView() {
        return R.layout.activity_my_announcement;
    }

    @Override
    protected void initializeView() {
        conversionData = SuperLifeSecretPreferences.getInstance().getConversionData();
        userData = SuperLifeSecretPreferences.getInstance().getUserData();
        setUpToolbar();
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        announcementList = new ArrayList<>();
        adapter = new MyAnnouncementAdapter(announcementList, this);
        recyclerView.setAdapter(adapter);
        findViewById(R.id.text_view_add).setOnClickListener(this);
    }

    private void setUpToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        TextView textViewTitle = findViewById(R.id.textView_title);
        if (conversionData != null)
            textViewTitle.setText(conversionData.getAnnouncement());
    }

    @Override
    protected void initializePresenter() {
        presenter = new MyAnnouncementPresenter(this);
        presenter.setView(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (shouldRefresh) {
            getMyAnnoucement();
            shouldRefresh = false;
        }
    }

    private void getMyAnnoucement() {
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + userData.getApi_token());
        presenter.getMyAnnoucement(headers);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.text_view_add:
                CommonUtils.startActivity(this, AddAnnouncementActivity.class);
                break;
        }
    }
}
