package com.superlifesecretcode.app.ui.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.superlifesecretcode.app.R;
import com.superlifesecretcode.app.data.model.SubcategoryModel;
import com.superlifesecretcode.app.ui.picker.DropDownWindow;
import com.superlifesecretcode.app.ui.subcategory.SubCategoryActivity;
import com.superlifesecretcode.app.ui.webview.WebViewActivity;
import com.superlifesecretcode.app.util.CommonUtils;

import java.util.List;

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
        holder.textView.setCompoundDrawablesWithIntrinsicBounds(s.getIcon(), 0, 0, 0);
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
            Bundle bundle = new Bundle();
            bundle.putString("title", list.get(getAdapterPosition()).getTitle());
            bundle.putString("url", list.get(getAdapterPosition()).getUrl());
            CommonUtils.startActivity((AppCompatActivity) mContext, WebViewActivity.class, bundle, false);
        }
    }
}
