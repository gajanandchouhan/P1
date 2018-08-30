package com.superlifesecretcode.app.ui.book.first;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.superlifesecretcode.app.R;
import com.superlifesecretcode.app.data.model.userdetails.UserDetailResponseData;
import com.superlifesecretcode.app.data.persistance.SuperLifeSecretPreferences;
import com.superlifesecretcode.app.ui.base.BaseActivity;
import com.superlifesecretcode.app.ui.base.BaseView;
import com.superlifesecretcode.app.ui.book.second.SecondBookActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FirstBookActivity extends BaseActivity implements FirstBookView {

    RecyclerView bookrecyclerview;
    EditText edittext_enteramount;
    TextView textview_select, textview_back, textview_next;
    FirstBookPresenter firstBookPresenter;
    private UserDetailResponseData userDetailResponseData;
    BookAapter bookAapter;
    public ArrayList<BookBean> booksList;
    public ArrayList<BookBean> selectedBookArrayList;
    ArrayList<String> selectedBooks;
    public String book_type;

    @Override
    protected int getContentView() {
        Bundle bundle = getIntent().getBundleExtra("bundle");
        book_type = bundle.getString("type");
        return R.layout.activity_book_first;
    }

    @Override
    protected void initializeView() {
        bookrecyclerview = findViewById(R.id.bookrecyclerview);
        textview_select = findViewById(R.id.textview_select);
        textview_back = findViewById(R.id.textview_back);
        edittext_enteramount = findViewById(R.id.edittext_enteramount);
        textview_next = findViewById(R.id.textview_next);

        booksList = new ArrayList<>();
        if (SuperLifeSecretPreferences.getInstance().getSelectedBooks() == null) {
            selectedBooks = new ArrayList<>();
        } else {
            selectedBooks = SuperLifeSecretPreferences.getInstance().getSelectedBooks();
        }

        if (SuperLifeSecretPreferences.getInstance().getSelectedBooksList() == null) {
            selectedBookArrayList = new ArrayList<>();
        } else {
            selectedBookArrayList = SuperLifeSecretPreferences.getInstance().getSelectedBooksList();
        }

        userDetailResponseData = SuperLifeSecretPreferences.getInstance().getUserData();
        getBookList();
        bookrecyclerview.setLayoutManager(new LinearLayoutManager(this));
        bookAapter = new BookAapter(booksList, this, new BookSelectedListener());
        bookrecyclerview.setAdapter(bookAapter);

        textview_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SuperLifeSecretPreferences.getInstance().setSelectedBooks(selectedBooks);

                if(selectedBooks.size()!=0){
                    Intent intent = new Intent(FirstBookActivity.this, SecondBookActivity.class);
                    intent.putExtra("selected_booklist", selectedBookArrayList);
                    startActivity(intent);
                }else {
                    Toast.makeText(FirstBookActivity.this, "Please selct atleast one item.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        textview_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void getBookList() {
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + userDetailResponseData.getApi_token());
        HashMap<String, String> params = new HashMap<>();
        params.put("book_type", book_type);
        firstBookPresenter.getBookList(params, headers);
    }

    @Override
    protected void initializePresenter() {
        firstBookPresenter = new FirstBookPresenter(this);
        firstBookPresenter.setView(this);
    }

    @Override
    public void getBookList(BookList bookData) {

        ArrayList<BookBean> finalBooklist = bookData.getData();
        booksList.clear();
        if (selectedBooks != null) {
            for (int i = 0; i < finalBooklist.size(); i++) {
                for (int j = 0; j < selectedBooks.size(); j++) {
                    //Log.e("seselectedBooks", "" + selectedBooks.get(j));
                    //Log.e("finalbookId", "" + finalBooklist.get(i).getId());
                    if (selectedBooks.contains(finalBooklist.get(i).getId())) {
                        finalBooklist.get(i).setSelected(true);
                    }
                }
            }
        }
        booksList.addAll(finalBooklist);
        bookAapter.notifyDataSetChanged();
    }

    public class BookSelectedListener {
        public void onSelected(int i, boolean status) {
            if (status == true) {
                selectedBookArrayList.add(booksList.get(i));
                selectedBooks.add("" + booksList.get(i).getId());
            } else {
                selectedBookArrayList.remove(booksList.get(i));
                selectedBooks.remove(booksList.get(i).getId());
            }
            SuperLifeSecretPreferences.getInstance().setSelectedBooksList(selectedBookArrayList);
            booksList.get(i).setSelected(status);
            bookAapter.notifyItemChanged(i);
        }
    }
}
