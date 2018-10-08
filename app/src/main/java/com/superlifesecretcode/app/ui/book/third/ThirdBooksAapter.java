package com.superlifesecretcode.app.ui.book.third;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Paint;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.superlifesecretcode.app.R;
import com.superlifesecretcode.app.SuperLifeSecretCodeApp;
import com.superlifesecretcode.app.data.model.language.LanguageResponseData;
import com.superlifesecretcode.app.data.persistance.SuperLifeSecretPreferences;
import com.superlifesecretcode.app.ui.book.first.BookBean;
import com.superlifesecretcode.app.ui.book.first.Discount;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Divya on 26-02-2018.
 */

public class ThirdBooksAapter extends RecyclerView.Adapter<ThirdBooksAapter.ItemViewHolder> {
    private final List<BookBean> list;
    private Context mContext;
    ThirsBookActivity.onDeleteListener onDeleteListener;
    Handler handler;

    public ThirdBooksAapter(List<BookBean> list, Context mContext, ThirsBookActivity.onDeleteListener onDeleteListener) {
        this.list = list;
        this.mContext = mContext;
        this.onDeleteListener = onDeleteListener;
        handler = new Handler();
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_book_third_list, parent, false));
    }

    @Override
    public void onBindViewHolder(final ItemViewHolder holder, final int position) {
        holder.textview_book_name.setText(list.get(position).getName());
        try {
            holder.textview_bookprice.setText("" + SuperLifeSecretPreferences.getInstance().getString("book_currency") + " " + String.format(Locale.getDefault(), "%,.2f", (list.get(position).getPrice() - list.get(position).getDiscount_applied())));
        } catch (Exception e) {
            holder.textview_bookprice.setText("" + SuperLifeSecretPreferences.getInstance().getString("book_currency") + " " + (list.get(position).getPrice() - list.get(position).getDiscount_applied()));
        }
        holder.edittext_quantity.setText("" + list.get(position).getQuantity());
        holder.edittext_quantity.setSelection(holder.edittext_quantity.getText().length());
        holder.textview_bookprice_cut.setPaintFlags(holder.textview_bookprice_cut.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        holder.textview_bookprice_cut.setText("" + SuperLifeSecretPreferences.getInstance().getString("book_currency") + " " + String.format(Locale.getDefault(), "%,.2f", list.get(position).getPrice()));
//        if (list.get(position).getPrice()==list.get(position).getPrice_after_discount()){
        if (list.get(position).isIs_discounted()) {
            holder.textview_bookprice_cut.setVisibility(View.VISIBLE);
        } else {
            holder.textview_bookprice_cut.setVisibility(View.GONE);
        }
        holder.edittext_quantity.setTag(position);
//        holder.edittext_quantity.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                return false;
//            }
//        });
//        holder.edittext_quantity.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//            @Override
//            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//                if (actionId == EditorInfo.IME_ACTION_DONE) {
//                    InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
//                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
//                    if (holder.edittext_quantity.getText().toString().equals("")) {
//                        onDeleteListener.onQualtityChanged("0", position);
//                    }
//                    if (!holder.edittext_quantity.getText().toString().equals("")) {
//                        onDeleteListener.onQualtityChanged(holder.edittext_quantity.getText().toString(), position);
//                    }
//                    return true;
//                }
//                return false;
//            }
//        });

       /* holder.edittext_quantity.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            *//*    if (!holder.edittext_quantity.getText().toString().equals("")) {
                    onDeleteListener.onQualtityChanged(holder.edittext_quantity.getText().toString(), position);
                }*//*
            }

            @Override
            public void afterTextChanged(final Editable s) {

                *//*if (holder.edittext_quantity.getText().toString().equals("")) {
                    // holder.edittext_quantity.setText("0");
                    onDeleteListener.onQualtityChanged("0", position);
                }*//*
            }
        });*/

        holder.delete_iamge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deletePopup(position, list.get(position).getName());
            }
        });
    }

    private void deletePopup(final int position, String name) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        LanguageResponseData conversionData = SuperLifeSecretCodeApp.getInstance().getConversionData();
        if (conversionData != null) {
            builder.setMessage(conversionData.getWant_delete() + " " + name);
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

    class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, TextWatcher {

        ImageView delete_iamge;
        TextView textview_book_name;
        TextView textview_bookprice, textview_bookprice_cut;
        EditText edittext_quantity;
        private Timer timer = new Timer();
        private final long DELAY = 500; // milliseconds

        public ItemViewHolder(View itemView) {
            super(itemView);
            delete_iamge = itemView.findViewById(R.id.delete_iamge);
            textview_book_name = itemView.findViewById(R.id.textview_book_name);
            textview_bookprice = itemView.findViewById(R.id.textview_bookprice);
            textview_bookprice_cut = itemView.findViewById(R.id.textview_bookprice_cut);
            edittext_quantity = itemView.findViewById(R.id.edittext_quantity);
            edittext_quantity.addTextChangedListener(this);
        }

        @Override
        public void onClick(View v) {
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(final CharSequence s, int start, int before, int count) {
            timer.cancel();
            timer = new Timer();
            timer.schedule(
                    new TimerTask() {
                        @Override
                        public void run() {
                            // TODO: do what you need here (refresh list)
                            // you will probably need to use runOnUiThread(Runnable action) for some specific actions
                            String s1 = s.toString();
                            if (!s1.isEmpty()) {
                                updateItem(s1, (Integer) edittext_quantity.getTag(), textview_bookprice_cut, textview_bookprice);
                                //onDeleteListener.onQualtityChanged(s1, (Integer) edittext_quantity.getTag());
                            } else {
                                updateItem("0", (Integer) edittext_quantity.getTag(), textview_bookprice_cut, textview_bookprice);

                            }
                        }
                    },
                    DELAY
            );
        }

        @Override
        public void afterTextChanged(final Editable s) {

        }
    }

    private void updateItem(String s, final int position, final TextView cutPrice, final TextView price) {
        int qty = Integer.parseInt(s);
        list.get(position).setQuantity(qty);
        ArrayList<Discount> discountArrayList = list.get(position).getDiscount();
        if (discountArrayList != null && discountArrayList.size() != 0) {
            for (Discount discountDetail : discountArrayList) {
                double book_price = list.get(position).getPrice();
                int min_quantity = Integer.parseInt(discountDetail.getMin_qty());
                int max_quantity = Integer.parseInt(discountDetail.getMax_qty());
                int book_quantity = list.get(position).getQuantity();
                list.get(position).setPrice_after_discount(book_price);
                if (book_quantity >= min_quantity && book_quantity <= max_quantity) {
                    if (discountDetail.getDiscount_type().equals("1")) {
                        double no = discountDetail.getDiscount_amount();
                        double discount = ((book_price * no) / 100);
                        list.get(position).setDiscount_applied(discount);
                        list.get(position).setIs_discounted(true);
                        list.get(position).setPrice_after_discount(book_price - discount);
                        break;
                    } else {
                        double discount = discountDetail.getDiscount_amount();
                        list.get(position).setDiscount_applied(discount);
                        list.get(position).setPrice_after_discount(book_price - discount);
                        list.get(position).setIs_discounted(true);
                        break;
                    }
                }
                else {
                    Discount maxQty = getMaxQty(discountArrayList);
                    if (maxQty != null && book_quantity > Integer.parseInt(maxQty.getMax_qty())) {
                        if (maxQty.getDiscount_type().equals("1")) {
                            double no = maxQty.getDiscount_amount();
                            double discount = ((book_price * no) / 100);
                            list.get(position).setDiscount_applied(discount);
                            list.get(position).setIs_discounted(true);
                            list.get(position).setPrice_after_discount(book_price - discount);
                            break;
                        } else {
                            double discount = maxQty.getDiscount_amount();
                            list.get(position).setDiscount_applied(discount);
                            list.get(position).setPrice_after_discount(book_price - discount);
                            list.get(position).setIs_discounted(true);
                            break;
                        }
                    } else {
                        list.get(position).setDiscount_applied(0);
                        list.get(position).setIs_discounted(false);
                        list.get(position).setPrice_after_discount(book_price);
                    }

                }
            }

        }
        handler.post(new Runnable() {
            @Override
            public void run() {
                try {
                    price.setText("" + SuperLifeSecretPreferences.getInstance().getString("book_currency") + " " + String.format(Locale.getDefault(), "%,.2f", (list.get(position).getPrice() - list.get(position).getDiscount_applied())));
                } catch (Exception e) {
                    price.setText("" + SuperLifeSecretPreferences.getInstance().getString("book_currency") + " " + (list.get(position).getPrice() - list.get(position).getDiscount_applied()));
                }
                cutPrice.setPaintFlags(cutPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                cutPrice.setText("" + SuperLifeSecretPreferences.getInstance().getString("book_currency") + " " + String.format(Locale.getDefault(), "%,.2f", list.get(position).getPrice()));
//        if (list.get(position).getPrice()==list.get(position).getPrice_after_discount()){
                if (list.get(position).isIs_discounted()) {
                    cutPrice.setVisibility(View.VISIBLE);
                } else {
                    cutPrice.setVisibility(View.GONE);
                }
            }
        });

        onDeleteListener.onQualtityChanged();
    }

    private Discount getMaxQty(ArrayList<Discount> discountArrayList) {
        Discount discountMax = null;
        if (discountArrayList == null || discountArrayList.isEmpty()) {
            return null;
        }
        int maxQty = Integer.parseInt(discountArrayList.get(0).getMax_qty());
        for (Discount discount : discountArrayList) {
            if (Integer.parseInt(discount.getMax_qty()) >= maxQty) {
                maxQty = Integer.parseInt(discount.getMax_qty());
                discountMax = discount;
            }
        }
        return discountMax;

    }


}
