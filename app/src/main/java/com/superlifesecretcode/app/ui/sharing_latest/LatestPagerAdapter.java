package com.superlifesecretcode.app.ui.sharing_latest;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.superlifesecretcode.app.R;

import java.util.List;


/**
 * Created by hp on 17-08-2017.
 */

public class LatestPagerAdapter extends PagerAdapter {
    private final List bannerList;
    private Context mContext;

    public LatestPagerAdapter(Context context, List bannerList) {
        mContext = context;
        this.bannerList = bannerList;
    }

    @Override
    public Object instantiateItem(ViewGroup collection, final int position) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        ViewGroup layout = (ViewGroup) inflater.inflate(R.layout.item_latest_pager, collection, false);
        ImageView imageView = layout.findViewById(R.id.imageView);
//        ImageLoadUtils.loadImage(bannerList.get(position).getImage(), imageView);
        collection.addView(layout);
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
