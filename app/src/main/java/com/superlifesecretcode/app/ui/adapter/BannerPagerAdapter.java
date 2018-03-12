package com.superlifesecretcode.app.ui.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.superlifesecretcode.app.R;
import com.superlifesecretcode.app.ui.webview.WebViewActivity;
import com.superlifesecretcode.app.util.CommonUtils;


/**
 * Created by hp on 17-08-2017.
 */

public class BannerPagerAdapter extends PagerAdapter {
    private Context mContext;
    public BannerPagerAdapter(Context context) {
        mContext = context;
    }

    @Override
    public Object instantiateItem(ViewGroup collection, int position) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        ViewGroup layout = (ViewGroup) inflater.inflate(R.layout.item_banner, collection, false);
        ImageView imageView=layout.findViewById(R.id.imageView);
       /* GlideApp.with(imageView).load(banners.get(position).getInsideImage())
                .placeholder(R.drawable.default_placeholder).into(imageView);*/
        collection.addView(layout);
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("title", "Banner");
                bundle.putString("url", "https://www.richestlife.com/product-category/%E8%AC%9B%E5%BA%A7%E8%AA%B2%E7%A8%8B/");
                CommonUtils.startActivity((AppCompatActivity) mContext, WebViewActivity.class, bundle, false);
            }
        });
        return layout;
    }

    @Override
    public void destroyItem(ViewGroup collection, int position, Object view) {
        collection.removeView((View) view);
    }

    @Override
    public int getCount() {
        return 3;
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
