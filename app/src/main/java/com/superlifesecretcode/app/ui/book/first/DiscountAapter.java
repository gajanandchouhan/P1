package com.superlifesecretcode.app.ui.book.first;

import android.content.Context;
import android.os.Build;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.superlifesecretcode.app.R;
import com.superlifesecretcode.app.data.model.language.LanguageResponseData;
import com.superlifesecretcode.app.data.persistance.SuperLifeSecretPreferences;
import com.superlifesecretcode.app.util.ImageLoadUtils;

import org.w3c.dom.Text;

import java.util.List;
import java.util.Locale;

public class DiscountAapter extends RecyclerView.Adapter<DiscountAapter.ItemViewHolder> {
    private final List<Discount> list;
    private Context mContext;
    LanguageResponseData languageResponseData;

    public DiscountAapter(List<Discount> list, Context mContext) {
        this.list = list;
        this.mContext = mContext;
        languageResponseData = SuperLifeSecretPreferences.getInstance().getConversionData();
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_book_discount, parent, false));
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, final int position) {
        if(list!=null){
            if (list.get(position).getDiscount_type()!=null){
                if (list.get(position).getDiscount_type().equals("1")){
                    holder.tv_percentage.setText(list.get(position).getDiscount_amount()+"%");
                }else {
                    holder.tv_percentage.setText(SuperLifeSecretPreferences.getInstance().getString("book_currency")+" "+list.get(position).getDiscount_amount());
                }
            }
        }
//        String s = languageResponseData.g
        holder.tv_discont_sentence.setText(String.format("%s-%s %s", list.get(position).getMin_qty(), list.get(position).getMax_qty(), languageResponseData.getBooks_with()));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tv_percentage;
        TextView tv_discont_sentence;
        TextView tv_discont_lbl;


        public ItemViewHolder(View itemView) {
            super(itemView);
            tv_percentage = itemView.findViewById(R.id.tv_percentage);
            tv_discont_sentence = itemView.findViewById(R.id.tv_discont_sentence);
            tv_discont_lbl=itemView.findViewById(R.id.tv_discount_lbl);
            tv_discont_lbl.setText(languageResponseData.getDiscount_off());
        }

        @Override
        public void onClick(View v) {
        }
    }
}
