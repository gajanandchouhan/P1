package com.superlifesecretcode.app.ui.book.second;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.superlifesecretcode.app.R;
import com.superlifesecretcode.app.data.model.language.LanguageResponseData;
import com.superlifesecretcode.app.data.persistance.SuperLifeSecretPreferences;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Divya on 26-02-2018.
 */

public class DiscountAapter extends RecyclerView.Adapter<DiscountAapter.ItemViewHolder> {
    private final List<Discount> list;
    private Context mContext;
    LanguageResponseData languageResponseData;

    public DiscountAapter(ArrayList<Discount> list, SecondBookActivity mContext) {
        this.list = list;
        this.mContext = mContext;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_delivery, parent, false));
    }

    @Override
    public void onBindViewHolder(final ItemViewHolder holder, final int position) {
        languageResponseData = SuperLifeSecretPreferences.getInstance().getConversionData();
        holder.textview_bankname.setText(""+list.get(position).getQuantity_from()+" - "+list.get(position).getQuantity_to()+" "+languageResponseData.getBooks()+""+" = ");
        holder.tvtotal.setText(" " + list.get(position).getDiscount()+" %");

//        try {
//            holder.tvtotal.setText("" + SuperLifeSecretPreferences.getInstance().getString("book_currency") + " " + String.format(java.util.Locale.getDefault(), "%,.2f", Double.parseDouble(list.get(position).getDiscount())));
//        }catch (Exception e){
//            holder.tvtotal.setText("" + SuperLifeSecretPreferences.getInstance().getString("book_currency") + " " + list.get(position).getDiscount());
//        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ItemViewHolder extends RecyclerView.ViewHolder  {

        TextView textview_bankname;
        TextView tvtotal;

        public ItemViewHolder(View itemView) {
            super(itemView);
            textview_bankname = itemView.findViewById(R.id.textview_bankname);
            tvtotal = itemView.findViewById(R.id.tvtotal);
        }
    }
}
