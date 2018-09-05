package com.superlifesecretcode.app.ui.book.five;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.superlifesecretcode.app.R;
import com.superlifesecretcode.app.util.ImageLoadUtils;

import java.util.ArrayList;

/**
 * Created by Divya on 26-02-2018.
 */

public class BigReceiptAapter extends RecyclerView.Adapter<BigReceiptAapter.ItemViewHolder> {
    ArrayList<Receipt> receipt_list;
    private Context fifthBookActivity;

    public BigReceiptAapter(ArrayList<Receipt> receipt_list, FifthBookActivity fifthBookActivity) {
        this.receipt_list = receipt_list;
        this.fifthBookActivity = fifthBookActivity;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_big_reciept, parent, false));
    }

    @Override
    public void onBindViewHolder(final ItemViewHolder holder, final int position) {
        holder.imageview_receipt.setImageURI(receipt_list.get(position).getReceipt_uri());
        try {
            ImageLoadUtils.loadImage(receipt_list.get(position).getReceipt_image_path(), holder.imageview_receipt);
        } catch (Exception e) {
            e.printStackTrace();
        }
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
