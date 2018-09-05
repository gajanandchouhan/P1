package com.superlifesecretcode.app.ui.book.myorder_book;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.superlifesecretcode.app.R;
import com.superlifesecretcode.app.data.model.userdetails.UserDetailResponseData;
import com.superlifesecretcode.app.data.persistance.SuperLifeSecretPreferences;
import com.superlifesecretcode.app.ui.base.BaseActivity;
import com.superlifesecretcode.app.ui.book.detail.MyOrderDetailActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class OrderBookActivity extends BaseActivity implements OrderBookView {

    OrderBookPresenter orderBookPresenter;
    RecyclerView recyclerview_myorderlist;
    ArrayList<Order> orderArrayList;
    OrderBookAdapter orderBookAdapter;
    private UserDetailResponseData userDetailResponseData;
    ImageView back_image;

    @Override
    protected int getContentView() {
        return R.layout.activity_book_order;
    }

    @Override
    protected void initializeView() {
        orderArrayList = new ArrayList<>();
        back_image = findViewById(R.id.back_image);
        userDetailResponseData = SuperLifeSecretPreferences.getInstance().getUserData();
        recyclerview_myorderlist = findViewById(R.id.recyclerview_myorderlist);
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
        orderArrayList.clear();
        orderArrayList.addAll(categoryResponseModel.getData());
        orderBookAdapter.notifyDataSetChanged();
    }
    public class BookListener {
        public void onClickDetail(String order_id) {
            Intent intent = new Intent(OrderBookActivity.this , MyOrderDetailActivity.class);
            intent.putExtra("order_id",order_id);
            startActivity(intent);
        }
    }
}
