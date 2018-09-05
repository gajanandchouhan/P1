package com.superlifesecretcode.app.ui.myannouncement;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.superlifesecretcode.app.R;
import com.superlifesecretcode.app.data.model.language.LanguageResponseData;
import com.superlifesecretcode.app.data.model.myannoucement.MyAnnouncementResponseData;
import com.superlifesecretcode.app.data.model.userdetails.UserDetailResponseData;
import com.superlifesecretcode.app.data.persistance.SuperLifeSecretPreferences;
import com.superlifesecretcode.app.ui.base.BaseActivity;
import com.superlifesecretcode.app.ui.myannouncement.addannouncement.AddAnnouncementActivity;
import com.superlifesecretcode.app.ui.picker.AlertDialog;
import com.superlifesecretcode.app.util.CommonUtils;
import com.superlifesecretcode.app.util.ConstantLib;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyAnnouncementActivity extends BaseActivity implements View.OnClickListener, MyAnnouncementView {

    public static boolean shouldRefresh = false;
    private UserDetailResponseData userData;
    private LanguageResponseData conversionData;
    private RecyclerView recyclerView;

    private List<MyAnnouncementResponseData> announcementList;
    private MyAnnouncementAdapter adapter;
    private MyAnnouncementPresenter presenter;
    private int position;
    private String permisionStatus = ConstantLib.PERMISSION_DEFAULT;


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
                if (permisionStatus.equals(ConstantLib.PERMISSION_DEFAULT)) {
                    showRequestAlert("You have not permission to add Events, Are you want to send request for submission event?");
                } else if (permisionStatus.equals(ConstantLib.PERMISSION_DECLINED)) {
                    showMessageAlert("You have not permission to add any event.");
                } else if (permisionStatus.equals(ConstantLib.PERMISSION_PENDING)) {
                    showMessageAlert("You event submission request pending for approval, we will update you soon.");
                } else {
                    CommonUtils.startActivity(this, AddAnnouncementActivity.class);
                }

                break;
        }
    }

    @Override
    public void setAnnouncementList(List<MyAnnouncementResponseData> data) {
        if (data != null && data.size() > 0) {
            announcementList.clear();
            announcementList.addAll(data);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void deleted() {
        announcementList.remove(position);
        adapter.notifyItemRemoved(position);
    }

    @Override
    public void onPermissionStatus(String permissionStatus) {
        this.permisionStatus = permissionStatus;
        if (permissionStatus.equals(ConstantLib.PERMISSION_DEFAULT)) {
            showRequestAlert("You have not permission to add Events, Are you want to send request for submission event?");
        } else if (permissionStatus.equals(ConstantLib.PERMISSION_DECLINED)) {
            showMessageAlert("You have not permission to add any event.");
        } else if (permissionStatus.equals(ConstantLib.PERMISSION_PENDING)) {
            showMessageAlert("You event submission request pending for approval, we will update you soon.");
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


    public void deleteAnnouncement(int position) {
        this.position = position;
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + userData.getApi_token());
        HashMap<String, String> params = new HashMap<>();
        params.put("id", announcementList.get(position).getAnnouncement_id());
        presenter.deleteAnnouncement(params, headers);
    }
}
