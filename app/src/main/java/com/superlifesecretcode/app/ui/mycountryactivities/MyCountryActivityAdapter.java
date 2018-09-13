package com.superlifesecretcode.app.ui.mycountryactivities;

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
import com.superlifesecretcode.app.data.model.mycountryactivities.MyCountryActivityResponseData;
import com.superlifesecretcode.app.data.persistance.SuperLifeSecretPreferences;
import com.superlifesecretcode.app.ui.mycountryactivities.addcountryactivity.AddCountryActivityActivity;
import com.superlifesecretcode.app.ui.picker.AlertDialog;
import com.superlifesecretcode.app.util.CommonUtils;
import com.superlifesecretcode.app.util.ConstantLib;
import com.superlifesecretcode.app.util.ImageLoadUtils;

import java.util.List;

public class MyCountryActivityAdapter extends RecyclerView.Adapter<MyCountryActivityAdapter.ItemViewHolder> {

    private final Context mContext;
    private final LanguageResponseData conversionData;
    private List<MyCountryActivityResponseData> list;

    public MyCountryActivityAdapter(List list, Context mContext) {
        this.list = list;
        this.mContext = mContext;
        conversionData = SuperLifeSecretPreferences.getInstance().getConversionData();
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_my_announcment, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        MyCountryActivityResponseData myAnnouncementResponseData = list.get(position);
        holder.textViewTitle.setText(myAnnouncementResponseData.getActivity_title());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Spanned spanned = Html.fromHtml(myAnnouncementResponseData.getActivity_discription(), Html.FROM_HTML_MODE_LEGACY);
            holder.textViewDesc.setText(spanned);
        } else {
            Spanned spanned = Html.fromHtml(myAnnouncementResponseData.getActivity_discription());
            holder.textViewDesc.setText(spanned);
        }
        holder.textViewDateTime.setText(String.format("%s", CommonUtils.getformattedDateFromString(ConstantLib.INPUT_DATE_TIME_FORMATE,
                ConstantLib.OUTPUT_DATE_TIME_FORMATE, myAnnouncementResponseData.getActivity_date() + " " + myAnnouncementResponseData.getActivity_time(), false, null)));

        switch (myAnnouncementResponseData.getStatus()) {
            case "0":
                holder.textViewStatus.setText(conversionData.getPending());
                holder.textViewStatus.setBackgroundResource(R.drawable.bg_pending);
                holder.textViewReason.setVisibility(View.GONE);
                holder.imageViewDelete.setVisibility(View.GONE);
                break;
            case "1":
                holder.textViewStatus.setText(conversionData.getPublished());
                holder.textViewStatus.setBackgroundResource(R.drawable.bg_published);
                holder.textViewReason.setVisibility(View.GONE);
                holder.imageViewDelete.setVisibility(View.VISIBLE);
                break;
            case "2":
                holder.textViewStatus.setText(conversionData.getRejected());
                holder.textViewStatus.setBackgroundResource(R.drawable.bg_declined);
                holder.textViewReason.setVisibility(View.VISIBLE);
                holder.imageViewDelete.setVisibility(View.GONE);
                break;
        }
        holder.textViewStatus.setPadding(25, 0, 25, 5);
        List<MyCountryActivityResponseData.ImageData> announcement_images = myAnnouncementResponseData.getActivity_images();
        if (announcement_images != null && announcement_images.size() > 0) {
            ImageLoadUtils.loadImage(announcement_images.get(0).getImage(), holder.imageView);
        } else {
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
                    CommonUtils.startActivity((AppCompatActivity) mContext, AddCountryActivityActivity.class, bundle, false);
                    break;
            }
        }
    }

    private void showReasonAlert(String reason) {
        CommonUtils.showAlert(mContext, reason, "OK", null, new AlertDialog.OnClickListner() {
            @Override
            public void onPositiveClick() {

            }

            @Override
            public void onNegativeClick() {

            }
        });
    }

    private void showDeleteAlert(final int position) {
        CommonUtils.showAlert(mContext, "Are you sure, want to delete?", "Ok", "Cancel", new AlertDialog.OnClickListner() {
            @Override
            public void onPositiveClick() {
                ((MyCountryActivity) mContext).deleteCountryActivity(position);

            }

            @Override
            public void onNegativeClick() {

            }
        });
    }
}
