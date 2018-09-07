package com.superlifesecretcode.app.ui.book.first;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.superlifesecretcode.app.R;
import com.superlifesecretcode.app.data.model.userdetails.UserDetailResponseData;
import com.superlifesecretcode.app.data.persistance.SuperLifeSecretPreferences;
import com.superlifesecretcode.app.ui.base.BaseActivity;
import com.superlifesecretcode.app.ui.base.BaseView;
import com.superlifesecretcode.app.ui.book.five.FifthBookActivity;
import com.superlifesecretcode.app.ui.book.forth.ForthBookActivity;
import com.superlifesecretcode.app.ui.book.second.SecondBookActivity;
import com.superlifesecretcode.app.ui.book.third.ThirsBookActivity;
import com.superlifesecretcode.app.util.CommonUtils;

import java.util.ArrayList;
import java.util.Currency;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class FirstBookActivity extends BaseActivity implements FirstBookView {

    RecyclerView bookrecyclerview;
    EditText edittext_enteramount;
    TextView textview_select, textview_back, textview_next;
    FirstBookPresenter firstBookPresenter;
    private UserDetailResponseData userDetailResponseData;
    BookAapter bookAapter;
    ArrayList<BookBean> booksList;
    ArrayList<BookBean> selectedBookArrayList;
    ArrayList<String> selectedBooks;
    String book_type;
    ImageView back_image;

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected int getContentView() {
//        Bundle bundle = getIntent().getBundleExtra("bundle");
//        book_type = bundle.getString("type");
        book_type = SuperLifeSecretPreferences.getInstance().getString("book_type");
        return R.layout.activity_book_first;
    }

    @Override
    protected void initializeView() {
        Locale current = getResources().getConfiguration().locale;
        Log.i("locale", Currency.getInstance(current).getSymbol());
        SuperLifeSecretPreferences.getInstance().putString("book_currency",""+Currency.getInstance(current).getSymbol());

        bookrecyclerview = findViewById(R.id.bookrecyclerview);
        textview_select = findViewById(R.id.textview_select);
        textview_back = findViewById(R.id.textview_back);
        edittext_enteramount = findViewById(R.id.edittext_enteramount);
        textview_next = findViewById(R.id.textview_next);
        back_image = findViewById(R.id.back_image);

        back_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        booksList = new ArrayList<>();
        selectedBooks = new ArrayList<>();
//        if (SuperLifeSecretPreferences.getInstance().getSelectedBooks() == null) {
            if (SuperLifeSecretPreferences.getInstance().getSelectedBooks().size()==0){
            selectedBooks = new ArrayList<>();
        } else {
            selectedBooks = SuperLifeSecretPreferences.getInstance().getSelectedBooks();
        }
//        selectedBookArrayList = new ArrayList<>();
        if (SuperLifeSecretPreferences.getInstance().getSelectedBooksList()==null||SuperLifeSecretPreferences.getInstance().getSelectedBooksList().size() == 0) {
//        if (SuperLifeSecretPreferences.getInstance().getSelectedBooksList() == null) {
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
                if (selectedBooks.size() != 0) {
                    SuperLifeSecretPreferences.getInstance().putString("book_stake_page_no","1");
                    Intent intent = new Intent(FirstBookActivity.this, SecondBookActivity.class);
                    SuperLifeSecretPreferences.getInstance().setSelectedBooksList(selectedBookArrayList);
                    startActivity(intent);
                } else {
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

        String stack = SuperLifeSecretPreferences.getInstance().getString("book_stake_page_no");
        if (stack!=null && !stack.equals("")){
            if (Integer.parseInt(stack)>=1){
                CommonUtils.startActivity(FirstBookActivity.this,SecondBookActivity.class);
            }
        }
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
                if (!selectedBooks.contains(booksList.get(i).getId())) {
                    Log.e("selectedBokArayListBEF", "" + selectedBookArrayList.size());
                    Log.e("selectedBooks_BEF", "" + selectedBookArrayList.size());
                    selectedBookArrayList.add(booksList.get(i));
                    selectedBooks.add("" + booksList.get(i).getId());
                    Log.e("selectedBokArayListAAF", "" + selectedBookArrayList.size());
                    Log.e("selectedBooks_AAF", "" + selectedBookArrayList.size());

                    SuperLifeSecretPreferences.getInstance().setSelectedBooksList(selectedBookArrayList);
                    SuperLifeSecretPreferences.getInstance().setSelectedBooks(selectedBooks);
                    booksList.get(i).setSelected(status);
                    bookAapter.notifyItemChanged(i);
                }
            } else {
                Log.e("selectedBokArayListBEF", "" + selectedBookArrayList.size());
                Log.e("selectedBooks_BEF", "" + selectedBooks.size());
                selectedBookArrayList.clear();
                booksList.get(i).setSelected(status);
                for (int p = 0; p < booksList.size(); p++) {
                    if (booksList.get(p).isSelected()) {
                        selectedBookArrayList.add(booksList.get(p));
                    }
                }
                //selectedBookArrayList.remove(i);
                selectedBooks.remove(booksList.get(i).getId());
                SuperLifeSecretPreferences.getInstance().setSelectedBooksList(selectedBookArrayList);
                SuperLifeSecretPreferences.getInstance().setSelectedBooks(selectedBooks);
                bookAapter.notifyItemChanged(i);
                Log.e("selectedBokArayListAAF", "" + selectedBookArrayList.size());
                Log.e("selectedBooks_AAF", "" + selectedBooks.size());
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
