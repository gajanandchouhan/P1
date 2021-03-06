package com.superlifesecretcode.app.ui.mycountryactivities;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.superlifesecretcode.app.R;
import com.superlifesecretcode.app.data.model.language.LanguageResponseData;
import com.superlifesecretcode.app.data.model.myannoucement.MyAnnouncementResponseData;
import com.superlifesecretcode.app.data.model.mycountryactivities.MyCountryActivityResponseData;
import com.superlifesecretcode.app.data.model.userdetails.UserDetailResponseData;
import com.superlifesecretcode.app.data.persistance.SuperLifeSecretPreferences;
import com.superlifesecretcode.app.ui.base.BaseActivity;
import com.superlifesecretcode.app.ui.myannouncement.MyAnnouncementAdapter;
import com.superlifesecretcode.app.ui.myannouncement.MyAnnouncementPresenter;
import com.superlifesecretcode.app.ui.myannouncement.MyAnnouncementView;
import com.superlifesecretcode.app.ui.myannouncement.addannouncement.AddAnnouncementActivity;
import com.superlifesecretcode.app.ui.mycountryactivities.addcountryactivity.AddCountryActivityActivity;
import com.superlifesecretcode.app.ui.picker.AlertDialog;
import com.superlifesecretcode.app.util.CommonUtils;
import com.superlifesecretcode.app.util.ConstantLib;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyCountryActivity extends BaseActivity implements View.OnClickListener, MyCountryActivityView {

    public static boolean shouldRefresh = false;
    private UserDetailResponseData userData;
    private LanguageResponseData conversionData;
    private RecyclerView recyclerView;

    private List<MyCountryActivityResponseData> countryActivityList;
    private MyCountryActivityAdapter adapter;
    private MyCountryActivitityPresenter presenter;
    private int position;
    private String permisionStatus = "1";// ConstantLib.PERMISSION_DEFAULT;


    @Override

    protected int getContentView() {
        return R.layout.activity_my_announcement;
    }

    @Override
    protected void initializeView() {
        conversionData = SuperLifeSecretPreferences.getInstance().getConversionData();
        userData = SuperLifeSecretPreferences.getInstance().getUserData();
        setUpToolbar();
        ImageView viewById = findViewById(R.id.imageView_profile);
        viewById.getLayoutParams().height = (int) getResources().getDimension(R.dimen._25sdp);
        viewById.getLayoutParams().width = (int) getResources().getDimension(R.dimen._25sdp);
        viewById.setVisibility(View.VISIBLE);
        viewById.setImageResource(R.drawable.add_plus_btn);
        viewById.setBackground(ContextCompat.getDrawable(this, R.drawable.circle_count_bg));
        int dimension = (int) getResources().getDimension(R.dimen._5sdp);
        viewById.setPadding(dimension, dimension, dimension, dimension);
        viewById.setOnClickListener(this);
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        countryActivityList = new ArrayList<>();
        adapter = new MyCountryActivityAdapter(countryActivityList, this);
        recyclerView.setAdapter(adapter);
        findViewById(R.id.text_view_add).setOnClickListener(this);
        getMyAnnoucement();
    }

    private void setUpToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        TextView textViewTitle = findViewById(R.id.textView_title);
        if (conversionData != null)
            textViewTitle.setText(conversionData.getCountry_activities());
    }

    @Override
    protected void initializePresenter() {
        presenter = new MyCountryActivitityPresenter(this);
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
        presenter.getMyCountryActivity(headers);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imageView_profile:
                switch (permisionStatus) {
                    case ConstantLib.PERMISSION_DEFAULT:
                        showRequestAlert(conversionData.getSubmit_req_msg());
                        break;
                    case ConstantLib.PERMISSION_DECLINED:
                        showMessageAlert(conversionData.getSubmit_req_declined_msg());
                        break;
                    case ConstantLib.PERMISSION_PENDING:
                        showMessageAlert(conversionData.getSubmit_req_pending_msg());
                        break;
                    default:
                        CommonUtils.startActivity(this, AddCountryActivityActivity.class);
                        break;
                }


                break;
        }
    }

    @Override
    public void setCountryActivties(List<MyCountryActivityResponseData> data) {
        if (data != null && data.size() > 0) {
            countryActivityList.clear();
            countryActivityList.addAll(data);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void deleted() {
        countryActivityList.remove(position);
        adapter.notifyItemRemoved(position);
    }

    @Override
    public void onPermissionStatus(String permissionStatus) {
        this.permisionStatus = permissionStatus;
        switch (permisionStatus) {
            case ConstantLib.PERMISSION_DEFAULT:
                showRequestAlert(conversionData.getSubmit_req_msg());
                break;
            case ConstantLib.PERMISSION_DECLINED:
                showMessageAlert(conversionData.getSubmit_req_declined_msg());
                break;
            case ConstantLib.PERMISSION_PENDING:
                showMessageAlert(conversionData.getSubmit_req_pending_msg());
                break;
        }
    }

    @Override
    public void onRequestSuccess() {
        onBackPressed();
    }

    @Override
    public void onRequestFailed() {
        onBackPressed();
    }

    private void showMessageAlert(String message) {
        CommonUtils.showAlert(this, message, conversionData.getOk(), null, new AlertDialog.OnClickListner() {
            @Override
            public void onPositiveClick() {
                onBackPressed();
            }

            @Override
            public void onNegativeClick() {
                onBackPressed();
            }
        });
    }

    private void showRequestAlert(String message) {
        CommonUtils.showAlert(this, message, conversionData.getYes(), conversionData.getNo(), new AlertDialog.OnClickListner() {
            @Override
            public void onPositiveClick() {
                sendRequest();
            }

            @Override
            public void onNegativeClick() {
                onBackPressed();
            }
        });
    }

    private void sendRequest() {
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + userData.getApi_token());
        presenter.sendEventAddRequest(headers);
    }


    public void deleteCountryActivity(int position) {
        this.position = position;
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + userData.getApi_token());
        HashMap<String, String> params = new HashMap<>();
        params.put("id", countryActivityList.get(position).getActivity_id());
        presenter.deleteActivity(params, headers);
    }
}
