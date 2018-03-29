package com.superlifesecretcode.app.ui.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.superlifesecretcode.app.R;
import com.superlifesecretcode.app.data.model.AlertModel;
import com.superlifesecretcode.app.data.model.category.CategoryResponseData;
import com.superlifesecretcode.app.data.persistance.SuperLifeSecretPreferences;
import com.superlifesecretcode.app.ui.main.MainActivity;
import com.superlifesecretcode.app.ui.subcategory.SubCategoryActivity;
import com.superlifesecretcode.app.ui.webview.WebViewActivity;
import com.superlifesecretcode.app.util.CommonUtils;
import com.superlifesecretcode.app.util.ImageLoadUtils;

import java.util.List;
import java.util.Random;

/**
 * Created by Divya on 26-02-2018.
 */

public class SubListAdapter extends RecyclerView.Adapter<SubListAdapter.ItemViewHolder> {
    private final List<CategoryResponseData> list;
    private final String colorCode;
    private Context mContext;

    public SubListAdapter(List<CategoryResponseData> list, Context mContext, String colorCode) {
        this.list = list;
        this.mContext = mContext;
        this.colorCode = colorCode;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_subcategory, parent, false));
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        holder.textView.setText(list.get(position).getTitle());
        holder.textViewChar.setText(list.get(position).getTitle().substring(0, 1));
        if (list.get(position).getImage() != null) {
            holder.imageView.setVisibility(View.VISIBLE);
            ImageLoadUtils.loadImage(list.get(position).getImage(), holder.imageView);
        } else {
            holder.imageView.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView imageView;
        TextView textView;
        TextView textViewChar;

        public ItemViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            textView = itemView.findViewById(R.id.textView_item);
            textViewChar = itemView.findViewById(R.id.textView_char);
            GradientDrawable gradientDrawable = (GradientDrawable) textViewChar.getBackground();
            gradientDrawable.setColor(Color.parseColor(colorCode));
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int clickedPostion = getAdapterPosition();
            if (list.get(clickedPostion).getAlert() != null && list.get(clickedPostion).getAlert().equalsIgnoreCase("1")) {
                AlertModel alertModel = new AlertModel();
                alertModel.setCount(Integer.parseInt(list.get(clickedPostion).getAlert_count()));
                alertModel.setId(list.get(clickedPostion).getId());
                if (!SuperLifeSecretPreferences.getInstance().getAcceptedIds().contains(alertModel)) {
                    showAlert(list.get(clickedPostion).getAlert_text(), clickedPostion, null, null);
                } else {
                    List<AlertModel> alertModelList = SuperLifeSecretPreferences.getInstance().getAcceptedIds();
                    int index = alertModelList.indexOf(alertModel);
                    AlertModel alertModel1 = alertModelList.get(index);
                    if (alertModel1.getCount() == 0) {
                        openNext(clickedPostion);
                    } else {
                        showAlert(list.get(clickedPostion).getAlert_text(), clickedPostion, alertModel1, alertModelList);
                    }

                }
            } else {
                openNext(clickedPostion);
            }
        }
    }

    private void showAlert(final String alert_text, final int clikedPostion, final AlertModel alertModel1, final List<AlertModel> alertModelList) {
        String positive_resp = list.get(clikedPostion).getPositive_resp();
        String negative_resp = list.get(clikedPostion).getNegative_resp();
        CommonUtils.showAlert(mContext, alert_text, positive_resp, negative_resp, new CommonUtils.ClickListner() {
            @Override
            public void onPositiveClick() {
                if (alertModel1 != null) {
                    alertModel1.setCount(alertModel1.getCount() - 1);
                    SuperLifeSecretPreferences.getInstance().updateAlertList(alertModelList);
                } else {
                    AlertModel alertModel = new AlertModel();
                    alertModel.setId(list.get(clikedPostion).getId());
                    alertModel.setCount(Integer.parseInt(list.get(clikedPostion).getAlert_count()) - 1);
                    SuperLifeSecretPreferences.getInstance().setAlertAccepted(alertModel);
                }
                openNext(clikedPostion);
            }

            @Override
            public void onNegativeClick() {

            }
        });
    }

    private void openNext(int position) {
        Bundle bundle = new Bundle();
        bundle.putString("title", list.get(position).getTitle());
        bundle.putBoolean("is_link", list.get(position).getType().equalsIgnoreCase("1"));
        bundle.putString("url", list.get(position).getLink());
        bundle.putString("content", list.get(position).getContent());
        CommonUtils.startActivity((AppCompatActivity) mContext, WebViewActivity.class, bundle, false);
    }
}
