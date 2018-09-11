package com.superlifesecretcode.app.ui.book.detail;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.superlifesecretcode.app.R;
import com.superlifesecretcode.app.data.model.userdetails.UserDetailResponseData;
import com.superlifesecretcode.app.data.persistance.SuperLifeSecretPreferences;
import com.superlifesecretcode.app.ui.base.BaseActivity;
import com.superlifesecretcode.app.ui.book.five.BigReceiptAapter;
import com.superlifesecretcode.app.ui.book.five.FifthBookActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MyOrderDetailActivity extends BaseActivity implements MyOrderDetailView {

    RecyclerView recyclerView, receipt_recyclerview;
    TextView order_id, book_type, order_for, delivery_type, delivery_address;
    TextView user_name, user_country, order_status, order_date, tv_receipt;
    TextView subtotal, delivery, grant_total;
    MyOrderDetailPresenter myOrderDetailPresenter;
    UserDetailResponseData userData;
    BookAapterDetail bookAapterDetail;
    ArrayList<Book> bookArrayList;
    ArrayList<Receipt> receipts;
    ImageView back_image;
    ReceiptAapterDetail receiptAapterDetail;

    @Override
    protected int getContentView() {
        return R.layout.activity_book_order_detail;
    }

    @Override
    protected void initializeView() {
        userData = SuperLifeSecretPreferences.getInstance().getUserData();
        order_id = findViewById(R.id.order_id);
        book_type = findViewById(R.id.book_type);
        order_for = findViewById(R.id.order_for);
        delivery_type = findViewById(R.id.delivery_type);
        delivery_address = findViewById(R.id.delivery_address);
        user_name = findViewById(R.id.user_name);
        user_country = findViewById(R.id.user_country);
        order_status = findViewById(R.id.order_status);
        order_date = findViewById(R.id.order_date);
        tv_receipt = findViewById(R.id.tv_receipt);
        subtotal = findViewById(R.id.subtotal);
        delivery = findViewById(R.id.delivery);
        grant_total = findViewById(R.id.grant_total);
        recyclerView = findViewById(R.id.recyclerView);
        receipt_recyclerview = findViewById(R.id.receipt_recyclerview);
        back_image = findViewById(R.id.back_image);
        bookArrayList = new ArrayList<>();
        receipts = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        bookAapterDetail = new BookAapterDetail(bookArrayList, MyOrderDetailActivity.this);
        recyclerView.setAdapter(bookAapterDetail);

        receipt_recyclerview.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true));
        receiptAapterDetail = new ReceiptAapterDetail(MyOrderDetailActivity.this, receipts, new onReceiptSelector()
        );
        receipt_recyclerview.setAdapter(receiptAapterDetail);
        String order_id = getIntent().getStringExtra("order_id");
        orderDetail(order_id);

        back_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void orderDetail(String order_id) {
        Map<String, String> headers = new HashMap<>();

        headers.put("Authorization", "Bearer " + userData.getApi_token());

        HashMap<String, String> params = new HashMap<>();
        params.put("id", order_id);
        myOrderDetailPresenter.getOrderDetail(params, headers);
    }

    @Override
    protected void initializePresenter() {
        myOrderDetailPresenter = new MyOrderDetailPresenter(this);
        myOrderDetailPresenter.setView(this);
    }

    @Override
    public void getOrderDetail(DetailBean newsResponseModel) {
        bookArrayList.clear();
        bookArrayList.addAll(newsResponseModel.getData().getBooks());
        bookAapterDetail.notifyDataSetChanged();
        OrderBean orderBean = newsResponseModel.getData().getOrder();
        order_id.setText(orderBean.getOrder_id());
        Log.e("getDelevry_stret_adress", "" + orderBean.getDelevery_street_address());
        Log.e("getDelevery_city", "" + orderBean.getDelevery_city());
        Log.e("getDelevery_state", "" + orderBean.getDelevery_state());
        delivery_address.setText(orderBean.getDelevery_street_address() + "," + orderBean.getDelevery_city() + "," + orderBean.getDelevery_state());
        order_for.setText(orderBean.getOrder_for());
        delivery_type.setText("" + orderBean.getDelevery_type());
        user_name.setText(orderBean.getName());
        user_country.setText(orderBean.getStore_country());
        book_type.setText(orderBean.getBook_type());
        order_status.setText(orderBean.getOrder_status());
        order_date.setText(orderBean.getOrder_date());

        subtotal.setText(SuperLifeSecretPreferences.getInstance().getString("book_currency") + " " + newsResponseModel.getData().getTotal_amount());
        delivery.setText(SuperLifeSecretPreferences.getInstance().getString("book_currency") + " " + "0");
        grant_total.setText(SuperLifeSecretPreferences.getInstance().getString("book_currency") + " " + newsResponseModel.getData().getTotal_amount());

        receipts.clear();
        receipts.addAll(newsResponseModel.getData().getReceipts());
        receiptAapterDetail.notifyDataSetChanged();
    }

    public class onReceiptSelector {
        public void clickReceipt(int pos) {
            showsDialog(pos);
        }
    }

    public void showsDialog(int pos) {
        final Dialog dialog = new Dialog(MyOrderDetailActivity.this, R.style.DialogCustomTheme);
        dialog.setCanceledOnTouchOutside(false);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_full_receipt);
        Window window = dialog.getWindow();
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        RecyclerView recyclerView = dialog.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        ReceiptFullPageAapterDetail bigReceiptAapter = new ReceiptFullPageAapterDetail(this,receipts);
        recyclerView.setAdapter(bigReceiptAapter);
        dialog.show();
    }
}