package com.superlifesecretcode.app.ui.language;

import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.superlifesecretcode.app.R;
import com.superlifesecretcode.app.data.model.language.LanguageResponseData;
import com.superlifesecretcode.app.data.persistance.SuperLifeSecretPreferences;
import com.superlifesecretcode.app.ui.adapter.LanguagePagerAdapter;
import com.superlifesecretcode.app.ui.base.BaseActivity;
import com.superlifesecretcode.app.ui.disclosure.DiscolsureActivity;
import com.superlifesecretcode.app.util.CommonUtils;
import com.superlifesecretcode.app.util.ConstantLib;
import com.superlifesecretcode.app.util.pagertransform.MyTransformer;

import java.util.HashMap;

public class LanguageActivity2 extends BaseActivity implements View.OnClickListener, LanguageView {
    ViewPager pager;
    Button buttonNext;
    TextView textViewLable;
    String languageId = "3";
    private LanguagePresenter presenter;

    @Override
    protected int getContentView() {
        return R.layout.activity_language;
    }

    @Override
    protected void initializeView() {
        final ImageView imageViewPre = findViewById(R.id.imageView_previous);
        final ImageView imageViewNext = findViewById(R.id.imageView_next);
        buttonNext = findViewById(R.id.button_next);
        textViewLable = findViewById(R.id.textView_label);
        buttonNext.setOnClickListener(this);
        imageViewPre.setOnClickListener(this);
        imageViewNext.setOnClickListener(this);
        imageViewPre.setVisibility(View.GONE);
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
                } else {
                    imageViewNext.setVisibility(View.VISIBLE);
                }
                if (position == 0) {
                    imageViewPre.setVisibility(View.GONE);
                } else {
                    imageViewPre.setVisibility(View.VISIBLE);
                }
                changeText(position);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        pager.setPageTransformer(true,new MyTransformer());


    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    private void changeText(int position) {
        switch (position) {
            case 0:
                languageId = ConstantLib.LANGUAGE_ENGLISH;
                textViewLable.setText("Select Language");
                buttonNext.setText("Next");
                break;
            case 1:
                languageId = ConstantLib.LANGUAGE_SIMPLIFIED;
                // simplified
                buttonNext.setText("下一步");
                textViewLable.setText("选择语言");
                break;
            case 2:
                languageId = ConstantLib.LANGUAGE_TRADITIONAL;
                buttonNext.setText("下一步");
                textViewLable.setText("選擇語言");
                break;
        }

        SuperLifeSecretPreferences.getInstance().setLanguageId(languageId);

    }

    @Override
    protected void initializePresenter() {
        presenter = new LanguagePresenter(this);
        presenter.setView(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_next:
                getConversionData();
                break;
            case R.id.imageView_next:
                pager.setCurrentItem(pager.getCurrentItem()+1, true);
                break;
            case R.id.imageView_previous:
                pager.setCurrentItem(pager.getCurrentItem()-1, true);
                break;
        }
    }

    private void getConversionData() {
        HashMap<String, String> body = new HashMap<>();
        body.put("language_id", languageId);
        presenter.getConversion(body);

    }

    @Override
    public void setConversionContent(LanguageResponseData data) {
        if (data != null) {
            SuperLifeSecretPreferences.getInstance().putBoolean(SuperLifeSecretPreferences.LANGUAGE_SETTED,true);
            SuperLifeSecretPreferences.getInstance().setConversionData(data);
            CommonUtils.startActivity(LanguageActivity2.this, DiscolsureActivity.class);
        }
    }
}
