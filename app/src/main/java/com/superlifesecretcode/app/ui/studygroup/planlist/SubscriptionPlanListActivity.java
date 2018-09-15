package com.superlifesecretcode.app.ui.studygroup.planlist;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.superlifesecretcode.app.R;
import com.superlifesecretcode.app.data.model.language.LanguageResponseData;
import com.superlifesecretcode.app.data.model.studygroups.studtgroupplans.StudyGroupPlanData;
import com.superlifesecretcode.app.data.model.userdetails.UserDetailResponseData;
import com.superlifesecretcode.app.data.persistance.SuperLifeSecretPreferences;
import com.superlifesecretcode.app.ui.base.BaseActivity;
import com.superlifesecretcode.app.ui.studygroup.paymentproof.PaymentProofActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SubscriptionPlanListActivity extends BaseActivity implements SubscriptionPlanView {


    private static SubscriptionPlanListActivity activity;
    private LanguageResponseData conversionData;
    private UserDetailResponseData userData;
    private String groupTitle;
    private String groupId;
    private SubscriptionPlanPresenter presenter;
    private RecyclerView recyclerView;
    private List<StudyGroupPlanData> list;
    private StudyGroupPlanAdapter adapter;
    
    

    @Override
    protected int getContentView() {
        return R.layout.activity_subscription_plan_list;
    }

    private void setUpToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        TextView textViewTitle = findViewById(R.id.textView_title);
        textViewTitle.setText("Select Plan");

    }

    @Override
    protected void initializeView() {
        activity=this;
        conversionData = SuperLifeSecretPreferences.getInstance().getConversionData();
        userData = SuperLifeSecretPreferences.getInstance().getUserData();
        groupTitle = getIntent().getStringExtra("title");
        groupId = getIntent().getStringExtra("id");
        setUpToolbar();
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<>();
        adapter = new StudyGroupPlanAdapter(this, list);
        recyclerView.setAdapter(adapter);
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + userData.getApi_token());
        HashMap<String, String> params = new HashMap<>();
        params.put("study_group_id", groupId);
        presenter.getPlanList(params, headers);

    }

    @Override
    protected void initializePresenter() {
        presenter = new SubscriptionPlanPresenter(this);
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
    public void setItemList(List<StudyGroupPlanData> data) {
        if (data != null) {
            list.clear();
            list.addAll(data);
            adapter.notifyDataSetChanged();
        }
    }


    public void startPaymentConirmScreen(int position) {

        Intent intent = new Intent(this, PaymentProofActivity.class);
        intent.putExtra("plan_id", list.get(position).getPlan_id());
        intent.putExtra("plan_cost", list.get(position).getPlan_cost());
        intent.putExtra("plan_title",list.get(position).getPlan_title());
        intent.putExtra("plan_currency", list.get(position).getCurrency_symbol());
        intent.putExtra("group_name", groupTitle);
        intent.putExtra("group_id", groupId);
        startActivity(intent);
    }

    public static void finishActivity() {
        if (activity != null) {
            activity.finish();
        }
    }
}
