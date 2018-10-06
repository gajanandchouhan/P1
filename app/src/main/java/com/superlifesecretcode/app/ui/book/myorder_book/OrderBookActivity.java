package com.superlifesecretcode.app.ui.book.myorder_book;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.superlifesecretcode.app.R;
import com.superlifesecretcode.app.data.model.userdetails.UserDetailResponseData;
import com.superlifesecretcode.app.data.persistance.SuperLifeSecretPreferences;
import com.superlifesecretcode.app.ui.base.BaseActivity;
import com.superlifesecretcode.app.ui.book.detail.MyOrderDetailActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Currency;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class OrderBookActivity extends BaseActivity implements OrderBookView {

    OrderBookPresenter orderBookPresenter;
    RecyclerView recyclerview_myorderlist;
    ArrayList<Order> orderArrayList;
    OrderBookAdapter orderBookAdapter;
    private UserDetailResponseData userDetailResponseData;
    ImageView back_image;
    TextView textView_title;

    @Override
    protected int getContentView() {
        return R.layout.activity_book_order;
    }

    @Override
    protected void initializeView() {
        Locale current = getResources().getConfiguration().locale;
        Log.i("locale", Currency.getInstance(current).getSymbol());
        SuperLifeSecretPreferences.getInstance().putString("book_currency", "" + Currency.getInstance(current).getSymbol());
        orderArrayList = new ArrayList<>();
        back_image = findViewById(R.id.back_image);
        userDetailResponseData = SuperLifeSecretPreferences.getInstance().getUserData();
        recyclerview_myorderlist = findViewById(R.id.recyclerview_myorderlist);
        textView_title = findViewById(R.id.textView_title);
        textView_title.setText(SuperLifeSecretPreferences.getInstance().getString("book_title"));
        getMyorderList();
        recyclerview_myorderlist.setLayoutManager(new LinearLayoutManager(this));
        orderBookAdapter = new OrderBookAdapter(OrderBookActivity.this,orderArrayList,new BookListener());
        recyclerview_myorderlist.setAdapter(orderBookAdapter);
        back_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void getMyorderList() {
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " +""+userDetailResponseData.getApi_token());
//        headers.put("Authorization", "Bearer " + "llTHt4b7S8pXx66HwcJmytxUljTfkCGZplDXaAMhfX8Hvoz16uVYRAaGCcMi");
//        HashMap<String, String> params = new HashMap<>();
//        params.put("book_type", book_type);
        orderBookPresenter.getMyOrderList(headers);
    }

    @Override
    protected void initializePresenter() {
        orderBookPresenter = new OrderBookPresenter(this);
        orderBookPresenter.setView(this);
    }

    @Override
    public void getOrderlist(MyOrderBean categoryResponseModel) {
        if (categoryResponseModel!=null){
            try {
                if (categoryResponseModel.getCurrencyUnit().getCurrency_symbol() != null || !categoryResponseModel.getCurrencyUnit().getCurrency_symbol().equals("")) {
                    SuperLifeSecretPreferences.getInstance().putString("book_currency", "" + categoryResponseModel.getCurrencyUnit().getCurrency_symbol());
                } else {
                    Locale current = getResources().getConfiguration().locale;
                    Log.i("locale", Currency.getInstance(current).getSymbol());
                    SuperLifeSecretPreferences.getInstance().putString("book_currency", "" + Currency.getInstance(current).getSymbol());
                }
            }catch (Exception e){
                Log.e("E",e.toString());
            }
            orderArrayList.clear();
            ArrayList<Order> orders = categoryResponseModel.getData();
            Collections.reverse(orders);
            orderArrayList.addAll(orders);
            orderBookAdapter.notifyDataSetChanged();
        }
    }
    public class BookListener {
        public void onClickDetail(String order_id) {
            Intent intent = new Intent(OrderBookActivity.this , MyOrderDetailActivity.class);
            intent.putExtra("order_id",order_id);
            startActivity(intent);
        }
    }
}
