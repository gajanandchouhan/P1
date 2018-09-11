package com.superlifesecretcode.app.ui.book.six;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.superlifesecretcode.app.R;
import com.superlifesecretcode.app.SuperLifeSecretCodeApp;
import com.superlifesecretcode.app.data.model.language.LanguageResponseData;
import com.superlifesecretcode.app.data.persistance.SuperLifeSecretPreferences;
import com.superlifesecretcode.app.ui.base.BaseActivity;
import com.superlifesecretcode.app.ui.book.first.BookBean;
import com.superlifesecretcode.app.ui.main.MainActivity;
import com.superlifesecretcode.app.util.CommonUtils;

import java.util.ArrayList;

public class SixthBookActivity extends BaseActivity {

    ImageView back_image;
    TextView textview_continue, textview_thankyou, textview_ty_line;
    private LanguageResponseData conversionData;
    TextView  textView_title;

    @Override
    protected int getContentView() {
        return R.layout.activity_book_sixth;
    }

    @Override
    protected void initializeView() {

        //first page
        SuperLifeSecretPreferences.getInstance().putString("book_print_status", "");
        SuperLifeSecretPreferences.getInstance().setSelectedBooks(new ArrayList<String>());
        SuperLifeSecretPreferences.getInstance().putString("book_stake_page_no", "");
        SuperLifeSecretPreferences.getInstance().setSelectedBooksList(new ArrayList<BookBean>());

        // second page
        SuperLifeSecretPreferences.getInstance().putString("total_amount", "");

        // forth page

        SuperLifeSecretPreferences.getInstance().putString("book_address", "");
        SuperLifeSecretPreferences.getInstance().putString("book_full_name", "");
        SuperLifeSecretPreferences.getInstance().putString("book_mobile", "");
        SuperLifeSecretPreferences.getInstance().putString("book_email", "");
        SuperLifeSecretPreferences.getInstance().putString("book_state", "");
        SuperLifeSecretPreferences.getInstance().putString("book_city", "");

        back_image = findViewById(R.id.back_image);
        //textview_back = findViewById(R.id.textview_back);
        textview_continue = findViewById(R.id.textview_continue);
        textview_thankyou = findViewById(R.id.textview_thankyou);
        textview_ty_line = findViewById(R.id.textview_ty_line);
        textView_title = findViewById(R.id.textView_title);
        textView_title.setText(SuperLifeSecretPreferences.getInstance().getString("book_title"));
        back_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommonUtils.startActivity(SixthBookActivity.this, MainActivity.class);
            }
        });
        textview_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommonUtils.startActivity(SixthBookActivity.this, MainActivity.class);
            }
        });
        setUpConversion();
    }

    @Override
    protected void initializePresenter() {
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        CommonUtils.startActivity(SixthBookActivity.this, MainActivity.class);
    }

    private void setUpConversion() {
        conversionData = SuperLifeSecretCodeApp.getInstance().getConversionData();
        if (conversionData != null) {
            //textview_back.setText(conversionData.getBack());
            textview_continue.setText(conversionData.getContinuee());
            textview_thankyou.setText(conversionData.getThank_you());
            textview_ty_line.setText(conversionData.getThank_you_message());
        }
    }
}
