package com.superlifesecretcode.app.ui.events;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.superlifesecretcode.app.R;

import java.util.List;

/**
 * Created by Divya on 26-02-2018.
 */

public class EventAapter extends RecyclerView.Adapter<EventAapter.ItemViewHolder> {
    private final List list;
    private Context mContext;

    public EventAapter(List list, Context mContext) {
        this.list = list;
        this.mContext = mContext;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_event, parent, false));
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
      /*  holder.textView.setText(list.get(position).getTitle());
        ImageLoadUtils.loadImage(list.get(position).getImage(), holder.imageView);
        GradientDrawable drawable = (GradientDrawable) holder.itemView.getBackground();
        drawable.setColor(Color.parseColor(list.get(position).getColor()));*/

    }

    @Override
    public int getItemCount() {
        return 5;
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
        }
    }
}
