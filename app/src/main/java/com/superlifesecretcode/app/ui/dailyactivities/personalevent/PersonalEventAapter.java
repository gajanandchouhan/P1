package com.superlifesecretcode.app.ui.dailyactivities.personalevent;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.sundeepk.compactcalendarview.domain.Event;
import com.superlifesecretcode.app.R;
import com.superlifesecretcode.app.data.model.language.LanguageResponseData;

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
//        Event event = list.get(position);
    }

    @Override
    public int getItemCount() {
        return 5;
    }

    class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        public ItemViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
        }
    }
}
