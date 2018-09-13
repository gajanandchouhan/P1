package com.superlifesecretcode.app.ui.book.forth;

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
import com.superlifesecretcode.app.ui.book.five.FifthBookActivity;
import com.superlifesecretcode.app.ui.picker.AlertDialog;
import com.superlifesecretcode.app.ui.picker.CountryStatePicker;
import com.superlifesecretcode.app.util.CommonUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
    ArrayList<Addresss> oldAddressList;
    private UserDetailResponseData userData;
    ImageView back_image;
    TextView textview_total, textview_total_price, textview_next;
    TextView textview_noaddressfound;
    String total_amont;
    int printing_status = 1;
    EditText edittext_fullname, edittext_contact_number, edittext_emailid;
    TextView textview_contact_number, textview_emailid , textview_state , textview_city;
    EditText edittext_deliveryaddress, edittext_postcode, edittext_state, edittext_city;
    String countryId;
    String stateId="", city_id="";
    private CountryStatePicker countryStatePicker;
    private LanguageResponseData conversionData;
    TextView textview_fullname ,textView_title , textview_ordertype , textview_deliveryaddress , textview_pincode;

    @Override
    protected int getContentView() {
        return R.layout.activity_book_forth;
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
        textview_other_destribution = findViewById(R.id.textview_other_destribution);
        linear_address = findViewById(R.id.linear_address);
        add_address_Layout = findViewById(R.id.add_address_Layout);
        textview_back = findViewById(R.id.textview_back);
        recyclerview_address = findViewById(R.id.recyclerview_address);
        back_image = findViewById(R.id.back_image);
        textview_total = findViewById(R.id.textview_total);
        textview_total_price = findViewById(R.id.textview_total_price);
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

        SuperLifeSecretPreferences.getInstance().putString("book_order_for", "1");
        SuperLifeSecretPreferences.getInstance().putString("book_store_id", "0");
        SuperLifeSecretPreferences.getInstance().putString("book_designated_type", "0");
        SuperLifeSecretPreferences.getInstance().putString("status_old_store_address", "0");

        edittext_emailid.setText("" + userData.getEmail());
        edittext_contact_number.setText("" + userData.getMobile());
        textview_total_price.setText("" + SuperLifeSecretPreferences.getInstance().getString("book_currency") + " " + total_amont);

        setUpConversion();
        back_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        edittext_state.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userData.getCountry() != null || !userData.getCountry().equals("")) {
                    countryId = userData.getCountry();
                } else {
                    countryId = SuperLifeSecretPreferences.getInstance().getLocationData().getCountry();
                }
                getState();
            }
        });

        edittext_city.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (stateId != null || !stateId.equals(""))
                    getCity();
                else CommonUtils.showSnakeBar(ForthBookActivity.this, conversionData.getSelect_state());
            }
        });

        textview_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (printing_status == 1) {
                    String contact_number = edittext_contact_number.getText().toString();
                    String email = edittext_emailid.getText().toString();
                    if (add_address_Layout.getVisibility() == View.GONE) {
                        if (edittext_fullname.getText().toString().equals("")) {
                            edittext_fullname.setError(conversionData.getName_national_card());
                            CommonUtils.showSnakeBar(ForthBookActivity.this, conversionData.getName_national_card());
                            return;
                        }
                        if (contact_number.isEmpty()) {
                            edittext_fullname.setError(conversionData.getEnter_contact_p_num());
                            CommonUtils.showSnakeBar(ForthBookActivity.this, conversionData.getEnter_contact_p_num());
                            return;
                        }
                        if (email.isEmpty()) {
                            edittext_emailid.setError(conversionData.getEnter_email());
                            CommonUtils.showSnakeBar(ForthBookActivity.this, conversionData.getEnter_email());
                            return;
                        }
//                        if (CommonUtils.isValidEmail(email)) {
//                            edittext_emailid.setError(conversionData.getEnter_valid_email());
//                            CommonUtils.showSnakeBar(ForthBookActivity.this,  conversionData.getEnter_valid_email());
//                            return;
//                        }
                        if (!contact_number.equals(userData.getMobile()) || !email.equals(userData.getEmail()) ) {
                            showAlert();
                            return;
                        }
                        if (SuperLifeSecretPreferences.getInstance().getString("book_designated_type").equals("0")) {
                            CommonUtils.showSnakeBar(ForthBookActivity.this, conversionData.getPlease_select_order_type());
                            return;
                        }
                        if (SuperLifeSecretPreferences.getInstance().getString("book_designated_type").equals("1")){
                            if (tv_add_address.getVisibility()==View.VISIBLE && recyclerview_address.getVisibility()==View.VISIBLE){
                                CommonUtils.showSnakeBar(ForthBookActivity.this, conversionData.getPlease_enter_address());
                                return;
                            }
                        }
                    } else {
                        if (edittext_fullname.getText().toString().equals("")) {
                            edittext_fullname.setError(conversionData.getName_national_card());
                            CommonUtils.showSnakeBar(ForthBookActivity.this, conversionData.getName_national_card());
                            return;
                        }
                        if (contact_number.isEmpty()) {
                            edittext_fullname.setError(conversionData.getEnter_contact_p_num());
                            CommonUtils.showSnakeBar(ForthBookActivity.this, conversionData.getEnter_contact_p_num());
                            return;
                        }
                        if (email.isEmpty()) {
                            edittext_emailid.setError(conversionData.getEnter_email());
                            CommonUtils.showSnakeBar(ForthBookActivity.this, conversionData.getEnter_email());
                            return;
                        }
//                        if (CommonUtils.isValidEmail(email)) {
//                            edittext_emailid.setError(conversionData.getEnter_valid_email());
//                            CommonUtils.showSnakeBar(ForthBookActivity.this,  conversionData.getEnter_valid_email());
//                            return;
//                        }
                        if (!contact_number.equals(userData.getMobile()) || !email.equals(userData.getEmail()) ) {
                            showAlert();
                            return;
                        }
                        if (SuperLifeSecretPreferences.getInstance().getString("book_designated_type").equals("0")) {
                            CommonUtils.showSnakeBar(ForthBookActivity.this, conversionData.getPlease_select_order_type());
                            return;
                        }
                        if (edittext_deliveryaddress.getText().toString().equals("")) {
                            edittext_deliveryaddress.setError(conversionData.getPlease_enter_address());
                            CommonUtils.showSnakeBar(ForthBookActivity.this, conversionData.getPlease_enter_address());
                            return;
                        }
                        if (edittext_postcode.getText().toString().equals("")) {
                            edittext_postcode.setError(conversionData.getPlease_enter_postcode());
                            CommonUtils.showSnakeBar(ForthBookActivity.this, conversionData.getPlease_enter_postcode());
                            return;
                        }
                        if (edittext_state.getText().toString().equals("")) {
                            CommonUtils.showSnakeBar(ForthBookActivity.this, conversionData.getSelect_state());
                            return;
                        }
                        if (edittext_city.getText().toString().equals("")) {
                            CommonUtils.showSnakeBar(ForthBookActivity.this, conversionData.getSelect_city());
                            return;
                        }
                    }
                    SuperLifeSecretPreferences.getInstance().putString("book_address", edittext_deliveryaddress.getText().toString());
                    SuperLifeSecretPreferences.getInstance().putString("book_full_name", edittext_fullname.getText().toString());
                    SuperLifeSecretPreferences.getInstance().putString("book_mobile", edittext_contact_number.getText().toString());
                    SuperLifeSecretPreferences.getInstance().putString("book_email", edittext_emailid.getText().toString());
                    SuperLifeSecretPreferences.getInstance().putString("book_pin_code", edittext_postcode.getText().toString());
                    SuperLifeSecretPreferences.getInstance().putString("book_state", stateId);
                    SuperLifeSecretPreferences.getInstance().putString("book_city", city_id);
                    SuperLifeSecretPreferences.getInstance().putString("book_stake_page_no", "4");
                    CommonUtils.startActivity(ForthBookActivity.this, FifthBookActivity.class);
                } else if (printing_status == 2) {
                    if (add_address_Layout.getVisibility() == View.GONE) {
                        if (edittext_fullname.getText().toString().equals("")) {
                            edittext_fullname.setError(conversionData.getName_national_card());
                            CommonUtils.showSnakeBar(ForthBookActivity.this, conversionData.getName_national_card());
                            return;
                        }
                        if (edittext_contact_number.getText().toString().equals("")) {
                            edittext_contact_number.setError(conversionData.getEnter_contact_p_num());
                            CommonUtils.showSnakeBar(ForthBookActivity.this, conversionData.getEnter_contact_p_num());
                            return;
                        }
                        if (edittext_emailid.getText().toString().equals("")) {
                            edittext_emailid.setError(conversionData.getEnter_email());
                            CommonUtils.showSnakeBar(ForthBookActivity.this, conversionData.getEnter_email());
                            return;
                        }
//                        if (CommonUtils.isValidEmail(edittext_emailid.getText().toString())) {
//                            edittext_emailid.setError(conversionData.getEnter_valid_email());
//                            CommonUtils.showSnakeBar(ForthBookActivity.this,  conversionData.getEnter_valid_email());
//                            return;
//                        }
                        if (SuperLifeSecretPreferences.getInstance().getString("book_designated_type").equals("0")) {
                            CommonUtils.showSnakeBar(ForthBookActivity.this, conversionData.getPlease_select_order_type());
                            return;
                        }
                        if (SuperLifeSecretPreferences.getInstance().getString("book_designated_type").equals("1")){
                            if (tv_add_address.getVisibility()==View.VISIBLE && recyclerview_address.getVisibility()==View.VISIBLE){
                                CommonUtils.showSnakeBar(ForthBookActivity.this, conversionData.getPlease_enter_address());
                                return;
                            }
                        }
                    } else {
                        if (edittext_fullname.getText().toString().equals("")) {
                            edittext_fullname.setError(conversionData.getName_national_card());
                            CommonUtils.showSnakeBar(ForthBookActivity.this, conversionData.getName_national_card());
                            return;
                        }
                        if (edittext_contact_number.getText().toString().equals("")) {
                            edittext_fullname.setError(conversionData.getEnter_contact_p_num());
                            CommonUtils.showSnakeBar(ForthBookActivity.this, conversionData.getEnter_contact_p_num());
                            return;
                        }
                        if (edittext_emailid.getText().toString().equals("")) {
                            edittext_emailid.setError(conversionData.getEnter_email());
                            CommonUtils.showSnakeBar(ForthBookActivity.this, conversionData.getEnter_email());
                            return;
                        }
//                        if (CommonUtils.isValidEmail(edittext_emailid.getText().toString())) {
//                            edittext_emailid.setError(conversionData.getEnter_valid_email());
//                            CommonUtils.showSnakeBar(ForthBookActivity.this,  conversionData.getEnter_valid_email());
//                            return;
//                        }
                        if (SuperLifeSecretPreferences.getInstance().getString("book_designated_type").equals("0")) {
                            CommonUtils.showSnakeBar(ForthBookActivity.this, conversionData.getPlease_select_order_type());
                            return;
                        }

                        if (edittext_deliveryaddress.getText().toString().equals("")) {
                            CommonUtils.showSnakeBar(ForthBookActivity.this, conversionData.getPlease_enter_address());
                            return;
                        }
                        if (edittext_postcode.getText().toString().equals("")) {
                            edittext_postcode.setError(conversionData.getPlease_enter_postcode());
                            CommonUtils.showSnakeBar(ForthBookActivity.this, conversionData.getPlease_enter_postcode());
                            return;
                        }
                        if (edittext_state.getText().toString().equals("")) {
                            CommonUtils.showSnakeBar(ForthBookActivity.this, conversionData.getSelect_state());
                            return;
                        }
                        if (edittext_city.getText().toString().equals("")) {
                            CommonUtils.showSnakeBar(ForthBookActivity.this,  conversionData.getSelect_city());
                            return;
                        }
                    }

                    SuperLifeSecretPreferences.getInstance().putString("book_address", edittext_deliveryaddress.getText().toString());
                    SuperLifeSecretPreferences.getInstance().putString("book_full_name", edittext_fullname.getText().toString());
                    SuperLifeSecretPreferences.getInstance().putString("book_mobile", edittext_contact_number.getText().toString());
                    SuperLifeSecretPreferences.getInstance().putString("book_email", edittext_emailid.getText().toString());
                    SuperLifeSecretPreferences.getInstance().putString("book_state", stateId);
                    SuperLifeSecretPreferences.getInstance().putString("book_city", city_id);
                    SuperLifeSecretPreferences.getInstance().putString("book_pin_code", edittext_postcode.getText().toString());
                    SuperLifeSecretPreferences.getInstance().putString("book_stake_page_no", "4");

                    CommonUtils.startActivity(ForthBookActivity.this, FifthBookActivity.class);
                }
            }
        });
        SuperLifeSecretPreferences.getInstance().putString("order_for_text", ""+conversionData.getPrinting_own());
        linear_print_for_own.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SuperLifeSecretPreferences.getInstance().putString("order_for_text", ""+conversionData.getPrinting_own());
                SuperLifeSecretPreferences.getInstance().putString("book_order_for", "1");
                SuperLifeSecretPreferences.getInstance().putString("status_old_store_address", "0");
                SuperLifeSecretPreferences.getInstance().putString("book_designated_type", "0");
                printing_status = 1;
                textview_print_for_own.setTextColor(getResources().getColor(R.color.color_black));
                textview_print_on_behalf.setTextColor(getResources().getColor(R.color.gray_color));
                view_printforown.setBackgroundColor(getResources().getColor(R.color.color_black));
                view_printfbehalf.setBackgroundColor(getResources().getColor(R.color.transparent));
                linear_address.setVisibility(View.GONE);
                linear_ordertype.setVisibility(View.GONE);
                tv_ordertype.setText(conversionData.getOrder_type());
                add_address_Layout.setVisibility(View.GONE);
                textview_noaddressfound.setVisibility(View.GONE);
            }
        });

        linear_print_onBehalf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SuperLifeSecretPreferences.getInstance().putString("order_for_text", ""+conversionData.getPrinting_behalf());
                SuperLifeSecretPreferences.getInstance().putString("book_order_for", "2");
                SuperLifeSecretPreferences.getInstance().putString("status_old_store_address", "0");
                SuperLifeSecretPreferences.getInstance().putString("book_designated_type", "0");
                printing_status = 2;
                textview_print_for_own.setTextColor(getResources().getColor(R.color.gray_color));
                textview_print_on_behalf.setTextColor(getResources().getColor(R.color.color_black));
                view_printforown.setBackgroundColor(getResources().getColor(R.color.transparent));
                view_printfbehalf.setBackgroundColor(getResources().getColor(R.color.color_black));
                recyclerview_address.setVisibility(View.GONE);
                add_address_Layout.setVisibility(View.GONE);
                linear_address.setVisibility(View.GONE);
                linear_ordertype.setVisibility(View.GONE);
                tv_ordertype.setText(conversionData.getOrder_type());
                textview_noaddressfound.setVisibility(View.GONE);
            }
        });

        tv_ordertype.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (linear_ordertype.getVisibility() == View.GONE) {
                    linear_ordertype.setVisibility(View.VISIBLE);
                } else
                    linear_ordertype.setVisibility(View.GONE);
                recyclerview_address.setVisibility(View.GONE);
                textview_noaddressfound.setVisibility(View.GONE);
            }
        });

        textview_designated_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SuperLifeSecretPreferences.getInstance().putString("delivery_type_text", ""+conversionData.getDesignated());
                SuperLifeSecretPreferences.getInstance().putString("status_old_store_address", "0");
                SuperLifeSecretPreferences.getInstance().putString("book_designated_type", "1");
                tv_ordertype.setText(conversionData.getDesignated());
                linear_ordertype.setVisibility(View.GONE);
                linear_address.setVisibility(View.VISIBLE);
                add_address_Layout.setVisibility(View.GONE);
                recyclerview_address.setVisibility(View.VISIBLE);
                if (oldAddressList.size() == 0) {
                    textview_noaddressfound.setVisibility(View.VISIBLE);
                } else {
                    textview_noaddressfound.setVisibility(View.GONE);
                }
            }
        });

        textview_other_destribution.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SuperLifeSecretPreferences.getInstance().putString("delivery_type_text", ""+conversionData.getDistribute());
                SuperLifeSecretPreferences.getInstance().putString("status_old_store_address", "2");
                SuperLifeSecretPreferences.getInstance().putString("book_designated_type", "2");
                tv_ordertype.setText(conversionData.getDistribute());
                linear_ordertype.setVisibility(View.GONE);
                linear_address.setVisibility(View.GONE);
                add_address_Layout.setVisibility(View.GONE);
                textview_noaddressfound.setVisibility(View.GONE);
            }
        });

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



        String stack = SuperLifeSecretPreferences.getInstance().getString("book_stake_page_no");
        if (stack != null) {
            if (Integer.parseInt(stack) >= 4) {
                CommonUtils.startActivity(ForthBookActivity.this, FifthBookActivity.class);
            }
        }
    }

    public void showAlert(){
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

        public void onSelectOldAddess(Addresss stores, String address) {
            SuperLifeSecretPreferences.getInstance().putString("status_old_store_address", "1");
            SuperLifeSecretPreferences.getInstance().putString("book_old_address_id", stores.getLast_order_id());
            recyclerview_address.setVisibility(View.GONE);
            tv_ordertype.setText(address);
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
    }

    @Override
    public void setStateData(List<CountryResponseData> data) {
        countryStatePicker = new CountryStatePicker(this, new CountryStatePicker.PickerListner() {
            @Override
            public void onPick(CountryResponseData country) {
                edittext_state.setText(country.getName());
//                if (!country.getId().equalsIgnoreCase(city)) {
//                    city = "";
//                    textViewCity.setText("");
//                }
                countryStatePicker.dismiss();
                stateId = country.getId();
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

    private void setUpConversion() {
        conversionData = SuperLifeSecretCodeApp.getInstance().getConversionData();
        if (conversionData != null) {
            textview_back.setText(conversionData.getBack());
            textview_next.setText(conversionData.getContinuee());
            textview_print_for_own.setText(conversionData.getPrinting_own());
            textview_print_on_behalf.setText(conversionData.getPrinting_behalf());
            textview_fullname.setText(conversionData.getName_national_card());
            textview_print_on_behalf.setText(conversionData.getPrinting_behalf());
            textview_noaddressfound.setText(conversionData.getNo_address());
            textview_total.setText(conversionData.getTotal());
            textview_state.setText(conversionData.getState());
            textview_city.setText(conversionData.getCity());
            textview_contact_number.setText(conversionData.getContact_number());
            textview_emailid.setText(conversionData.getEmail_id());
            textview_ordertype.setText(conversionData.getOrder_type());
            //tv_add_address.setText(conversionData.getAdd_address());
            textview_deliveryaddress.setText(conversionData.getDelivery_address());
            textview_pincode.setText(conversionData.getPostcode());
            tv_ordertype.setText(conversionData.getOrder_type());
            textview_designated_location.setText(conversionData.getDesignated());
            textview_other_destribution.setText(conversionData.getDistribute());

            edittext_deliveryaddress.setHint(conversionData.getPlease_enter_address());
            edittext_fullname.setHint(conversionData.getEnter_name());
            edittext_contact_number.setHint(conversionData.getEnter_contact_p_num());
            edittext_emailid.setHint(conversionData.getEnter_email());
            edittext_postcode.setHint(conversionData.getEnter_postcode());
            edittext_state.setHint(conversionData.getSelect_state());
            edittext_city.setHint(conversionData.getSelect_city());
        }
    }
}
