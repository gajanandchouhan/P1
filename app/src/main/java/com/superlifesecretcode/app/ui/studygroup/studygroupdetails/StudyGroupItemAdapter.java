package com.superlifesecretcode.app.ui.studygroup.studygroupdetails;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.superlifesecretcode.app.R;
import com.superlifesecretcode.app.data.model.studygroups.studygroupitem.StudyGroupItemData;
import com.superlifesecretcode.app.player.activity.AudioPlayerActivity;
import com.superlifesecretcode.app.player.activity.VideoPlayerActivity;
import com.superlifesecretcode.app.ui.webview.WebViewActivity;
import com.superlifesecretcode.app.util.CommonUtils;
import com.superlifesecretcode.app.util.ConstantLib;
import com.superlifesecretcode.app.util.ImageLoadUtils;

import org.w3c.dom.Text;

import java.util.List;

public class StudyGroupItemAdapter extends RecyclerView.Adapter<StudyGroupItemAdapter.ItemViewHolder> {

    private Context mContext;
    private List<StudyGroupItemData> list;
    private String subscirptionStatus;

    public StudyGroupItemAdapter(Context mContext, List list) {
        this.mContext = mContext;
        this.list = list;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_study_group_item, parent, false);
        return new ItemViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        StudyGroupItemData studyGroupItemData = list.get(position);
        switch (studyGroupItemData.getItem_type_id()) {
            case ConstantLib.TYPE_AUDIO_ITEM:
                holder.textViewDuration.setVisibility(View.VISIBLE);
                holder.textViewDuration.setText(studyGroupItemData.getDuration());
                break;
            case ConstantLib.TYPE_VIDEO_ITEM:
                holder.textViewDuration.setVisibility(View.VISIBLE);
                holder.textViewDuration.setText(studyGroupItemData.getDuration());
                break;
            case ConstantLib.TYPE_WEB_LINK:
                holder.textViewDuration.setVisibility(View.GONE);
                break;
            case ConstantLib.TYPE_TEXT:
                holder.textViewDuration.setVisibility(View.GONE);
                break;
        }
        holder.textViewName.setText(studyGroupItemData.getItem_title());
        holder.textViewDesc.setText(studyGroupItemData.getItem_description());
        holder.textViewExpiry.setText(String.format("Expire on : %s", CommonUtils.getformattedDateFromString(ConstantLib.INPUT_DATE_ONLY_FORMATE, ConstantLib.OUTPUT_DATE_FORMATE, studyGroupItemData.getItem_expiry_date(), false, null)));
        ImageLoadUtils.loadImage(studyGroupItemData.getItem_icon(), holder.imageView);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setSubscirptionStatus(String subscirptionStatus) {
        this.subscirptionStatus = subscirptionStatus;
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView textViewName;
        TextView textViewDesc;
        TextView textViewDuration;
        TextView textViewExpiry;
        ImageView imageView;

        public ItemViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            textViewName = itemView.findViewById(R.id.textView_title);
            textViewDesc = itemView.findViewById(R.id.textView_desc);
            textViewDuration = itemView.findViewById(R.id.text_view_duration);
            textViewExpiry = itemView.findViewById(R.id.textView_expire);
            imageView = itemView.findViewById(R.id.image_view);
        }

        @Override
        public void onClick(View v) {
            StudyGroupItemData studyGroupItemData = list.get(getAdapterPosition());
            if (subscirptionStatus.equals(ConstantLib.STATUS_GROUP_SUBSCRIBED)) {
                Bundle bundle = new Bundle();
                if (studyGroupItemData.getItem_type_id().equals(ConstantLib.TYPE_AUDIO_ITEM)) {
                    bundle.putString("name", studyGroupItemData.getItem_title());
                    bundle.putString("url", studyGroupItemData.getItem_url());
                    bundle.putString("image", studyGroupItemData.getItem_icon());
                    CommonUtils.startActivity((AppCompatActivity) mContext, AudioPlayerActivity.class, bundle, false);
                } else if (studyGroupItemData.getItem_type_id().equals(ConstantLib.TYPE_VIDEO_ITEM)) {
                    bundle.putString("video", studyGroupItemData.getItem_url());
                    if (isValidUrl(studyGroupItemData.getItem_url())){
                        bundle.putString("title", studyGroupItemData.getItem_title());
                        bundle.putBoolean("is_link", true);
                        bundle.putString("url", studyGroupItemData.getItem_url());
                        bundle.putString("content", studyGroupItemData.getItem_html_text());
                        CommonUtils.startActivity((AppCompatActivity) mContext, WebViewActivity.class, bundle, false);
                    }else{
                        CommonUtils.startActivity((AppCompatActivity) mContext, VideoPlayerActivity.class, bundle, false);
                    }
                  /*  if (Patterns..matcher(studyGroupItemData.getItem_url()).matches()) {
                        CommonUtils.startActivity((AppCompatActivity) mContext, VideoPlayerActivity.class, bundle, false);
                    }else {
                        bundle.putString("title", studyGroupItemData.getItem_title());
                        bundle.putBoolean("is_link", true);
                        bundle.putString("url", studyGroupItemData.getItem_url());
                        bundle.putString("content", studyGroupItemData.getItem_html_text());
                        CommonUtils.startActivity((AppCompatActivity) mContext, WebViewActivity.class, bundle, false);
                    }*/
                } else {
                    bundle.putString("title", studyGroupItemData.getItem_title());
                    bundle.putBoolean("is_link", studyGroupItemData.getItem_type_id().equalsIgnoreCase(ConstantLib.TYPE_WEB_LINK));
                    bundle.putString("url", studyGroupItemData.getItem_url());
                    bundle.putString("content", studyGroupItemData.getItem_html_text());
                    CommonUtils.startActivity((AppCompatActivity) mContext, WebViewActivity.class, bundle, false);
                }
            } else {
                ((StudyGroupDetailsActivity) mContext).showAlertSubscriptionStatus();
            }
        }
    }
    private boolean isValidUrl(String url) {

        if (url == null) {
            return false;
        }
        if (URLUtil.isValidUrl(url)) {
            // Check host of url if youtube exists
            Uri uri = Uri.parse(url);
            if ("www.youtube.com".equals(uri.getHost())) {
                return true;
            }
            // Other way You can check into url also like
            //if (url.startsWith("https://www.youtube.com/")) {
            //return true;
            //}
        }
        // In other any case
        return false;
    }
}
