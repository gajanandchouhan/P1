package com.superlifesecretcode.app.ui.dailyactivities.interestedevent;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.sundeepk.compactcalendarview.domain.Event;
import com.superlifesecretcode.app.R;
import com.superlifesecretcode.app.data.model.events.EventsInfoModel;
import com.superlifesecretcode.app.data.model.interesetdevent.InterestedEventdata;
import com.superlifesecretcode.app.data.model.language.LanguageResponseData;
import com.superlifesecretcode.app.ui.countryactivities.CountryActivityDetailsActivity;
import com.superlifesecretcode.app.ui.events.EventDetailsActivity;
import com.superlifesecretcode.app.util.CommonUtils;
import com.superlifesecretcode.app.util.ConstantLib;
import com.superlifesecretcode.app.util.ImageLoadUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Divya on 26-02-2018.
 */

public class InterestedEventAapter extends RecyclerView.Adapter<InterestedEventAapter.ItemViewHolder> {
    private final List<Event> list;
    private final LanguageResponseData coversionData;
    TextView textViewInterested;
    private Context mContext;
    boolean isToday;

    public void setToday(boolean today) {
        isToday = today;
    }

    public InterestedEventAapter(List list, Context mContext, LanguageResponseData conversionData) {
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
        Event event = list.get(position);
        InterestedEventdata eventsInfoModel = (InterestedEventdata) event.getData();
        holder.textViewTitle.setText(eventsInfoModel.getTitle());
        ImageLoadUtils.loadImage(eventsInfoModel.getImage(), holder.imageView);
        holder.textAddress.setText(eventsInfoModel.getVenue());
        holder.layoutInterested.setSelected(eventsInfoModel.getUserIntrested() != null && eventsInfoModel.getUserIntrested().equalsIgnoreCase("1"));
        if (isToday) {
            holder.textViewDate.setText(coversionData.getToday());
        } else {
            holder.textViewDate.setText(CommonUtils.getformattedDateFromString(ConstantLib.INPUT_DATE_ONLY_FORMATE,
                    ConstantLib.OUTPUT_DATE_FORMATE, eventsInfoModel.getEvent_date()));
        }

        holder.textViewTitme.setText(CommonUtils.getformattedDateFromString("HH:mm:ss", "hh:mm a", eventsInfoModel.getEvent_time()));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Spanned spanned = Html.fromHtml(eventsInfoModel.getDescription(), Html.FROM_HTML_MODE_LEGACY);
            holder.textViewDesc.setText(spanned);
        } else {
            Spanned spanned = Html.fromHtml(eventsInfoModel.getDescription());
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

            InterestedEventdata eventsInfoModel = (InterestedEventdata) list.get(getAdapterPosition()).getData();
            Bundle bundle = new Bundle();
            bundle.putString("id", eventsInfoModel.getEvent_id());
            bundle.putBoolean("from_calendar", true);
            if (eventsInfoModel.getEvent_type().equalsIgnoreCase("1")) {
                CommonUtils.startActivity((AppCompatActivity) mContext, InterestedEventActivityDetailsActivity.class, bundle, false);
            } else if (eventsInfoModel.getEvent_type().equalsIgnoreCase("2")) {
                CommonUtils.startActivity((AppCompatActivity) mContext, CountryActivityDetailsActivity.class, bundle, false);
            }
            //            infoList.add(infoModel);
//            Bundle bundle = new Bundle();
//            bundle.putSerializable("events", infoList);
//            bundle.putInt("position", getAdapterPosition());
//            CommonUtils.startActivity((AppCompatActivity) mContext, EventDetailsActivity.class, bundle, false);
        }
    }
}
