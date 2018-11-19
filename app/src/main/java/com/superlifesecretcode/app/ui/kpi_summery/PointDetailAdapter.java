package com.superlifesecretcode.app.ui.kpi_summery;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.superlifesecretcode.app.R;
import com.superlifesecretcode.app.data.model.kpi.PointDetailsModel;
import com.superlifesecretcode.app.data.model.kpi.StudySharingBean;
import com.superlifesecretcode.app.data.model.language.LanguageResponseData;
import com.superlifesecretcode.app.data.persistance.SuperLifeSecretPreferences;
import com.superlifesecretcode.app.util.ImageLoadUtils;

import java.util.List;

/**
 * Created by Divya on 26-02-2018.
 */

public class PointDetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    LanguageResponseData languageResponseData;

    private static final int VIEW_ITEM = 1;
    private static final int VIEW_DATE = 2;
    private List<PointDetailsModel> list;

    public PointDetailAdapter(List list, Context mContext) {
        this.list = list;
        this.mContext = mContext;
        languageResponseData = SuperLifeSecretPreferences.getInstance().getConversionData();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_ITEM)
            return new ItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_points_details, parent, false));
        else
            return new ItemDateViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_point_date, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof ItemDateViewHolder) {
            ItemDateViewHolder dateViewHolder = (ItemDateViewHolder) holder;
            dateViewHolder.textViewDate.setText(list.get(position).getDate());
        } else if (holder instanceof ItemViewHolder) {
            ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
            itemViewHolder.textViewTitle.setText(list.get(position).getTaskDetails().getTitle());
            itemViewHolder.textViewPoint.setText(list.get(position).getTaskDetails().getPoint());
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView textViewTitle;
        TextView textViewPoint;

        public ItemViewHolder(View itemView) {
            super(itemView);
            textViewTitle=itemView.findViewById(R.id.text_view_title);
            textViewPoint=itemView.findViewById(R.id.text_view_points);
        }

        @Override
        public void onClick(View v) {
        }
    }

    class ItemDateViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView textViewDate;

        public ItemDateViewHolder(View itemView) {
            super(itemView);
            textViewDate = itemView.findViewById(R.id.text_view_date);
        }

        @Override
        public void onClick(View v) {
        }
    }

    @Override
    public int getItemViewType(int position) {
        return list.get(position).isShowDate() ? VIEW_DATE : VIEW_ITEM;
    }
}
