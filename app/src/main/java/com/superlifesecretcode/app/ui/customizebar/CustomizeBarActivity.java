package com.superlifesecretcode.app.ui.customizebar;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.superlifesecretcode.app.R;
import com.superlifesecretcode.app.data.model.SubcategoryModel;
import com.superlifesecretcode.app.data.model.allmenu.AllMenuResponseData;
import com.superlifesecretcode.app.data.model.language.LanguageResponseData;
import com.superlifesecretcode.app.data.model.userdetails.UserDetailResponseData;
import com.superlifesecretcode.app.data.persistance.SuperLifeSecretPreferences;
import com.superlifesecretcode.app.ui.base.BaseActivity;
import com.superlifesecretcode.app.ui.main.MainActivity;
import com.superlifesecretcode.app.ui.sharing_latest.LatestAapter;
import com.superlifesecretcode.app.util.CommonUtils;

import java.util.ArrayList;
import java.util.List;

public class CustomizeBarActivity extends BaseActivity {


    private BottomBartAapter latestAapter;
    private UserDetailResponseData userData;
    private LanguageResponseData conversionData;

    @Override
    protected int getContentView() {
        return R.layout.activity_customize_bar;
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
        latestAapter = new BottomBartAapter(this);
        recyclerView.setAdapter(latestAapter);
    }

    private void setUpToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        TextView textViewTitle = findViewById(R.id.textView_title);
        ImageView imageViewProfile = findViewById(R.id.imageView_profile);
        imageViewProfile.setVisibility(View.VISIBLE);
        imageViewProfile.setImageResource(R.drawable.right);
        imageViewProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<AllMenuResponseData> list = latestAapter.getList();
                if (isValid(list)) {
                    SuperLifeSecretPreferences.getInstance().setAllCategories(list);
                    MainActivity.BOTTOM_BAR_CHANGED=true;
                    onBackPressed();
                } else {
                    CommonUtils.showToast(CustomizeBarActivity.this, "Please select at least 4 menu options.");
                }

            }
        });
        textViewTitle.setText(conversionData.getCustmize_bar());
    }

    @Override
    protected void initializePresenter() {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    private boolean isValid(List<AllMenuResponseData> list) {
        int count = 0;
        for (AllMenuResponseData subcategoryModel : list) {
            if (subcategoryModel.isSelected()) {
                count = count + 1;
            }
        }
        return count == 4;
    }
}
