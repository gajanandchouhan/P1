package com.superlifesecretcode.app.ui.book.third;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.superlifesecretcode.app.R;
import com.superlifesecretcode.app.ui.base.BaseActivity;
import com.superlifesecretcode.app.ui.book.first.BookAapter;
import com.superlifesecretcode.app.ui.book.first.BookBean;
import com.superlifesecretcode.app.ui.book.first.FirstBookActivity;
import com.superlifesecretcode.app.ui.book.forth.ForthBookActivity;
import com.superlifesecretcode.app.util.CommonUtils;

import java.util.ArrayList;

public class ThirsBookActivity extends BaseActivity {

    RecyclerView bookrecyclerview;
    ThirdBooksAapter thirdBooksAapter;
    ArrayList<BookBean> bookArrayList;
    TextView textview_next , textview_back , textview_total_price;
    String total_amont;
    @Override
    protected int getContentView() {
         total_amont = getIntent().getStringExtra("total_amount");
//        String total_amont = bundle.getString("total_amount");
        return R.layout.activity_book_third;
    }

    @Override
    protected void initializeView() {
        bookrecyclerview = findViewById(R.id.bookrecyclerview);
        textview_next = findViewById(R.id.textview_next);
        textview_back = findViewById(R.id.textview_back);
        textview_total_price = findViewById(R.id.textview_total_price);


        textview_total_price.setText(""+total_amont);

        bookArrayList = (ArrayList<BookBean>) getIntent().getSerializableExtra("selected_booklist");
        bookrecyclerview.setLayoutManager(new LinearLayoutManager(this));
        thirdBooksAapter = new ThirdBooksAapter(bookArrayList, this);
        bookrecyclerview.setAdapter(thirdBooksAapter);

        textview_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommonUtils.startActivity(ThirsBookActivity.this, ForthBookActivity.class);
            }
        });
        textview_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void initializePresenter() {

    }
}
