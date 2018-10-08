package com.superlifesecretcode.app.ui.book.first;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.superlifesecretcode.app.R;
import com.superlifesecretcode.app.SuperLifeSecretCodeApp;
import com.superlifesecretcode.app.data.model.language.LanguageResponseData;
import com.superlifesecretcode.app.data.model.userdetails.UserDetailResponseData;
import com.superlifesecretcode.app.data.persistance.SuperLifeSecretPreferences;
import com.superlifesecretcode.app.ui.base.BaseActivity;
import com.superlifesecretcode.app.ui.book.second.SecondBookActivity;
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
    private LanguageResponseData conversionData;
    String select_book;
    TextView textView_title;

    @Override
    protected void onResume() {
        super.onResume();
        if (CommonUtils.book_stake == true) {
            finish();
        }
    }

    @Override
    protected int getContentView() {
        book_type = SuperLifeSecretPreferences.getInstance().getString("book_type");
        return R.layout.activity_book_first;
    }

    @Override
    protected void initializeView() {

        bookrecyclerview = findViewById(R.id.bookrecyclerview);
        textview_select = findViewById(R.id.textview_select);
        textview_back = findViewById(R.id.textview_back);
        edittext_enteramount = findViewById(R.id.edittext_enteramount);
        textview_next = findViewById(R.id.textview_next);
        back_image = findViewById(R.id.back_image);
        textView_title = findViewById(R.id.textView_title);
        textView_title.setText(SuperLifeSecretPreferences.getInstance().getString("book_title"));
        setUpConversion();
        back_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        booksList = new ArrayList<>();
        selectedBooks = new ArrayList<>();

        if (SuperLifeSecretPreferences.getInstance().getSelectedBooks().size() == 0) {
            selectedBooks = new ArrayList<>();
        } else {
            selectedBooks = SuperLifeSecretPreferences.getInstance().getSelectedBooks();
        }
        if (SuperLifeSecretPreferences.getInstance().getSelectedBooksList() == null || SuperLifeSecretPreferences.getInstance().getSelectedBooksList().size() == 0) {
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
                Log.e("selectedBookArrayList", "" + selectedBookArrayList);
                if (selectedBooks.size() != 0) {
                    SuperLifeSecretPreferences.getInstance().putString("book_stake_page_no", "1");
                    Intent intent = new Intent(FirstBookActivity.this, SecondBookActivity.class);
                    SuperLifeSecretPreferences.getInstance().setSelectedBooksList(selectedBookArrayList);
                    startActivity(intent);
                } else {
                    CommonUtils.showSnakeBar(FirstBookActivity.this, select_book);
                }
            }
        });
        textview_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SuperLifeSecretPreferences.getInstance().putString("book_stake_page_no", "0");
                SuperLifeSecretPreferences.getInstance().putString("book_type", "");
                SuperLifeSecretPreferences.getInstance().setSelectedBooks(new ArrayList<String>());
                SuperLifeSecretPreferences.getInstance().setSelectedBooksList(new ArrayList<BookBean>());
                SuperLifeSecretPreferences.getInstance().putString("total_amount", "");
                SuperLifeSecretPreferences.getInstance().putString("book_address", "");
                SuperLifeSecretPreferences.getInstance().putString("book_full_name", "");
                SuperLifeSecretPreferences.getInstance().putString("book_mobile", "");
                SuperLifeSecretPreferences.getInstance().putString("book_email", "");
                SuperLifeSecretPreferences.getInstance().putString("book_state", "");
                SuperLifeSecretPreferences.getInstance().putString("book_city", "");
                finish();
            }
        });

        String stack = SuperLifeSecretPreferences.getInstance().getString("book_stake_page_no");
        if (stack != null && !stack.equals("")) {
            if (Integer.parseInt(stack) >= 1) {
                CommonUtils.startActivity(FirstBookActivity.this, SecondBookActivity.class);
            }
        }
    }

    private void setUpConversion() {
        conversionData = SuperLifeSecretCodeApp.getInstance().getConversionData();
        if (conversionData != null) {
            textview_back.setText(conversionData.getBack());
            textview_next.setText(conversionData.getNext());
            select_book = conversionData.getSelect_book();
            textview_select.setText(conversionData.getSelect_book_on_sales());
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
        try {
            if (bookData.getCurrencyUnit().getCurrency_symbol() != null || !bookData.getCurrencyUnit().getCurrency_symbol().equals("")) {
                SuperLifeSecretPreferences.getInstance().putString("book_currency", "" + bookData.getCurrencyUnit().getCurrency_symbol());
            } else {
                Locale current = getResources().getConfiguration().locale;
                Log.i("locale", Currency.getInstance(current).getSymbol());
                SuperLifeSecretPreferences.getInstance().putString("book_currency", "" + Currency.getInstance(current).getSymbol());
            }
        } catch (Exception e) {
            Log.e("E", e.toString());
        }

        ArrayList<BookBean> finalBooklist = bookData.getData();
        booksList.clear();
        if (selectedBooks != null) {
            for (int i = 0; i < finalBooklist.size(); i++) {
                for (int j = 0; j < selectedBooks.size(); j++) {
                    if (selectedBooks.contains(finalBooklist.get(i).getId())) {
                        finalBooklist.get(i).setSelected(true);
                    }
                }
            }
        }
        booksList.addAll(finalBooklist);
        for (int p = 0; p < booksList.size(); p++) {
            if (booksList.get(p).getDiscount().size() != 0) {
                for (int d = 0; d < booksList.get(p).getDiscount().size(); d++) {
                    double book_price = booksList.get(p).getPrice();
                    int min_quantity = Integer.parseInt(booksList.get(p).getDiscount().get(d).getMin_qty());
                    int max_quantity = Integer.parseInt(booksList.get(p).getDiscount().get(d).getMax_qty());
                    int book_quantity = booksList.get(p).getQuantity();
                    booksList.get(p).setPrice_after_discount(book_price);
                    if (book_quantity >= min_quantity && book_quantity <= max_quantity) {
                        if (booksList.get(p).getDiscount().get(d).getDiscount_type().equals("1")) {
                            double no = booksList.get(p).getDiscount().get(d).getDiscount_amount();
                            double discount = ((book_price * no) / 100);
                            booksList.get(p).setDiscount_applied(discount);
                            booksList.get(p).setIs_discounted(true);
                            booksList.get(p).setPrice_after_discount(book_price - discount);
                            break;
                        } else {
                            double discount = booksList.get(p).getDiscount().get(d).getDiscount_amount();
                            booksList.get(p).setDiscount_applied(discount);
                            booksList.get(p).setPrice_after_discount(book_price - discount);
                            booksList.get(p).setIs_discounted(true);
                            break;
                        }
                    } else {
                        Discount maxQty = getMaxQty(booksList.get(p).getDiscount());
                        if (maxQty != null && book_quantity > Integer.parseInt(maxQty.getMax_qty())) {
                            if (maxQty.getDiscount_type().equals("1")) {
                                double no = maxQty.getDiscount_amount();
                                double discount = ((book_price * no) / 100);
                                booksList.get(p).setDiscount_applied(discount);
                                booksList.get(p).setIs_discounted(true);
                                booksList.get(p).setPrice_after_discount(book_price - discount);
                                break;
                            } else {
                                double discount = maxQty.getDiscount_amount();
                                booksList.get(p).setDiscount_applied(discount);
                                booksList.get(p).setPrice_after_discount(book_price - discount);
                                booksList.get(p).setIs_discounted(true);
                                break;
                            }
                        } else {
                            booksList.get(p).setDiscount_applied(0);
                            booksList.get(p).setIs_discounted(false);
                            booksList.get(p).setPrice_after_discount(book_price);
                        }
                    }
                }
            } else {
                booksList.get(p).setPrice_after_discount(booksList.get(p).getPrice());
            }
        }
        bookAapter.notifyDataSetChanged();
    }

    public class BookSelectedListener {

        public void onExpandCollapse(int post, boolean expand_collapse_status) {

            for (int i = 0; i < booksList.size(); i++) {
                if (i == post) {
                    booksList.get(i).setExpand_collapase(expand_collapse_status);
                } else {
                    booksList.get(i).setExpand_collapase(false);
                }
            }
            bookAapter.notifyDataSetChanged();
        }


        public void onSelected(int i, boolean status) {
            if (status == true) {
                if (!selectedBooks.contains(booksList.get(i).getId())) {
                    Log.e("selectedBokArayListBEF", "" + selectedBookArrayList.size());
                    Log.e("selectedBooks_BEF", "" + selectedBookArrayList.size());
                    selectedBookArrayList.add(booksList.get(i));
                    selectedBooks.add("" + booksList.get(i).getId());
                    booksList.get(i).setQuantity(1);
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
                booksList.get(i).setQuantity(0);
                selectedBooks.remove(booksList.get(i).getId());
                SuperLifeSecretPreferences.getInstance().setSelectedBooksList(selectedBookArrayList);
                SuperLifeSecretPreferences.getInstance().setSelectedBooks(selectedBooks);
                bookAapter.notifyItemChanged(i);
                Log.e("selectedBokArayListAAF", "" + selectedBookArrayList.size());
                Log.e("selectedBooks_AAF", "" + selectedBooks.size());
            }
            for (int p = 0; p < booksList.size(); p++) {
                if (booksList.get(p).getDiscount().size() != 0) {
                    for (int d = 0; d < booksList.get(p).getDiscount().size(); d++) {
                        double book_price = booksList.get(p).getPrice();
                        int min_quantity = Integer.parseInt(booksList.get(p).getDiscount().get(d).getMin_qty());
                        int max_quantity = Integer.parseInt(booksList.get(p).getDiscount().get(d).getMax_qty());
                        int book_quantity = booksList.get(p).getQuantity();
                        booksList.get(p).setPrice_after_discount(book_price);
                        if (book_quantity >= min_quantity && book_quantity <= max_quantity) {
                            if (booksList.get(p).getDiscount().get(d).getDiscount_type().equals("1")) {
                                double no = booksList.get(p).getDiscount().get(d).getDiscount_amount();
                                double discount = ((book_price * no) / 100);
                                booksList.get(p).setDiscount_applied(discount);
                                booksList.get(p).setIs_discounted(true);
                                booksList.get(p).setPrice_after_discount(book_price - discount);
                                break;
                            } else {
                                double discount = booksList.get(p).getDiscount().get(d).getDiscount_amount();
                                booksList.get(p).setDiscount_applied(discount);
                                booksList.get(p).setPrice_after_discount(book_price - discount);
                                booksList.get(p).setIs_discounted(true);
                                break;
                            }
                        } else {
                            Discount maxQty = getMaxQty(booksList.get(p).getDiscount());
                            if (maxQty != null && book_quantity > Integer.parseInt(maxQty.getMax_qty())) {
                                if (maxQty.getDiscount_type().equals("1")) {
                                    double no = maxQty.getDiscount_amount();
                                    double discount = ((book_price * no) / 100);
                                    booksList.get(p).setDiscount_applied(discount);
                                    booksList.get(p).setIs_discounted(true);
                                    booksList.get(p).setPrice_after_discount(book_price - discount);
                                    break;
                                } else {
                                    double discount = maxQty.getDiscount_amount();
                                    booksList.get(p).setDiscount_applied(discount);
                                    booksList.get(p).setPrice_after_discount(book_price - discount);
                                    booksList.get(p).setIs_discounted(true);
                                    break;
                                }
                            } else {
                                booksList.get(p).setDiscount_applied(0);
                                booksList.get(p).setIs_discounted(false);
                                booksList.get(p).setPrice_after_discount(book_price);
                            }
                        }
                    }
                } else {
                    booksList.get(p).setPrice_after_discount(booksList.get(p).getPrice());
                }
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        SuperLifeSecretPreferences.getInstance().putString("book_stake_page_no", "0");
        SuperLifeSecretPreferences.getInstance().putString("book_type", "");
        SuperLifeSecretPreferences.getInstance().setSelectedBooks(new ArrayList<String>());
        SuperLifeSecretPreferences.getInstance().setSelectedBooksList(new ArrayList<BookBean>());
        SuperLifeSecretPreferences.getInstance().putString("total_amount", "");
        SuperLifeSecretPreferences.getInstance().putString("book_address", "");
        SuperLifeSecretPreferences.getInstance().putString("book_full_name", "");
        SuperLifeSecretPreferences.getInstance().putString("book_mobile", "");
        SuperLifeSecretPreferences.getInstance().putString("book_email", "");
        SuperLifeSecretPreferences.getInstance().putString("book_state", "");
        SuperLifeSecretPreferences.getInstance().putString("book_city", "");
    }


    private Discount getMaxQty(ArrayList<Discount> discountArrayList) {
        Discount discountMax = null;
        if (discountArrayList == null || discountArrayList.isEmpty()) {
            return null;
        }
        int maxQty = Integer.parseInt(discountArrayList.get(0).getMax_qty());
        for (Discount discount : discountArrayList) {
            if (Integer.parseInt(discount.getMax_qty()) >= maxQty) {
                maxQty = Integer.parseInt(discount.getMax_qty());
                discountMax = discount;
            }
        }
        return discountMax;

    }
}
