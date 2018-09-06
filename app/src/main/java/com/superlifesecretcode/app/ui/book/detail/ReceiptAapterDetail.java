package com.superlifesecretcode.app.ui.book.detail;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.superlifesecretcode.app.R;
import com.superlifesecretcode.app.util.ImageLoadUtils;
import java.util.ArrayList;

/**
 * Created by Divya on 26-02-2018.
 */

public class ReceiptAapterDetail extends RecyclerView.Adapter<ReceiptAapterDetail.ItemViewHolder> {
    ArrayList<Receipt> receipt_list;
    private Context fifthBookActivity;
    MyOrderDetailActivity.onReceiptSelector receiptListene;


    public ReceiptAapterDetail(MyOrderDetailActivity myOrderDetailActivity, ArrayList<Receipt> receipts, MyOrderDetailActivity.onReceiptSelector onReceiptSelector) {
        this.receipt_list = receipts;
        this.fifthBookActivity = myOrderDetailActivity;
        receiptListene = onReceiptSelector;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_reciept_, parent, false));
    }

    @Override
    public void onBindViewHolder(final ItemViewHolder holder, final int position) {
        //holder.imageview_receipt.setImageURI(receipt_list.get(position).getReceipt_uri());
        try {
            ImageLoadUtils.loadImage(receipt_list.get(position).getReceipt_image(), holder.imageview_receipt);
        } catch (Exception e) {
            e.printStackTrace();
        }

        holder.imageview_receipt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                receiptListene.clickReceipt(position);
            }
        });
//        holder.cross.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                receiptListene.onDeleteImage(position);
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return receipt_list.size();
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {

        ImageView imageview_receipt;

        public ItemViewHolder(View itemView) {
            super(itemView);
            imageview_receipt = itemView.findViewById(R.id.imageview_receipt);
        }
    }
}
