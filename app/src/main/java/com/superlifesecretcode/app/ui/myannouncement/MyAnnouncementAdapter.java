package com.superlifesecretcode.app.ui.myannouncement;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.superlifesecretcode.app.R;
import com.superlifesecretcode.app.ui.picker.AlertDialog;
import com.superlifesecretcode.app.util.CommonUtils;

import java.util.List;

public class MyAnnouncementAdapter extends RecyclerView.Adapter<MyAnnouncementAdapter.ItemViewHolder> {

    private final Context mContext;
    private List list;

    public MyAnnouncementAdapter(List list, Context mContext) {
        this.list = list;
        this.mContext=mContext;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_my_announcment, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imageView;
        TextView textViewTitle;
        TextView textViewDateTime;
        TextView textViewDesc;
        ImageView imageViewDelete;
        public ItemViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView_news);
            textViewTitle = itemView.findViewById(R.id.textView_title);
            textViewDateTime = itemView.findViewById(R.id.textView_time);
            textViewDesc = itemView.findViewById(R.id.textView_desc);
            imageViewDelete=itemView.findViewById(R.id.image_view_delete);
            imageViewDelete.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.image_view_delete:
                    showDeleteAlert();
                    break;
            }
        }
    }

    private void showDeleteAlert() {
        CommonUtils.showAlert(mContext, "Are you sure, want to delete?", "Ok", "Cancel", new AlertDialog.OnClickListner() {
            @Override
            public void onPositiveClick() {

            }

            @Override
            public void onNegativeClick() {

            }
        });
    }
}
