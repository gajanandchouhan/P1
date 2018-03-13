package com.superlifesecretcode.app.ui.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.superlifesecretcode.app.R;
import com.superlifesecretcode.app.data.model.category.CategoryResponseData;
import com.superlifesecretcode.app.ui.main.MainActivity;
import com.superlifesecretcode.app.ui.webview.WebViewActivity;
import com.superlifesecretcode.app.util.CommonUtils;
import com.superlifesecretcode.app.util.ImageLoadUtils;

import java.util.List;
import java.util.Random;

/**
 * Created by Divya on 26-02-2018.
 */

public class SubListAdapter extends RecyclerView.Adapter<SubListAdapter.ItemViewHolder> {
    private final List<CategoryResponseData> list;
    private Context mContext;

    public SubListAdapter(List<CategoryResponseData> list, Context mContext) {
        this.list = list;
        this.mContext = mContext;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_main, parent, false));
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        holder.textView.setText(list.get(position).getTitle());
        ImageLoadUtils.loadImage(list.get(position).getImage(), holder.imageView);
        GradientDrawable drawable = (GradientDrawable) holder.itemView.getBackground();
        drawable.setColor(Color.parseColor(list.get(position).getColor()));

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView imageView;
        TextView textView;

        public ItemViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            textView = itemView.findViewById(R.id.textView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            Bundle bundle = new Bundle();
            bundle.putString("title", list.get(getAdapterPosition()).getTitle());
            bundle.putBoolean("is_link", list.get(getAdapterPosition()).getType().equalsIgnoreCase("1"));
            bundle.putString("url", list.get(getAdapterPosition()).getLink());
            bundle.putString("content", list.get(getAdapterPosition()).getContent());
            CommonUtils.startActivity((AppCompatActivity) mContext, WebViewActivity.class, bundle, false);
        }
    }
}
