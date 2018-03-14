package com.superlifesecretcode.app.ui.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.superlifesecretcode.app.R;
import com.superlifesecretcode.app.data.model.SubcategoryModel;
import com.superlifesecretcode.app.ui.events.EventActivity;
import com.superlifesecretcode.app.ui.news.NewsActivity;
import com.superlifesecretcode.app.ui.subcategory.SubCategoryActivity;
import com.superlifesecretcode.app.util.CommonUtils;
import com.superlifesecretcode.app.util.ConstantLib;

import java.util.List;
import java.util.Random;

/**
 * Created by Divya on 26-02-2018.
 */

public class SubacategoryListAdapter extends RecyclerView.Adapter<SubacategoryListAdapter.ItemViewHolder> {
    private final List<SubcategoryModel> list;
    private Context mContext;

    public SubacategoryListAdapter(List<SubcategoryModel> list, Context mContext) {
        this.list = list;
        this.mContext = mContext;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_subcategory, parent, false));
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        SubcategoryModel s = list.get(position);
        holder.textView.setText(s.getTitle());
        GradientDrawable drawable = (GradientDrawable) holder.itemView.getBackground();
        Random rnd = new Random();
        int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
        drawable.setColor(color);
//        holder.textView.setCompoundDrawablesWithIntrinsicBounds(s.getIcon(), 0, 0, 0);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final TextView textView;

        public ItemViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textView_item);
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
                break;
        }
    }
}
