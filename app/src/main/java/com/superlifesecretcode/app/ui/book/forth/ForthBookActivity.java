package com.superlifesecretcode.app.ui.book.forth;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.superlifesecretcode.app.R;
import com.superlifesecretcode.app.ui.base.BaseActivity;

public class ForthBookActivity extends BaseActivity implements ForthBookView {

    LinearLayout linear_printofbehalf, linear_printfordistribution;
    TextView textview_print_for_own, textview_print_on_behalf;
    View view_printforown, view_printfbehalf;
    ForthBookPresneter forthBookPresneter;
    LinearLayout linear_contact_id, linear_ordertype;
    TextView tv_add_address, tv_saved_address;
    TextView tv_ordertype;
    TextView textview_designated_location, textview_other_destribution,textview_back;
    LinearLayout linear_address , add_address_Layout;

    @Override
    protected int getContentView() {
        return R.layout.activity_book_forth;
    }

    @Override
    protected void initializeView() {

        linear_printofbehalf = findViewById(R.id.linear_printofbehalf);
        linear_printfordistribution = findViewById(R.id.linear_printfordistribution);
        textview_print_for_own = findViewById(R.id.textview_print_for_own);
        textview_print_on_behalf = findViewById(R.id.textview_print_on_behalf);
        view_printforown = findViewById(R.id.view_printforown);
        view_printfbehalf = findViewById(R.id.view_printfbehalf);
        linear_contact_id = findViewById(R.id.linear_contact_id);
        tv_add_address = findViewById(R.id.tv_add_address);
        tv_saved_address = findViewById(R.id.tv_saved_address);
        tv_ordertype = findViewById(R.id.tv_ordertype);
        linear_ordertype = findViewById(R.id.linear_ordertype);
        textview_designated_location = findViewById(R.id.textview_designated_location);
        textview_other_destribution = findViewById(R.id.textview_other_destribution);
        linear_address = findViewById(R.id.linear_address);
        add_address_Layout = findViewById(R.id.add_address_Layout);
        textview_back = findViewById(R.id.textview_back);

        linear_printofbehalf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textview_print_for_own.setTextColor(getResources().getColor(R.color.colorPrimary));
                textview_print_on_behalf.setTextColor(getResources().getColor(R.color.colorBlack));
                view_printforown.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                view_printfbehalf.setBackgroundColor(getResources().getColor(R.color.transparent));
                linear_contact_id.setVisibility(View.GONE);
            }
        });

        linear_printfordistribution.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textview_print_for_own.setTextColor(getResources().getColor(R.color.colorBlack));
                textview_print_on_behalf.setTextColor(getResources().getColor(R.color.colorPrimary));
                view_printforown.setBackgroundColor(getResources().getColor(R.color.transparent));
                view_printfbehalf.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                linear_contact_id.setVisibility(View.VISIBLE);
                //
            }
        });

        tv_ordertype.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (linear_ordertype.getVisibility() == View.GONE) {
                    linear_ordertype.setVisibility(View.VISIBLE);
                } else
                    linear_ordertype.setVisibility(View.GONE);
                //add_address_Layout.setVisibility(View.GONE);
            }
        });

        textview_designated_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_ordertype.setText("Books at a Designated Location");
                linear_ordertype.setVisibility(View.GONE);
                linear_address.setVisibility(View.VISIBLE);
                add_address_Layout.setVisibility(View.GONE);
            }
        });

        textview_other_destribution.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_ordertype.setText("Let Others to Distribute the Books");
                linear_ordertype.setVisibility(View.GONE);
                linear_address.setVisibility(View.GONE);
                add_address_Layout.setVisibility(View.GONE);
                // get the list of ADdres and time
            }
        });

        tv_add_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linear_address.setVisibility(View.GONE);
                add_address_Layout.setVisibility(View.VISIBLE);
            }
        });
        textview_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void initializePresenter() {
        forthBookPresneter = new ForthBookPresneter(this);
        forthBookPresneter.setView(this);
    }

    @Override
    public void getStores(StoreBean categoryResponseModel) {

    }
}
