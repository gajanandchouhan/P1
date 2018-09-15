package com.superlifesecretcode.app.ui.book.forth;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.superlifesecretcode.app.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Divya on 26-02-2018.
 */

public class AddressAapter extends RecyclerView.Adapter<AddressAapter.ItemViewHolder> {
    private final List<Stores> list;
    private Context mContext;
    ForthBookActivity.StoreListener bookSelectedListener;


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
        holder.textview_address.setText(list.get(position).getStreet_address()+", "+list.get(position).getCity()+", "+list.get(position).getState());
        //holder.textview_addeess_header.setText(list.get(position).getAuthor_name());
//        holder.textview_time.setText(list.get(position).getOpening_time());
        //holder.textview_time_header.setText("$" + list.get(position).getPrice());

        holder.linear_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String address = list.get(position).getStreet_address()+", "+list.get(position).getCity()+", "+list.get(position).getState();
                bookSelectedListener.onSelectOldAddess(list.get(position),address);
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
//        TextView textview_time_header;
//        TextView textview_time;
        LinearLayout linear_address;

        public ItemViewHolder(View itemView) {
            super(itemView);
            textview_addeess_header = itemView.findViewById(R.id.textview_addeess_header);
            textview_address = itemView.findViewById(R.id.textview_address);
//            textview_time_header = itemView.findViewById(R.id.textview_time_header);
//            textview_time = itemView.findViewById(R.id.textview_time);
            linear_address = itemView.findViewById(R.id.linear_address);
        }

        @Override
        public void onClick(View v) {
        }
    }
}
