package com.superlifesecretcode.app.ui.sharing_submit;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.superlifesecretcode.app.R;
import com.superlifesecretcode.app.data.model.language.LanguageResponseData;
import com.superlifesecretcode.app.data.model.userdetails.UserDetailResponseData;
import com.superlifesecretcode.app.data.persistance.SuperLifeSecretPreferences;
import com.superlifesecretcode.app.ui.base.BaseActivity;
import com.superlifesecretcode.app.util.CommonUtils;
import com.superlifesecretcode.app.util.ImagePickerUtils;
import com.superlifesecretcode.app.util.MyGldieEngine;
import com.superlifesecretcode.app.util.PermissionConstant;
import com.superlifesecretcode.app.util.SpacesItemDecoration;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;
import com.zhihu.matisse.filter.Filter;

import java.util.ArrayList;
import java.util.List;

public class SubmitActivity extends BaseActivity implements View.OnClickListener {


    private static final int REQUEST_CODE_CHOOSE = 115;
    private ImageAapter submitAapter;
    private UserDetailResponseData userData;
    private LanguageResponseData conversionData;
    private List<String> imageList;

    @Override
    protected int getContentView() {
        overridePendingTransition(R.anim.activity_open_translate, R.anim.activity_close_scale);
        return R.layout.activity_submit;
    }

    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(R.anim.activity_open_scale, R.anim.activity_close_translate);
    }

    @Override
    protected void initializeView() {
        userData = SuperLifeSecretPreferences.getInstance().getUserData();
        conversionData = SuperLifeSecretPreferences.getInstance().getConversionData();
        setUpToolbar();
        findViewById(R.id.imageView_upload).setOnClickListener(this);
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        imageList = new ArrayList<>();
        submitAapter = new ImageAapter(imageList, this);
        recyclerView.addItemDecoration(new SpacesItemDecoration(5));
        recyclerView.setAdapter(submitAapter);
    }

    private void setUpToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        TextView textViewTitle = findViewById(R.id.textView_title);
        textViewTitle.setText(conversionData != null ? conversionData.getSubmit() : "Submit");
    }

    @Override
    protected void initializePresenter() {

    }

    private void pickImage() {
        Matisse.from(this)
                .choose(MimeType.allOf())
                .countable(true)
                .maxSelectable(9)
                .gridExpectedSize((int) getResources().getDimension(R.dimen._100sdp))
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                .thumbnailScale(0.85f)
                .imageEngine(new MyGldieEngine())
                .forResult(REQUEST_CODE_CHOOSE);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imageView_upload:
                if (CommonUtils.hasPermissions(this, PermissionConstant.PERMISSION_PROFILE)) {
                    pickImage();
                } else {
                    ActivityCompat.requestPermissions(this, PermissionConstant.PERMISSION_PROFILE, PermissionConstant.CODE_PROFILE);
                }
                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CHOOSE && resultCode == RESULT_OK) {
            List<Uri> uris = Matisse.obtainResult(data);
            setPathArray(uris);
            Log.d("Matisse", "mSelected: " + uris);
        }
    }

    private void setPathArray(List<Uri> uris) {
        for (Uri uri : uris) {
            if (uri != null) {
                String path = ImagePickerUtils.getPath(this, uri);
                if (path != null) {
                    imageList.add(path);
                }
            }
        }
        submitAapter.notifyDataSetChanged();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PermissionConstant.CODE_PROFILE) {
            for (int grantResult : grantResults) {
                if (grantResult != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
            }
            pickImage();
        }
    }
}
