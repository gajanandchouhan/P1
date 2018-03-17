package com.superlifesecretcode.app.ui.sharing_latest;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.superlifesecretcode.app.R;
import com.superlifesecretcode.app.util.CommonUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Divya on 26-02-2018.
 */

public class LatestAapter extends RecyclerView.Adapter<LatestAapter.ItemViewHolder> {
    private final List list;
    private Context mContext;
    private int screenWidth;
    private int pagerHeight;


    public LatestAapter(List list, Context mContext) {
        this.list = list;
        this.mContext = mContext;
        screenWidth = CommonUtils.getScreenWidth(mContext);
        pagerHeight = screenWidth * 7 / 16;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_latest, parent, false));
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        holder.pager.setAdapter(new LatestPagerAdapter(mContext, new ArrayList<>()));
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ViewPager pager;

        public ItemViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            pager = itemView.findViewById(R.id.pager);
            pager.getLayoutParams().height = pagerHeight;
            pager.getLayoutParams().width = screenWidth;
        }

        @Override
        public void onClick(View v) {
            CommonUtils.startActivity((Activity) mContext, LatestDetailsActivity.class);
        }
    }
}
