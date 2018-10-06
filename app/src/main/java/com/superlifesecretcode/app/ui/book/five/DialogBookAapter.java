package com.superlifesecretcode.app.ui.book.five;

import android.content.Context;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.superlifesecretcode.app.R;
import com.superlifesecretcode.app.data.persistance.SuperLifeSecretPreferences;
import com.superlifesecretcode.app.ui.book.detail.Book;
import com.superlifesecretcode.app.ui.book.first.BookBean;
import com.superlifesecretcode.app.util.ImageLoadUtils;

import java.util.List;
import java.util.Locale;

/**
 * Created by Divya on 26-02-2018.
 */

public class DialogBookAapter extends RecyclerView.Adapter<DialogBookAapter.ItemViewHolder> {
    private final List<BookBean> list;
    private Context mContext;

    public DialogBookAapter(List<BookBean> list, Context mContext) {
        this.list = list;
        this.mContext = mContext;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_book, parent, false));
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, final int position) {
        holder.textview_book_name.setText(list.get(position).getName());
        try {
            holder.textview_price_quantity.setText(String.format(Locale.getDefault(), "%,.2f", list.get(position).getPrice_after_discount())+" X "+list.get(position).getQuantity());
        }catch (Exception e){
            holder.textview_price_quantity.setText(list.get(position).getPrice_after_discount()+" X "+list.get(position).getQuantity());
        }
        Double total = (list.get(position).getPrice_after_discount() * list.get(position).getQuantity());
        Double total_cut = (list.get(position).getPrice() * list.get(position).getQuantity());
        try {
            holder.textview_total.setText("" + SuperLifeSecretPreferences.getInstance().getString("book_currency") + " " + String.format(Locale.getDefault(), "%,.2f", total));
        }catch (Exception e){
            holder.textview_total.setText("" + SuperLifeSecretPreferences.getInstance().getString("book_currency") + " " + total);
        }
        try {
            holder.textview_total_cut.setText("" + SuperLifeSecretPreferences.getInstance().getString("book_currency") + " " + String.format(Locale.getDefault(), "%,.2f", total_cut));
        }catch (Exception e){
            holder.textview_total_cut.setText("" + SuperLifeSecretPreferences.getInstance().getString("book_currency") + " " + total_cut);
        }
        if (list.get(position).isIs_discounted()) {
            holder.textview_price_quantity_cut.setVisibility(View.VISIBLE);
            holder.textview_total_cut.setVisibility(View.VISIBLE);
        } else {
            holder.textview_price_quantity_cut.setVisibility(View.GONE);
            holder.textview_total_cut.setVisibility(View.GONE);
        }
        holder.textview_total_cut.setPaintFlags(holder.textview_total_cut.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        holder.textview_price_quantity_cut.setPaintFlags(holder.textview_price_quantity_cut.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        try {
            holder.textview_price_quantity_cut.setText(String.format(Locale.getDefault(), "%,.2f", list.get(position).getPrice())+" X "+list.get(position).getQuantity());
        }catch (Exception e){
            holder.textview_price_quantity_cut.setText(list.get(position).getPrice()+" X "+list.get(position).getQuantity());
        }
        ImageLoadUtils.loadImage(list.get(position).getImage(), holder.book);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView book;
        TextView textview_book_name;
        TextView textview_price_quantity , textview_price_quantity_cut;
        TextView textview_total , textview_total_cut;
        LinearLayout linear_select;

        public ItemViewHolder(View itemView) {
            super(itemView);
            book = itemView.findViewById(R.id.book);
            textview_book_name = itemView.findViewById(R.id.textview_book_name);
            textview_price_quantity = itemView.findViewById(R.id.textview_price_quantity);
            textview_total = itemView.findViewById(R.id.textview_total);
            linear_select = itemView.findViewById(R.id.linear_select);
            textview_total_cut = itemView.findViewById(R.id.textview_total_cut);
            textview_price_quantity_cut = itemView.findViewById(R.id.textview_price_quantity_cut);
        }
        @Override
        public void onClick(View v) {
        }
    }
}
