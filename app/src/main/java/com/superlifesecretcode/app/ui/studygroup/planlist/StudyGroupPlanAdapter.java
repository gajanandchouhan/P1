package com.superlifesecretcode.app.ui.studygroup.planlist;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.superlifesecretcode.app.R;
import com.superlifesecretcode.app.data.model.studygroups.studtgroupplans.StudyGroupPlanData;
import com.superlifesecretcode.app.ui.studygroup.paymentproof.PaymentProofActivity;

import java.util.List;
import java.util.Locale;

public class StudyGroupPlanAdapter extends RecyclerView.Adapter<StudyGroupPlanAdapter.ItemViewHolder> {

    private Context mContext;
    private List<StudyGroupPlanData> list;

    public StudyGroupPlanAdapter(Context mContext, List list) {
        this.mContext = mContext;
        this.list = list;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_study_group_plan, parent, false);
        return new ItemViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        StudyGroupPlanData studyGroupPlanData = list.get(position);
        holder.textViewName.setText(studyGroupPlanData.getPlan_title());
        holder.textViewDesc.setText(studyGroupPlanData.getPlan_description());
        holder.textViewCurrency.setText(studyGroupPlanData.getCurrency_symbol());
        try {
            if (studyGroupPlanData.getPlan_cost() != null && !studyGroupPlanData.getPlan_cost().isEmpty()) {
                holder.textViewAmount.setText(String.format(Locale.getDefault(), "%,.2f", Double.parseDouble(studyGroupPlanData.getPlan_cost())));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView textViewName;
        TextView textViewDesc;
        TextView textViewCurrency;
        TextView textViewAmount;
        ImageView imageView;

        public ItemViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            textViewName = itemView.findViewById(R.id.textView_title);
            textViewDesc = itemView.findViewById(R.id.textView_desc);
            textViewCurrency = itemView.findViewById(R.id.text_view_currency);
            textViewAmount = itemView.findViewById(R.id.text_view_amount);
        }

        @Override
        public void onClick(View v) {
            ((SubscriptionPlanListActivity)mContext).startPaymentConirmScreen(getAdapterPosition());
        }
    }
}
