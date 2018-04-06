package com.superlifesecretcode.app.ui.dailyactivities.personalevent;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.sundeepk.compactcalendarview.domain.Event;
import com.superlifesecretcode.app.R;
import com.superlifesecretcode.app.data.model.language.LanguageResponseData;
import com.superlifesecretcode.app.data.model.personalevent.PersonalEventResponseData;
import com.superlifesecretcode.app.util.CommonUtils;

import java.util.List;

/**
 * Created by Divya on 26-02-2018.
 */

public class PersonalEventAapter extends RecyclerView.Adapter<PersonalEventAapter.ItemViewHolder> {
    private final List<Event> list;
    private final LanguageResponseData coversionData;
    private final Context mContext;

    public PersonalEventAapter(List list, Context mContext, LanguageResponseData conversionData) {
        this.list = list;
        this.mContext = mContext;
        this.coversionData = conversionData;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_personal_calendar, parent, false));
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        Event event = list.get(position);
        PersonalEventResponseData data = (PersonalEventResponseData) event.getData();
        holder.textViewTitle.setText(data.getTitle());
        holder.textViewTime.setText(CommonUtils.getformattedDateFromString("HH:mm","hh:mm a",data.getActivity_time(),true));
        holder.switchCompat.setChecked(data.getStatus().equals("1"));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView textViewTime;
        TextView textViewTitle;
        SwitchCompat switchCompat;

        public ItemViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            textViewTitle = itemView.findViewById(R.id.textView_title);
            textViewTime = itemView.findViewById(R.id.textView_time);
            switchCompat = itemView.findViewById(R.id.switch_reminder);
            itemView.findViewById(R.id.imageView_remove).setOnClickListener(this);
            switchCompat.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.switch_reminder) {
                ((PersonalEventCalendarActivity) mContext).updateState(getAdapterPosition());
            } else  if (v.getId() == R.id.imageView_remove) {
                ((PersonalEventCalendarActivity) mContext).removeActivity(getAdapterPosition());
            } else {
                PersonalEventResponseData data = (PersonalEventResponseData) list.get(getAdapterPosition()).getData();
                Bundle bundle = new Bundle();
                bundle.putSerializable("detail", data);
                bundle.putBoolean("isDetails", true);
                CommonUtils.startActivity((AppCompatActivity) mContext, AddNewEventCalendarActivity.class, bundle, false);
            }
        }
    }


}
