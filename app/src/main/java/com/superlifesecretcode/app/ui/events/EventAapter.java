package com.superlifesecretcode.app.ui.events;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.superlifesecretcode.app.R;
import com.superlifesecretcode.app.data.model.events.EventsInfoModel;
import com.superlifesecretcode.app.ui.news.NewsActivity;
import com.superlifesecretcode.app.ui.news.NewsDetailsActivity;
import com.superlifesecretcode.app.util.CommonUtils;
import com.superlifesecretcode.app.util.ConstantLib;
import com.superlifesecretcode.app.util.ImageLoadUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Divya on 26-02-2018.
 */

public class EventAapter extends RecyclerView.Adapter<EventAapter.ItemViewHolder> {
    private final List<EventsInfoModel> list;
    private Context mContext;
    boolean isToday;

    public void setToday(boolean today) {
        isToday = today;
    }

    public EventAapter(List list, Context mContext) {
        this.list = list;
        this.mContext = mContext;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_event, parent, false));
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        EventsInfoModel eventsInfoModel = list.get(position);
        holder.textViewTitle.setText(eventsInfoModel.getAnnouncement_name());
        ImageLoadUtils.loadImage(list.get(position).getImage(), holder.imageView);
        holder.textAddress.setText(eventsInfoModel.getVenue());
        if (isToday) {
            holder.textViewDate.setText("Today");
        } else {
            holder.textViewDate.setText(CommonUtils.getformattedDateFromString(ConstantLib.INPUT_DATE_ONLY_FORMATE,
                    ConstantLib.OUTPUT_DATE_FORMATE, eventsInfoModel.getAnnouncement_date()));
        }

        holder.textViewTitme.setText(CommonUtils.getformattedDateFromString("HH:mm:ss", "hh:mm a", eventsInfoModel.getAnnouncement_time()));
        holder.textViewDesc.setText(eventsInfoModel.getAnnouncement_description());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView imageView;
        TextView textViewTitle;
        TextView textViewDate;
        TextView textViewTitme;
        TextView textViewDesc;
        TextView textAddress;

        public ItemViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView_event);
            textViewTitle = itemView.findViewById(R.id.textView_title);
            textViewDate = itemView.findViewById(R.id.textView_date);
            textViewTitme = itemView.findViewById(R.id.textView_time);
            textViewDesc = itemView.findViewById(R.id.textView_desc);
            textAddress = itemView.findViewById(R.id.textView_addr);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Bundle bundle=new Bundle();
            bundle.putSerializable("events",(ArrayList)list);
            bundle.putInt("position",getAdapterPosition());
            CommonUtils.startActivity((EventActivity) mContext, EventDetailsActivity.class,bundle,false);
        }
    }
}
