package com.superlifesecretcode.app.ui.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.superlifesecretcode.app.R;
import com.superlifesecretcode.app.data.model.category.BannerModel;
import com.superlifesecretcode.app.ui.webview.WebViewActivity;
import com.superlifesecretcode.app.util.CommonUtils;
import com.superlifesecretcode.app.util.ImageLoadUtils;

import java.util.List;


/**
 * Created by hp on 17-08-2017.
 */

public class BannerPagerAdapter extends PagerAdapter {
    private final List<BannerModel> bannerList;
    private Context mContext;

    public BannerPagerAdapter(Context context, List<BannerModel> bannerList) {
        mContext = context;
        this.bannerList = bannerList;
    }

    @Override
    public Object instantiateItem(ViewGroup collection, final int position) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        ViewGroup layout = (ViewGroup) inflater.inflate(R.layout.item_banner, collection, false);
        ImageView imageView = layout.findViewById(R.id.imageView);
        ImageLoadUtils.loadImage(bannerList.get(position).getImage(), imageView);
        collection.addView(layout);
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putBoolean("is_link", true);
                bundle.putString("title", bannerList.get(position).getTitle());
                bundle.putString("url", bannerList.get(position).getLink());
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
        return bannerList.size();
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
