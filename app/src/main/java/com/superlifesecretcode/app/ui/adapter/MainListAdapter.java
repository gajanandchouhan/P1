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
import com.superlifesecretcode.app.data.model.DrawerItem;
import com.superlifesecretcode.app.data.model.SubcategoryModel;
import com.superlifesecretcode.app.data.model.category.CategoryResponseData;
import com.superlifesecretcode.app.data.persistance.SuperLifeSecretPreferences;
import com.superlifesecretcode.app.ui.main.MainActivity;
import com.superlifesecretcode.app.ui.webview.WebViewActivity;
import com.superlifesecretcode.app.util.CommonUtils;
import com.superlifesecretcode.app.util.ImageLoadUtils;

import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Created by Divya on 26-02-2018.
 */

public class MainListAdapter extends RecyclerView.Adapter<MainListAdapter.ItemViewHolder> {
    private final List<CategoryResponseData> list;
    private Context mContext;

    public MainListAdapter(List<CategoryResponseData> list, Context mContext) {
        this.list = list;
        this.mContext = mContext;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_main, parent, false));
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        if (list.get(position).getId() != null) {
            holder.textView.setText(list.get(position).getTitle());
            ImageLoadUtils.loadImage(list.get(position).getImage(), holder.imageView);
            GradientDrawable drawable = (GradientDrawable) holder.itemView.getBackground();
     /*   Random rnd = new Random();
        int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256),
        rnd.nextInt(256));*/
            drawable.setColor(Color.parseColor(list.get(position).getColor()));
        } else {
            holder.textView.setText(list.get(position).getTitle());
            holder.imageView.setImageResource(list.get(position).getIcon());
            GradientDrawable drawable = (GradientDrawable) holder.itemView.getBackground();
            Random rnd = new Random();
            int color = Color.argb(255, rnd.nextInt(100), rnd.nextInt(100),
                    rnd.nextInt(100));
            drawable.setColor(color);
        }
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
            textView.setSelected(true);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            ((MainActivity) mContext).openNextScreen(getAdapterPosition(),list.get(getAdapterPosition()).getPosition(), list.get(getAdapterPosition()).getTitle(), list.get(getAdapterPosition()).getId(),list.get(getAdapterPosition()).getColor());
        }
    }


}
