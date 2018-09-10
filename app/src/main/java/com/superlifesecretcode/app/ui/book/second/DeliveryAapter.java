package com.superlifesecretcode.app.ui.book.second;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.superlifesecretcode.app.R;
import java.util.ArrayList;
import java.util.List;
/**
 * Created by Divya on 26-02-2018.
 */

public class DeliveryAapter extends RecyclerView.Adapter<DeliveryAapter.ItemViewHolder> {
    private final List<DeliveryData> list;
    private Context mContext;

    public DeliveryAapter(ArrayList<DeliveryData> list, SecondBookActivity mContext) {
        this.list = list;
        this.mContext = mContext;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_delivery, parent, false));
    }

    @Override
    public void onBindViewHolder(final ItemViewHolder holder, final int position) {
        holder.textview_bankname.setText(""+list.get(position).getRange_from()+" - "+list.get(position).getRange_to()+" = "+list.get(position).getDelivery_charge());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ItemViewHolder extends RecyclerView.ViewHolder  {

        TextView textview_bankname;

        public ItemViewHolder(View itemView) {
            super(itemView);
            textview_bankname = itemView.findViewById(R.id.textview_bankname);
        }
    }
}
