package com.superlifesecretcode.app.ui.book.first;

import android.animation.ValueAnimator;
import android.content.Context;
import android.os.Build;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.superlifesecretcode.app.R;
import com.superlifesecretcode.app.data.model.language.LanguageResponseData;
import com.superlifesecretcode.app.data.persistance.SuperLifeSecretPreferences;
import com.superlifesecretcode.app.util.ImageLoadUtils;

import java.util.List;
import java.util.Locale;

public class BookAapter extends RecyclerView.Adapter<BookAapter.ItemViewHolder> {
    private final List<BookBean> list;
    private Context mContext;
    FirstBookActivity.BookSelectedListener bookSelectedListener;
    LanguageResponseData languageResponseData;

    public BookAapter(List<BookBean> list, Context mContext, FirstBookActivity.BookSelectedListener bookSelectedListener) {
        this.list = list;
        this.mContext = mContext;
        this.bookSelectedListener = bookSelectedListener;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_book_list2, parent, false));
    }

    @Override
    public void onBindViewHolder(final ItemViewHolder holder, final int position) {
        languageResponseData = SuperLifeSecretPreferences.getInstance().getConversionData();
        holder.discount_recyclerview.setLayoutManager(new LinearLayoutManager(mContext));
        holder.discount_recyclerview.setAdapter(new DiscountAapter(list.get(position).getDiscount(), mContext));
        holder.textview_book_name.setText(list.get(position).getName());
        holder.textview_auther_name.setText(list.get(position).getAuthor_name());
        holder.tv_viewoffer.setText(languageResponseData.getView_offer());
        try {
            holder.textview_bookprice.setText("" + SuperLifeSecretPreferences.getInstance().getString("book_currency") + " " + String.format(Locale.getDefault(), "%,.2f", list.get(position).getPrice()));
        } catch (Exception e) {
        }
        ImageLoadUtils.loadImage(list.get(position).getImage(), holder.book);
        if (list.get(position).isSelected()) {
            holder.checkbox.setImageDrawable(mContext.getResources().getDrawable(R.drawable.check));
        } else {
            holder.checkbox.setImageDrawable(mContext.getResources().getDrawable(R.drawable.uncheck));
        }
        holder.linear_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (list.get(position).isSelected()) {
                    bookSelectedListener.onSelected(position, false);
                } else {
                    bookSelectedListener.onSelected(position, true);
                }
            }
        });
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Spanned spanned = Html.fromHtml(list.get(position).getDescription(), Html.FROM_HTML_MODE_LEGACY);
            holder.textview_book_descrption.setText(spanned);
        } else {
            Spanned spanned = Html.fromHtml(list.get(position).getDescription());
            holder.textview_book_descrption.setText(spanned);
        }
        if (list.get(position).getDiscount().size() == 0) {
            holder.offer_layout.setVisibility(View.GONE);
            //holder.discount_recyclerview.setVisibility(View.GONE);
        } else {
//            holder.discount_recyclerview.setVisibility(View.VISIBLE);
            holder.offer_layout.setVisibility(View.VISIBLE);
        }
        holder.offer_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (list.get(position).isExpand_collapase()) {
                    bookSelectedListener.onExpandCollapse(position, false);
                } else {
                    bookSelectedListener.onExpandCollapse(position, true);
                }
            }
        });
        if (list.get(position).isExpand_collapase()) {
            holder.discount_recyclerview.setVisibility(View.VISIBLE);
            //collapse(holder.discount_recyclerview, 500, 0);
        } else {
            holder.discount_recyclerview.setVisibility(View.GONE);
           // expand(holder.discount_recyclerview, 500, holder.discount_recyclerview.getHeight());
        }
//        ViewGroup.LayoutParams params=holder.discount_recyclerview.getLayoutParams();
//        params.height= (list.get(position).getDiscount().size()*300);
//        holder.discount_recyclerview.setLayoutParams(params);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        ImageView checkbox, book;
        TextView textview_book_name;
        TextView textview_auther_name;
        TextView textview_book_descrption;
        TextView textview_bookprice , tv_viewoffer;
        LinearLayout linear_select;
        //LinearLayout linear_discount;
        RecyclerView discount_recyclerview;
        RelativeLayout offer_layout;

        public ItemViewHolder(View itemView) {
            super(itemView);
            checkbox = itemView.findViewById(R.id.checkbox);
            book = itemView.findViewById(R.id.book);
            textview_book_name = itemView.findViewById(R.id.textview_book_name);
            textview_auther_name = itemView.findViewById(R.id.textview_auther_name);
            textview_book_descrption = itemView.findViewById(R.id.textview_book_descrption);
            textview_bookprice = itemView.findViewById(R.id.textview_bookprice);
            linear_select = itemView.findViewById(R.id.linear_select);
            // linear_discount = itemView.findViewById(R.id.linear_discount);
            discount_recyclerview = itemView.findViewById(R.id.discount_recyclerview);
            offer_layout = itemView.findViewById(R.id.offer_linearlayout);
            tv_viewoffer = itemView.findViewById(R.id.tv_viewoffer);
        }


    }

    public static void expand(final View v, int duration, int targetHeight) {
        int prevHeight = v.getHeight();
        v.setVisibility(View.VISIBLE);
        ValueAnimator valueAnimator = ValueAnimator.ofInt(prevHeight, targetHeight);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                v.getLayoutParams().height = (int) animation.getAnimatedValue();
                v.requestLayout();
            }
        });
        valueAnimator.setInterpolator(new DecelerateInterpolator());
        valueAnimator.setDuration(duration);
        valueAnimator.start();
    }

    public static void collapse(final View v, int duration, int targetHeight) {
        int prevHeight = v.getHeight();
        ValueAnimator valueAnimator = ValueAnimator.ofInt(prevHeight, targetHeight);
        valueAnimator.setInterpolator(new DecelerateInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                v.getLayoutParams().height = (int) animation.getAnimatedValue();
                v.requestLayout();
            }
        });
        valueAnimator.setInterpolator(new DecelerateInterpolator());
        valueAnimator.setDuration(duration);
        valueAnimator.start();
    }
}
