package com.superlifesecretcode.app.ui.book.five;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.superlifesecretcode.app.R;
import com.superlifesecretcode.app.ui.book.forth.Addresss;
import com.superlifesecretcode.app.ui.book.forth.ForthBookActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Divya on 26-02-2018.
 */

public class BankAapter extends RecyclerView.Adapter<BankAapter.ItemViewHolder> {
    private final List<Bank> list;
    private Context mContext;
    FifthBookActivity.BankListener bookSelectedListener;

    public BankAapter(ArrayList<Bank> list, FifthBookActivity mContext, FifthBookActivity.BankListener bookSelectedListener) {
        this.list = list;
        this.mContext = mContext;
        this.bookSelectedListener = bookSelectedListener;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bank, parent, false));
    }

    @Override
    public void onBindViewHolder(final ItemViewHolder holder, final int position) {
        holder.textview_bankname.setText(list.get(position).getBank_name());
        holder.textview_accountnumber.setText(list.get(position).getAccount_number());
        if (list.get(position).isSelected())
            holder.imageview_check.setVisibility(View.VISIBLE);
        else
            holder.imageview_check.setVisibility(View.GONE);

        holder.linear_bank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bookSelectedListener.onSelectBank(list.get(position).isSelected(),position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ItemViewHolder extends RecyclerView.ViewHolder  {

        TextView textview_bankname;
        TextView textview_accountnumber;
        LinearLayout linear_bank;
        ImageView imageview_check;

        public ItemViewHolder(View itemView) {
            super(itemView);
            textview_bankname = itemView.findViewById(R.id.textview_bankname);
            textview_accountnumber = itemView.findViewById(R.id.textview_accountnumber);
            imageview_check = itemView.findViewById(R.id.imageview_check);
            linear_bank = itemView.findViewById(R.id.linear_bank);
        }
    }
}
