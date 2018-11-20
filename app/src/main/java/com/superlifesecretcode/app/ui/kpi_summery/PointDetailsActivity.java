package com.superlifesecretcode.app.ui.kpi_summery;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.superlifesecretcode.app.R;
import com.superlifesecretcode.app.data.model.kpi.KPI;
import com.superlifesecretcode.app.data.model.kpi.PointDetailsModel;
import com.superlifesecretcode.app.data.model.kpi.TaskDetails;
import com.superlifesecretcode.app.data.model.language.LanguageResponseData;
import com.superlifesecretcode.app.data.model.userdetails.UserDetailResponseData;
import com.superlifesecretcode.app.data.persistance.SuperLifeSecretPreferences;
import com.superlifesecretcode.app.ui.base.BaseActivity;
import com.superlifesecretcode.app.ui.picker.DateRangePicker;
import com.superlifesecretcode.app.util.CommonUtils;
import com.superlifesecretcode.app.util.SpacesItemDecoration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PointDetailsActivity extends BaseActivity implements KpiView, View.OnClickListener {


    private UserDetailResponseData userData;
    private LanguageResponseData conversionData;
    private PointDetailsPresenter presenter;
    private ImageView imageViewProfile;
    private RecyclerView recyclerView;
    private PointDetailAdapter adapter;
    private List<PointDetailsModel> list;
    private DateRangePicker rangePicker;

    @Override
    protected int getContentView() {
        return R.layout.activity_point_details;
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
        recyclerView = findViewById(R.id.recycler_view);
        imageViewProfile = findViewById(R.id.imageView_profile);
        imageViewProfile.setVisibility(View.VISIBLE);
        imageViewProfile.setImageResource(R.drawable.filter);
        imageViewProfile.setOnClickListener(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<>();
        adapter = new PointDetailAdapter(list, this);
        recyclerView.setAdapter(adapter);
        getPointSummary("","");
//        recyclerView.addItemDecoration(new SpacesItemDecoration(10));


    }

    private void getPointSummary(String startDate, String endDate) {
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + userData.getApi_token());
        Map<String, String> params = new HashMap<>();
        params.put("start", startDate);
        params.put("end", endDate);
        presenter.getPointSummary(headers, params);
    }

    private void setUpToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        TextView textViewTitle = findViewById(R.id.textView_title);
        textViewTitle.setText(conversionData != null ? conversionData.getSummary() : "Summary");
    }


    @Override
    protected void initializePresenter() {
        presenter = new PointDetailsPresenter(this);
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
    public void getKpiSummeryData(KPI kpi) {

    }

    @Override
    public void setPointList(List<TaskDetails> taskDetailsList,String sharingPoints) {
        if (taskDetailsList != null && taskDetailsList.size() > 0) {
            this.list.clear();
            this.list.add(new PointDetailsModel(null,false,new TaskDetails(conversionData.getSharing(),sharingPoints,null)));
            String dateTemp = "";
            List<PointDetailsModel> pointDetailsModelList = new ArrayList<>();
            for (TaskDetails taskDetails : taskDetailsList) {
                if (!taskDetails.getDate().equalsIgnoreCase(dateTemp)) {
                    pointDetailsModelList.add(new PointDetailsModel(taskDetails.getDate(), true, taskDetails));
                }
                pointDetailsModelList.add(new PointDetailsModel(taskDetails.getDate(), false, taskDetails));
                dateTemp = taskDetails.getDate();
            }
            this.list.addAll(pointDetailsModelList);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.imageView_profile) {
            showDateRnagePicker();
        }
    }


    private void showDateRnagePicker() {
        if (rangePicker == null) {
            rangePicker = new DateRangePicker(this);
            rangePicker.setShowForTask(true);
            rangePicker.setOnClickListner(new DateRangePicker.OnClickListner() {
                @Override
                public void onDateRnageSelected(String startDate, String endDate) {
                    CommonUtils.showLog("DATE", startDate + ", " + endDate);
                    rangePicker.dismiss();
                    getPointSummary(startDate,endDate);
                }
            });
        }
        rangePicker.show();
    }
}
