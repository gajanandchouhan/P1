package com.superlifesecretcode.app.ui.sharing_submit;

import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.superlifesecretcode.app.R;
import com.superlifesecretcode.app.ui.base.BaseActivity;
import com.superlifesecretcode.app.util.ImageLoadUtils;

public class ImageViewerActivity extends BaseActivity {


    @Override
    protected int getContentView() {
        return R.layout.activity_image_viewer;
    }

    @Override
    protected void initializeView() {
        setUpToolbar();
        String image = getIntent().getBundleExtra("bundle").getString("image");
        ImageLoadUtils.loadImage(image, (ImageView) findViewById(R.id.imageView));
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
    private void setUpToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        TextView textViewTitle = findViewById(R.id.textView_title);
    }
}
