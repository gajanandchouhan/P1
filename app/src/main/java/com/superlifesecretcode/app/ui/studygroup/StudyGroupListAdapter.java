package com.superlifesecretcode.app.ui.studygroup;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.superlifesecretcode.app.R;
import com.superlifesecretcode.app.data.model.language.LanguageResponseData;
import com.superlifesecretcode.app.data.model.studygroups.StudyGroupDetails;
import com.superlifesecretcode.app.data.persistance.SuperLifeSecretPreferences;
import com.superlifesecretcode.app.ui.picker.AlertDialog;
import com.superlifesecretcode.app.ui.studygroup.studygroupdetails.StudyGroupDetailsActivity;
import com.superlifesecretcode.app.util.CommonUtils;
import com.superlifesecretcode.app.util.ConstantLib;
import com.superlifesecretcode.app.util.ImageLoadUtils;

import java.util.List;

public class StudyGroupListAdapter extends RecyclerView.Adapter<StudyGroupListAdapter.ViewHolder> {
    private List<StudyGroupDetails> list;
    private Context mContext;
    private LanguageResponseData conversionData;

    public StudyGroupListAdapter(List list, Context mContext) {
        this.list = list;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_study_group, parent, false);
        ViewHolder viewHolder = new ViewHolder(inflate);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        conversionData = SuperLifeSecretPreferences.getInstance().getConversionData();
        StudyGroupDetails studyGroupDetails = list.get(position);
        holder.textViewTitle.setText(studyGroupDetails.getGroup_name());
        holder.textViewDesc.setText(studyGroupDetails.getGroup_description());
        ImageLoadUtils.loadImage(studyGroupDetails.getGroup_image(), holder.imageView);
        switch (studyGroupDetails.getSubcription_status()) {
            case ConstantLib.STATUS_GROUP_NEW:
                holder.layoutStatus.setVisibility(View.GONE);
                holder.textViewSubscribe.setVisibility(View.GONE);
                holder.textViewReason.setVisibility(View.GONE);
                break;
            case ConstantLib.STATUS_GROUP_SUBSCRIBED:
                holder.layoutStatus.setVisibility(View.VISIBLE);
                holder.textViewStatus.setText(conversionData.getSubscribed());
                holder.textViewStatus.setBackgroundResource(R.drawable.bg_published);
                holder.textViewSubscribe.setVisibility(View.GONE);
                holder.textViewReason.setVisibility(View.GONE);
                break;
            case ConstantLib.STATUS_GROUP_PENDING:
                holder.layoutStatus.setVisibility(View.VISIBLE);
                holder.textViewStatus.setText(conversionData.getPending());
                holder.textViewStatus.setBackgroundResource(R.drawable.bg_pending);
                holder.textViewSubscribe.setVisibility(View.GONE);
                holder.textViewReason.setVisibility(View.GONE);
                break;
            case ConstantLib.STATUS_GROUP_EXPIRED:
                holder.layoutStatus.setVisibility(View.VISIBLE);
                holder.textViewStatus.setText(conversionData.getExpire_on());
                holder.textViewStatus.setText(conversionData.getRenew());
                holder.textViewStatus.setBackgroundResource(R.drawable.bg_declined);
                holder.textViewSubscribe.setVisibility(View.GONE);
                holder.textViewReason.setVisibility(View.GONE);
                break;
            case ConstantLib.STATUS_GROUP_REJECTED:
                holder.layoutStatus.setVisibility(View.VISIBLE);
                holder.textViewStatus.setText(conversionData.getRejected());
                holder.textViewReason.setVisibility(View.VISIBLE);
                holder.textViewStatus.setBackgroundResource(R.drawable.bg_declined);
                holder.textViewSubscribe.setVisibility(View.GONE);
                break;
        }
        holder.textViewStatus.setPadding(25, 0, 25, 5);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView textViewTitle;
        TextView textViewDesc;
        TextView textViewSubscribe;
        TextView textViewStatus;
        LinearLayout layoutStatus;
        TextView textViewReason;
        ImageView imageView;


        public ViewHolder(View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.textView_title);
            textViewDesc = itemView.findViewById(R.id.textView_desc);
            textViewSubscribe = itemView.findViewById(R.id.text_view_subscribe);
            textViewStatus = itemView.findViewById(R.id.text_view_status);
            textViewReason = itemView.findViewById(R.id.text_view_reason);
            imageView = itemView.findViewById(R.id.image_view_group);
            layoutStatus = itemView.findViewById(R.id.layout_status);
            itemView.setOnClickListener(this);
            textViewReason.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.text_view_reason) {
                CommonUtils.showAlert(mContext, list.get(getAdapterPosition()).getReason(), SuperLifeSecretPreferences.getInstance().getConversionData().getOk(), null, new AlertDialog.OnClickListner() {
                    @Override
                    public void onPositiveClick() {

                    }

                    @Override
                    public void onNegativeClick() {

                    }
                });
            } else {
                Bundle bundle = new Bundle();
                bundle.putSerializable("data", list.get(getAdapterPosition()));
                CommonUtils.startActivity((AppCompatActivity) mContext, StudyGroupDetailsActivity.class, bundle, false);
            }
        }
    }
}
