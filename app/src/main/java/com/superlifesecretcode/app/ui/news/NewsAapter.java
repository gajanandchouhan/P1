package com.superlifesecretcode.app.ui.news;

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
import android.widget.TextView;

import com.superlifesecretcode.app.R;
import com.superlifesecretcode.app.data.model.news.NewsResponseData;
import com.superlifesecretcode.app.util.CommonUtils;
import com.superlifesecretcode.app.util.ConstantLib;
import com.superlifesecretcode.app.util.ImageLoadUtils;
import com.twitter.sdk.android.core.models.Card;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Divya on 26-02-2018.
 */

public class NewsAapter extends RecyclerView.Adapter<NewsAapter.ItemViewHolder> {
    private final List<NewsResponseData> list;
    private Context mContext;

    public NewsAapter(List list, Context mContext) {
        this.list = list;
        this.mContext = mContext;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news, parent, false));
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        if (list.get(position).getReaded_by_user().equalsIgnoreCase("1")) {
            holder.cardView.setCardBackgroundColor(Color.WHITE);
        } else {
            holder.cardView.setCardBackgroundColor(Color.LTGRAY);
        }
        holder.textViewTitle.setText(list.get(position).getAnnouncement_name());
        ImageLoadUtils.loadImage(list.get(position).getImage(), holder.imageView);
        holder.textViewDateTime.setText(CommonUtils.getformattedDateFromString(ConstantLib.INPUT_DATE_TIME_FORMATE, ConstantLib.OUTPUT_DATE_TIME_FORMATE, list.get(position).getCreated_at()));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Spanned spanned = Html.fromHtml(list.get(position).getAnnouncement_description(), Html.FROM_HTML_MODE_LEGACY);
            holder.textViewDesc.setText(spanned);
        } else {
            Spanned spanned = Html.fromHtml(list.get(position).getAnnouncement_description());
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
        TextView textViewDateTime;
        TextView textViewDesc;
        CardView cardView;

        public ItemViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView_news);
            textViewTitle = itemView.findViewById(R.id.textView_title);
            textViewDateTime = itemView.findViewById(R.id.textView_time);
            textViewDesc = itemView.findViewById(R.id.textView_desc);
            cardView = itemView.findViewById(R.id.card_view);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            notifyItemChanged(getAdapterPosition());
            Bundle bundle = new Bundle();
            bundle.putSerializable("news", (ArrayList) list);
            bundle.putInt("position", getAdapterPosition());
            CommonUtils.startActivity((NewsActivity) mContext, NewsDetailsActivity.class, bundle, false);
        }
    }
}
