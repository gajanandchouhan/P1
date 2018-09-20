package com.superlifesecretcode.app.ui.book.second;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.superlifesecretcode.app.R;
import com.superlifesecretcode.app.SuperLifeSecretCodeApp;
import com.superlifesecretcode.app.data.model.language.LanguageResponseData;
import com.superlifesecretcode.app.data.model.userdetails.UserDetailResponseData;
import com.superlifesecretcode.app.data.persistance.SuperLifeSecretPreferences;
import com.superlifesecretcode.app.ui.base.BaseActivity;
import com.superlifesecretcode.app.ui.book.first.BookBean;
import com.superlifesecretcode.app.ui.book.third.ThirsBookActivity;
import com.superlifesecretcode.app.util.CommonUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class SecondBookActivity extends BaseActivity implements SecondBookView {

    LinearLayout linearlayout_book_quality, linearlayout_affortdability, amount_linear;
    ImageView checkbox_affordibility, check_book_quality;
    TextView textview_printbybookquality, textview_bookquality_description, textview_affordability, textview_affordability_description;
    TextView textview_totalamount, textview_next, textview_back;
    EditText edittext_enteramount;
    ArrayList<BookBean> bookArrayList;
    double TOTAL_AMOUNT, REMAINING_AMOUNT;
    ImageView back_image;
    SecondBookPresenter secondBookPresenter;
    private UserDetailResponseData userData;
    RecyclerView delivery_recyclerview , discount_recyclerview;
    DeliveryAapter deliveryAapter;
    DiscountAapter discountAapter;
    ArrayList<DeliveryData> deliveryDataArrayList;
    ArrayList<Discount> discountArrayList;
    private LanguageResponseData conversionData;
    String enter_amount;
    TextView textView_title, textview_delivery_charges, textview_delivery_description_buy;
    boolean print_by_bookquantity = false ;

    @Override
    protected int getContentView() {
        return R.layout.activity_book_second;
    }

    @Override
    protected void onResume() {
        super.onResume();
        bookArrayList = SuperLifeSecretPreferences.getInstance().getSelectedBooksList();
    }

    @Override
    protected void initializeView() {
        userData = SuperLifeSecretPreferences.getInstance().getUserData();

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
        back_image = findViewById(R.id.back_image);
        delivery_recyclerview = findViewById(R.id.delivery_recyclerview);
        textView_title = findViewById(R.id.textView_title);
        textview_delivery_charges = findViewById(R.id.textview_delivery_charges);
        textview_delivery_description_buy = findViewById(R.id.textview_delivery_description_buy);
        discount_recyclerview = findViewById(R.id.discount_recyclerview);

        textView_title.setText(SuperLifeSecretPreferences.getInstance().getString("book_title"));

        deliveryDataArrayList = new ArrayList<>();
        delivery_recyclerview.setLayoutManager(new LinearLayoutManager(this));
        deliveryAapter = new DeliveryAapter(deliveryDataArrayList, this);
        delivery_recyclerview.setAdapter(deliveryAapter);

        discountArrayList = new ArrayList<>();
        discount_recyclerview.setLayoutManager(new LinearLayoutManager(this));
        discountAapter = new DiscountAapter(discountArrayList, this);
        discount_recyclerview.setAdapter(discountAapter);

        if (SuperLifeSecretPreferences.getInstance().getString("book_type").equals("2"))
            textview_delivery_description_buy.setVisibility(View.GONE);
        else
            textview_delivery_description_buy.setVisibility(View.VISIBLE);
        getDeliveryCharges();
        setUpConversion();
        back_image.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });

        if (SuperLifeSecretPreferences.getInstance().getString("book_type").equals("1")) {
            linearlayout_affortdability.setVisibility(View.GONE);
            linearlayout_book_quality.setVisibility(View.GONE);
            amount_linear.setVisibility(View.VISIBLE);
        } else {
            amount_linear.setVisibility(View.GONE);
            linearlayout_book_quality.setVisibility(View.VISIBLE);
            linearlayout_affortdability.setVisibility(View.VISIBLE);
        }

        linearlayout_book_quality.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                print_by_bookquantity = true;
                amount_linear.setVisibility(View.GONE);
                check_book_quality.setImageDrawable(getResources().getDrawable(R.drawable.radio_checked));
                checkbox_affordibility.setImageDrawable(getResources().getDrawable(R.drawable.radio_unchecked));
            }
        });
        linearlayout_affortdability.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                print_by_bookquantity = true;
                check_book_quality.setImageDrawable(getResources().getDrawable(R.drawable.radio_unchecked));
                checkbox_affordibility.setImageDrawable(getResources().getDrawable(R.drawable.radio_checked));
                amount_linear.setVisibility(View.VISIBLE);
            }
        });

        textview_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (SuperLifeSecretPreferences.getInstance().getString("book_type").equals("2")){
                    if (print_by_bookquantity==false){
                        CommonUtils.showSnakeBar(SecondBookActivity.this,conversionData.getSelect_option());
                        return;
                    }
                }
                if (amount_linear.getVisibility() == View.GONE) {
                    double AMOUNT_TOTAL_SEND = 0.0;
                    for (int i = 0; i < bookArrayList.size(); i++) {
                        AMOUNT_TOTAL_SEND = bookArrayList.get(i).getPrice() + AMOUNT_TOTAL_SEND;
                        bookArrayList.get(i).setQuantity(1);
                    }
                    Intent intent = new Intent(SecondBookActivity.this, ThirsBookActivity.class);
                    SuperLifeSecretPreferences.getInstance().setSelectedBooksList(bookArrayList);
                    SuperLifeSecretPreferences.getInstance().putString("total_amount", "" + AMOUNT_TOTAL_SEND);
                    SuperLifeSecretPreferences.getInstance().putString("book_stake_page_no", "2");
                    startActivity(intent);
                } else {
                    if (SuperLifeSecretPreferences.getInstance().getString("book_type").equals("2")) {
                        if (!edittext_enteramount.getText().toString().equals("")) {
                            getBookAmount();
                            if (TOTAL_AMOUNT != 0.0) {
                                Intent intent = new Intent(SecondBookActivity.this, ThirsBookActivity.class);
                                intent.putExtra("selected_booklist", bookArrayList);
                                for (int p = 0; p < bookArrayList.size(); p++) {
                                    Log.e("quaadddd" + p, "" + bookArrayList.get(p).getQuantity());
                                }
                                intent.putExtra("total_amount", "" + TOTAL_AMOUNT);
                                SuperLifeSecretPreferences.getInstance().putString("total_amount", "" + TOTAL_AMOUNT);
                                SuperLifeSecretPreferences.getInstance().putString("book_stake_page_no", "2");
                                SuperLifeSecretPreferences.getInstance().setSelectedBooksList(bookArrayList);
                                startActivity(intent);
                            }
                        } else {
                            edittext_enteramount.setError(enter_amount);
                            CommonUtils.showSnakeBar(SecondBookActivity.this, enter_amount);
                        }
                    } else {
                        double AMOUNT_TOTAL_SEND = 0.0;
                        for (int i = 0; i < bookArrayList.size(); i++) {
                            AMOUNT_TOTAL_SEND = bookArrayList.get(i).getPrice() + AMOUNT_TOTAL_SEND;
                            bookArrayList.get(i).setQuantity(1);
                        }
                        Intent intent = new Intent(SecondBookActivity.this, ThirsBookActivity.class);
                        SuperLifeSecretPreferences.getInstance().setSelectedBooksList(bookArrayList);
                        SuperLifeSecretPreferences.getInstance().putString("total_amount", "" + AMOUNT_TOTAL_SEND);
                        SuperLifeSecretPreferences.getInstance().putString("book_stake_page_no", "2");
                        startActivity(intent);
                    }
                }
            }
        });
        textview_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SuperLifeSecretPreferences.getInstance().putString("book_stake_page_no", "1");
                finish();
            }
        });
        String stack = SuperLifeSecretPreferences.getInstance().getString("book_stake_page_no");
        if (stack != null) {
            if (Integer.parseInt(stack) > 2) {
                CommonUtils.startActivity(SecondBookActivity.this, ThirsBookActivity.class);
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        SuperLifeSecretPreferences.getInstance().putString("book_stake_page_no", "1");
    }

    private void setUpConversion() {
        conversionData = SuperLifeSecretCodeApp.getInstance().getConversionData();
        if (conversionData != null) {
            textview_back.setText(conversionData.getBack());
            textview_next.setText(conversionData.getNext());
//            select_book = conversionData.getSelect_book();
            enter_amount = conversionData.getEnter_amount();
            textview_totalamount.setText(conversionData.getEnter_amount());
            edittext_enteramount.setHint(conversionData.getEnter_amount());
            textview_delivery_charges.setText(conversionData.getDelivery_charges());

            if (SuperLifeSecretPreferences.getInstance().getString("book_type").equals("2")) {
                textview_printbybookquality.setText(conversionData.getPrint_by_quantity());
                textview_affordability.setText(conversionData.getPrint_by_affordability());
            } else {
                textview_printbybookquality.setText(conversionData.getBuy_by_quantity());
            }
        }
    }

    private void getDeliveryCharges() {
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + userData.getApi_token());
        secondBookPresenter.getDeliveryCharges(headers);
    }

    private void getDeliveryBuySentence() {
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + userData.getApi_token());
        secondBookPresenter.getDeliveryDescription(headers);
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
                Log.e("qua" + p, "" + bookArrayList.get(p).getQuantity());
            }
            TOTAL_AMOUNT = (AMOUNT_ENTERED - DIVID_REMAINDER);
            REMAINING_AMOUNT = DIVID_REMAINDER;
            Log.e("DIVID_REMAINDER", "" + DIVID_REMAINDER);
        } else {
            Toast.makeText(this, "amount is insufficient!!!", Toast.LENGTH_SHORT).show();
            return;
        }

        double NEXT_REMAINDER = 0.0;
        Collections.sort(bookArrayList, BookBean.PriceComparater);
        int setquantity = 0;
        for (int i = 0; i < bookArrayList.size(); i++) {
            if (bookArrayList.get(i).getPrice() <= DIVID_REMAINDER) {
                NEXT_REMAINDER = DIVID_REMAINDER % bookArrayList.get(i).getPrice();
                Log.e("DIVID_REMAINDER_new" + DIVID_REMAINDER, "" + bookArrayList.get(i).getPrice());
                setquantity = (int) (DIVID_REMAINDER / bookArrayList.get(i).getPrice());
                if (setquantity != 0) {
                    bookArrayList.get(i).setQuantity((setquantity + bookArrayList.get(i).getQuantity()));
                    TOTAL_AMOUNT = (AMOUNT_ENTERED - NEXT_REMAINDER);
                    REMAINING_AMOUNT = NEXT_REMAINDER;
                    DIVID_REMAINDER = DIVID_REMAINDER - bookArrayList.get(i).getPrice();
                }
            }
        }
    }

    @Override
    protected void initializePresenter() {
        secondBookPresenter = new SecondBookPresenter(this);
        secondBookPresenter.setView(this);
    }

    @Override
    public void getDeliveryCharges(Delivery categoryResponseModel) {
        getDeliveryBuySentence();
        deliveryDataArrayList.clear();
        deliveryDataArrayList.addAll(categoryResponseModel.getData().getDelivery_charges());
        SuperLifeSecretPreferences.getInstance().setDeliveryChargesList(deliveryDataArrayList);
        deliveryAapter.notifyDataSetChanged();
        discountArrayList.clear();
        discountArrayList.addAll(categoryResponseModel.getData().getDiscounts());
        discountAapter.notifyDataSetChanged();
        SuperLifeSecretPreferences.getInstance().setDiscountList(discountArrayList);
    }

    @Override
    public void getDeliveryDescription(DeliveryDescription categoryResponseModel) {
        textview_delivery_description_buy.setText(categoryResponseModel.getData());
    }
}
