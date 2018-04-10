package com.superlifesecretcode.app.ui.events;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.CardView;
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
import com.superlifesecretcode.app.data.model.language.LanguageResponseData;
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
    private final LanguageResponseData coversionData;
    TextView textViewInterested;
    private Context mContext;
    boolean isToday;

    public void setToday(boolean today) {
        isToday = today;
    }

    public EventAapter(List list, Context mContext, LanguageResponseData conversionData) {
        this.list = list;
        this.mContext = mContext;
        this.coversionData = conversionData;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_event, parent, false));
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        EventsInfoModel eventsInfoModel = list.get(position);
        if (eventsInfoModel.getReaded_by_user().equalsIgnoreCase("1")) {
            holder.cardView.setCardBackgroundColor(Color.WHITE);
        } else {
            holder.cardView.setCardBackgroundColor(Color.LTGRAY);
        }
        holder.textViewTitle.setText(eventsInfoModel.getAnnouncement_name());
        ImageLoadUtils.loadImage(list.get(position).getImage(), holder.imageView);
        holder.textAddress.setText(eventsInfoModel.getVenue());
        holder.layoutInterested.setSelected(eventsInfoModel.getUserIntrested() != null && eventsInfoModel.getUserIntrested().equalsIgnoreCase("1"));
        if (isToday) {
            holder.textViewDate.setText(coversionData.getToday());
        } else {
            holder.textViewDate.setText(String.format("Start Date : %s", CommonUtils.getformattedDateFromString(ConstantLib.INPUT_DATE_ONLY_FORMATE,
                    ConstantLib.OUTPUT_DATE_FORMATE, eventsInfoModel.getAnnouncement_date(), true)));
        }

        holder.textViewTitme.setText(String.format("Time : %s", CommonUtils.getformattedDateFromString("HH:mm:ss", "hh:mm a", eventsInfoModel.getAnnouncement_time(), true)));
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
        CardView cardView;

        public ItemViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView_event);
            cardView = itemView.findViewById(R.id.card_view);
            textViewInterested = itemView.findViewById(R.id.textVew_interested);
            textViewTitle = itemView.findViewById(R.id.textView_title);
            textViewDate = itemView.findViewById(R.id.textView_date);
            textViewTitme = itemView.findViewById(R.id.textView_time);
            textViewDesc = itemView.findViewById(R.id.textView_desc);
            textAddress = itemView.findViewById(R.id.textView_addr);
            textViewInterested.setText(coversionData.getInterested());
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
                ((EventActivity) mContext).shareImageAndText(list.get(getAdapterPosition()).getImage(), Html.fromHtml(list.get(getAdapterPosition()).getAnnouncement_description()).toString());
            } else {
                Bundle bundle = new Bundle();
                bundle.putSerializable("events", (ArrayList) list);
                bundle.putInt("position", getAdapterPosition());
                CommonUtils.startActivity((EventActivity) mContext, EventDetailsActivity.class, bundle, false);
            }

        }
    }
}
