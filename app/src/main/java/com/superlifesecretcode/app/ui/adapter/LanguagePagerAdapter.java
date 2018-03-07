package com.superlifesecretcode.app.ui.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.superlifesecretcode.app.R;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by hp on 17-08-2017.
 */

public class LanguagePagerAdapter extends PagerAdapter {
    private Context mContext;
    List<Integer> list;

    public LanguagePagerAdapter(Context context) {
        mContext = context;
        list = new ArrayList<>();
        list.add(R.drawable.english);
        list.add(R.drawable.simplify_chinese);
        list.add(R.drawable.traditional_chinese);
    }

    @Override
    public Object instantiateItem(ViewGroup collection, int position) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        ViewGroup layout = (ViewGroup) inflater.inflate(R.layout.item_language, collection, false);
        ImageView imageView = layout.findViewById(R.id.imageView_language);
        imageView.setImageResource(list.get(position));
       /* GlideApp.with(imageView).load(banners.get(position).getInsideImage())
                .placeholder(R.drawable.default_placeholder).into(imageView);*/
        collection.addView(layout);
        return layout;
    }

    @Override
    public void destroyItem(ViewGroup collection, int position, Object view) {
        collection.removeView((View) view);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return "";
    }

    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

}
