package com.superlifesecretcode.app.ui.sharing_submit;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.superlifesecretcode.app.R;
import com.superlifesecretcode.app.data.model.country.CountryResponseData;
import com.superlifesecretcode.app.data.model.language.LanguageResponseData;
import com.superlifesecretcode.app.data.model.shares.ShareListResponseData;
import com.superlifesecretcode.app.data.model.userdetails.UserDetailResponseData;
import com.superlifesecretcode.app.data.persistance.SuperLifeSecretPreferences;
import com.superlifesecretcode.app.ui.base.BaseActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SubmitListActivity extends BaseActivity implements ShareListView {


    private SubmitListAapter submitAapter;
    private UserDetailResponseData userData;
    private LanguageResponseData conversionData;
    private List<ShareListResponseData> shareList;
    private ShareListPresenter presenter;

    @Override
    protected int getContentView() {
        return R.layout.activity_submit_list;
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void initializeView() {
        userData = SuperLifeSecretPreferences.getInstance().getUserData();
        conversionData = SuperLifeSecretPreferences.getInstance().getConversionData();
        setUpToolbar();
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        shareList = new ArrayList<>();
        submitAapter = new SubmitListAapter(shareList, this);
        recyclerView.setAdapter(submitAapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getSubmitionList();
    }

    private void getSubmitionList() {
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + userData.getApi_token());
        HashMap<String, String> params = new HashMap<>();
        params.put("user_id", userData.getUser_id());
        presenter.getShare(params, headers);
    }

    private void setUpToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        TextView textViewTitle = findViewById(R.id.textView_title);
        textViewTitle.setText(conversionData != null ? conversionData.getShare_submit() : "Submit");
    }

    @Override
    protected void initializePresenter() {
        presenter = new ShareListPresenter(this);
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
    public void setShareListData(List<ShareListResponseData> listData,String nextPage) {
        if (listData != null) {
            shareList.clear();
            shareList.addAll(listData);
            submitAapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onLiked() {

    }

    @Override
    public void setCountryData(List<CountryResponseData> data) {

    }
}
