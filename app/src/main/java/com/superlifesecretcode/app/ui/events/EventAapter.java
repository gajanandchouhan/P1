package com.superlifesecretcode.app.ui.events;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
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
        holder.layoutInterested.setSelected(eventsInfoModel.getUserIntrested() != null && eventsInfoModel.getUserIntrested().equalsIgnoreCase("1"));
        if (isToday) {
            holder.textViewDate.setText("Today");
        } else {
            holder.textViewDate.setText(CommonUtils.getformattedDateFromString(ConstantLib.INPUT_DATE_ONLY_FORMATE,
                    ConstantLib.OUTPUT_DATE_FORMATE, eventsInfoModel.getAnnouncement_date()));
        }

        holder.textViewTitme.setText(CommonUtils.getformattedDateFromString("HH:mm:ss", "hh:mm a", eventsInfoModel.getAnnouncement_time()));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Spanned spanned = Html.fromHtml(eventsInfoModel.getAnnouncement_description(), Html.FROM_HTML_MODE_LEGACY);
            holder.textViewDesc.setText(spanned);
        } else {
            Spanned spanned = Html.fromHtml(eventsInfoModel.getAnnouncement_description());
            holder.textViewDesc.setText(spanned);
        }
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
        RelativeLayout layoutInterested;

        public ItemViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView_event);
            textViewTitle = itemView.findViewById(R.id.textView_title);
            textViewDate = itemView.findViewById(R.id.textView_date);
            textViewTitme = itemView.findViewById(R.id.textView_time);
            textViewDesc = itemView.findViewById(R.id.textView_desc);
            textAddress = itemView.findViewById(R.id.textView_addr);
            itemView.findViewById(R.id.imageView_share).setOnClickListener(this);
            layoutInterested = itemView.findViewById(R.id.button_interested);
            layoutInterested.setOnClickListener(this);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.button_interested) {
                String userIntrested = list.get(getAdapterPosition()).getUserIntrested();
                if (userIntrested != null && userIntrested.equalsIgnoreCase("1")) {
                    ((EventActivity) mContext).updateEventInterest(getAdapterPosition(), "0", list.get(getAdapterPosition()).getAnnouncement_id());
                } else {
                    ((EventActivity) mContext).updateEventInterest(getAdapterPosition(), "1", list.get(getAdapterPosition()).getAnnouncement_id());
                }

            } else if (v.getId() == R.id.imageView_share) {
                CommonUtils.shareContent(mContext, Html.fromHtml(list.get(getAdapterPosition()).getAnnouncement_description()).toString());
            } else {
                Bundle bundle = new Bundle();
                bundle.putSerializable("events", (ArrayList) list);
                bundle.putInt("position", getAdapterPosition());
                CommonUtils.startActivity((EventActivity) mContext, EventDetailsActivity.class, bundle, false);
            }

        }
    }
}
