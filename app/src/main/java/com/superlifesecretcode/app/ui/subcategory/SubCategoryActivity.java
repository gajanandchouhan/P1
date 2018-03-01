package com.superlifesecretcode.app.ui.subcategory;


import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.superlifesecretcode.app.R;
import com.superlifesecretcode.app.data.model.SubcategoryModel;
import com.superlifesecretcode.app.ui.adapter.SubacategoryListAdapter;
import com.superlifesecretcode.app.ui.base.BaseActivity;
import com.superlifesecretcode.app.util.SpacesItemDecoration;

import java.util.ArrayList;
import java.util.List;

public class SubCategoryActivity extends BaseActivity {

    private int positon;

    @Override
    protected int getContentView() {
        overridePendingTransition(R.anim.activity_open_translate, R.anim.activity_close_scale);
        return R.layout.activity_sub_category;
    }

    @Override
    protected void initializeView() {
        String title = getIntent().getBundleExtra("bundle").getString("title");
        positon = getIntent().getBundleExtra("bundle").getInt("pos");
        setUpToolbar(title);
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new SpacesItemDecoration(10));
        recyclerView.setAdapter(new SubacategoryListAdapter(getList(positon),this));
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

    @Override
    protected void initializePresenter() {

    }

    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(R.anim.activity_open_scale, R.anim.activity_close_translate);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    public List<SubcategoryModel> getList(int position) {
        List<SubcategoryModel> list = new ArrayList<>();
        switch (position) {
            case 1:
                list.add(new SubcategoryModel(android.R.drawable.ic_menu_camera, "Disclosure","http://www.google.com"));
                list.add(new SubcategoryModel(android.R.drawable.ic_menu_camera, "Service Provided","http://www.google.com"));
                return list;

            case 3:
                list.add(new SubcategoryModel(android.R.drawable.ic_menu_camera, "Join Online Course"," https://zoom.us/join"));
                list.add(new SubcategoryModel(android.R.drawable.ic_menu_camera, "Facebook","https://m.facebook.com/SuperLifeCode/?refid=46"));
                list.add(new SubcategoryModel(android.R.drawable.ic_menu_camera, "Sun Youtube Video","https://www.youtube.com/watch?v=PKNSIpl1aYQ&feature=youtu.be%20Inbox%20x"));
                list.add(new SubcategoryModel(android.R.drawable.ic_menu_camera, "Learning Administrative Module","http://www.google.com"));
                return list;
        }
        return list;
    }
}
