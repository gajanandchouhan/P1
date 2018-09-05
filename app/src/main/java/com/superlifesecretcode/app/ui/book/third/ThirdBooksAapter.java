package com.superlifesecretcode.app.ui.book.third;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.superlifesecretcode.app.R;
import com.superlifesecretcode.app.SuperLifeSecretCodeApp;
import com.superlifesecretcode.app.data.model.language.LanguageResponseData;
import com.superlifesecretcode.app.data.persistance.SuperLifeSecretPreferences;
import com.superlifesecretcode.app.ui.book.first.BookBean;
import com.superlifesecretcode.app.ui.book.first.FirstBookActivity;
import com.superlifesecretcode.app.ui.player.PLayerPopupActivityYesNO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Divya on 26-02-2018.
 */

public class ThirdBooksAapter extends RecyclerView.Adapter<ThirdBooksAapter.ItemViewHolder> {
    private final List<BookBean> list;
    private Context mContext;
    ThirsBookActivity.onDeleteListener onDeleteListener;

    public ThirdBooksAapter(List<BookBean> list, Context mContext, ThirsBookActivity.onDeleteListener onDeleteListener) {
        this.list = list;
        this.mContext = mContext;
        this.onDeleteListener = onDeleteListener;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_book_third_list, parent, false));
    }

    @Override
    public void onBindViewHolder(final ItemViewHolder holder, final int position) {
        holder.textview_book_name.setText(list.get(position).getName());
        holder.textview_bookprice.setText(""+ SuperLifeSecretPreferences.getInstance().getString("book_currency")+" " + list.get(position).getPrice());
        Log.e("adapter" + position, "" + list.get(position).getQuantity());
        holder.edittext_quantity.setText("" + list.get(position).getQuantity());
        holder.edittext_quantity.setSelection(holder.edittext_quantity.getText().length());


        holder.edittext_quantity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (holder.edittext_quantity.getText().toString().equals("")) {
                    holder.edittext_quantity.setText("1");
                    onDeleteListener.onQualtityChanged("1",position);
                }else {
                    onDeleteListener.onQualtityChanged(holder.edittext_quantity.getText().toString(),position);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        holder.delete_iamge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deletePopup(position);
            }
        });
    }

    private void deletePopup(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        LanguageResponseData conversionData = SuperLifeSecretCodeApp.getInstance().getConversionData();
        if (conversionData != null) {
            builder.setMessage("Do you really want to delete");
            builder.setPositiveButton(conversionData.getYes(), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    onDeleteListener.onItemDelete(position);
                }
            }).setNegativeButton(conversionData.getNo(), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            AlertDialog alert = builder.create();
            alert.setTitle(R.string.app_name);
            alert.show();
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView delete_iamge;
        TextView textview_book_name;
        TextView textview_bookprice;
        EditText edittext_quantity;
        //LinearLayout linear_select;

        public ItemViewHolder(View itemView) {
            super(itemView);
//            plus = itemView.findViewById(R.id.plus);
//            minus = itemView.findViewById(R.id.minus);
            delete_iamge = itemView.findViewById(R.id.delete_iamge);
            textview_book_name = itemView.findViewById(R.id.textview_book_name);
            textview_bookprice = itemView.findViewById(R.id.textview_bookprice);
            edittext_quantity = itemView.findViewById(R.id.edittext_quantity);
            //linear_select = itemView.findViewById(R.id.linear_select);
        }

        @Override
        public void onClick(View v) {
        }
    }
}
