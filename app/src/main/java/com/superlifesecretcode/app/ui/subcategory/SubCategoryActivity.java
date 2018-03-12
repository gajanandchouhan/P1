package com.superlifesecretcode.app.ui.subcategory;


import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.superlifesecretcode.app.R;
import com.superlifesecretcode.app.data.model.SubcategoryModel;
import com.superlifesecretcode.app.ui.adapter.SubacategoryListAdapter;
import com.superlifesecretcode.app.ui.base.BaseActivity;
import com.superlifesecretcode.app.util.ConstantLib;
import com.superlifesecretcode.app.util.SpacesItemDecoration;
import com.superlifesecretcode.app.util.SpacesItemDecorationGridLayout;

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
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        recyclerView.addItemDecoration(new SpacesItemDecorationGridLayout(3, 30, true));
        recyclerView.setAdapter(new SubacategoryListAdapter(getList(positon), this));
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
                list.add(new SubcategoryModel(android.R.drawable.ic_menu_camera, "Disclosure", "http://www.google.com"));
                list.add(new SubcategoryModel(android.R.drawable.ic_menu_camera, "Service Provided", ConstantLib.SERVICE_PROVIDED_SIMPLY));
                return list;
            case 2:
                list.add(new SubcategoryModel(android.R.drawable.ic_menu_camera, "Books", " https://www.richestlife.com/product-category/publishing-books/"));
                list.add(new SubcategoryModel(android.R.drawable.ic_menu_camera, "Talks + Classes", "https://www.richestlife.com/product-category/%E8%AC%9B%E5%BA%A7%E8%AA%B2%E7%A8%8B/"));
                list.add(new SubcategoryModel(android.R.drawable.ic_menu_camera, "Online Courses", " https://www.richestlife.com/product-category/%25e4%25b8%25bb%25e9%25a1%258c%25e8%25aa%25b2%25e7%25a8%258b/"));
                list.add(new SubcategoryModel(android.R.drawable.ic_menu_camera, "Cards", "https://www.richestlife.com/product-category/%E7%89%8C%E5%8D%A1/"));
//                list.add(new SubcategoryModel(android.R.drawable.ic_menu_camera, "Accessories","http://www.google.com"));
                return list;
            case 3:
                list.add(new SubcategoryModel(android.R.drawable.ic_menu_camera, "Join Online Course", "https://zoom.us/join"));
                list.add(new SubcategoryModel(android.R.drawable.ic_menu_camera, "Facebook", "https://m.facebook.com/SuperLifeCode/?refid=46"));
                list.add(new SubcategoryModel(android.R.drawable.ic_menu_camera, "Sun Youtube Video", "https://www.youtube.com/watch?v=PKNSIpl1aYQ&feature=youtu.be%20Inbox%20x"));
                return list;
            case 4:
                list.add(new SubcategoryModel(android.R.drawable.ic_menu_camera, "News Update", ""));
                list.add(new SubcategoryModel(android.R.drawable.ic_menu_camera, "Event+Activities", ""));
                return list;

            case 5:
                list.add(new SubcategoryModel(android.R.drawable.ic_menu_camera, "Latest", ""));
                list.add(new SubcategoryModel(android.R.drawable.ic_menu_camera, "Submit", ""));
                return list;
            case 6:
                list.add(new SubcategoryModel(android.R.drawable.ic_menu_camera, "Personal+Event Calendar", ""));
                return list;
            case 7:
                list.add(new SubcategoryModel(android.R.drawable.ic_menu_camera, "Songs", " https://www.richestlife.com/music-downloads/"));
//                list.add(new SubcategoryModel(android.R.drawable.ic_menu_camera, "Wallpapers","https://m.facebook.com/SuperLifeCode/?refid=46"));
//                list.add(new SubcategoryModel(android.R.drawable.ic_menu_camera, "Sun Emoji","https://www.youtube.com/watch?v=PKNSIpl1aYQ&feature=youtu.be%20Inbox%20x"));
                return list;

            case 8:
                list.add(new SubcategoryModel(android.R.drawable.ic_menu_camera, "Study Group", ""));
                list.add(new SubcategoryModel(android.R.drawable.ic_menu_camera, "On-site sharing", ""));
                return list;
        }
        return list;
    }
}
