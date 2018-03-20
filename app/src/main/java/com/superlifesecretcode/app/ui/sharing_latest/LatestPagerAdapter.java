package com.superlifesecretcode.app.ui.sharing_latest;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.superlifesecretcode.app.R;
import com.superlifesecretcode.app.data.model.shares.FileResponseData;
import com.superlifesecretcode.app.data.model.shares.ShareListResponseData;
import com.superlifesecretcode.app.util.CommonUtils;
import com.superlifesecretcode.app.util.ConstantLib;
import com.superlifesecretcode.app.util.ImageLoadUtils;

import java.util.List;


/**
 * Created by hp on 17-08-2017.
 */

public class LatestPagerAdapter extends PagerAdapter {
    private final List<FileResponseData> bannerList;
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
        ImageView imageViewPlay = layout.findViewById(R.id.imageView_play);
        if (bannerList.get(position).getType().equalsIgnoreCase(ConstantLib.TYPE_VIDEO)) {
            imageViewPlay.setVisibility(View.VISIBLE);
        } else {
            imageViewPlay.setVisibility(View.GONE);
        }
        ImageLoadUtils.loadImage(bannerList.get(position).getFile(), imageView);
        collection.addView(layout);
        imageViewPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("video", bannerList.get(position).getFile());
                CommonUtils.startActivity((AppCompatActivity) mContext, VideoPlayerActivity.class, bundle, false);
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
