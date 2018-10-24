package com.superlifesecretcode.app.ui.book.forth;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.superlifesecretcode.app.R;
import com.superlifesecretcode.app.SuperLifeSecretCodeApp;
import com.superlifesecretcode.app.data.model.country.CountryResponseData;
import com.superlifesecretcode.app.data.model.language.LanguageResponseData;
import com.superlifesecretcode.app.data.model.userdetails.UserDetailResponseData;
import com.superlifesecretcode.app.data.persistance.SuperLifeSecretPreferences;
import com.superlifesecretcode.app.ui.base.BaseActivity;
import com.superlifesecretcode.app.ui.book.first.BookBean;
import com.superlifesecretcode.app.ui.book.five.FifthBookActivity;
import com.superlifesecretcode.app.ui.picker.AlertDialog;
import com.superlifesecretcode.app.ui.picker.CountryStatePicker;
import com.superlifesecretcode.app.util.CommonUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class ForthBookActivity extends BaseActivity implements ForthBookView {

    LinearLayout linear_print_for_own, linear_print_onBehalf;
    TextView textview_print_for_own, textview_print_on_behalf;
    View view_printforown, view_printfbehalf;
    ForthBookPresneter forthBookPresneter;
    LinearLayout linear_ordertype;
    TextView tv_add_address;
    TextView tv_ordertype;
    TextView textview_designated_location, textview_other_destribution, textview_back;
    LinearLayout linear_address, add_address_Layout;
    RecyclerView recyclerview_address;
    AddressAapter addressAapter;
    ArrayList<Stores> storyList;
    ArrayList<Stores> oldAddressList;
    private UserDetailResponseData userData;
    ImageView back_image;
    TextView textview_total, textview_total_price, textview_next;
    TextView textview_sub_total, textview_sub_total_price, textview_delivery, textview_delivery_price;
    TextView textview_noaddressfound;
    String total_amont;
    int printing_status = 1;
    EditText edittext_fullname, edittext_contact_number, edittext_emailid;
    TextView textview_contact_number, textview_emailid, textview_state, textview_city;
    EditText edittext_deliveryaddress, edittext_postcode, edittext_state, edittext_city;
    String countryId, country_name;
    String stateId = "", city_id = "";
    private CountryStatePicker countryStatePicker;
    private LanguageResponseData conversionData;
    TextView textview_fullname, textView_title, textview_ordertype, textview_deliveryaddress, textview_pincode;
    LinearLayout linear_other_person;
    TextView textview_other_person_name, textview_other_person_mobile;
    EditText edittext_other_person_name, edittext_otehr_person_mobile;
    String no = "";
    private TextView textViewDeliveryAtDest;
    private int qty;
    boolean moveToNext = true;
    private String message;

    @Override
    protected int getContentView() {
        return R.layout.activity_book_forth;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (CommonUtils.book_stake) {
            finish();
        }
    }

    @Override
    protected void initializeView() {
        userData = SuperLifeSecretPreferences.getInstance().getUserData();

        storyList = new ArrayList<>();
        oldAddressList = new ArrayList<>();
        total_amont = SuperLifeSecretPreferences.getInstance().getString("total_amount");
        textView_title = findViewById(R.id.textView_title);

        textView_title.setText(SuperLifeSecretPreferences.getInstance().getString("book_title"));

        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + userData.getApi_token());
        forthBookPresneter.getOldAddresses(headers);

        linear_print_for_own = findViewById(R.id.linear_print_for_own);
        linear_print_onBehalf = findViewById(R.id.linear_print_onBehalf);
        textview_print_for_own = findViewById(R.id.textview_print_for_own);
        textview_print_on_behalf = findViewById(R.id.textview_print_on_behalf);
        view_printforown = findViewById(R.id.view_printforown);
        view_printfbehalf = findViewById(R.id.view_printfbehalf);
        tv_add_address = findViewById(R.id.tv_add_address);
        tv_ordertype = findViewById(R.id.tv_ordertype);
        linear_ordertype = findViewById(R.id.linear_ordertype);
        textview_state = findViewById(R.id.textview_state);
        textview_city = findViewById(R.id.textview_city);
        textview_pincode = findViewById(R.id.textview_pincode);
        textview_designated_location = findViewById(R.id.textview_designated_location);
        textViewDeliveryAtDest = findViewById(R.id.textview_delivery_at_dest);
        textview_other_destribution = findViewById(R.id.textview_other_destribution);
        linear_address = findViewById(R.id.linear_address);
        add_address_Layout = findViewById(R.id.add_address_Layout);
        textview_back = findViewById(R.id.textview_back);
        recyclerview_address = findViewById(R.id.recyclerview_address);
        back_image = findViewById(R.id.back_image);
        textview_total = findViewById(R.id.textview_total);
        textview_total_price = findViewById(R.id.textview_total_price);
        textview_sub_total = findViewById(R.id.textview_sub_total);
        textview_sub_total_price = findViewById(R.id.textview_sub_total_price);
        textview_delivery = findViewById(R.id.textview_delivery_charges);
        textview_delivery_price = findViewById(R.id.textview_delivery_charges_price);
        textview_next = findViewById(R.id.textview_next);
        textview_noaddressfound = findViewById(R.id.textview_noaddressfound);
        edittext_contact_number = findViewById(R.id.edittext_contact_number);
        edittext_fullname = findViewById(R.id.edittext_fullname);
        textview_contact_number = findViewById(R.id.textview_contact_number);
        textview_emailid = findViewById(R.id.textview_emailid);
        edittext_emailid = findViewById(R.id.edittext_emailid);
        edittext_deliveryaddress = findViewById(R.id.edittext_deliveryaddress);
        edittext_postcode = findViewById(R.id.edittext_postcode);
        edittext_state = findViewById(R.id.edittext_state);
        edittext_city = findViewById(R.id.edittext_city);
        textview_fullname = findViewById(R.id.textview_fullname);
        textview_ordertype = findViewById(R.id.textview_ordertype);
        textview_deliveryaddress = findViewById(R.id.textview_deliveryaddress);
        linear_other_person = findViewById(R.id.linear_other_person);
        textview_other_person_name = findViewById(R.id.textview_other_person_name);
        edittext_other_person_name = findViewById(R.id.edittext_other_person_name);
        textview_other_person_mobile = findViewById(R.id.textview_other_person_mobile);
        edittext_otehr_person_mobile = findViewById(R.id.edittext_otehr_person_mobile);

        String stack = SuperLifeSecretPreferences.getInstance().getString("book_stake_page_no");
        if (stack != null) {
            if (Integer.parseInt(stack) < 4) {
                SuperLifeSecretPreferences.getInstance().putString("book_order_for", "1");
                SuperLifeSecretPreferences.getInstance().putString("book_store_id", "0");
                SuperLifeSecretPreferences.getInstance().putString("book_designated_type", "");
                SuperLifeSecretPreferences.getInstance().putString("status_old_store_address", "0");
            }
        }
        edittext_fullname.setText(userData.getUsername());
        edittext_emailid.setText(userData.getEmail());
        edittext_contact_number.setText(userData.getMobile());
        try {
            textview_sub_total_price.setText("" + SuperLifeSecretPreferences.getInstance().getString("book_currency") + " " + String.format(Locale.getDefault(), "%,.2f", Double.parseDouble(total_amont)));
        } catch (Exception e) {
            textview_sub_total_price.setText("" + SuperLifeSecretPreferences.getInstance().getString("book_currency") + " " + total_amont);
        }
        moveToNext = true;
        setUpConversion();
        back_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SuperLifeSecretPreferences.getInstance().putString("book_stake_page_no", "3");
                finish();
            }
        });
        edittext_state.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userData.getCountry() != null || !userData.getCountry().equals("")) {
                    countryId = userData.getCountry();
                    country_name = userData.getCountryName();
                } else {
                    country_name = SuperLifeSecretPreferences.getInstance().getLocationData().getCountryName();
                    countryId = SuperLifeSecretPreferences.getInstance().getLocationData().getCountry();
                }
                getState();
            }
        });

        edittext_city.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (stateId != null || !stateId.isEmpty())
                    getCity();
                else
                    CommonUtils.showSnakeBar(ForthBookActivity.this, conversionData.getSelect_state());
            }
        });

        textview_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String contact_number = edittext_contact_number.getText().toString();
                String email = edittext_emailid.getText().toString();
                String fullname = edittext_fullname.getText().toString();
                String other_person_name = edittext_other_person_name.getText().toString();
                String other_person_contactno = edittext_otehr_person_mobile.getText().toString();

                if (fullname.equals("")) {
                    edittext_fullname.setError(conversionData.getName_national_card());
                    CommonUtils.showSnakeBar(ForthBookActivity.this, conversionData.getName_national_card());
                    return;
                }
                if (contact_number.isEmpty()) {
                    edittext_contact_number.setError(conversionData.getEnter_contact_p_num());
                    CommonUtils.showSnakeBar(ForthBookActivity.this, conversionData.getEnter_contact_p_num());
                    return;
                }
                if (printing_status == 1) {
                    if (email.isEmpty()) {
                        edittext_emailid.setError(conversionData.getEnter_email());
                        CommonUtils.showSnakeBar(ForthBookActivity.this, conversionData.getEnter_email());
                        return;
                    }
                    if (!contact_number.equals(userData.getMobile()) || !email.equals(userData.getEmail())) {
                        showAlert();
                        return;
                    }
                } else {
                    if (other_person_name.equals("")) {
                        edittext_other_person_name.setError(conversionData.getOther_person());
                        CommonUtils.showSnakeBar(ForthBookActivity.this, conversionData.getOther_person());
                        return;
                    }
                    if (other_person_name.equals("")) {
                        edittext_otehr_person_mobile.setError(conversionData.getOther_mobile());
                        CommonUtils.showSnakeBar(ForthBookActivity.this, conversionData.getOther_mobile());
                        return;
                    }
                    if (email.isEmpty()) {
                        edittext_emailid.setError(conversionData.getEnter_email());
                        CommonUtils.showSnakeBar(ForthBookActivity.this, conversionData.getEnter_email());
                        return;
                    }
                }
                if (SuperLifeSecretPreferences.getInstance().getString("book_designated_type").equals("")) {
                    CommonUtils.showSnakeBar(ForthBookActivity.this, conversionData.getPlease_select_order_type());
                    return;
                }
                if (SuperLifeSecretPreferences.getInstance().getString("book_designated_type").equals("1")) {
                    if (recyclerview_address.getVisibility() == View.VISIBLE) {
                        CommonUtils.showSnakeBar(ForthBookActivity.this, conversionData.getPlease_enter_address());
                        return;
                    }
                }
                if (SuperLifeSecretPreferences.getInstance().getString("book_designated_type").equals("3")) {
                    if (!moveToNext) {
                        showNoDeliveryMessage(message);
                        return;
                    }
                    if (stateId == null || stateId.isEmpty()) {
                        CommonUtils.showToast(ForthBookActivity.this, conversionData.getSelect_state());
                        return;
                    }
                    String address = edittext_deliveryaddress.getText().toString().trim();
                    if (address.isEmpty()) {
                        CommonUtils.showToast(ForthBookActivity.this, conversionData.getPlease_enter_address());
                        return;
                    }
                    String postCode = edittext_postcode.getText().toString().trim();
                    if (postCode.isEmpty()) {
                        CommonUtils.showToast(ForthBookActivity.this, conversionData.getPlease_enter_postcode());
                        return;
                    }
                    if (city_id == null || city_id.isEmpty()) {
                        CommonUtils.showToast(ForthBookActivity.this, conversionData.getSelect_city());
                        return;
                    }

                    //  getDeliveryCharges();
                    //return;
                }

                SuperLifeSecretPreferences.getInstance().putString("book_address", edittext_deliveryaddress.getText().toString());
                SuperLifeSecretPreferences.getInstance().putString("book_full_name", fullname);
                SuperLifeSecretPreferences.getInstance().putString("book_mobile", contact_number);
                SuperLifeSecretPreferences.getInstance().putString("book_email", email);
                SuperLifeSecretPreferences.getInstance().putString("book_state", stateId);
                SuperLifeSecretPreferences.getInstance().putString("book_city", city_id);
                SuperLifeSecretPreferences.getInstance().putString("book_pin_code", edittext_postcode.getText().toString());
                SuperLifeSecretPreferences.getInstance().putString("book_stake_page_no", "4");
                SuperLifeSecretPreferences.getInstance().putString("other_person_name", other_person_name);
                SuperLifeSecretPreferences.getInstance().putString("other_person_contact", other_person_contactno);
                CommonUtils.startActivity(ForthBookActivity.this, FifthBookActivity.class);
            }
        });

        SuperLifeSecretPreferences.getInstance().putString("order_for_text", "" + conversionData.getPrinting_own());
        ArrayList<BookBean> bookBeanArrayList = SuperLifeSecretPreferences.getInstance().getSelectedBooksList();

        for (int i = 0; i < bookBeanArrayList.size(); i++) {
            qty = qty + bookBeanArrayList.get(i).getQuantity();
        }
        SuperLifeSecretPreferences.getInstance().putString("status_old_store_address", "0");
        SuperLifeSecretPreferences.getInstance().putString("book_designated_type", "");
        tv_ordertype.setHint(conversionData.getPlease_select_order_type());
        textview_other_destribution.setVisibility(View.VISIBLE);

        linear_print_for_own.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edittext_fullname.setText(userData.getUsername());
                SuperLifeSecretPreferences.getInstance().putString("order_for_text", "" + conversionData.getPrinting_own());
                SuperLifeSecretPreferences.getInstance().putString("book_order_for", "1");
                SuperLifeSecretPreferences.getInstance().putString("status_old_store_address", "0");
                SuperLifeSecretPreferences.getInstance().putString("book_designated_type", "");
                printing_status = 1;
                textview_print_for_own.setTextColor(getResources().getColor(R.color.colorBlack));
                textview_print_on_behalf.setTextColor(getResources().getColor(R.color.gray_color));
                view_printforown.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                view_printfbehalf.setBackgroundColor(getResources().getColor(R.color.transparent));
                linear_address.setVisibility(View.GONE);
                linear_ordertype.setVisibility(View.GONE);
                tv_ordertype.setText(conversionData.getPlease_select_order_type());
                add_address_Layout.setVisibility(View.GONE);
                textview_noaddressfound.setVisibility(View.GONE);
                linear_other_person.setVisibility(View.GONE);
                SuperLifeSecretPreferences.getInstance().putString("other_person_name", "");
                SuperLifeSecretPreferences.getInstance().putString("other_person_contact", "");
            }
        });

        linear_print_onBehalf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edittext_fullname.setText(userData.getUsername());
                SuperLifeSecretPreferences.getInstance().putString("order_for_text", "" + conversionData.getPrinting_behalf());
                SuperLifeSecretPreferences.getInstance().putString("book_order_for", "2");
                SuperLifeSecretPreferences.getInstance().putString("status_old_store_address", "0");
                SuperLifeSecretPreferences.getInstance().putString("book_designated_type", "");
                printing_status = 2;
                textview_print_for_own.setTextColor(getResources().getColor(R.color.gray_color));
                textview_print_on_behalf.setTextColor(getResources().getColor(R.color.colorBlack));
                view_printforown.setBackgroundColor(getResources().getColor(R.color.transparent));
                view_printfbehalf.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                recyclerview_address.setVisibility(View.GONE);
                add_address_Layout.setVisibility(View.GONE);
                linear_address.setVisibility(View.GONE);
                linear_ordertype.setVisibility(View.GONE);
                tv_ordertype.setText(conversionData.getPlease_select_order_type());
                textview_noaddressfound.setVisibility(View.GONE);

                linear_other_person.setVisibility(View.VISIBLE);
            }
        });

        tv_ordertype.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_ordertype.setTextColor(getResources().getColor(R.color.gray_color));
                if (linear_ordertype.getVisibility() == View.GONE) {
                    linear_ordertype.setVisibility(View.VISIBLE);
                } else
                    linear_ordertype.setVisibility(View.GONE);
                tv_ordertype.setText(conversionData.getPlease_select_order_type());
                SuperLifeSecretPreferences.getInstance().putString("book_designated_type", "");
                SuperLifeSecretPreferences.getInstance().putString("status_old_store_address", "0");
                recyclerview_address.setVisibility(View.GONE);
                textview_noaddressfound.setVisibility(View.GONE);
            }
        });

        textview_designated_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SuperLifeSecretPreferences.getInstance().putString("delivery_type_text", "" + conversionData.getDesignated());
                SuperLifeSecretPreferences.getInstance().putString("status_old_store_address", "0");
                SuperLifeSecretPreferences.getInstance().putString("book_designated_type", "1");
                tv_ordertype.setText(conversionData.getDesignated());
                tv_ordertype.setTextColor(getResources().getColor(R.color.colorBlack));
                linear_ordertype.setVisibility(View.GONE);
                linear_address.setVisibility(View.VISIBLE);
                add_address_Layout.setVisibility(View.GONE);
                recyclerview_address.setVisibility(View.VISIBLE);
                if (oldAddressList.size() == 0) {
                    textview_noaddressfound.setVisibility(View.VISIBLE);
                } else {
                    textview_noaddressfound.setVisibility(View.GONE);
                }
                moveToNext = true;
                resetTotal();
            }
        });

        textview_other_destribution.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SuperLifeSecretPreferences.getInstance().putString("final_dialog_email", "");
                SuperLifeSecretPreferences.getInstance().putString("final_dialog_phone", "");
                SuperLifeSecretPreferences.getInstance().putString("delivery_type_text", "" + conversionData.getDistribute());
                SuperLifeSecretPreferences.getInstance().putString("status_old_store_address", "2");
                SuperLifeSecretPreferences.getInstance().putString("book_designated_type", "2");
                tv_ordertype.setText(conversionData.getDistribute());
                tv_ordertype.setTextColor(getResources().getColor(R.color.colorBlack));
                linear_ordertype.setVisibility(View.GONE);
                linear_address.setVisibility(View.GONE);
                add_address_Layout.setVisibility(View.GONE);
                textview_noaddressfound.setVisibility(View.GONE);
                moveToNext = true;
                resetTotal();
            }
        });

        textViewDeliveryAtDest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SuperLifeSecretPreferences.getInstance().putString("final_dialog_email", "");
                SuperLifeSecretPreferences.getInstance().putString("final_dialog_phone", "");
                SuperLifeSecretPreferences.getInstance().putString("delivery_type_text", "" + conversionData.getDelivery_at_destination());
                SuperLifeSecretPreferences.getInstance().putString("status_old_store_address", "0");
                SuperLifeSecretPreferences.getInstance().putString("book_designated_type", "3");
                tv_ordertype.setText(conversionData.getDelivery_at_destination());
                tv_ordertype.setTextColor(getResources().getColor(R.color.colorBlack));
                linear_ordertype.setVisibility(View.GONE);
                linear_address.setVisibility(View.GONE);
                add_address_Layout.setVisibility(View.VISIBLE);
                recyclerview_address.setVisibility(View.VISIBLE);
                getDeliveryCharges();
               /* if (oldAddressList.size() == 0) {
                    textview_noaddressfound.setVisibility(View.VISIBLE);
                } else {
                    textview_noaddressfound.setVisibility(View.GONE);
                }*/
            }
        });
        stateId = userData.getState();
        edittext_state.setText(userData.getStateName());
        city_id = userData.getCity();
        edittext_city.setText(userData.getCityName());
        tv_add_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linear_address.setVisibility(View.GONE);
                add_address_Layout.setVisibility(View.VISIBLE);
                textview_noaddressfound.setVisibility(View.GONE);
            }
        });
        textview_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        recyclerview_address.setLayoutManager(new LinearLayoutManager(this));
        addressAapter = new AddressAapter(oldAddressList, this, new StoreListener());
        recyclerview_address.setAdapter(addressAapter);

        String stack2 = SuperLifeSecretPreferences.getInstance().getString("book_stake_page_no");
        if (stack2 != null) {
            if (Integer.parseInt(stack2) >= 4) {
                String delivery_charges_destination = SuperLifeSecretPreferences.getInstance().getString("delivery_charges_destination");
                if (delivery_charges_destination != null && !delivery_charges_destination.isEmpty()) {
                    try {
                        textview_delivery_price.setText("" + SuperLifeSecretPreferences.getInstance().getString("book_currency") + " " + String.format(Locale.getDefault(), "%,.2f", 0.00));
                    } catch (Exception e) {
                        textview_delivery_price.setText("" + SuperLifeSecretPreferences.getInstance().getString("book_currency") + " " + 0.00);
                    }

                    double totalCost = Double.parseDouble(total_amont) + 0.00;
                    try {
                        textview_total_price.setText("" + SuperLifeSecretPreferences.getInstance().getString("book_currency") + " " + String.format(Locale.getDefault(), "%,.2f", totalCost));
                    } catch (Exception e) {
                        textview_total_price.setText("" + SuperLifeSecretPreferences.getInstance().getString("book_currency") + " " + totalCost);
                    }
                }
                CommonUtils.startActivity(ForthBookActivity.this, FifthBookActivity.class);
            } else {
                resetTotal();
            }
        } else {
            resetTotal();
        }
    }

    private void resetTotal() {
        try {
            textview_delivery_price.setText("" + SuperLifeSecretPreferences.getInstance().getString("book_currency") + " " + String.format(Locale.getDefault(), "%,.2f", 0.0));
        } catch (Exception e) {
            textview_delivery_price.setText("" + SuperLifeSecretPreferences.getInstance().getString("book_currency") + " " + 0.00);
        }
        double totalCost = Double.parseDouble(total_amont) + 0.00;
        try {
            textview_total_price.setText("" + SuperLifeSecretPreferences.getInstance().getString("book_currency") + " " + String.format(Locale.getDefault(), "%,.2f", totalCost));
        } catch (Exception e) {
            textview_total_price.setText("" + SuperLifeSecretPreferences.getInstance().getString("book_currency") + " " + totalCost);
        }
        SuperLifeSecretPreferences.getInstance().putString("delivery_charges_destination", "0");
    }

    public void showAlert() {
        CommonUtils.showAlert(ForthBookActivity.this, conversionData.getWrong_mobile_email()
                , conversionData.getOk(), null, new AlertDialog.OnClickListner() {
                    @Override
                    public void onPositiveClick() {
                    }

                    @Override
                    public void onNegativeClick() {

                    }
                });
    }

    private void getState() {
        HashMap<String, String> body = new HashMap<>();
        body.put("country_id", countryId);
        forthBookPresneter.getStates(body);
    }

    private void getCity() {
        HashMap<String, String> map = new HashMap();
        map.put("state_id", stateId);
        forthBookPresneter.getCities(map);
    }

    public class StoreListener {

        public void onSelectOldAddess(Stores stores, String address) {
            SuperLifeSecretPreferences.getInstance().putString("status_old_store_address", "1");
            Log.e("stores.getLast_order", stores.getId());
            SuperLifeSecretPreferences.getInstance().putString("book_designated_type", "1");
            SuperLifeSecretPreferences.getInstance().putString("book_old_address_id", stores.getId());
            SuperLifeSecretPreferences.getInstance().putString("final_dialog_address", address);
            SuperLifeSecretPreferences.getInstance().putString("final_dialog_email", stores.getEmail());
            SuperLifeSecretPreferences.getInstance().putString("final_dialog_phone", stores.getMobile());
            recyclerview_address.setVisibility(View.GONE);
            tv_ordertype.setText(address);
        }

        public void onCall(String number) {
            no = number;
            int permissionCheck = ContextCompat.checkSelfPermission(ForthBookActivity.this, Manifest.permission.CALL_PHONE);
            if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(ForthBookActivity.this, new String[]{Manifest.permission.CALL_PHONE}, 1);
            } else {
                startActivity(new Intent(Intent.ACTION_CALL).setData(Uri.parse("tel:" + no)));
            }
        }
    }

    @Override
    protected void initializePresenter() {
        forthBookPresneter = new ForthBookPresneter(this);
        forthBookPresneter.setView(this);
    }

    @Override
    public void getStores(StoreBean categoryResponseModel) {
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + userData.getApi_token());
        forthBookPresneter.getOldAddresses(headers);
        // storyList.clear();
        //storyList.addAll(categoryResponseModel.getStores());
        //storesAapter.notifyDataSetChanged();
    }

    @Override
    public void getOldAddress(AddressBean categoryResponseModel) {
        oldAddressList.clear();
        oldAddressList.addAll(categoryResponseModel.getData());
        addressAapter.notifyDataSetChanged();
        if (oldAddressList.size() == 0) {
            textview_noaddressfound.setVisibility(View.VISIBLE);
        } else {
            textview_noaddressfound.setVisibility(View.GONE);
        }
    }

    @Override
    public void setStateData(List<CountryResponseData> data) {
        countryStatePicker = new CountryStatePicker(this, new CountryStatePicker.PickerListner() {
            @Override
            public void onPick(CountryResponseData country) {
                edittext_state.setText(country.getName());
                if (!country.getId().equalsIgnoreCase(city_id)) {
                    city_id = "";
                    edittext_city.setText("");
                }
                countryStatePicker.dismiss();
                stateId = country.getId();
                getDeliveryCharges();
            }
        }, data);
        countryStatePicker.show();
    }

    @Override
    public void setCities(List<CountryResponseData> data) {
        countryStatePicker = new CountryStatePicker(this, new CountryStatePicker.PickerListner() {
            @Override
            public void onPick(CountryResponseData country) {
                edittext_city.setText(country.getName());
                city_id = country.getId();
                countryStatePicker.dismiss();
            }
        }, data);
        countryStatePicker.show();
    }

    @Override
    public void setDeliveryCost(String delivery_charges) {
        moveToNext = true;
        try {
            textview_delivery_price.setText("" + SuperLifeSecretPreferences.getInstance().getString("book_currency") + " " + String.format(Locale.getDefault(), "%,.2f", Double.parseDouble(delivery_charges)));
        } catch (Exception e) {
            textview_delivery_price.setText("" + SuperLifeSecretPreferences.getInstance().getString("book_currency") + " " + delivery_charges);
        }

        double totalCost = Double.parseDouble(total_amont) + Double.parseDouble(delivery_charges);
        try {
            textview_total_price.setText("" + SuperLifeSecretPreferences.getInstance().getString("book_currency") + " " + String.format(Locale.getDefault(), "%,.2f", totalCost));
        } catch (Exception e) {
            textview_total_price.setText("" + SuperLifeSecretPreferences.getInstance().getString("book_currency") + " " + totalCost);
        }
      /*  String contact_number = edittext_contact_number.getText().toString();
        String email = edittext_emailid.getText().toString();
        String fullname = edittext_fullname.getText().toString();
        String other_person_name = edittext_other_person_name.getText().toString();
        String other_person_contactno = edittext_otehr_person_mobile.getText().toString();*/
        SuperLifeSecretPreferences.getInstance().putString("delivery_charges_destination", "" + delivery_charges);

      /*  SuperLifeSecretPreferences.getInstance().putString("book_address", edittext_deliveryaddress.getText().toString());
        SuperLifeSecretPreferences.getInstance().putString("book_full_name", fullname);
        SuperLifeSecretPreferences.getInstance().putString("book_mobile", contact_number);
        SuperLifeSecretPreferences.getInstance().putString("book_email", email);
        SuperLifeSecretPreferences.getInstance().putString("book_state", stateId);
        SuperLifeSecretPreferences.getInstance().putString("book_city", city_id);
        SuperLifeSecretPreferences.getInstance().putString("book_pin_code", edittext_postcode.getText().toString());
        SuperLifeSecretPreferences.getInstance().putString("book_stake_page_no", "4");
        SuperLifeSecretPreferences.getInstance().putString("other_person_name", other_person_name);
        SuperLifeSecretPreferences.getInstance().putString("other_person_contact", other_person_contactno);

        SuperLifeSecretPreferences.getInstance().putString("city_name", edittext_city.getText().toString());
        SuperLifeSecretPreferences.getInstance().putString("state_name", edittext_state.getText().toString());
        CommonUtils.startActivity(ForthBookActivity.this, FifthBookActivity.class);*/
    }

    @Override
    public void showNoDeliveryMessage(String message) {
        moveToNext = false;
        this.message = message;
        CommonUtils.showAlert(this, message, conversionData.getOk(), null, new AlertDialog.OnClickListner() {
            @Override
            public void onPositiveClick() {

            }

            @Override
            public void onNegativeClick() {

            }
        });

    }

    private void setUpConversion() {
        conversionData = SuperLifeSecretCodeApp.getInstance().getConversionData();
        if (conversionData != null) {
            textview_back.setText(conversionData.getBack());
            textview_next.setText(conversionData.getContinuee());
            if (SuperLifeSecretPreferences.getInstance().getString("book_type").equals("1")) {
                textview_print_for_own.setText(conversionData.getBuying_own());
                textview_print_on_behalf.setText(conversionData.getBuying_behalf());
            } else {
                textview_print_for_own.setText(conversionData.getPrinting_own());
                textview_print_on_behalf.setText(conversionData.getPrinting_behalf());
            }
            textview_fullname.setText(conversionData.getName_national_card());
            textview_noaddressfound.setText(conversionData.getNo_address());
            textview_total.setText(conversionData.getTotal());
            textview_state.setText(conversionData.getState());
            textview_city.setText(conversionData.getCity());
            textview_contact_number.setText(conversionData.getContact_number());
            textview_emailid.setText(conversionData.getEmail_id());
            textview_ordertype.setText(conversionData.getOrder_type());
            textview_deliveryaddress.setText(conversionData.getDelivery_address());
            textview_pincode.setText(conversionData.getPostcode());
            tv_ordertype.setText(conversionData.getPlease_select_order_type());
            textview_designated_location.setText(conversionData.getDesignated());
            textview_other_destribution.setText(conversionData.getDistribute());

            edittext_deliveryaddress.setHint(conversionData.getPlease_enter_address());
            edittext_fullname.setHint(conversionData.getEnter_name());
            edittext_contact_number.setHint(conversionData.getEnter_contact_p_num());
            edittext_emailid.setHint(conversionData.getEnter_email());
            edittext_postcode.setHint(conversionData.getEnter_postcode());
            edittext_state.setHint(conversionData.getSelect_state());
            edittext_city.setHint(conversionData.getSelect_city());
            textview_sub_total.setText(conversionData.getSubtotal());
            textview_delivery.setText(conversionData.getDelivery_charges());
            textview_other_person_name.setText(conversionData.getOther_person());
            textview_other_person_mobile.setText(conversionData.getOther_mobile());
            edittext_other_person_name.setHint(conversionData.getEnter_other_person());
            edittext_otehr_person_mobile.setHint(conversionData.getEnter_other_mobile());
            textViewDeliveryAtDest.setText(conversionData.getDelivery_at_destination());
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        SuperLifeSecretPreferences.getInstance().putString("book_stake_page_no", "3");
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

    private void getDeliveryCharges() {
        if (stateId == null || stateId.isEmpty()) {
            return;
        }
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + userData.getApi_token());
        HashMap<String, String> param = new HashMap<>();
        param.put("state_id", stateId);
        param.put("amount", total_amont);
        param.put("qty", String.valueOf(qty));
        forthBookPresneter.getDeliveryCharges(param, headers);
    }
}
