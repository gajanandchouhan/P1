package com.superlifesecretcode.app.ui.book.first;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import com.superlifesecretcode.app.R;
import com.superlifesecretcode.app.data.model.userdetails.UserDetailResponseData;
import com.superlifesecretcode.app.data.persistance.SuperLifeSecretPreferences;
import com.superlifesecretcode.app.ui.base.BaseActivity;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FirstBookActivity extends BaseActivity implements FirstBookView {
    RecyclerView bookrecyclerview;
    TextView textview_select;
    FirstBookPresenter firstBookPresenter;
    private UserDetailResponseData userDetailResponseData;
    BookAapter bookAapter;
    List<BookBean> booksList;

    @Override
    protected int getContentView() {
        return R.layout.activity_book_first;
    }

    @Override
    protected void initializeView() {
        bookrecyclerview = findViewById(R.id.bookrecyclerview);
        textview_select = findViewById(R.id.textview_select);
        booksList = new ArrayList<>();
        userDetailResponseData = SuperLifeSecretPreferences.getInstance().getUserData();
        bookrecyclerview.setLayoutManager(new LinearLayoutManager(this));
        bookAapter = new BookAapter(booksList,this);
        bookrecyclerview.setAdapter(bookAapter);
        getBookList();
    }

    private void getBookList() {
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + userDetailResponseData.getApi_token());
        HashMap<String, String> params = new HashMap<>();
        params.put("book_type", "2");
        firstBookPresenter.getBookList(params, headers);
    }

    @Override
    protected void initializePresenter() {
        firstBookPresenter = new FirstBookPresenter(this);
        firstBookPresenter.setView(this);
    }

    @Override
    public void getBookList(BookList bookList) {
        booksList.clear();
        booksList.addAll(bookList.getData());
        bookAapter.notifyDataSetChanged();
    }
}
