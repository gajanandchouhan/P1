package com.superlifesecretcode.app.ui.myannouncement;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.superlifesecretcode.app.R;
import com.superlifesecretcode.app.data.model.language.LanguageResponseData;
import com.superlifesecretcode.app.data.model.myannoucement.MyAnnouncementResponseData;
import com.superlifesecretcode.app.data.persistance.SuperLifeSecretPreferences;
import com.superlifesecretcode.app.ui.myannouncement.addannouncement.AddAnnouncementActivity;
import com.superlifesecretcode.app.ui.picker.AlertDialog;
import com.superlifesecretcode.app.util.CommonUtils;
import com.superlifesecretcode.app.util.ConstantLib;
import com.superlifesecretcode.app.util.ImageLoadUtils;

import java.util.List;

public class MyAnnouncementAdapter extends RecyclerView.Adapter<MyAnnouncementAdapter.ItemViewHolder> {

    private final Context mContext;
    private List<MyAnnouncementResponseData> list;
    private LanguageResponseData languageResponseData;

    public MyAnnouncementAdapter(List list, Context mContext) {
        this.list = list;
        this.mContext = mContext;
        languageResponseData = SuperLifeSecretPreferences.getInstance().getConversionData();

    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_my_announcment, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        MyAnnouncementResponseData myAnnouncementResponseData = list.get(position);
        holder.textViewTitle.setText(myAnnouncementResponseData.getAnnouncement_name());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Spanned spanned = Html.fromHtml(myAnnouncementResponseData.getAnnouncement_description(), Html.FROM_HTML_MODE_LEGACY);
            holder.textViewDesc.setText(spanned);
        } else {
            Spanned spanned = Html.fromHtml(myAnnouncementResponseData.getAnnouncement_description());
            holder.textViewDesc.setText(spanned);
        }
        if (myAnnouncementResponseData.getAnnouncement_type().equals("1")) {
            holder.textViewDateTime.setText(String.format("%s", CommonUtils.getformattedDateFromString(ConstantLib.INPUT_DATE_TIME_FORMATE,
                    ConstantLib.OUTPUT_DATE_TIME_FORMATE, myAnnouncementResponseData.getStart_date() + " " + myAnnouncementResponseData.getStart_time(), false, null)));
        } else {
            holder.textViewDateTime.setText(String.format("%s", CommonUtils.getformattedDateFromString(ConstantLib.INPUT_DATE_TIME_FORMATE,
                    ConstantLib.OUTPUT_DATE_TIME_FORMATE, myAnnouncementResponseData.getCreated_at(), false, null)));
        }

        switch (myAnnouncementResponseData.getApproval_status() != null ? myAnnouncementResponseData.getApproval_status() : "0") {
            case "0":
                holder.textViewStatus.setText(languageResponseData.getPending());
                holder.textViewStatus.setBackgroundResource(R.drawable.bg_pending);
                holder.textViewReason.setVisibility(View.GONE);
                holder.imageViewDelete.setVisibility(View.GONE);
                break;
            case "1":
                holder.textViewStatus.setText(languageResponseData.getPublished());
                holder.textViewStatus.setBackgroundResource(R.drawable.bg_published);
                holder.textViewReason.setVisibility(View.GONE);
                holder.imageViewDelete.setVisibility(View.VISIBLE);
                break;
            case "2":
                holder.textViewStatus.setText(languageResponseData.getRejected());
                holder.textViewStatus.setBackgroundResource(R.drawable.bg_declined);
                holder.textViewReason.setVisibility(View.VISIBLE);
                holder.imageViewDelete.setVisibility(View.GONE);
                break;
        }
        holder.textViewStatus.setPadding(25, 0, 25, 5);
        List<MyAnnouncementResponseData.ImageData> announcement_images = myAnnouncementResponseData.getAnnouncement_images();
        if (announcement_images != null && announcement_images.size() > 0) {
            ImageLoadUtils.loadImage(announcement_images.get(0).getImage(), holder.imageView);
        }else{
           holder.imageView.setImageResource(R.drawable.default_placeholder);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imageView;
        TextView textViewTitle;
        TextView textViewDateTime;
        TextView textViewDesc;
        ImageView imageViewDelete;
        TextView textViewStatus;
        TextView textViewReason;

        public ItemViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView_news);
            textViewTitle = itemView.findViewById(R.id.textView_title);
            textViewDateTime = itemView.findViewById(R.id.textView_time);
            textViewDesc = itemView.findViewById(R.id.textView_desc);
            imageViewDelete = itemView.findViewById(R.id.image_view_delete);
            textViewStatus = itemView.findViewById(R.id.text_view_status);
            textViewReason = itemView.findViewById(R.id.text_view_reason);
            imageViewDelete.setOnClickListener(this);
            textViewReason.setOnClickListener(this);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.image_view_delete:
                    showDeleteAlert(getAdapterPosition());
                    break;
                case R.id.text_view_reason:
                    showReasonAlert(list.get(getAdapterPosition()).getReason());
                    break;
                default:
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("data", list.get(getAdapterPosition()));
                    CommonUtils.startActivity((AppCompatActivity) mContext, AddAnnouncementActivity.class, bundle, false);
                    break;
            }
        }
    }

    private void showReasonAlert(String reason) {
        CommonUtils.showAlert(mContext, reason, SuperLifeSecretPreferences.getInstance().getConversionData().getOk(), null, new AlertDialog.OnClickListner() {
            @Override
            public void onPositiveClick() {

            }

            @Override
            public void onNegativeClick() {

            }
        });
    }

    private void showDeleteAlert(final int position) {
        CommonUtils.showAlert(mContext, languageResponseData.getDelete_alert(), languageResponseData.getYes(), languageResponseData.getNo(), new AlertDialog.OnClickListner() {
            @Override
            public void onPositiveClick() {
                ((MyAnnouncementActivity) mContext).deleteAnnouncement(position);

            }

            @Override
            public void onNegativeClick() {

            }
        });
    }
}
