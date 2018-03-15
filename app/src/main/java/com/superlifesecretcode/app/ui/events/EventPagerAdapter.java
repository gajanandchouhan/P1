package com.superlifesecretcode.app.ui.events;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.superlifesecretcode.app.R;
import com.superlifesecretcode.app.data.model.events.EventsInfoModel;
import com.superlifesecretcode.app.data.model.news.NewsResponseData;
import com.superlifesecretcode.app.util.CommonUtils;
import com.superlifesecretcode.app.util.ConstantLib;
import com.superlifesecretcode.app.util.ImageLoadUtils;

import java.util.List;


/**
 * Created by hp on 17-08-2017.
 */

public class EventPagerAdapter extends PagerAdapter {
    private final List<EventsInfoModel> newsList;
    private Context mContext;

    public EventPagerAdapter(Context context, List newsList) {
        mContext = context;
        this.newsList = newsList;
    }

    @Override
    public Object instantiateItem(ViewGroup collection, final int position) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        ViewGroup layout = (ViewGroup) inflater.inflate(R.layout.item_event_pager, collection, false);
        ImageView imageView = layout.findViewById(R.id.imageView);
        TextView textViewTitle = layout.findViewById(R.id.textView_title);
        TextView textViewTime = layout.findViewById(R.id.textView_time);
        TextView textViewAddr = layout.findViewById(R.id.textView_addr);
        ImageView imageViewShare = layout.findViewById(R.id.imageView_share);
        textViewTitle.setText(newsList.get(position).getAnnouncement_name());
        textViewTime.setText(CommonUtils.getformattedDateFromString(ConstantLib.INPUT_DATE_TIME_FORMATE, ConstantLib.OUTPUT_DATE_TIME_FORMATE, newsList.get(position).getAnnouncement_date() + " " + newsList.get(position).getAnnouncement_time()));
        ImageLoadUtils.loadImage(newsList.get(position).getImage(), imageView);
        TextView textViewDesc = layout.findViewById(R.id.textView_desc);
        textViewAddr.setText(newsList.get(position).getVenue());
        textViewDesc.setText(newsList.get(position).getAnnouncement_description());
        collection.addView(layout);
        return layout;
    }

    @Override
    public void destroyItem(ViewGroup collection, int position, Object view) {
        collection.removeView((View) view);
    }

    @Override
    public int getCount() {
        return newsList.size();
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
