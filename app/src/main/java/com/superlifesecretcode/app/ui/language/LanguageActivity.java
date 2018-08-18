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

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LanguageActivity extends BaseActivity implements View.OnClickListener, LanguageView {
    ViewPager pager;
    Button buttonNext;
    TextView textViewLable, textViewLangenglish, textViewLangSimplified, textViewLangTraditional;
    ImageView imageViewEnglish , imageViewTraditional , imageViewSimplified;
    String languageId = "3";
    private LanguagePresenter presenter;
    String[] strings = new String[]{"簡化中文", "繁體中文", "English"};


    @Override
    protected int getContentView() {
        return R.layout.activity_language;
    }

    @Override
    protected void initializeView() {
        buttonNext = findViewById(R.id.button_next);
        textViewLable = findViewById(R.id.textView_label);
        textViewLangenglish = findViewById(R.id.tv_lang_english);
        textViewLangSimplified = findViewById(R.id.tv_lang_simplified);
        textViewLangTraditional = findViewById(R.id.tv_lang_traditional);
        imageViewSimplified = findViewById(R.id.imageViewSimplified);
        imageViewEnglish = findViewById(R.id.imageViewEnglish);
        imageViewTraditional  = findViewById(R.id.imageViewTraditional);
        buttonNext.setOnClickListener(this);
        textViewLangenglish.setOnClickListener(this);
        textViewLangSimplified.setOnClickListener(this);
        textViewLangTraditional.setOnClickListener(this);
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

            case R.id.tv_lang_english:
                languageId = ConstantLib.LANGUAGE_ENGLISH;
                textViewLable.setText("Select Language");
                buttonNext.setText("Next");
                SuperLifeSecretPreferences.getInstance().setLanguageId(languageId);
                imageViewEnglish.setVisibility(View.VISIBLE);
                imageViewSimplified.setVisibility(View.GONE);
                imageViewTraditional.setVisibility(View.GONE);

                break;

            case R.id.tv_lang_simplified:
                languageId = ConstantLib.LANGUAGE_SIMPLIFIED;
                // simplified
                buttonNext.setText("下一步");
                textViewLable.setText("选择语言");
                SuperLifeSecretPreferences.getInstance().setLanguageId(languageId);
                imageViewEnglish.setVisibility(View.GONE);
                imageViewSimplified.setVisibility(View.VISIBLE);
                imageViewTraditional.setVisibility(View.GONE);
                break;

            case R.id.tv_lang_traditional:
                languageId = ConstantLib.LANGUAGE_TRADITIONAL;
                buttonNext.setText("下一步");
                textViewLable.setText("選擇語言");
                SuperLifeSecretPreferences.getInstance().setLanguageId(languageId);
                imageViewEnglish.setVisibility(View.GONE);
                imageViewSimplified.setVisibility(View.GONE);
                imageViewTraditional.setVisibility(View.VISIBLE);
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
            SuperLifeSecretPreferences.getInstance().putBoolean(SuperLifeSecretPreferences.LANGUAGE_SETTED, true);
            SuperLifeSecretPreferences.getInstance().setConversionData(data);
            CommonUtils.startActivity(LanguageActivity.this, DiscolsureActivity.class);
        }
    }
}
