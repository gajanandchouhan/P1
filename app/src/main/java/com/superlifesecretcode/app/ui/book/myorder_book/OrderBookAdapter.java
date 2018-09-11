package com.superlifesecretcode.app.ui.book.myorder_book;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.superlifesecretcode.app.R;
import com.superlifesecretcode.app.data.model.language.LanguageResponseData;
import com.superlifesecretcode.app.data.persistance.SuperLifeSecretPreferences;
import java.util.ArrayList;
/**
 * Created by Divya on 26-02-2018.
 */

public class OrderBookAdapter extends RecyclerView.Adapter<OrderBookAdapter.ItemViewHolder> {
    OrderBookActivity orderBookActivity;
    ArrayList<Order> orderArrayList;
    OrderBookActivity.BookListener bookListener;
    LanguageResponseData languageResponseData;

    public OrderBookAdapter(OrderBookActivity orderBookActivity, ArrayList<Order> orderArrayList, OrderBookActivity.BookListener bookListener) {
        this.orderBookActivity=orderBookActivity;
        this.orderArrayList=orderArrayList;
        this.bookListener=bookListener;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_myorder, parent, false));
    }

    @Override
    public void onBindViewHolder(final ItemViewHolder holder, final int position) {

        languageResponseData = SuperLifeSecretPreferences.getInstance().getConversionData();


        holder.order_id.setText(languageResponseData.getOrder_id());
        holder.date.setText(languageResponseData.getDate());
        holder.amount.setText(languageResponseData.getAmount());
        holder.status.setText(languageResponseData.getStatus());
        holder.textview_detail.setText(languageResponseData.getView_detail());

        holder.textview_orderid.setText(orderArrayList.get(position).getOrder_code());
        holder.textview_status.setText(orderArrayList.get(position).getOrder_status());
        holder.textview_date.setText(orderArrayList.get(position).getOrder_date());
        holder.textview_amount.setText(""+ SuperLifeSecretPreferences.getInstance().getString("book_currency")+" "+orderArrayList.get(position).getTotal_amount());
        holder.textview_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bookListener.onClickDetail(orderArrayList.get(position).getOrder_id());
            }
        });
    }

    @Override
    public int getItemCount() {
        return orderArrayList.size();
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {

        TextView textview_orderid , textview_amount , textview_date , textview_status , textview_detail;
        TextView order_id , amount , date , status;

        public ItemViewHolder(View itemView) {
            super(itemView);
            textview_orderid = itemView.findViewById(R.id.textview_orderid);
            textview_amount = itemView.findViewById(R.id.textview_amount);
            textview_date = itemView.findViewById(R.id.textview_date);
            textview_status = itemView.findViewById(R.id.textview_status);
            textview_detail = itemView.findViewById(R.id.textview_detail);

            order_id = itemView.findViewById(R.id.order_id);
            amount = itemView.findViewById(R.id.amount);
            date = itemView.findViewById(R.id.date);
            status = itemView.findViewById(R.id.status);
        }
    }
}
