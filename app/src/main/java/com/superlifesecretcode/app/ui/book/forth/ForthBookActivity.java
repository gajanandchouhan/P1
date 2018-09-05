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
import com.superlifesecretcode.app.data.model.country.CountryResponseData;
import com.superlifesecretcode.app.data.model.userdetails.UserDetailResponseData;
import com.superlifesecretcode.app.data.persistance.SuperLifeSecretPreferences;
import com.superlifesecretcode.app.ui.base.BaseActivity;
import com.superlifesecretcode.app.ui.book.five.FifthBookActivity;
import com.superlifesecretcode.app.ui.book.third.ThirsBookActivity;
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
    LinearLayout linear_contact_id, linear_ordertype;
    TextView tv_add_address;
    TextView tv_ordertype;
    TextView textview_designated_location, textview_other_destribution,textview_back;
    LinearLayout linear_address , add_address_Layout;
    RecyclerView recyclerview_address , recyclerview_storelist;
    StoresAapter storesAapter;
    AddressAapter addressAapter;
    ArrayList<Stores> storyList;
    ArrayList<Addresss> oldAddressList;
    private UserDetailResponseData userData;
    ImageView back_image;
    TextView textview_total , textview_total_price , textview_next;
    TextView textview_noaddressfound;
    String total_amont;
    int printing_status = 1;
    EditText edittext_fullname , edittext_contact_number , edittext_emailid;
    TextView textview_contact_number , textview_emailid;
    EditText edittext_deliveryaddress,edittext_postcode,edittext_state,edittext_city;
    String countryId;
    String stateId,city_id;
    private CountryStatePicker countryStatePicker;
    int status_old_store_address=0;

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
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + userData.getApi_token());
        forthBookPresneter.getStores(headers);

        linear_print_for_own = findViewById(R.id.linear_print_for_own);
        linear_print_onBehalf = findViewById(R.id.linear_print_onBehalf);
        textview_print_for_own = findViewById(R.id.textview_print_for_own);
        textview_print_on_behalf = findViewById(R.id.textview_print_on_behalf);
        view_printforown = findViewById(R.id.view_printforown);
        view_printfbehalf = findViewById(R.id.view_printfbehalf);
        linear_contact_id = findViewById(R.id.linear_contact_id);
        tv_add_address = findViewById(R.id.tv_add_address);
        tv_ordertype = findViewById(R.id.tv_ordertype);
        linear_ordertype = findViewById(R.id.linear_ordertype);
        textview_designated_location = findViewById(R.id.textview_designated_location);
        textview_other_destribution = findViewById(R.id.textview_other_destribution);
        linear_address = findViewById(R.id.linear_address);
        add_address_Layout = findViewById(R.id.add_address_Layout);
        textview_back = findViewById(R.id.textview_back);
        recyclerview_address = findViewById(R.id.recyclerview_address);
        recyclerview_storelist = findViewById(R.id.recyclerview_storelist);
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
        SuperLifeSecretPreferences.getInstance().putString("book_order_for","1");
        SuperLifeSecretPreferences.getInstance().putString("status_old_store_address","0");
        textview_total_price.setText(total_amont);

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
                if (stateId!=null || !stateId.equals(""))
                getCity();
                else CommonUtils.showSnakeBar(ForthBookActivity.this , "Please select state first!");
            }
        });

        textview_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (printing_status==1){
                    if (add_address_Layout.getVisibility()==View.GONE){
                        if (edittext_fullname.getText().toString().equals("")){
                            CommonUtils.showSnakeBar(ForthBookActivity.this,"Please enter full name!");
                            return;
                        }
                        if (tv_ordertype.getText().toString().equals("Select Order Type")){
                            CommonUtils.showSnakeBar(ForthBookActivity.this,"Please enter full name!");
                            return;
                        }
                    }else {
                        if (edittext_fullname.getText().toString().equals("")){
                            CommonUtils.showSnakeBar(ForthBookActivity.this,"Please enter full name!");
                            return;
                        }
                        if (tv_ordertype.getText().toString().equals("Select Order Type")){
                            CommonUtils.showSnakeBar(ForthBookActivity.this,"Please select order type!");
                            return;
                        }
                        if (edittext_deliveryaddress.getText().toString().equals("")){
                            CommonUtils.showSnakeBar(ForthBookActivity.this,"Please enter delivery address!");
                            return;
                        }
                        if (edittext_postcode.getText().toString().equals("")){
                            CommonUtils.showSnakeBar(ForthBookActivity.this,"Please enter postcode!");
                            return;
                        }
                        if (edittext_state.getText().toString().equals("")){
                            CommonUtils.showSnakeBar(ForthBookActivity.this,"Please enter state!");
                            return;
                        }
                        if (edittext_city.getText().toString().equals("")){
                            CommonUtils.showSnakeBar(ForthBookActivity.this,"Please enter city!");
                            return;
                        }
                    }
                    Log.e("edittext_delivaddress",""+edittext_deliveryaddress.getText().toString());
                    SuperLifeSecretPreferences.getInstance().putString("book_address",edittext_deliveryaddress.getText().toString());
                    Log.e("edittext_fullname",""+edittext_fullname.getText().toString());
                    SuperLifeSecretPreferences.getInstance().putString("book_full_name",edittext_fullname.getText().toString());
                    Log.e("book_mobile",""+edittext_contact_number.getText().toString());
                    SuperLifeSecretPreferences.getInstance().putString("book_mobile",edittext_contact_number.getText().toString());
                    Log.e("book_email",""+edittext_emailid.getText().toString());
                    SuperLifeSecretPreferences.getInstance().putString("book_email",edittext_emailid.getText().toString());
                    Log.e("stateId",""+stateId);
                    SuperLifeSecretPreferences.getInstance().putString("book_state",stateId);
                    Log.e("book_city",""+city_id);
                    SuperLifeSecretPreferences.getInstance().putString("book_city",city_id);
                    SuperLifeSecretPreferences.getInstance().putString("book_stake_page_no","4");
                    CommonUtils.startActivity(ForthBookActivity.this, FifthBookActivity.class);
                }else  if (printing_status==2) {
                    if (add_address_Layout.getVisibility()==View.GONE){
                        if (edittext_fullname.getText().toString().equals("")){
                            CommonUtils.showSnakeBar(ForthBookActivity.this,"Please enter full name!");
                            return;
                        }
                        if (edittext_contact_number.getText().toString().equals("")){
                            CommonUtils.showSnakeBar(ForthBookActivity.this,"Please enter contact number!");
                            return;
                        }
                        if (edittext_emailid.getText().toString().equals("")){
                            CommonUtils.showSnakeBar(ForthBookActivity.this,"Please enter email id!");
                            return;
                        }
                        if (tv_ordertype.getText().toString().equals("Select Order Type")){
                            CommonUtils.showSnakeBar(ForthBookActivity.this,"Please enter full name!");
                            return;
                        }
                    }else {
                        if (edittext_fullname.getText().toString().equals("")){
                            CommonUtils.showSnakeBar(ForthBookActivity.this,"Please enter full name!");
                            return;
                        }
                        if (edittext_contact_number.getText().toString().equals("")){
                            CommonUtils.showSnakeBar(ForthBookActivity.this,"Please enter contact number!");
                            return;
                        }
                        if (edittext_emailid.getText().toString().equals("")){
                            CommonUtils.showSnakeBar(ForthBookActivity.this,"Please enter email id!");
                            return;
                        }
                        if (tv_ordertype.getText().toString().equals("Select Order Type")){
                            CommonUtils.showSnakeBar(ForthBookActivity.this,"Please enter full name!");
                            return;
                        }
                        if (edittext_deliveryaddress.getText().toString().equals("")){
                            CommonUtils.showSnakeBar(ForthBookActivity.this,"Please enter delivery address!");
                            return;
                        }
                        if (edittext_postcode.getText().toString().equals("")){
                            CommonUtils.showSnakeBar(ForthBookActivity.this,"Please enter postcode!");
                            return;
                        }
                        if (edittext_state.getText().toString().equals("")){
                            CommonUtils.showSnakeBar(ForthBookActivity.this,"Please enter state!");
                            return;
                        }
                        if (edittext_city.getText().toString().equals("")){
                            CommonUtils.showSnakeBar(ForthBookActivity.this,"Please enter city!");
                            return;
                        }
                    }

                    Log.e("edittext_delivaddress",""+edittext_deliveryaddress.getText().toString());
                    SuperLifeSecretPreferences.getInstance().putString("book_address",edittext_deliveryaddress.getText().toString());
                    Log.e("edittext_fullname",""+edittext_fullname.getText().toString());
                    SuperLifeSecretPreferences.getInstance().putString("book_full_name",edittext_fullname.getText().toString());
                    Log.e("book_mobile",""+edittext_contact_number.getText().toString());
                    SuperLifeSecretPreferences.getInstance().putString("book_mobile",edittext_contact_number.getText().toString());
                    Log.e("book_email",""+edittext_emailid.getText().toString());
                    SuperLifeSecretPreferences.getInstance().putString("book_email",edittext_emailid.getText().toString());
                    Log.e("stateId",""+stateId);
                    SuperLifeSecretPreferences.getInstance().putString("book_state",stateId);
                    Log.e("book_city",""+city_id);
                    SuperLifeSecretPreferences.getInstance().putString("book_city",city_id);
                    SuperLifeSecretPreferences.getInstance().putString("book_stake_page_no","4");
                    CommonUtils.startActivity(ForthBookActivity.this, FifthBookActivity.class);
                }
            }
        });

        linear_print_for_own.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SuperLifeSecretPreferences.getInstance().putString("book_order_for","1");
                printing_status = 1;
                textview_print_for_own.setTextColor(getResources().getColor(R.color.colorPrimary));
                textview_print_on_behalf.setTextColor(getResources().getColor(R.color.colorBlack));
                view_printforown.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                view_printfbehalf.setBackgroundColor(getResources().getColor(R.color.transparent));
                linear_contact_id.setVisibility(View.GONE);
                linear_address.setVisibility(View.GONE);
                linear_ordertype.setVisibility(View.GONE);
                tv_ordertype.setText("Select Order Type");
                add_address_Layout.setVisibility(View.GONE);
                textview_noaddressfound.setVisibility(View.GONE);
            }
        });

        linear_print_onBehalf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SuperLifeSecretPreferences.getInstance().putString("book_order_for","2");
                printing_status = 2;
                textview_print_for_own.setTextColor(getResources().getColor(R.color.colorBlack));
                textview_print_on_behalf.setTextColor(getResources().getColor(R.color.colorPrimary));
                view_printforown.setBackgroundColor(getResources().getColor(R.color.transparent));
                view_printfbehalf.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                linear_contact_id.setVisibility(View.VISIBLE);
                recyclerview_address.setVisibility(View.GONE);
                recyclerview_storelist.setVisibility(View.GONE);
                add_address_Layout.setVisibility(View.GONE);
                linear_address.setVisibility(View.GONE);
                linear_ordertype.setVisibility(View.GONE);
                tv_ordertype.setText("Select Order Type");
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
                recyclerview_storelist.setVisibility(View.GONE);
                textview_noaddressfound.setVisibility(View.GONE);
            }
        });

        textview_designated_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SuperLifeSecretPreferences.getInstance().putString("book_designated_type","1");
                tv_ordertype.setText("Books at a Designated Location");
                linear_ordertype.setVisibility(View.GONE);
                linear_address.setVisibility(View.VISIBLE);
                add_address_Layout.setVisibility(View.GONE);
//                textview_noaddressfound.setVisibility(View.GONE);
                if (oldAddressList.size()==0){
                    textview_noaddressfound.setVisibility(View.VISIBLE);
                }else {
                    textview_noaddressfound.setVisibility(View.GONE);
                }
            }
        });

        textview_other_destribution.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SuperLifeSecretPreferences.getInstance().putString("book_designated_type","2");
                tv_ordertype.setText("Let Others to Distribute the Books");
                linear_ordertype.setVisibility(View.GONE);
                linear_address.setVisibility(View.GONE);
                add_address_Layout.setVisibility(View.GONE);
                recyclerview_storelist.setVisibility(View.VISIBLE);
                if (storyList.size()==0){
                    textview_noaddressfound.setVisibility(View.VISIBLE);
                }else {
                    textview_noaddressfound.setVisibility(View.GONE);
                }
                // get the list of ADdres and time
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

//        tv_saved_address.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (oldAddressList.size()==0){
//                    textview_noaddressfound.setVisibility(View.VISIBLE);
//                }else {
//                    textview_noaddressfound.setVisibility(View.GONE);
//                }
//                recyclerview_address.setVisibility(View.VISIBLE);
//                linear_address.setVisibility(View.GONE);
//            }
//        });

        recyclerview_address.setLayoutManager(new LinearLayoutManager(this));
        addressAapter = new AddressAapter(oldAddressList, this,new StoreListener());
        recyclerview_address.setAdapter(storesAapter);

        recyclerview_storelist.setLayoutManager(new LinearLayoutManager(this));
        storesAapter = new StoresAapter(storyList, this,new StoreListener());
        recyclerview_storelist.setAdapter(storesAapter);

        String stack = SuperLifeSecretPreferences.getInstance().getString("book_stake_page_no");
        if (stack!=null){
            if (Integer.parseInt(stack)>=4){
                CommonUtils.startActivity(ForthBookActivity.this,FifthBookActivity.class);
            }
        }
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

    public class StoreListener{

        public void onSelectStoreAddess(Stores stores, String address){
            status_old_store_address = 1;
            SuperLifeSecretPreferences.getInstance().putString("status_old_store_address","1");
            SuperLifeSecretPreferences.getInstance().putString("book_old_address_id",stores.getId());
            recyclerview_storelist.setVisibility(View.GONE);
            tv_ordertype.setText(address);
        }
        public void onSelectOldAddess(Addresss stores, String address){
            SuperLifeSecretPreferences.getInstance().putString("status_old_store_address","2");
            SuperLifeSecretPreferences.getInstance().putString("book_store_id",stores.getLast_order_id());
            status_old_store_address = 2;
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
        storyList.clear();
        storyList.addAll(categoryResponseModel.getStores());
        storesAapter.notifyDataSetChanged();
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
}
