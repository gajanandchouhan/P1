package com.superlifesecretcode.app.ui.book.detail;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
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
import android.widget.Toast;

import com.superlifesecretcode.app.R;
import com.superlifesecretcode.app.data.model.language.LanguageResponseData;
import com.superlifesecretcode.app.data.model.userdetails.UserDetailResponseData;
import com.superlifesecretcode.app.data.persistance.SuperLifeSecretPreferences;
import com.superlifesecretcode.app.ui.base.BaseActivity;
import com.superlifesecretcode.app.ui.book.five.BigReceiptAapter;
import com.superlifesecretcode.app.ui.book.five.FifthBookActivity;
import com.superlifesecretcode.app.ui.book.forth.ForthBookActivity;
import com.superlifesecretcode.app.util.CommonUtils;
import com.superlifesecretcode.app.util.ConstantLib;

import java.util.ArrayList;
import java.util.Currency;
import java.util.HashMap;
import java.util.Locale;
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
    TextView order_detail, tv_order_status, tv_order_date;
    TextView tv_orderid, tv_book_type, tv_order_for, tv_delivery_type, tv_delivery_address, tv_user_name, tv_user_country;
    TextView tv_subtotal, tv_delivery, tv_grand_total, tv_paymentdetail, textView_title;
    LanguageResponseData conversionData;
    TextView tv_other_person_name_dialog, other_person_name_dialog, tv_other_person_mobile_dialog, other_person_mobile_dialog;
    LinearLayout linear_otherperson_name, linear_other_person_mobile;
    TextView tv_total, total;
    TextView tv_bank_name, bank_name, tv_account_no, account_no;
    TextView tv_payment_mode, payment_mode, tv_payment_date, payment_date;
    TextView tv_email, email, tv_contactno, contacno;

    @Override
    protected int getContentView() {
        return R.layout.activity_book_order_detail;
    }

    @Override
    protected void initializeView() {
        userData = SuperLifeSecretPreferences.getInstance().getUserData();
        conversionData = SuperLifeSecretPreferences.getInstance().getConversionData();
        tv_orderid = findViewById(R.id.tv_orderid);
        tv_book_type = findViewById(R.id.tv_book_type);
        tv_order_for = findViewById(R.id.tv_order_for);
        tv_delivery_type = findViewById(R.id.tv_delivery_type);
        tv_delivery_address = findViewById(R.id.tv_delivery_address);
        tv_user_name = findViewById(R.id.tv_user_name);
        tv_user_country = findViewById(R.id.tv_user_country);
        order_detail = findViewById(R.id.order_detail);
        tv_order_status = findViewById(R.id.tv_order_status);
        tv_order_date = findViewById(R.id.tv_order_date);
        tv_paymentdetail = findViewById(R.id.tv_paymentdetail);
        tv_subtotal = findViewById(R.id.tv_subtotal);
        tv_delivery = findViewById(R.id.tv_delivery);
        tv_grand_total = findViewById(R.id.tv_grand_total);
        linear_otherperson_name = findViewById(R.id.linear_otherperson_name);
        linear_other_person_mobile = findViewById(R.id.linear_other_person_mobile);
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
        textView_title = findViewById(R.id.textView_title);
        tv_total = findViewById(R.id.tv_total);
        total = findViewById(R.id.total);
        tv_email = findViewById(R.id.tv_email);
        email = findViewById(R.id.email);
        tv_contactno = findViewById(R.id.tv_contactno);
        contacno = findViewById(R.id.contacno);

        tv_bank_name = findViewById(R.id.tv_bank_name);
        bank_name = findViewById(R.id.bank_name);
        tv_account_no = findViewById(R.id.tv_account_no);
        account_no = findViewById(R.id.account_no);
        tv_payment_mode = findViewById(R.id.tv_payment_mode);
        payment_mode = findViewById(R.id.payment_mode);
        tv_payment_date = findViewById(R.id.tv_payment_date);
        payment_date = findViewById(R.id.payment_date);

        tv_other_person_name_dialog = findViewById(R.id.tv_other_person_name_dialog);
        other_person_name_dialog = findViewById(R.id.other_person_name_dialog);
        tv_other_person_mobile_dialog = findViewById(R.id.tv_other_person_mobile_dialog);
        other_person_mobile_dialog = findViewById(R.id.other_person_mobile_dialog);

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
        if (newsResponseModel != null) {
            try {
                if (newsResponseModel.getCurrencyUnit().getCurrency_symbol() != null || !newsResponseModel.getCurrencyUnit().getCurrency_symbol().equals("")) {
                    SuperLifeSecretPreferences.getInstance().putString("book_currency", "" + newsResponseModel.getCurrencyUnit().getCurrency_symbol());
                } else {
                    Locale current = getResources().getConfiguration().locale;
                    Log.i("locale", Currency.getInstance(current).getSymbol());
                    SuperLifeSecretPreferences.getInstance().putString("book_currency", "" + Currency.getInstance(current).getSymbol());
                }
            } catch (Exception e) {
                Log.e("E", e.toString());
            }
            bookArrayList.clear();
            bookArrayList.addAll(newsResponseModel.getData().getBooks());
            bookAapterDetail.notifyDataSetChanged();
            if (newsResponseModel != null) {
                OrderBean orderBean = newsResponseModel.getData().getOrder();
                if (orderBean != null) {
                    order_id.setText(orderBean.getOrder_code());
//                if (orderBean.getDelevery_street_address().equals("")){
//                    delivery_address.setVisibility(View.GONE);
//                    tv_delivery_address.setVisibility(View.GONE);
//                }else {
//                    delivery_address.setVisibility(View.VISIBLE);
//                    tv_delivery_address.setVisibility(View.VISIBLE);
//                    delivery_address.setText(orderBean.getDelevery_street_address() + "," + orderBean.getDelevery_city() + "," + orderBean.getDelevery_state());
//                }
                    if (orderBean.getDelevery_type().equals("1")) {
                        delivery_type.setText(conversionData.getDesignated());
                    } else {
                        delivery_type.setText(conversionData.getDistribute());
                    }
                    if (delivery_type.getText().toString().equals("")) {
                        delivery_type.setVisibility(View.GONE);
                        tv_delivery_type.setVisibility(View.GONE);
                    } else {
                        delivery_type.setVisibility(View.VISIBLE);
                        tv_delivery_type.setVisibility(View.VISIBLE);
                    }
                    user_name.setText(orderBean.getName());
                    UserDetailResponseData userDetailResponseData = SuperLifeSecretPreferences.getInstance().getUserData();
                    user_country.setText(userDetailResponseData.getCountryName());
//                    if (orderBean.getStore_country().equals("")) {
//                        user_country.setVisibility(View.GONE);
//                        tv_user_country.setVisibility(View.GONE);
//                    } else {
//                        user_country.setVisibility(View.VISIBLE);
//                        tv_user_country.setVisibility(View.VISIBLE);
//                        user_country.setText(orderBean.getStore_country());
//                    }

                    if (orderBean.getBook_type().equals("1")) {
                        book_type.setText(conversionData.getBuying());
                    } else {
                        book_type.setText(conversionData.getPrinting());
                    }
                    if (orderBean.getBook_type().equals("2")) {
                        order_for.setText(conversionData.getBehalf());
                    } else {
                        order_for.setText(conversionData.getOwn());
                    }
                    if (!orderBean.getOther_person_name().equals("")) {
                        other_person_name_dialog.setText(orderBean.getOther_person_name());
                    } else {
                        linear_otherperson_name.setVisibility(View.GONE);
                    }
                    if (!orderBean.getOther_person_mobile().equals("")) {
                        other_person_mobile_dialog.setText(orderBean.getOther_person_mobile());
                    } else {
                        linear_other_person_mobile.setVisibility(View.GONE);
                    }
//                    if (!orderBean.getStore_country().equals("")) {
//                        other_person_mobile_dialog.setText(orderBean.getOther_person_mobile());
//                    } else {
//                        linear_other_person_mobile.setVisibility(View.GONE);
//                    }
                    //order_status.setText(orderBean.getOrder_status());
                    if (orderBean.getOrder_status().equals("1")) {
                        order_status.setText(conversionData.getPending());
                    } else if (orderBean.getOrder_status().equals("2")) {
                        order_status.setText(conversionData.getSuccess());
                    } else {
                        order_status.setText(conversionData.getDeclined());
                    }
                    bank_name.setText(orderBean.getBank_name());
                    account_no.setText(orderBean.getAccount_number());

                    if (orderBean.getPayment_mode().equals("1")) {
                        payment_mode.setText(conversionData.getBank_transfer());
                    } else if (orderBean.getPayment_mode().equals("2")) {
                        payment_mode.setText(conversionData.getCash_deposit());
                    } else {
                        payment_mode.setText(conversionData.getCheque_deposit());
                    }
                    payment_date.setText(CommonUtils.getformattedDateFromString(ConstantLib.INPUT_DATE_ONLY_FORMATE, "dd MMMM, yyyy", orderBean.getPayment_date(), true, "utc"));

                    if (orderBean.getEmail().equals("")) {
                        tv_email.setVisibility(View.GONE);
                        tv_contactno.setVisibility(View.GONE);
                        email.setVisibility(View.GONE);
                        contacno.setVisibility(View.GONE);
                    } else {
                        tv_email.setVisibility(View.VISIBLE);
                        tv_contactno.setVisibility(View.VISIBLE);
                        email.setVisibility(View.VISIBLE);
                        contacno.setVisibility(View.VISIBLE);
                    }
                    email.setText(orderBean.getStore_email());
                    contacno.setText(orderBean.getStore_mobile());
                    //order_date.setText(orderBean.getOrder_date());
                    order_date.setText(CommonUtils.getformattedDateFromString(ConstantLib.INPUT_DATE_TIME_FORMATE, "dd MMMM, yyyy hh:mm a", orderBean.getOrder_date(), true, "utc"));
                    tv_total.setText(conversionData.getTotal());
                    total.setText(SuperLifeSecretPreferences.getInstance().getString("book_currency") + " " + Double.parseDouble(newsResponseModel.getData().getTotal_amount()));
                    subtotal.setText(SuperLifeSecretPreferences.getInstance().getString("book_currency") + " " + Double.parseDouble(newsResponseModel.getData().getTotal_amount()));
                    delivery.setText(conversionData.getFree());
//                    if (orderBean.getDelivery_charge().equals("0")) {
//                        delivery.setText(conversionData.getFree());
//                        try {
//                            subtotal.setText("" + SuperLifeSecretPreferences.getInstance().getString("book_currency") + " " + String.format(Locale.getDefault(), "%,.2f",Double.parseDouble(newsResponseModel.getData().getTotal_amount())));
//                        }catch (Exception e){
//                            subtotal.setText(SuperLifeSecretPreferences.getInstance().getString("book_currency") + " " + Double.parseDouble(newsResponseModel.getData().getTotal_amount()));
//                        }
//                    } else {
//                        try {
//                            delivery.setText("" + SuperLifeSecretPreferences.getInstance().getString("book_currency") + " " + String.format(Locale.getDefault(), "%,.2f",Double.parseDouble(orderBean.getDelivery_charge())));
//                        }catch (Exception e){
//                            delivery.setText(SuperLifeSecretPreferences.getInstance().getString("book_currency") + " " + Double.parseDouble(orderBean.getDelivery_charge()));
//                        }
//                        double subt = (Double.parseDouble(newsResponseModel.getData().getTotal_amount())  - Double.parseDouble(orderBean.getDelivery_charge()) );
//                        try {
//                            subtotal.setText("" + SuperLifeSecretPreferences.getInstance().getString("book_currency") + " " + String.format(Locale.getDefault(), "%,.2f",subt));
//                        }catch (Exception e){
//                            subtotal.setText(SuperLifeSecretPreferences.getInstance().getString("book_currency") + " " + Double.parseDouble(newsResponseModel.getData().getTotal_amount()));
//                        }
//                    }
                    try {
                        subtotal.setText("" + SuperLifeSecretPreferences.getInstance().getString("book_currency") + " " + String.format(Locale.getDefault(), "%,.2f", Double.parseDouble(newsResponseModel.getData().getTotal_amount())));
                    } catch (Exception e) {
                        subtotal.setText(SuperLifeSecretPreferences.getInstance().getString("book_currency") + " " + Double.parseDouble(newsResponseModel.getData().getTotal_amount()));
                    }
                    if (orderBean.getDelivery_charge().equals("0") || orderBean.getDelivery_charge().equals("0.0")) {
                        delivery.setText(conversionData.getFree());
                        try {
                            grant_total.setText("" + SuperLifeSecretPreferences.getInstance().getString("book_currency") + " " + String.format(Locale.getDefault(), "%,.2f", Double.parseDouble(newsResponseModel.getData().getTotal_amount())));
                        } catch (Exception e) {
                            grant_total.setText(SuperLifeSecretPreferences.getInstance().getString("book_currency") + " " + Double.parseDouble(newsResponseModel.getData().getTotal_amount()));
                        }
                    } else {
                        try {
                            delivery.setText("" + SuperLifeSecretPreferences.getInstance().getString("book_currency") + " " + String.format(Locale.getDefault(), "%,.2f", Double.parseDouble(newsResponseModel.getData().getOrder().getDelivery_charge())));
                        } catch (Exception e) {
                            delivery.setText(SuperLifeSecretPreferences.getInstance().getString("book_currency") + " " + Double.parseDouble(newsResponseModel.getData().getOrder().getDelivery_charge()));
                        }
                        double gt = (Double.parseDouble(newsResponseModel.getData().getTotal_amount()) + Double.parseDouble(newsResponseModel.getData().getOrder().getDelivery_charge()));
                        try {
                            grant_total.setText("" + SuperLifeSecretPreferences.getInstance().getString("book_currency") + " " + String.format(Locale.getDefault(), "%,.2f", gt));
                        } catch (Exception e) {
                            grant_total.setText(SuperLifeSecretPreferences.getInstance().getString("book_currency") + " " + gt);
                        }
                    }

                    //grant_total.setText(SuperLifeSecretPreferences.getInstance().getString("book_currency") + " " + Double.parseDouble(newsResponseModel.getData().getTotal_amount()));
                    receipts.clear();
                    receipts.addAll(newsResponseModel.getData().getReceipts());
                    receiptAapterDetail.notifyDataSetChanged();
                }
            }
        }
        setConversionData();
        contacno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int permissionCheck = ContextCompat.checkSelfPermission(MyOrderDetailActivity.this, Manifest.permission.CALL_PHONE);
                if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(MyOrderDetailActivity.this, new String[]{Manifest.permission.CALL_PHONE},1);
                } else {
                    startActivity(new Intent(Intent.ACTION_CALL).setData(Uri.parse("tel:"+contacno.getText().toString())));
                }
            }
        });
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if ((grantResults.length > 0) && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + ""));
                    startActivity(intent);
                } else {
                    Log.d("TAG", "Call Permission Not Granted");
                }
                break;

            default:
                break;
        }
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
        ReceiptFullPageAapterDetail bigReceiptAapter = new ReceiptFullPageAapterDetail(this, receipts);
        recyclerView.setAdapter(bigReceiptAapter);
        dialog.show();
    }


    public void setConversionData() {
        if (conversionData != null) {
            tv_orderid.setText(conversionData.getOrder_id());
            tv_book_type.setText(conversionData.getBook_type());
            tv_order_for.setText(conversionData.getOrder_for());
            tv_delivery_type.setText(conversionData.getOrder_type());
            tv_user_name.setText(conversionData.getUsername());
            tv_user_country.setText(conversionData.getUser_country());
            tv_order_status.setText(conversionData.getOrder_status());
            tv_order_date.setText(conversionData.getOrder_date());
            tv_receipt.setText(conversionData.getReceipts());
            tv_subtotal.setText(conversionData.getSubtotal());
            tv_delivery.setText(conversionData.getDelivery());
            tv_grand_total.setText(conversionData.getGrandtotal());
            tv_paymentdetail.setText(conversionData.getPayment_detail());
            textView_title.setText(conversionData.getOrder_detail());
            order_detail.setText(conversionData.getOrder_detail());
            tv_other_person_name_dialog.setText(conversionData.getOther_person());
            tv_other_person_mobile_dialog.setText(conversionData.getOther_mobile());
            tv_bank_name.setText(conversionData.getBank_name());
            tv_account_no.setText(conversionData.getAccount_number());
            tv_payment_mode.setText(conversionData.getPayment_mode());
            tv_payment_date.setText(conversionData.getPayment_date());
            tv_email.setText(conversionData.getEmail_id());
            tv_contactno.setText(conversionData.getContact_number());
        }
    }
}
