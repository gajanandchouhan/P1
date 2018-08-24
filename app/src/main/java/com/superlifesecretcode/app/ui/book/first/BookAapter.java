package com.superlifesecretcode.app.ui.book.first;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.superlifesecretcode.app.R;
import com.superlifesecretcode.app.data.model.notifications.NotificationResponseData;
import com.superlifesecretcode.app.util.CommonUtils;
import com.superlifesecretcode.app.util.ConstantLib;
import com.superlifesecretcode.app.util.ImageLoadUtils;

import java.util.List;

/**
 * Created by Divya on 26-02-2018.
 */

public class BookAapter extends RecyclerView.Adapter<BookAapter.ItemViewHolder> {
    private final List<BookBean> list;
    private Context mContext;
    FirstBookActivity.BookSelectedListener bookSelectedListener;

    public BookAapter(List<BookBean> list, Context mContext, FirstBookActivity.BookSelectedListener bookSelectedListener) {
        this.list = list;
        this.mContext = mContext;
        this.bookSelectedListener = bookSelectedListener;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_book_list, parent, false));
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, final int position) {
        holder.textview_book_name.setText(list.get(position).getName());
        holder.textview_auther_name.setText(list.get(position).getAuthor_name());
        holder.textview_book_descrption.setText(list.get(position).getDescription());
        holder.textview_bookprice.setText("$" + list.get(position).getPrice());
        //ImageLoadUtils.loadImage(list.get(position).getImage(), holder.book);

        if (list.get(position).isSelected()) {
            holder.checkbox.setImageDrawable(mContext.getResources().getDrawable(R.drawable.check));
        } else {
            holder.checkbox.setImageDrawable(mContext.getResources().getDrawable(R.drawable.uncheck));
        }
        holder.linear_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (list.get(position).isSelected()) {
                    bookSelectedListener.onSelected(position, false);
                } else {
                    bookSelectedListener.onSelected(position, true);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView checkbox, book;
        TextView textview_book_name;
        TextView textview_auther_name;
        TextView textview_book_descrption;
        TextView textview_bookprice;
        LinearLayout linear_select;

        public ItemViewHolder(View itemView) {
            super(itemView);
            checkbox = itemView.findViewById(R.id.checkbox);
            book = itemView.findViewById(R.id.book);
            textview_book_name = itemView.findViewById(R.id.textview_book_name);
            textview_auther_name = itemView.findViewById(R.id.textview_auther_name);
            textview_book_descrption = itemView.findViewById(R.id.textview_book_descrption);
            textview_bookprice = itemView.findViewById(R.id.textview_bookprice);
            linear_select = itemView.findViewById(R.id.linear_select);
        }

        @Override
        public void onClick(View v) {
        }
    }
}
