package com.superlifesecretcode.app.ui.sharing_submit;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.superlifesecretcode.app.R;
import com.superlifesecretcode.app.data.model.language.LanguageResponseData;
import com.superlifesecretcode.app.data.model.shares.ShareListResponseData;
import com.superlifesecretcode.app.data.persistance.SuperLifeSecretPreferences;
import com.superlifesecretcode.app.ui.sharing_latest.LatestDetailsActivity;
import com.superlifesecretcode.app.ui.sharing_latest.LatestPagerAdapter;
import com.superlifesecretcode.app.util.CommonUtils;
import com.superlifesecretcode.app.util.ConstantLib;
import com.superlifesecretcode.app.util.ImageLoadUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Divya on 26-02-2018.
 */

public class SubmitListAapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int VIEW_HEADER = 1;
    private static final int VIEW_ITEM = 2;
    private final List<ShareListResponseData> list;
    private Context mContext;
    private int screenWidth;
    private int pagerHeight;
    LanguageResponseData languageResponseData;


    public SubmitListAapter(List list, Context mContext) {
        this.list = list;
        this.mContext = mContext;
        screenWidth = CommonUtils.getScreenWidth(mContext);
        pagerHeight = screenWidth * 7 / 16;
        languageResponseData = SuperLifeSecretPreferences.getInstance().getConversionData();
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
            ItemViewHolder holder1 = (ItemViewHolder) holder;
            ShareListResponseData shareListResponseData = list.get(position - 1);
            holder1.textViewName.setText(shareListResponseData.getUsername());
            holder1.textViewDateTime.setText(CommonUtils.getformattedDateFromString(ConstantLib.INPUT_DATE_TIME_FORMATE, ConstantLib.OUTPUT_DATE_TIME_FORMATE, shareListResponseData.getCreated_at(), false, null));
            holder1.textViewDesc.setText(shareListResponseData.getContent());
            holder1.textViewCountryName.setText(shareListResponseData.getCountryName());
            holder1.textViewStatus.setText(shareListResponseData.getStatus().equals("2") ? languageResponseData.getRejected() : languageResponseData.getPublished());
            ImageLoadUtils.loadImage(shareListResponseData.getUser_image(), holder1.imageView);
            if (shareListResponseData.getSharing_files() != null && shareListResponseData.getSharing_files().size() > 0) {
                holder1.pager.setVisibility(View.VISIBLE);
                holder1.pager.setAdapter(new LatestPagerAdapter(mContext, shareListResponseData.getSharing_files()));
            } else {
                holder1.pager.setVisibility(View.GONE);
            }

        } else if (holder instanceof HeaderViewHolder) {


        }

    }

    @Override
    public int getItemCount() {
        return list.size() + 1;
    }

    class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ViewPager pager;
        TextView textViewName;
        TextView textViewDateTime;
        TextView textViewCountryName;
        TextView textViewStatus;
        TextView textViewDesc;
        ImageView imageView;

        public ItemViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            pager = itemView.findViewById(R.id.pager);
            textViewName = itemView.findViewById(R.id.textView_name);
            textViewCountryName = itemView.findViewById(R.id.textView_country);
            textViewStatus = itemView.findViewById(R.id.textView_status);
            textViewDateTime = itemView.findViewById(R.id.textView_date_time);
            textViewDesc = itemView.findViewById(R.id.textView_desc);
            textViewName = itemView.findViewById(R.id.textView_name);
            imageView = itemView.findViewById(R.id.imageView_user);
            pager.getLayoutParams().height = pagerHeight;
            pager.getLayoutParams().width = screenWidth;
        }

        @Override
        public void onClick(View v) {
            Bundle bundle = new Bundle();
            bundle.putSerializable("share", list.get(getAdapterPosition() - 1));
            bundle.putBoolean("from_submit", true);
            CommonUtils.startActivity((AppCompatActivity) mContext, LatestDetailsActivity.class, bundle, false);
        }
    }

    class HeaderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public HeaderViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            TextView viewById = itemView.findViewById(R.id.text_view_what_in_mind);
            viewById.setHint(SuperLifeSecretPreferences.getInstance().getConversionData().getWhat_in_mind());
        }

        @Override
        public void onClick(View v) {
            CommonUtils.startActivity((Activity) mContext, SubmitActivity.class);
        }
    }
}
