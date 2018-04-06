package com.superlifesecretcode.app.ui.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.superlifesecretcode.app.R;
import com.superlifesecretcode.app.data.model.SubcategoryModel;
import com.superlifesecretcode.app.data.persistance.SuperLifeSecretPreferences;
import com.superlifesecretcode.app.ui.countryactivities.CountryAcitvitiesActivity;
import com.superlifesecretcode.app.ui.dailyactivities.interestedevent.InterestedEventCalendarActivity;
import com.superlifesecretcode.app.ui.dailyactivities.personalevent.PersonalEventCalendarActivity;
import com.superlifesecretcode.app.ui.events.EventActivity;
import com.superlifesecretcode.app.ui.news.NewsActivity;
import com.superlifesecretcode.app.ui.sharing_latest.LatestActivity;
import com.superlifesecretcode.app.ui.sharing_submit.SubmitListActivity;
import com.superlifesecretcode.app.ui.subcategory.SubCategoryActivity;
import com.superlifesecretcode.app.util.CommonUtils;
import com.superlifesecretcode.app.util.ConstantLib;

import java.util.List;

/**
 * Created by Divya on 26-02-2018.
 */

public class SubacategoryListAdapter extends RecyclerView.Adapter<SubacategoryListAdapter.ItemViewHolder> {
    private final List<SubcategoryModel> list;
    private final String colorCode;
    private final SuperLifeSecretPreferences preferences;
    private Context mContext;


    public SubacategoryListAdapter(List<SubcategoryModel> list, Context mContext, String colorCode) {
        this.list = list;
        this.mContext = mContext;
        this.colorCode = colorCode;
        preferences = SuperLifeSecretPreferences.getInstance();
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_subcategory, parent, false));
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        SubcategoryModel s = list.get(position);
        holder.textView.setText(s.getTitle());
        holder.textViewChar.setText(s.getTitle().substring(0, 1));
        if (list.size() > 0 && list.get(position).getType() == ConstantLib.TYPE_ANNOUNCEMENT) {
            if (position == 0 && preferences.getNewsUnread() > 0) {
                holder.textViewCount.setVisibility(View.VISIBLE);
                holder.textViewCount.setText(String.valueOf(preferences.getNewsUnread()));
            } else if (position == 1 && preferences.getEventUnread() > 0) {
                holder.textViewCount.setVisibility(View.VISIBLE);
                holder.textViewCount.setText(String.valueOf(preferences.getEventUnread()));
            } else {
                holder.textViewCount.setVisibility(View.GONE);
            }

        }
//        holder.textView.setCompoundDrawablesWithIntrinsicBounds(s.getIcon(), 0, 0, 0);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final TextView textView;
        private final TextView textViewCount;
        private TextView textViewChar;

        public ItemViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textView_item);
            textViewCount = itemView.findViewById(R.id.textView_count);
            textViewChar = itemView.findViewById(R.id.textView_char);
            GradientDrawable gradientDrawable = (GradientDrawable) textViewChar.getBackground();
            gradientDrawable.setColor(Color.parseColor(colorCode));
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            int type = list.get(getAdapterPosition()).getType();
            moveToNext(getAdapterPosition(), type);
        }
    }

    private void moveToNext(int position, int type) {
        switch (type) {
            case ConstantLib.TYPE_ANNOUNCEMENT:
                if (position == 0) {
                    CommonUtils.startActivity(((SubCategoryActivity) mContext), NewsActivity.class);
                } else if (position == 1) {
                    CommonUtils.startActivity(((SubCategoryActivity) mContext), EventActivity.class);
                }
                break;
            case ConstantLib.TYPE_SHARING:
                if (position == 0) {
                    CommonUtils.startActivity(((SubCategoryActivity) mContext), LatestActivity.class);
                } else if (position == 1) {
                    CommonUtils.startActivity(((SubCategoryActivity) mContext), SubmitListActivity.class);
                }
                break;
            case ConstantLib.TYPE_DAILY_ACTIVITIES:
                if (position == 0) {
                    CommonUtils.startActivity(((SubCategoryActivity) mContext), PersonalEventCalendarActivity.class);
                } else if (position == 1) {
                    CommonUtils.startActivity(((SubCategoryActivity) mContext), InterestedEventCalendarActivity.class);
                }

                break;
            case ConstantLib.TYPE_COUNTRY_ACTIVITIES:
                boolean isStudyGroup = false;
                if (position == 0) {
                    isStudyGroup = true;
                } else if (position == 1) {
                    isStudyGroup = false;
                }
                Bundle bundle = new Bundle();
                bundle.putString("title", list.get(position).getTitle());
                bundle.putBoolean("isStudyGroup", isStudyGroup);
                CommonUtils.startActivity(((SubCategoryActivity) mContext), CountryAcitvitiesActivity.class, bundle, false);
                break;
        }
    }
}
