package com.superlifesecretcode.app.ui.book.third;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.superlifesecretcode.app.R;
import com.superlifesecretcode.app.ui.base.BaseActivity;
import com.superlifesecretcode.app.ui.book.first.BookAapter;
import com.superlifesecretcode.app.ui.book.first.BookBean;
import com.superlifesecretcode.app.ui.book.first.FirstBookActivity;

import java.util.ArrayList;

public class ThirsBookActivity extends BaseActivity {

    RecyclerView bookrecyclerview;
    ThirdBooksAapter thirdBooksAapter;
    ArrayList<BookBean> bookArrayList;

    @Override
    protected int getContentView() {
        return R.layout.activity_book_third;
    }

    @Override
    protected void initializeView() {
        bookrecyclerview = findViewById(R.id.bookrecyclerview);
        bookArrayList = (ArrayList<BookBean>) getIntent().getSerializableExtra("selected_booklist");
        bookrecyclerview.setLayoutManager(new LinearLayoutManager(this));
        thirdBooksAapter = new ThirdBooksAapter(bookArrayList, this);
        bookrecyclerview.setAdapter(thirdBooksAapter);
    }

    @Override
    protected void initializePresenter() {

    }
}
