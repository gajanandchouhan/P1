package com.superlifesecretcode.app.ui.book.detail;

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


public class ReceiptFullPageAapterDetail extends RecyclerView.Adapter<ReceiptFullPageAapterDetail.ItemViewHolder> {
    ArrayList<Receipt> receipt_list;
    private Context fifthBookActivity;


    public ReceiptFullPageAapterDetail(MyOrderDetailActivity myOrderDetailActivity, ArrayList<Receipt> receipts) {
        this.receipt_list = receipts;
        this.fifthBookActivity = myOrderDetailActivity;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_full_page_reciept, parent, false));
    }

    @Override
    public void onBindViewHolder(final ItemViewHolder holder, final int position) {
        try {
            ImageLoadUtils.loadImage(receipt_list.get(position).getReceipt_image(), holder.imageview_receipt);
        } catch (Exception e) {
            e.printStackTrace();
        }
        holder.counter.setText(""+(position+1)+"/"+receipt_list.size());
    }

    @Override
    public int getItemCount() {
        return receipt_list.size();
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {

        ImageView imageview_receipt;
        TextView counter;

        public ItemViewHolder(View itemView) {
            super(itemView);
            imageview_receipt = itemView.findViewById(R.id.imageview_receipt);
            counter = itemView.findViewById(R.id.counter);
        }
    }
}
