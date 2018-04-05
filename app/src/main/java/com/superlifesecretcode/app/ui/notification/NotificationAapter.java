package com.superlifesecretcode.app.ui.notification;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.superlifesecretcode.app.R;
import com.superlifesecretcode.app.data.model.news.NewsResponseData;

import java.util.List;

/**
 * Created by Divya on 26-02-2018.
 */

public class NotificationAapter extends RecyclerView.Adapter<NotificationAapter.ItemViewHolder> {
    private final List<NewsResponseData> list;
    private Context mContext;

    public NotificationAapter(List list, Context mContext) {
        this.list = list;
        this.mContext = mContext;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notification, parent, false));
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {


    }

    @Override
    public int getItemCount() {
        return 10;
    }

    class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView textViewTitle;
        TextView textViewDateTime;
        TextView textViewDesc;

        public ItemViewHolder(View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.textView_title);
            textViewDateTime = itemView.findViewById(R.id.textView_time);
            textViewDesc = itemView.findViewById(R.id.textView_desc);
        }

        @Override
        public void onClick(View v) {

        }
    }
}
