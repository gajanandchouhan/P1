package com.superlifesecretcode.app.ui.sharing_submit;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.superlifesecretcode.app.R;
import com.superlifesecretcode.app.ui.sharing_latest.LatestPagerAdapter;
import com.superlifesecretcode.app.util.CommonUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Divya on 26-02-2018.
 */

public class SubmitListAapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int VIEW_HEADER = 1;
    private static final int VIEW_ITEM = 2;
    private final List list;
    private Context mContext;
    private int screenWidth;
    private int pagerHeight;


    public SubmitListAapter(List list, Context mContext) {
        this.list = list;
        this.mContext = mContext;
        screenWidth = CommonUtils.getScreenWidth(mContext);
        pagerHeight = screenWidth * 7 / 16;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_HEADER) {
            return new HeaderViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_submit_header, parent, false));
        }
        return new ItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_submit_list, parent, false));
    }

    @Override
    public int getItemViewType(int position) {
        return position == 0 ? VIEW_HEADER : VIEW_ITEM;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder) {
            ((ItemViewHolder) holder).pager.setAdapter(new LatestPagerAdapter(mContext, new ArrayList<>()));
        } else if (holder instanceof HeaderViewHolder) {

        }

    }

    @Override
    public int getItemCount() {
        return 3;
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
        }
    }

    class HeaderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public HeaderViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            CommonUtils.startActivity((Activity) mContext, SubmitActivity.class);
        }
    }
}
