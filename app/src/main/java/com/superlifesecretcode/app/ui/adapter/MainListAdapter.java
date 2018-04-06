package com.superlifesecretcode.app.ui.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.DrawableContainer;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.StateListDrawable;
import android.media.MediaPlayer;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.superlifesecretcode.app.R;
import com.superlifesecretcode.app.data.model.category.CategoryResponseData;
import com.superlifesecretcode.app.data.persistance.SuperLifeSecretPreferences;
import com.superlifesecretcode.app.ui.main.MainActivity;
import com.superlifesecretcode.app.util.ImageLoadUtils;

import java.util.List;
import java.util.Random;

/**
 * Created by Divya on 26-02-2018.
 */

public class MainListAdapter extends RecyclerView.Adapter<MainListAdapter.ItemViewHolder> {
    private final List<CategoryResponseData> list;
    private Context mContext;
    SuperLifeSecretPreferences preferences;

    public MainListAdapter(List<CategoryResponseData> list, Context mContext) {
        this.list = list;
        this.mContext = mContext;
        preferences=SuperLifeSecretPreferences.getInstance();

    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_main, parent, false));
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        if (list.get(position).getId() != null) {
            if (list.get(position).getPosition() == 5&&(preferences.getNewsUnread()+preferences.getEventUnread())>0) {
                holder.textViewCount.setVisibility(View.VISIBLE);
                holder.textViewCount.setText(String.valueOf(preferences.getNewsUnread()+preferences.getEventUnread()));
            } else {
                holder.textViewCount.setVisibility(View.GONE);
            }
            holder.textView.setText(list.get(position).getTitle());
            ImageLoadUtils.loadImage(list.get(position).getImage(), holder.imageView);
            StateListDrawable stateListDrawable = (StateListDrawable) holder.itemView.getBackground();
            DrawableContainer.DrawableContainerState containerState = (DrawableContainer.DrawableContainerState) stateListDrawable
                    .getConstantState();
            Drawable[] children = containerState.getChildren();
            for (Drawable child : children) {
                if (child instanceof LayerDrawable) {
                    LayerDrawable layerDrawable = (LayerDrawable) child;
                    GradientDrawable gradientDrawable = (GradientDrawable) layerDrawable.findDrawableByLayerId(R.id.gradientDrawble);
                    gradientDrawable.setColor(Color.parseColor(list.get(position).getColor()));
                } else if (child instanceof GradientDrawable) {
                    GradientDrawable gradientDrawable = (GradientDrawable) child;
                    gradientDrawable.setColor(Color.parseColor(list.get(position).getColor()));
                }
            }

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
        final Animation myAnim;
        ImageView imageView;
        TextView textView;
        MediaPlayer mPlayer;
        TextView textViewCount;

        public ItemViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            textView = itemView.findViewById(R.id.textView);
            textViewCount = itemView.findViewById(R.id.textView_count);
            textView.setSelected(true);
            itemView.setOnClickListener(this);
            myAnim = AnimationUtils.loadAnimation(mContext, R.anim.bounce);
            mPlayer = MediaPlayer.create(mContext, R.raw.beep);
            MyBounceInterpolator interpolator = new MyBounceInterpolator(0.2, 20);
            myAnim.setInterpolator(interpolator);

        }

        @Override
        public void onClick(View v) {
            ((MainActivity) mContext).openNextScreen(getAdapterPosition(), list.get(getAdapterPosition()).getPosition(), list.get(getAdapterPosition()).getTitle(), list.get(getAdapterPosition()).getId(), list.get(getAdapterPosition()).getColor());
            itemView.startAnimation(myAnim);
            mPlayer.start();
        }
    }

    class MyBounceInterpolator implements android.view.animation.Interpolator {
        private double mAmplitude = 1;
        private double mFrequency = 10;

        MyBounceInterpolator(double amplitude, double frequency) {
            mAmplitude = amplitude;
            mFrequency = frequency;
        }

        public float getInterpolation(float time) {
            return (float) (-1 * Math.pow(Math.E, -time / mAmplitude) *
                    Math.cos(mFrequency * time) + 1);
        }
    }

}
