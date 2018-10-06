package com.superlifesecretcode.app.ui.studygroup.paymentproof;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.superlifesecretcode.app.R;
import com.superlifesecretcode.app.ui.book.five.Bank;
import com.superlifesecretcode.app.ui.book.five.FifthBookActivity;
import com.superlifesecretcode.app.util.ImageLoadUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Divya on 26-02-2018.
 */

public class StudyGroupBankAapter extends RecyclerView.Adapter<StudyGroupBankAapter.ItemViewHolder> {
    private final List<Bank> list;
    private Context mContext;
    PaymentProofActivity.BankListener bookSelectedListener;

    public StudyGroupBankAapter(ArrayList<Bank> list, PaymentProofActivity mContext, PaymentProofActivity.BankListener bookSelectedListener) {
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
        ImageLoadUtils.loadImage(list.get(position).getBank_icon(), holder.iv_bank_logo);
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
        ImageView imageview_check , iv_bank_logo;

        public ItemViewHolder(View itemView) {
            super(itemView);
            textview_bankname = itemView.findViewById(R.id.textview_bankname);
            textview_accountnumber = itemView.findViewById(R.id.textview_accountnumber);
            imageview_check = itemView.findViewById(R.id.imageview_check);
            linear_bank = itemView.findViewById(R.id.linear_bank);
            iv_bank_logo = itemView.findViewById(R.id.iv_bank_logo);
        }
    }
}
