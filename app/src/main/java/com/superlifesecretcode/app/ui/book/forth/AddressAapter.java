package com.superlifesecretcode.app.ui.book.forth;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.superlifesecretcode.app.R;
import com.superlifesecretcode.app.data.model.language.LanguageResponseData;
import com.superlifesecretcode.app.data.persistance.SuperLifeSecretPreferences;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Divya on 26-02-2018.
 */

public class AddressAapter extends RecyclerView.Adapter<AddressAapter.ItemViewHolder> {
    private final List<Stores> list;
    private Context mContext;
    ForthBookActivity.StoreListener bookSelectedListener;
    LanguageResponseData languageResponseData;


    public AddressAapter(ArrayList<Stores> list, ForthBookActivity mContext, ForthBookActivity.StoreListener bookSelectedListener) {
        this.list = list;
        this.mContext = mContext;
        this.bookSelectedListener = bookSelectedListener;

    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_old_address, parent, false));
    }

    @Override
    public void onBindViewHolder(final ItemViewHolder holder, final int position) {
        languageResponseData = SuperLifeSecretPreferences.getInstance().getConversionData();
        holder.textview_address.setText(list.get(position).getStreet_address() + ", " + list.get(position).getCity() + ", " + list.get(position).getState());

        holder.textview_email.setText(list.get(position).getEmail());
        holder.textview_mobile.setText(list.get(position).getMobile());
        holder.textview_addeess_header.setText(languageResponseData.getAddress());
        holder.textview_email_header.setText(languageResponseData.getEmail());
        holder.textview_mobile_header.setText(languageResponseData.getContact_number());

        holder.linear_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String address = list.get(position).getStreet_address() + ", " + list.get(position).getCity() + ", " + list.get(position).getState();
                bookSelectedListener.onSelectOldAddess(list.get(position), address);
            }
        });
        holder.textview_mobile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bookSelectedListener.onCall(list.get(position).getMobile());
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView textview_addeess_header;
        TextView textview_address;
        TextView textview_email_header, textview_email;
        TextView textview_mobile_header, textview_mobile;
        LinearLayout linear_address;

        public ItemViewHolder(View itemView) {
            super(itemView);
            textview_addeess_header = itemView.findViewById(R.id.textview_addeess_header);
            textview_address = itemView.findViewById(R.id.textview_address);
            textview_email_header = itemView.findViewById(R.id.textview_email_header);
            textview_email = itemView.findViewById(R.id.textview_email);
            linear_address = itemView.findViewById(R.id.linear_address);
            textview_mobile_header = itemView.findViewById(R.id.textview_mobile_header);
            textview_mobile = itemView.findViewById(R.id.textview_mobile);
        }

        @Override
        public void onClick(View v) {
        }
    }

}

