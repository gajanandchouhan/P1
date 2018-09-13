package com.superlifesecretcode.app.ui.book.five;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.superlifesecretcode.app.R;
import com.superlifesecretcode.app.util.ImageLoadUtils;
import com.superlifesecretcode.app.util.ImagePickerUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Divya on 26-02-2018.
 */

public class ReceiptAapter extends RecyclerView.Adapter<ReceiptAapter.ItemViewHolder> {
    ArrayList<Receipt> receipt_list;
    private Context fifthBookActivity;
    FifthBookActivity.ReceiptListener receiptListene;
    boolean crossshowornot;

    public ReceiptAapter(ArrayList<Receipt> receipt_list, FifthBookActivity fifthBookActivity, FifthBookActivity.ReceiptListener receiptListener, boolean crossshowornot) {
        this.receipt_list = receipt_list;
        this.fifthBookActivity = fifthBookActivity;
        receiptListene = receiptListener;
        this.crossshowornot = crossshowornot;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_reciept, parent, false));
    }

    @Override
    public void onBindViewHolder(final ItemViewHolder holder, final int position) {
        holder.imageview_receipt.setImageURI(receipt_list.get(position).getReceipt_uri());
        try {
            ImageLoadUtils.loadImage(receipt_list.get(position).getReceipt_image_path(), holder.imageview_receipt);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (crossshowornot == true)
            holder.cross.setVisibility(View.VISIBLE);
        else holder.cross.setVisibility(View.GONE);

        holder.imageview_receipt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                receiptListene.onImageSelect();
            }
        });
        holder.cross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                receiptListene.onDeleteImage(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return receipt_list.size();
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {

        ImageView imageview_receipt;
        TextView cross;

        public ItemViewHolder(View itemView) {
            super(itemView);
            imageview_receipt = itemView.findViewById(R.id.imageview_receipt);
            cross = itemView.findViewById(R.id.cross);
        }
    }
}
