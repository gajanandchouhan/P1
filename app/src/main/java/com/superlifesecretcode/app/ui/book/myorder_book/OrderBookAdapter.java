package com.superlifesecretcode.app.ui.book.myorder_book;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.superlifesecretcode.app.R;
import com.superlifesecretcode.app.data.model.language.LanguageResponseData;
import com.superlifesecretcode.app.data.persistance.SuperLifeSecretPreferences;
import com.superlifesecretcode.app.ui.picker.AlertDialog;
import com.superlifesecretcode.app.util.CommonUtils;
import com.superlifesecretcode.app.util.ConstantLib;

import java.util.ArrayList;
import java.util.Locale;

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
        if (orderArrayList.get(position).getOrder_status().equals("1")){
            holder.textViewReason.setVisibility(View.GONE);
            holder.textview_status.setText(languageResponseData.getPending());
            holder.textViewReason.setVisibility(View.GONE);
        }else if (orderArrayList.get(position).getOrder_status().equals("2")){
            holder.textview_status.setText(languageResponseData.getSuccess());
        }else {
            holder.textViewReason.setVisibility(View.VISIBLE);
            holder.textViewReason.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showReasonAlert(orderArrayList.get(position).getReason());
                }
            });
            holder.textview_status.setText(languageResponseData.getDeclined());
        }
        holder.textview_date.setText(CommonUtils.getformattedDateFromString(ConstantLib.INPUT_DATE_TIME_FORMATE, "dd MMMM, yyyy hh:mm a", orderArrayList.get(position).getOrder_date() + " " + orderArrayList.get(position).getOrder_time(), true, "utc"));
       // holder.textview_date.setText(CommonUtils.getformattedDateFromString(ConstantLib.INPUT_DATE_TIME_FORMATE, "dd MMM, yy hh:mm a", list.get(position).getCreated_at(), true, "utc"));
       // holder.textview_date.setText(orderArrayList.get(position).getOrder_date());
        try {
            holder.textview_amount.setText("" + SuperLifeSecretPreferences.getInstance().getString("book_currency") + " " + String.format(Locale.getDefault(), "%,.2f",Double.parseDouble(orderArrayList.get(position).getTotal_amount())));
        }catch (Exception e){
            holder.textview_amount.setText(""+ SuperLifeSecretPreferences.getInstance().getString("book_currency")+" "+orderArrayList.get(position).getTotal_amount());
        }
        //holder.textview_amount.setText(""+ SuperLifeSecretPreferences.getInstance().getString("book_currency")+" "+orderArrayList.get(position).getTotal_amount());
        holder.textview_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bookListener.onClickDetail(orderArrayList.get(position).getOrder_id());
            }
        });


    }

    private void showReasonAlert(String reason) {
        CommonUtils.showAlert(orderBookActivity, reason, SuperLifeSecretPreferences.getInstance().getConversionData().getOk(), null, new AlertDialog.OnClickListner() {
            @Override
            public void onPositiveClick() {

            }

            @Override
            public void onNegativeClick() {

            }
        });
    }

    @Override
    public int getItemCount() {
        return orderArrayList.size();
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {

        TextView textview_orderid , textview_amount , textview_date , textview_status , textview_detail;
        TextView order_id , amount , date , status,textViewReason;

        public ItemViewHolder(View itemView) {
            super(itemView);
            textview_orderid = itemView.findViewById(R.id.textview_orderid);
            textview_amount = itemView.findViewById(R.id.textview_amount);
            textview_date = itemView.findViewById(R.id.textview_date);
            textview_status = itemView.findViewById(R.id.textview_status);
            textview_detail = itemView.findViewById(R.id.textview_detail);
            textViewReason = itemView.findViewById(R.id.text_view_reason);

            order_id = itemView.findViewById(R.id.order_id);
            amount = itemView.findViewById(R.id.amount);
            date = itemView.findViewById(R.id.date);
            status = itemView.findViewById(R.id.status);
        }
    }
}
