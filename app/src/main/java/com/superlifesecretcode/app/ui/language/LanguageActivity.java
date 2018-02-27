package com.superlifesecretcode.app.ui.language;

import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

import com.superlifesecretcode.app.R;
import com.superlifesecretcode.app.ui.adapter.LanguagePagerAdapter;
import com.superlifesecretcode.app.ui.base.BaseActivity;
import com.superlifesecretcode.app.ui.disclosure.DiscolsureActivity;
import com.superlifesecretcode.app.util.CommonUtils;

import java.util.ArrayList;
import java.util.List;

public class LanguageActivity extends BaseActivity implements View.OnClickListener {
    ViewPager pager;

    @Override
    protected int getContentView() {
        return R.layout.activity_language;
    }

    @Override
    protected void initializeView() {
        final List<String> list = new ArrayList<>();
        list.add("English");
        list.add("繁體中文");
        list.add("繁体中文");
        final ImageView imageViewPre = findViewById(R.id.imageView_previous);
        final ImageView imageViewNext = findViewById(R.id.imageView_next);
        imageViewPre.setOnClickListener(this);
        imageViewNext.setOnClickListener(this);
        imageViewPre.setVisibility(View.GONE);
        findViewById(R.id.textView_next).setOnClickListener(this);
        pager = findViewById(R.id.pager);
        pager.setAdapter(new LanguagePagerAdapter(this));
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 2) {
                    imageViewNext.setVisibility(View.GONE);
                }
                else{
                    imageViewNext.setVisibility(View.VISIBLE);
                }
                if (position == 0) {
                    imageViewPre.setVisibility(View.GONE);
                }
                else{
                    imageViewPre.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        pager.setPageTransformer(true, new ViewPager.PageTransformer() {
            @Override
            public void transformPage(View page, float position) {
                float percentage = 1 - Math.abs(position);
                page.setCameraDistance(12000);
                setVisibility(page, position);
                setTranslation(page);
                setSize(page, position, percentage);
                setRotation(page, position, percentage);
            }

            private void setVisibility(View page, float position) {
                if (position < 0.5 && position > -0.5) {
                    page.setVisibility(View.VISIBLE);
                } else {
                    page.setVisibility(View.INVISIBLE);
                }
            }

            private void setTranslation(View page) {
                ViewPager viewPager = (ViewPager) page.getParent();
                int scroll = viewPager.getScrollX() - page.getLeft();
                page.setTranslationX(scroll);
            }

            private void setSize(View page, float position, float percentage) {
                page.setScaleX((position != 0 && position != 1) ? percentage : 1);
                page.setScaleY((position != 0 && position != 1) ? percentage : 1);
            }

            private void setRotation(View page, float position, float percentage) {
                if (position > 0) {
                    page.setRotationY(-180 * (percentage + 1));
                } else {
                    page.setRotationY(180 * (percentage + 1));
                }
            }
        });

    }

    @Override
    protected void initializePresenter() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.textView_next:
                CommonUtils.startActivity(LanguageActivity.this, DiscolsureActivity.class);
                finish();
                break;

        }
    }
}
