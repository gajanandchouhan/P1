package com.superlifesecretcode.app.ui.myannouncement.addannouncement;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.superlifesecretcode.app.R;
import com.superlifesecretcode.app.data.model.myannoucement.MyAnnouncementResponseData;
import com.superlifesecretcode.app.data.model.mycountryactivities.MyCountryActivityResponseData;
import com.superlifesecretcode.app.ui.countryactivities.CountryAcitvitiesActivity;
import com.superlifesecretcode.app.ui.mycountryactivities.addcountryactivity.AddCountryActivityActivity;
import com.superlifesecretcode.app.ui.sharing_submit.ImageViewerActivity;
import com.superlifesecretcode.app.util.CommonUtils;
import com.superlifesecretcode.app.util.ImageLoadUtils;

import java.util.List;

/**
 * Created by Divya on 26-02-2018.
 */

public class HorizontalImageAapter extends RecyclerView.Adapter<HorizontalImageAapter.ItemViewHolder> {
    private final List list;
    private Context mContext;


    public HorizontalImageAapter(List list, Context mContext) {
        this.list = list;
        this.mContext = mContext;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_image_horizontal, parent, false));
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        Object s = list.get(position);
        if (s instanceof String) {
            ImageLoadUtils.loadImage(s.toString(), holder.imageView);
        } else if (s instanceof MyAnnouncementResponseData.ImageData) {
            MyAnnouncementResponseData.ImageData imageData = (MyAnnouncementResponseData.ImageData) s;
            ImageLoadUtils.loadImage(imageData.getImage(), holder.imageView);
        } else if (s instanceof MyCountryActivityResponseData.ImageData) {
            MyCountryActivityResponseData.ImageData imageData = (MyCountryActivityResponseData.ImageData) s;
            ImageLoadUtils.loadImage(imageData.getImage(), holder.imageView);
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imageView;

        public ItemViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            imageView = itemView.findViewById(R.id.imageView);
            itemView.findViewById(R.id.imageView_remove).setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Object o = list.get(getAdapterPosition());
            if (v.getId() == R.id.imageView_remove) {

                if (o instanceof MyAnnouncementResponseData.ImageData) {
                    ((AddAnnouncementActivity) mContext).deleteImage(getAdapterPosition());

                } else if (o instanceof MyCountryActivityResponseData.ImageData) {
                    ((AddCountryActivityActivity) mContext).deleteImage(getAdapterPosition());
                } else {
                    if (mContext instanceof AddAnnouncementActivity){
                        ((AddAnnouncementActivity) mContext).deleteLocalImage(list.get(getAdapterPosition()));
                    }else if (mContext instanceof AddCountryActivityActivity){
                        ((AddCountryActivityActivity) mContext).deleteLocalImage(list.get(getAdapterPosition()));
                    }
                    list.remove(getAdapterPosition());
                    notifyItemRemoved(getAdapterPosition());
                }
                if (mContext instanceof AddAnnouncementActivity) {
                    if (list.isEmpty()) {
                        ((AddAnnouncementActivity) mContext).hideReccylerView();
                    }
                }
                if (mContext instanceof AddCountryActivityActivity) {
                    if (list.isEmpty()) {
                        ((AddCountryActivityActivity) mContext).hideReccylerView();
                    }
                }
            } else {
                Bundle bundle = new Bundle();
                if (o instanceof MyAnnouncementResponseData.ImageData) {
                    bundle.putString("image", ((MyAnnouncementResponseData.ImageData) o).getImage());
                } else if (o instanceof MyCountryActivityResponseData.ImageData) {
                    bundle.putString("image", ((MyCountryActivityResponseData.ImageData) o).getImage());
                } else {
                    bundle.putString("image", o.toString());
                }
                CommonUtils.startActivity((AppCompatActivity) mContext, ImageViewerActivity.class, bundle, false);
            }
        }
    }
}
