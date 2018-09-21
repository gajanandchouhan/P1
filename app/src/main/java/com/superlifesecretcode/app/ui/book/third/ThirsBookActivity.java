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
import com.superlifesecretcode.app.ui.book.forth.ForthBookActivity;
import com.superlifesecretcode.app.ui.book.second.DeliveryData;
import com.superlifesecretcode.app.util.CommonUtils;

import java.util.ArrayList;
import java.util.Locale;

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
        if(CommonUtils.book_stake==true){
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
            textview_total_price.setText("" + SuperLifeSecretPreferences.getInstance().getString("book_currency") + " " + String.format(Locale.getDefault(), "%.2f", total));
        } catch (Exception e) {
            textview_total_price.setText("" + SuperLifeSecretPreferences.getInstance().getString("book_currency") + " " + total);
        }
        //textview_total_price.setText("" + SuperLifeSecretPreferences.getInstance().getString("book_currency") + " " + total_amont);
        textView_title = findViewById(R.id.textView_title);
        textView_title.setText(SuperLifeSecretPreferences.getInstance().getString("book_title"));
        bookrecyclerview.setLayoutManager(new LinearLayoutManager(this));
        thirdBooksAapter = new ThirdBooksAapter(bookArrayList, this, new onDeleteListener());
        bookrecyclerview.setAdapter(thirdBooksAapter);

        textview_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                for (int i = 0; i < bookArrayList.size(); i++) {
                    if(bookArrayList.get(i).getQuantity()==0){
                        CommonUtils.showSnakeBar(ThirsBookActivity.this, conversionData.getAdd_quantity_for()+" "+bookArrayList.get(i).getName());
                        return;
                    }
                }

                int qu = 0;
                for (int i = 0; i < bookArrayList.size(); i++) {
                    qu = (bookArrayList.get(i).getQuantity() + qu);
                }
                //Toast.makeText(ThirsBookActivity.this, "quu"+qu, Toast.LENGTH_SHORT).show();
                double delivery_charges = 0.;
                ArrayList<DeliveryData> deliveryDataArrayList = SuperLifeSecretPreferences.getInstance().getDeliveryChargesList();
                for (int i = 0; i < deliveryDataArrayList.size(); i++) {
                    double from = Double.parseDouble(deliveryDataArrayList.get(i).getRange_from());
                    double to = Double.parseDouble(deliveryDataArrayList.get(i).getRange_to());
                    if (qu >= from && qu <= to) {
                        delivery_charges = Double.parseDouble(deliveryDataArrayList.get(i).getDelivery_charge());
                        //Toast.makeText(ThirsBookActivity.this, "if  "+delivery_charges, Toast.LENGTH_SHORT).show();
                    }
                    if (i == (deliveryDataArrayList.size() - 1) && qu > to) {
                        //Toast.makeText(ThirsBookActivity.this, "else "+delivery_charges, Toast.LENGTH_SHORT).show();
                        delivery_charges = 0;
                    }
                }
                SuperLifeSecretPreferences.getInstance().putString("delivery_charges", "" + delivery_charges);
                //Toast.makeText(ThirsBookActivity.this, ""+delivery_charges, Toast.LENGTH_SHORT).show();
                if (total == 0.0) {
                    CommonUtils.showSnakeBar(ThirsBookActivity.this, insufficient_fund);
                    return;
                }
                SuperLifeSecretPreferences.getInstance().putString("book_stake_page_no", "3");
                Intent intent = new Intent(ThirsBookActivity.this, ForthBookActivity.class);
                SuperLifeSecretPreferences.getInstance().putString("total_amount", total_amont);
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
                AMOUNT_TOTAL = ((bookArrayList.get(i).getPrice() * bookArrayList.get(i).getQuantity()) + AMOUNT_TOTAL);
            }
            total = AMOUNT_TOTAL;
            total_amont = "" + AMOUNT_TOTAL;
            try {
                textview_total_price.setText("" + SuperLifeSecretPreferences.getInstance().getString("book_currency") + " " + String.format(Locale.getDefault(), "%.2f", AMOUNT_TOTAL));
            } catch (Exception e) {
                textview_total_price.setText("" + SuperLifeSecretPreferences.getInstance().getString("book_currency") + " " + AMOUNT_TOTAL);
            }
            thirdBooksAapter.notifyDataSetChanged();
        }

        public void onQualtityChanged(String s, int position) {
            try {
                double AMOUNT_TOTAL = 0.0;
                for (int i = 0; i < bookArrayList.size(); i++) {
                    if (i == position) {
                        bookArrayList.get(i).setQuantity(Integer.parseInt(s));
                    }
                    AMOUNT_TOTAL = ((bookArrayList.get(i).getPrice() * bookArrayList.get(i).getQuantity()) + AMOUNT_TOTAL);
                }
                try {
                    textview_total_price.setText("" + SuperLifeSecretPreferences.getInstance().getString("book_currency") + " " + String.format(Locale.getDefault(), "%.2f", AMOUNT_TOTAL));
                } catch (Exception e) {
                    textview_total_price.setText("" + SuperLifeSecretPreferences.getInstance().getString("book_currency") + " " + AMOUNT_TOTAL);
                }
                total = AMOUNT_TOTAL;
                total_amont = "" + AMOUNT_TOTAL;
                SuperLifeSecretPreferences.getInstance().setSelectedBooksList(bookArrayList);
            } catch (Exception e) {
                s = "1";
                double AMOUNT_TOTAL = 0.0;
                for (int i = 0; i < bookArrayList.size(); i++) {
                    if (i == position) {
                        bookArrayList.get(i).setQuantity(Integer.parseInt("1"));
                    }
                    AMOUNT_TOTAL = ((bookArrayList.get(i).getPrice() * bookArrayList.get(i).getQuantity()) + AMOUNT_TOTAL);
                }
                total = AMOUNT_TOTAL;
                total_amont = "" + AMOUNT_TOTAL;
                try {
                    textview_total_price.setText("" + SuperLifeSecretPreferences.getInstance().getString("book_currency") + " " + String.format(Locale.getDefault(), "%.2f", AMOUNT_TOTAL));
                } catch (Exception e2) {
                    textview_total_price.setText("" + SuperLifeSecretPreferences.getInstance().getString("book_currency") + " " + AMOUNT_TOTAL);
                }
                //textview_total_price.setText("" + SuperLifeSecretPreferences.getInstance().getString("book_currency") + " " + AMOUNT_TOTAL);
                thirdBooksAapter.notifyDataSetChanged();
            }
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
}
