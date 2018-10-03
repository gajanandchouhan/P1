package com.superlifesecretcode.app.ui.book.third;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
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
import com.superlifesecretcode.app.util.CommonUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
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
        try {
            holder.textview_bookprice.setText(""+ SuperLifeSecretPreferences.getInstance().getString("book_currency")+" " + String.format(Locale.getDefault(), "%,.2f", (list.get(position).getPrice() - list.get(position).getDiscount_applied())));
        }catch (Exception e){
            holder.textview_bookprice.setText(""+ SuperLifeSecretPreferences.getInstance().getString("book_currency")+" " + (list.get(position).getPrice() - list.get(position).getDiscount_applied()));
        }
        holder.edittext_quantity.setText("" + list.get(position).getQuantity());
        holder.edittext_quantity.setSelection(holder.edittext_quantity.getText().length());
        holder.textview_bookprice_cut.setPaintFlags(holder.textview_bookprice_cut.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

        holder.textview_bookprice_cut.setText(""+ SuperLifeSecretPreferences.getInstance().getString("book_currency")+" " + String.format(Locale.getDefault(), "%,.2f", list.get(position).getPrice()));
        holder.edittext_quantity.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });
        holder.edittext_quantity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!holder.edittext_quantity.getText().toString().equals("")) {
                    onDeleteListener.onQualtityChanged(holder.edittext_quantity.getText().toString(),position);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (holder.edittext_quantity.getText().toString().equals("")) {
                   // holder.edittext_quantity.setText("0");
                    onDeleteListener.onQualtityChanged("0",position);
                }
            }
        });

        holder.delete_iamge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deletePopup(position,list.get(position).getName());
            }
        });
    }

    private void deletePopup(final int position, String name) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        LanguageResponseData conversionData = SuperLifeSecretCodeApp.getInstance().getConversionData();
        if (conversionData != null) {
            builder.setMessage(conversionData.getWant_delete()+" "+name);
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
        TextView textview_bookprice , textview_bookprice_cut;
        EditText edittext_quantity;

        public ItemViewHolder(View itemView) {
            super(itemView);
            delete_iamge = itemView.findViewById(R.id.delete_iamge);
            textview_book_name = itemView.findViewById(R.id.textview_book_name);
            textview_bookprice = itemView.findViewById(R.id.textview_bookprice);
            textview_bookprice_cut = itemView.findViewById(R.id.textview_bookprice_cut);
            edittext_quantity = itemView.findViewById(R.id.edittext_quantity);
        }

        @Override
        public void onClick(View v) {
        }
    }
}
