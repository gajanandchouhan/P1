package com.superlifesecretcode.app.ui.ringtone;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.OpenableColumns;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.superlifesecretcode.app.R;
import com.superlifesecretcode.app.data.model.news.NewsResponseData;

import java.util.List;

/**
 * Created by Divya on 26-02-2018.
 */

public class RingtoneAapter extends RecyclerView.Adapter<RingtoneAapter.ItemViewHolder> {
    private final Uri[] list;
    private Context mContext;
    int playPosition = -1;

    public RingtoneAapter(Uri[] list, Context mContext) {
        this.list = list;
        this.mContext = mContext;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ringtone, parent, false));
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        if (position == playPosition) {
            holder.imageViewPlayPause.setImageResource(android.R.drawable.ic_media_pause);
        } else {
            holder.imageViewPlayPause.setImageResource(android.R.drawable.ic_media_play);
        }
        holder.textViewTitle.setText(getFileName(list[position]));
    }

    @Override
    public int getItemCount() {
        return list.length;
    }

    class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView textViewTitle;
        ImageView imageViewPlayPause;

        public ItemViewHolder(View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.textView_title);
            imageViewPlayPause = itemView.findViewById(R.id.image_play_pause);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            playPosition = getAdapterPosition();
            notifyDataSetChanged();
            ((RingtoneActivity) mContext).playPause(list[playPosition], playPosition);
        }
    }

    public String getFileName(Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            Cursor cursor = mContext.getContentResolver().query(uri, null, null, null, null);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            } finally {
                cursor.close();
            }
        }
        if (result == null) {
            result = uri.getPath();
            int cut = result.lastIndexOf('/');
            if (cut != -1) {
                result = result.substring(cut + 1);
            }
        }
        return result.substring(0, result.indexOf("."));
    }
}
