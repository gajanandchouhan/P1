package com.superlifesecretcode.app.ui.sharing_submit;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.kbeanie.multipicker.api.ImagePicker;
import com.kbeanie.multipicker.api.Picker;
import com.kbeanie.multipicker.api.VideoPicker;
import com.kbeanie.multipicker.api.callbacks.ImagePickerCallback;
import com.kbeanie.multipicker.api.callbacks.VideoPickerCallback;
import com.kbeanie.multipicker.api.entity.ChosenImage;
import com.kbeanie.multipicker.api.entity.ChosenVideo;
import com.superlifesecretcode.app.R;
import com.superlifesecretcode.app.data.model.language.LanguageResponseData;
import com.superlifesecretcode.app.data.model.userdetails.UserDetailResponseData;
import com.superlifesecretcode.app.data.persistance.SuperLifeSecretPreferences;
import com.superlifesecretcode.app.ui.base.BaseActivity;
import com.superlifesecretcode.app.util.CommonUtils;
import com.superlifesecretcode.app.util.ImagePickerUtils;
import com.superlifesecretcode.app.util.PermissionConstant;
import com.superlifesecretcode.app.util.SpacesItemDecorationGridLayout;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class SubmitActivity extends BaseActivity implements View.OnClickListener, SubmitView {


    private static final int REQUEST_CODE_CHOOSE = 115;
    private ImageAapter submitAapter;
    private UserDetailResponseData userData;
    private LanguageResponseData conversionData;
    private List<String> imageList;
    EditText editTextDesc;
    private SubmitPresenter presenter;
    private ImagePicker imagePicker;
    private VideoPicker videoPicker;
    private boolean openVideoPicker;

    @Override
    protected int getContentView() {
        return R.layout.activity_submit;
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void initializeView() {
        userData = SuperLifeSecretPreferences.getInstance().getUserData();
        conversionData = SuperLifeSecretPreferences.getInstance().getConversionData();
        editTextDesc = findViewById(R.id.edit_text_desc);
        Button buttonSubmit = findViewById(R.id.button_submit);
        editTextDesc.setHint(conversionData.getWhat_in_mind());
        buttonSubmit.setOnClickListener(this);
        setUpToolbar();
        findViewById(R.id.imageView_upload).setOnClickListener(this);
        findViewById(R.id.imageView_upload_image).setOnClickListener(this);
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        imageList = new ArrayList<>();
        submitAapter = new ImageAapter(imageList, this);
        recyclerView.setAdapter(submitAapter);
        recyclerView.addItemDecoration(new SpacesItemDecorationGridLayout(3, 25, true));
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
        presenter = new SubmitPresenter(this);
        presenter.setView(this);

    }

    private void pickImage() {
        if (imagePicker == null) {
            imagePicker = new ImagePicker(this);
        }
        imagePicker.setImagePickerCallback(new ImagePickerCallback() {
            @Override
            public void onImagesChosen(List<ChosenImage> list) {
                setPathArray(list);
                for (ChosenImage chosenImage : list) {
                    long size = chosenImage.getSize();
                    Log.v("SIZE", size + "");
                }

            }

            @Override
            public void onError(String s) {

            }
        });
        imagePicker.allowMultiple(); // Default is false
        imagePicker.shouldGenerateMetadata(true); // Default is true
        imagePicker.shouldGenerateThumbnails(false); // Default is true
        imagePicker.pickImage();
    }

    private void pickVideo() {
        if (videoPicker == null) {
            videoPicker = new VideoPicker(this);
        }
        videoPicker.setVideoPickerCallback(new VideoPickerCallback() {
            @Override
            public void onVideosChosen(List<ChosenVideo> list) {
                setVideoPathArray(list);
            }

            @Override
            public void onError(String s) {

            }
        });
        videoPicker.allowMultiple(); // Default is false
        videoPicker.shouldGenerateMetadata(true); // Default is true
        videoPicker.shouldGeneratePreviewImages(false); // Default is true
        videoPicker.pickVideo();
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
            case R.id.imageView_upload_image:
                if (CommonUtils.hasPermissions(this, PermissionConstant.PERMISSION_PROFILE)) {
                    pickImage();
                } else {
                    openVideoPicker = false;
                    ActivityCompat.requestPermissions(this, PermissionConstant.PERMISSION_PROFILE, PermissionConstant.CODE_PROFILE);
                }
                break;
            case R.id.imageView_upload:
                if (CommonUtils.hasPermissions(this, PermissionConstant.PERMISSION_PROFILE)) {
                    pickVideo();
                } else {
                    openVideoPicker = true;
                    ActivityCompat.requestPermissions(this, PermissionConstant.PERMISSION_PROFILE, PermissionConstant.CODE_PROFILE);
                }
                break;
            case R.id.button_submit:
                addShare();
                break;
        }
    }

    private void addShare() {
        String desc = editTextDesc.getText().toString().trim();
        if (desc.isEmpty() && imageList.isEmpty()) {
            return;
        }
        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM);
        builder.addFormDataPart("user_id", userData.getUser_id());
        builder.addFormDataPart("content", desc);
        for (int i = 0; i < imageList.size(); i++) {
            try {
                if (imageList.get(i) != null) {
                    File file = new File(imageList.get(i));
                    RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                    builder.addFormDataPart("sharing_files[]", file.getName(), requestBody);
                }
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }
        }
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + userData.getApi_token());
        RequestBody finalRequestBody = builder.build();
        presenter.addShare(finalRequestBody, headers);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Picker.PICK_IMAGE_DEVICE) {
            imagePicker.submit(data);
        }
        if (requestCode == Picker.PICK_VIDEO_DEVICE) {
            videoPicker.submit(data);
        }
    }

    private void setPathArray(List<ChosenImage> uris) {
        for (ChosenImage uri : uris) {
            if (uri != null) {
                String path = uri.getOriginalPath();
                if (path != null) {
                    imageList.add(path);
                }
            }
        }
        submitAapter.notifyDataSetChanged();
    }

    private void setVideoPathArray(List<ChosenVideo> uris) {
        for (ChosenVideo uri : uris) {
            if (uri != null) {
                String path = uri.getOriginalPath();
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
            if (openVideoPicker)
                pickVideo();
            else
                pickImage();
        }
    }

    @Override
    public void onAdded() {
        onBackPressed();
    }
}
