package com.superlifesecretcode.app.ui.book.second;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.dynamic.IFragmentWrapper;
import com.superlifesecretcode.app.R;
import com.superlifesecretcode.app.ui.base.BaseActivity;
import com.superlifesecretcode.app.ui.book.first.BookBean;
import com.superlifesecretcode.app.ui.book.first.FirstBookActivity;
import com.superlifesecretcode.app.ui.book.third.ThirsBookActivity;

import java.util.ArrayList;
import java.util.Collections;

public class SecondBookActivity extends BaseActivity {

    LinearLayout linearlayout_book_quality, linearlayout_affortdability, amount_linear;
    ImageView checkbox_affordibility, check_book_quality;
    TextView textview_printbybookquality, textview_bookquality_description, textview_affordability, textview_affordability_description;
    TextView textview_totalamount, textview_next, textview_back;
    EditText edittext_enteramount;
    ArrayList<BookBean> bookArrayList;
    double TOTAL_AMOUNT, REMAINING_AMOUNT;

    @Override
    protected int getContentView() {
        return R.layout.activity_book_second;
    }

    @Override
    protected void initializeView() {

        bookArrayList = (ArrayList<BookBean>) getIntent().getSerializableExtra("selected_booklist");

        linearlayout_book_quality = findViewById(R.id.linearlayout_book_quality);
        linearlayout_affortdability = findViewById(R.id.linearlayout_affortdability);
        amount_linear = findViewById(R.id.amount_linear);
        checkbox_affordibility = findViewById(R.id.checkbox_affordibility);
        check_book_quality = findViewById(R.id.check_book_quality);
        textview_printbybookquality = findViewById(R.id.textview_printbybookquality);
        textview_bookquality_description = findViewById(R.id.textview_bookquality_description);
        textview_affordability = findViewById(R.id.textview_affordability);
        textview_affordability_description = findViewById(R.id.textview_affordability_description);
        textview_totalamount = findViewById(R.id.textview_totalamount);
        edittext_enteramount = findViewById(R.id.edittext_enteramount);
        textview_next = findViewById(R.id.textview_next);
        textview_back = findViewById(R.id.textview_back);
        amount_linear.setVisibility(View.GONE);
        linearlayout_book_quality.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                amount_linear.setVisibility(View.GONE);
                check_book_quality.setImageDrawable(getResources().getDrawable(R.drawable.radio_checked));
                checkbox_affordibility.setImageDrawable(getResources().getDrawable(R.drawable.radio_unchecked));
            }
        });
        linearlayout_affortdability.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check_book_quality.setImageDrawable(getResources().getDrawable(R.drawable.radio_unchecked));
                checkbox_affordibility.setImageDrawable(getResources().getDrawable(R.drawable.radio_checked));
                amount_linear.setVisibility(View.VISIBLE);
            }
        });

        textview_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (amount_linear.getVisibility() == View.GONE) {
                    //getBookAmount();
                    Intent intent = new Intent(SecondBookActivity.this, ThirsBookActivity.class);
                    intent.putExtra("selected_booklist", bookArrayList);
                    intent.putExtra("total_amount", bookArrayList);
                    intent.putExtra("remaminder", bookArrayList);
                    startActivity(intent);
                } else {
                    getBookAmount();
                    if (TOTAL_AMOUNT != 0.0) {
                        Intent intent = new Intent(SecondBookActivity.this, ThirsBookActivity.class);
                        intent.putExtra("selected_booklist", bookArrayList);
                        for (int p = 0; p < bookArrayList.size(); p++) {
                            Log.e("quaadddd"+p,""+bookArrayList.get(p).getQuantity());
                        }
                        intent.putExtra("total_amount", ""+TOTAL_AMOUNT);
                        intent.putExtra("remaminder", ""+REMAINING_AMOUNT);
                        startActivity(intent);
                    }

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

    public void getBookAmount() {

        double AMOUNT_ENTERED = Double.parseDouble(edittext_enteramount.getText().toString());
        Log.e("AMOUNT_ENTERED", "" + AMOUNT_ENTERED);
        Log.e("bookArrayList SIZE", "" + bookArrayList.size());
        double AMOUNT_TOTAL = 0.0;
        double DIVID_REMAINDER = 0.0;
        int increased_quantity = 0;

        for (int i = 0; i < bookArrayList.size(); i++) {
            Log.e("unsortedprice", "" + bookArrayList.get(i).getPrice());
            AMOUNT_TOTAL = bookArrayList.get(i).getPrice() + AMOUNT_TOTAL;
        }
        Log.e("AMOUNT_TOTAL", "" + AMOUNT_TOTAL);

        if (AMOUNT_ENTERED > AMOUNT_TOTAL) {
            DIVID_REMAINDER = AMOUNT_ENTERED % AMOUNT_TOTAL;
            increased_quantity = (int) (AMOUNT_ENTERED / AMOUNT_TOTAL);
            for (int p = 0; p < bookArrayList.size(); p++) {
                bookArrayList.get(p).setQuantity(increased_quantity);
                Log.e("qua"+p,""+bookArrayList.get(p).getQuantity());
            }
            TOTAL_AMOUNT = (AMOUNT_ENTERED - DIVID_REMAINDER);
            REMAINING_AMOUNT = DIVID_REMAINDER;
            Log.e("DIVID_REMAINDER", "" + DIVID_REMAINDER);
        } else {
            Toast.makeText(this, "amount is insufficient!!!", Toast.LENGTH_SHORT).show();
            return;
        }

        double NEXT_REMAINDER=0.0;
        Collections.sort(bookArrayList, BookBean.PriceComparater);
        int setquantity=0;
        for (int i = 0; i < bookArrayList.size(); i++) {
            if (bookArrayList.get(i).getPrice() <= DIVID_REMAINDER) {
                NEXT_REMAINDER = DIVID_REMAINDER % bookArrayList.get(i).getPrice();
                Log.e("DIVID_REMAINDER_new"+DIVID_REMAINDER, "" + bookArrayList.get(i).getPrice());
                setquantity = (int) (DIVID_REMAINDER / bookArrayList.get(i).getPrice());
                if (setquantity!=0){
                    bookArrayList.get(i).setQuantity((setquantity + bookArrayList.get(i).getQuantity()));
                    TOTAL_AMOUNT = (AMOUNT_ENTERED - NEXT_REMAINDER);
                    REMAINING_AMOUNT = NEXT_REMAINDER;
                    DIVID_REMAINDER = DIVID_REMAINDER - bookArrayList.get(i).getPrice();
                }
            }
//            else {
//                TOTAL_AMOUNT = (AMOUNT_ENTERED - DIVID_REMAINDER);
//                REMAINING_AMOUNT = DIVID_REMAINDER;
//                return;
//            }
        }
    }

    @Override
    protected void initializePresenter() {
    }
}
