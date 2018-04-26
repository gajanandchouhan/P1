package com.superlifesecretcode.app.ui.customizebar;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.superlifesecretcode.app.R;
import com.superlifesecretcode.app.data.model.SubcategoryModel;
import com.superlifesecretcode.app.data.model.allmenu.AllMenuResponseData;
import com.superlifesecretcode.app.data.persistance.SuperLifeSecretPreferences;
import com.superlifesecretcode.app.util.CommonUtils;

import java.util.List;

/**
 * Created by Divya on 26-02-2018.
 */

public class BottomBartAapter extends RecyclerView.Adapter<BottomBartAapter.ItemViewHolder> {
    private final List<AllMenuResponseData> list;
    private Context mContext;

    public List<AllMenuResponseData> getList() {
        return list;
    }

    public BottomBartAapter(Context mContext, List<AllMenuResponseData> allCategories) {
        this.mContext = mContext;
        list = allCategories;

    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bottom_bar, parent, false));
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        holder.textViewTitle.setText(list.get(position).getTitle());
        holder.checkBox.setSelected(list.get(position).isSelected());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView textViewTitle;
        ImageView checkBox;

        public ItemViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            textViewTitle = itemView.findViewById(R.id.textView_title);
            checkBox = itemView.findViewById(R.id.checkbox);
        }

        @Override
        public void onClick(View v) {
            AllMenuResponseData subcategoryModel = list.get(getAdapterPosition());
            if (!subcategoryModel.isSelected()) {
                if (shouldAdd()) {
                    subcategoryModel.setSelected(!subcategoryModel.isSelected());
                    notifyItemChanged(getAdapterPosition());
                } else {
                    CommonUtils.showToast(mContext, "You can select only four menu options.");
                }
            } else {
                subcategoryModel.setSelected(!subcategoryModel.isSelected());
                notifyItemChanged(getAdapterPosition());
            }

        }
    }

    private boolean shouldAdd() {
        int count = 0;
        for (AllMenuResponseData subcategoryModel : list) {
            if (subcategoryModel.isSelected()) {
                count = count + 1;
            }
        }
        return count < 4;
    }
}
