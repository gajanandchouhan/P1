package com.superlifesecretcode.app.ui.book.third;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.superlifesecretcode.app.R;
import com.superlifesecretcode.app.ui.book.first.BookBean;
import com.superlifesecretcode.app.ui.book.first.FirstBookActivity;

import java.util.List;

/**
 * Created by Divya on 26-02-2018.
 */

public class ThirdBooksAapter extends RecyclerView.Adapter<ThirdBooksAapter.ItemViewHolder> {
    private final List<BookBean> list;
    private Context mContext;

    public ThirdBooksAapter(List<BookBean> list, Context mContext) {
        this.list = list;
        this.mContext = mContext;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_book_third_list, parent, false));
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, final int position) {
        holder.textview_book_name.setText(list.get(position).getName());
        holder.textview_bookprice.setText("$" + list.get(position).getPrice());
        Log.e("adapter"+position,""+list.get(position).getQuantity());
        holder.edittext_quantity.setText(""+list.get(position).getQuantity());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

//        ImageView plus, minus;
        TextView textview_book_name;
        TextView textview_bookprice ;
        EditText edittext_quantity;
        //LinearLayout linear_select;

        public ItemViewHolder(View itemView) {
            super(itemView);
//            plus = itemView.findViewById(R.id.plus);
//            minus = itemView.findViewById(R.id.minus);
            textview_book_name = itemView.findViewById(R.id.textview_book_name);
            textview_bookprice = itemView.findViewById(R.id.textview_bookprice);
            edittext_quantity = itemView.findViewById(R.id.edittext_quantity);
            //linear_select = itemView.findViewById(R.id.linear_select);
        }

        @Override
        public void onClick(View v) {
        }
    }
}
