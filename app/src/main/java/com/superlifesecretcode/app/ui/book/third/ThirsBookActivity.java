package com.superlifesecretcode.app.ui.book.third;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.superlifesecretcode.app.R;
import com.superlifesecretcode.app.SuperLifeSecretCodeApp;
import com.superlifesecretcode.app.data.model.language.LanguageResponseData;
import com.superlifesecretcode.app.data.persistance.SuperLifeSecretPreferences;
import com.superlifesecretcode.app.ui.base.BaseActivity;
import com.superlifesecretcode.app.ui.book.first.BookBean;
import com.superlifesecretcode.app.ui.book.first.Discount;
import com.superlifesecretcode.app.ui.book.forth.ForthBookActivity;
import com.superlifesecretcode.app.ui.book.second.DeliveryData;
import com.superlifesecretcode.app.util.CommonUtils;

import java.util.ArrayList;
import java.util.Locale;
import java.util.logging.Handler;

public class ThirsBookActivity extends BaseActivity {

    RecyclerView bookrecyclerview;
    ThirdBooksAapter thirdBooksAapter;
    ArrayList<BookBean> bookArrayList;
    TextView textview_next, textview_back, textview_total_price, textview_select, textview_total;
    String total_amont;
    ImageView back_image;
    private LanguageResponseData conversionData;
    String insufficient_fund;
    TextView textView_title;
    double total = 0.0;

    @Override
    protected int getContentView() {
        return R.layout.activity_book_third;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (CommonUtils.book_stake == true) {
            finish();
        }
    }

    @Override
    protected void initializeView() {
        bookrecyclerview = findViewById(R.id.bookrecyclerview);
        textview_next = findViewById(R.id.textview_next);
        textview_back = findViewById(R.id.textview_back);
        textview_total_price = findViewById(R.id.textview_total_price);
        back_image = findViewById(R.id.back_image);
        textview_total = findViewById(R.id.textview_total);
        textview_select = findViewById(R.id.textview_select);
        setUpConversion();
        back_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        bookArrayList = SuperLifeSecretPreferences.getInstance().getSelectedBooksList();
        total_amont = SuperLifeSecretPreferences.getInstance().getString("total_amount");
        total = Double.parseDouble(SuperLifeSecretPreferences.getInstance().getString("total_amount"));
        try {
            textview_total_price.setText("" + SuperLifeSecretPreferences.getInstance().getString("book_currency") + " " + String.format("%,.2f", total));
        } catch (Exception e) {
            textview_total_price.setText("" + SuperLifeSecretPreferences.getInstance().getString("book_currency") + " " + total);
        }
        textView_title = findViewById(R.id.textView_title);
        textView_title.setText(SuperLifeSecretPreferences.getInstance().getString("book_title"));
        bookrecyclerview.setLayoutManager(new LinearLayoutManager(this));
        thirdBooksAapter = new ThirdBooksAapter(bookArrayList, this, new onDeleteListener());
        bookrecyclerview.setAdapter(thirdBooksAapter);

        textview_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < bookArrayList.size(); i++) {
                    if (bookArrayList.get(i).getQuantity() == 0) {
                        CommonUtils.showSnakeBar(ThirsBookActivity.this, conversionData.getAdd_quantity_for() + " " + bookArrayList.get(i).getName());
                        return;
                    }
                }
                for (int p = 0; p < bookArrayList.size(); p++) {
                    if (bookArrayList.get(p).getDiscount().size() != 0) {
                        for (int d = 0; d < bookArrayList.get(p).getDiscount().size(); d++) {
                            double book_price = bookArrayList.get(p).getPrice();
                            int min_quantity = Integer.parseInt(bookArrayList.get(p).getDiscount().get(d).getMin_qty());
                            int max_quantity = Integer.parseInt(bookArrayList.get(p).getDiscount().get(d).getMax_qty());
                            int book_quantity = bookArrayList.get(p).getQuantity();
                            bookArrayList.get(p).setPrice_after_discount(book_price);
                            if (book_quantity >= min_quantity && book_quantity <= max_quantity) {
                                if (bookArrayList.get(p).getDiscount().get(d).getDiscount_type().equals("1")) {
                                    double no = bookArrayList.get(p).getDiscount().get(d).getDiscount_amount();
                                    double discount = ((book_price * no) / 100);
                                    bookArrayList.get(p).setDiscount_applied(discount);
                                    bookArrayList.get(p).setIs_discounted(true);
                                    bookArrayList.get(p).setPrice_after_discount(book_price - discount);
                                    break;
                                } else {
                                    double discount = bookArrayList.get(p).getDiscount().get(d).getDiscount_amount();
                                    bookArrayList.get(p).setDiscount_applied(discount);
                                    bookArrayList.get(p).setPrice_after_discount(book_price - discount);
                                    bookArrayList.get(p).setIs_discounted(true);
                                    break;
                                }
                            }
                           /* else if (d == (bookArrayList.get(p).getDiscount().size() - 1) && book_quantity > max_quantity) {
                                if (bookArrayList.get(p).getDiscount().get(d).getDiscount_type().equals("1")) {
                                    double no = bookArrayList.get(p).getDiscount().get(d).getDiscount_amount();
                                    double discount = ((book_price * no) / 100);
                                    bookArrayList.get(p).setDiscount_applied(discount);
                                    bookArrayList.get(p).setIs_discounted(true);
                                    bookArrayList.get(p).setPrice_after_discount(book_price - discount);
                                    break;
                                } else {
                                    double discount = bookArrayList.get(p).getDiscount().get(d).getDiscount_amount();
                                    bookArrayList.get(p).setDiscount_applied(discount);
                                    bookArrayList.get(p).setPrice_after_discount(book_price - discount);
                                    bookArrayList.get(p).setIs_discounted(true);
                                    break;
                                }
                            }*/
                            else {
                                Discount maxQty = getMaxQty(bookArrayList.get(p).getDiscount());
                                if (maxQty != null && book_quantity > Integer.parseInt(maxQty.getMax_qty())) {
                                    if (maxQty.getDiscount_type().equals("1")) {
                                        double no = maxQty.getDiscount_amount();
                                        double discount = ((book_price * no) / 100);
                                        bookArrayList.get(p).setDiscount_applied(discount);
                                        bookArrayList.get(p).setIs_discounted(true);
                                        bookArrayList.get(p).setPrice_after_discount(book_price - discount);
                                        break;
                                    } else {
                                        double discount = maxQty.getDiscount_amount();
                                        bookArrayList.get(p).setDiscount_applied(discount);
                                        bookArrayList.get(p).setPrice_after_discount(book_price - discount);
                                        bookArrayList.get(p).setIs_discounted(true);
                                        break;
                                    }
                                } else {
                                    bookArrayList.get(p).setDiscount_applied(0);
                                    bookArrayList.get(p).setIs_discounted(false);
                                    bookArrayList.get(p).setPrice_after_discount(book_price);
                                }
                            }
                        }
                    } else {
                        bookArrayList.get(p).setPrice_after_discount(bookArrayList.get(p).getPrice());
                    }
                }
                int qu = 0;
                for (int i = 0; i < bookArrayList.size(); i++) {
                    qu = (bookArrayList.get(i).getQuantity() + qu);
                }
                double delivery_charges = 0.;
                ArrayList<DeliveryData> deliveryDataArrayList = SuperLifeSecretPreferences.getInstance().getDeliveryChargesList();
                for (int i = 0; i < deliveryDataArrayList.size(); i++) {
                    double from = Double.parseDouble(deliveryDataArrayList.get(i).getRange_from());
                    double to = Double.parseDouble(deliveryDataArrayList.get(i).getRange_to());
                    if (qu >= from && qu <= to) {
                        delivery_charges = Double.parseDouble(deliveryDataArrayList.get(i).getDelivery_charge());
                    }
                    if (i == (deliveryDataArrayList.size() - 1) && qu > to) {
                        delivery_charges = 0;
                    }
                }
                SuperLifeSecretPreferences.getInstance().putString("delivery_charges", "" + delivery_charges);
                if (total == 0.0) {
                    CommonUtils.showSnakeBar(ThirsBookActivity.this, insufficient_fund);
                    return;
                }

//                double AMOUNT_TOTAL = 0.0;
//                for (int i = 0; i < bookArrayList.size(); i++) {
//                    AMOUNT_TOTAL = ((bookArrayList.get(i).getPrice_after_discount() * bookArrayList.get(i).getQuantity()) + AMOUNT_TOTAL);
//                }
//                total_amont = "" + AMOUNT_TOTAL;
                SuperLifeSecretPreferences.getInstance().setSelectedBooksList(bookArrayList);
                SuperLifeSecretPreferences.getInstance().putString("book_stake_page_no", "3");
                SuperLifeSecretPreferences.getInstance().putString("total_amount", total_amont);
                Intent intent = new Intent(ThirsBookActivity.this, ForthBookActivity.class);
                startActivity(intent);
            }
        });
        textview_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SuperLifeSecretPreferences.getInstance().putString("book_stake_page_no", "2");
                finish();
            }
        });
        String stack = SuperLifeSecretPreferences.getInstance().getString("book_stake_page_no");
        if (stack != null) {
            if (Integer.parseInt(stack) > 3) {
                CommonUtils.startActivity(ThirsBookActivity.this, ForthBookActivity.class);
            }
        }
    }

    public class onDeleteListener {

        public void onItemDelete(int position) {
            String id = bookArrayList.remove(position).getId();
            Log.e("Bef_getSelelisBoks_size", "" + SuperLifeSecretPreferences.getInstance().getSelectedBooksList().size());
            SuperLifeSecretPreferences.getInstance().setSelectedBooksList(bookArrayList);
            Log.e("Aaf_getSelelisBoks_size", "" + SuperLifeSecretPreferences.getInstance().getSelectedBooksList().size());

            Log.e("Before_getSeleBoks_size", "" + SuperLifeSecretPreferences.getInstance().getSelectedBooks().size());
            ArrayList<String> strings = SuperLifeSecretPreferences.getInstance().getSelectedBooks();
            strings.remove(id);
            SuperLifeSecretPreferences.getInstance().setSelectedBooks(strings);
            Log.e("After_getSeleBoks_size", "" + SuperLifeSecretPreferences.getInstance().getSelectedBooks().size());

            double AMOUNT_TOTAL = 0.0;
            for (int i = 0; i < bookArrayList.size(); i++) {
                AMOUNT_TOTAL = ((bookArrayList.get(i).getPrice_after_discount() * bookArrayList.get(i).getQuantity()) + AMOUNT_TOTAL);
            }
            total = AMOUNT_TOTAL;
            total_amont = "" + AMOUNT_TOTAL;
            try {
                textview_total_price.setText("" + SuperLifeSecretPreferences.getInstance().getString("book_currency") + " " + String.format(Locale.getDefault(), "%,.2f", AMOUNT_TOTAL));
            } catch (Exception e) {
                textview_total_price.setText("" + SuperLifeSecretPreferences.getInstance().getString("book_currency") + " " + AMOUNT_TOTAL);
            }
            thirdBooksAapter.notifyDataSetChanged();
        }

        public void onQualtityChanged(String s, final int position) {
            try {
                Log.v("QTY Changed", s + " ," + position);
                bookArrayList.get(position).setQuantity(Integer.parseInt(s));
                double AMOUNT_TOTAL = 0.0;
                for (int i = 0; i < bookArrayList.size(); i++) {
                   /* if (i == position) {
                        bookArrayList.get(i).setQuantity(Integer.parseInt(s));
                    }*/
                    AMOUNT_TOTAL = ((bookArrayList.get(i).getPrice_after_discount() * bookArrayList.get(i).getQuantity()) + AMOUNT_TOTAL);
                }

              /*  try {
                    textview_total_price.setText("" + SuperLifeSecretPreferences.getInstance().getString("book_currency") + " " + String.format(Locale.getDefault(), "%,.2f", AMOUNT_TOTAL));
                } catch (Exception e) {
                    textview_total_price.setText("" + SuperLifeSecretPreferences.getInstance().getString("book_currency") + " " + AMOUNT_TOTAL);
                }*/
                for (int p = 0; p < bookArrayList.size(); p++) {
                    if (bookArrayList.get(p).getDiscount().size() != 0) {
                        for (int d = 0; d < bookArrayList.get(p).getDiscount().size(); d++) {
                            double book_price = bookArrayList.get(p).getPrice();
                            int min_quantity = Integer.parseInt(bookArrayList.get(p).getDiscount().get(d).getMin_qty());
                            int max_quantity = Integer.parseInt(bookArrayList.get(p).getDiscount().get(d).getMax_qty());
                            int book_quantity = bookArrayList.get(p).getQuantity();
                            bookArrayList.get(p).setPrice_after_discount(book_price);
                            if (book_quantity >= min_quantity && book_quantity <= max_quantity) {
                                if (bookArrayList.get(p).getDiscount().get(d).getDiscount_type().equals("1")) {
                                    double no = bookArrayList.get(p).getDiscount().get(d).getDiscount_amount();
                                    double discount = ((book_price * no) / 100);
                                    bookArrayList.get(p).setDiscount_applied(discount);
                                    bookArrayList.get(p).setIs_discounted(true);
                                    bookArrayList.get(p).setPrice_after_discount(book_price - discount);
                                    break;
                                } else {
                                    double discount = bookArrayList.get(p).getDiscount().get(d).getDiscount_amount();
                                    bookArrayList.get(p).setDiscount_applied(discount);
                                    bookArrayList.get(p).setPrice_after_discount(book_price - discount);
                                    bookArrayList.get(p).setIs_discounted(true);
                                    break;
                                }
                            } else {
                                bookArrayList.get(p).setDiscount_applied(0);
                                bookArrayList.get(p).setIs_discounted(false);
                                bookArrayList.get(p).setPrice_after_discount(book_price);
                            }
                        }
                    } else {
                        bookArrayList.get(p).setPrice_after_discount(bookArrayList.get(p).getPrice());
                    }
                }
                total = AMOUNT_TOTAL;
                total_amont = "" + AMOUNT_TOTAL;
                final double finalAMOUNT_TOTAL = AMOUNT_TOTAL;
                bookrecyclerview.post(new Runnable() {
                    @Override
                    public void run() {
                        thirdBooksAapter.notifyItemChanged(position);
                        textview_total_price.setText(String.format("%s %s", SuperLifeSecretPreferences.getInstance().getString("book_currency"), String.format(Locale.getDefault(), "%,.2f", finalAMOUNT_TOTAL)));
                        // thirdBooksAapter.notifyItemChanged(position);
                    }
                });
                SuperLifeSecretPreferences.getInstance().setSelectedBooksList(bookArrayList);
            /*    new android.os.Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        thirdBooksAapter.notifyItemChanged(position);
                    }
                },1000);*/
            } catch (Exception e) {
                e.printStackTrace();
            /*    s = "1";
                double AMOUNT_TOTAL = 0.0;
                for (int i = 0; i < bookArrayList.size(); i++) {
                    if (i == position) {
                        bookArrayList.get(i).setQuantity(Integer.parseInt("1"));
                    }
                    AMOUNT_TOTAL = ((bookArrayList.get(i).getPrice_after_discount() * bookArrayList.get(i).getQuantity()) + AMOUNT_TOTAL);
                }
                total = AMOUNT_TOTAL;
                total_amont = "" + AMOUNT_TOTAL;
                try {
                    textview_total_price.setText("" + SuperLifeSecretPreferences.getInstance().getString("book_currency") + " " + String.format(Locale.getDefault(), "%,.2f", AMOUNT_TOTAL));
                } catch (Exception e2) {
                    textview_total_price.setText("" + SuperLifeSecretPreferences.getInstance().getString("book_currency") + " " + AMOUNT_TOTAL);
                }
                thirdBooksAapter.notifyDataSetChanged();*/
            }
        }

        public void onQualtityChanged() {

            double AMOUNT_TOTAL = 0.0;
            for (int i = 0; i < bookArrayList.size(); i++) {
                   /* if (i == position) {
                        bookArrayList.get(i).setQuantity(Integer.parseInt(s));
                    }*/
                AMOUNT_TOTAL = ((bookArrayList.get(i).getPrice_after_discount() * bookArrayList.get(i).getQuantity()) + AMOUNT_TOTAL);
            }
            total_amont = "" + AMOUNT_TOTAL;
            final double finalAMOUNT_TOTAL = AMOUNT_TOTAL;
            bookrecyclerview.post(new Runnable() {
                @Override
                public void run() {
                    textview_total_price.setText(String.format("%s %s", SuperLifeSecretPreferences.getInstance().getString("book_currency"), String.format(Locale.getDefault(), "%,.2f", finalAMOUNT_TOTAL)));
                    // thirdBooksAapter.notifyItemChanged(position);
                }
            });
            SuperLifeSecretPreferences.getInstance().setSelectedBooksList(bookArrayList);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        SuperLifeSecretPreferences.getInstance().putString("book_stake_page_no", "2");
    }

    private void setUpConversion() {
        conversionData = SuperLifeSecretCodeApp.getInstance().getConversionData();
        if (conversionData != null) {
            textview_back.setText(conversionData.getBack());
            textview_next.setText(conversionData.getNext());
            insufficient_fund = conversionData.getInsufficient_funds();
            textview_total.setText(conversionData.getTotal());
            if (SuperLifeSecretPreferences.getInstance().getString("book_type").equals("1")) {
                textview_select.setText(conversionData.getSelect_quantity_buying());
            } else {
                textview_select.setText(conversionData.getSelect_quantity_printing());
            }
            insufficient_fund = conversionData.getInsufficient_funds();
        }
    }

    @Override
    protected void initializePresenter() {
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
