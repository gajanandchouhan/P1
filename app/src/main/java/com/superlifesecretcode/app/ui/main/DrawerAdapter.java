package com.superlifesecretcode.app.ui.main;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.superlifesecretcode.app.R;
import com.superlifesecretcode.app.data.model.DrawerItem;
import com.superlifesecretcode.app.data.model.category.CategoryResponseData;
import com.superlifesecretcode.app.util.ImageLoadUtils;
import com.superlifesecretcode.app.util.ItemClickListner;

import java.util.List;

/**
 * Created by Divya on 22-02-2018.
 */

public class DrawerAdapter extends RecyclerView.Adapter<DrawerAdapter.ItemViewHolder> {
private ItemClickListner listner;

    public void setListner(ItemClickListner listner) {
        this.listner = listner;
    }

    private final List<CategoryResponseData> list;

    public DrawerAdapter(List<CategoryResponseData> drawerList) {
        this.list = drawerList;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_drawer_list, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        holder.textViewTitle.setText(list.get(position).getTitle());
        if (list.get(position).getId() != null) {
            ImageLoadUtils.loadImage(list.get(position).getImage(), holder.imageViewIcon);
        }else{
            holder.imageViewIcon.setImageResource(list.get(position).getIcon());
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView textViewTitle;
        ImageView imageViewIcon;

        public ItemViewHolder(View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.textView_title);
            imageViewIcon = itemView.findViewById(R.id.imageView_icon);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listner.onItemClick(list.get(getAdapterPosition()),getAdapterPosition());
        }
    }
}
