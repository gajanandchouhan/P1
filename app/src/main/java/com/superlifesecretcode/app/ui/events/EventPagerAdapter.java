package com.superlifesecretcode.app.ui.events;

import android.content.Context;
import android.os.Build;
import android.support.v4.view.PagerAdapter;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.superlifesecretcode.app.R;
import com.superlifesecretcode.app.data.model.events.EventsInfoModel;
import com.superlifesecretcode.app.data.model.language.LanguageResponseData;
import com.superlifesecretcode.app.data.model.news.NewsResponseData;
import com.superlifesecretcode.app.ui.news.NewsDetailsActivity;
import com.superlifesecretcode.app.util.AlarmUtility;
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
    private LanguageResponseData conversionData;

    public EventPagerAdapter(Context context, List newsList, LanguageResponseData conversionData) {
        mContext = context;
        this.newsList = newsList;
        this.conversionData = conversionData;
    }

    @Override
    public Object instantiateItem(ViewGroup collection, final int position) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        ViewGroup layout = (ViewGroup) inflater.inflate(R.layout.item_event_pager, collection, false);
        ImageView imageView = layout.findViewById(R.id.imageView);
        RelativeLayout relativeLayout = layout.findViewById(R.id.button_interested);
        TextView textViewTitle = layout.findViewById(R.id.textView_title);
        TextView textViewTime = layout.findViewById(R.id.textView_time);
        TextView textViewInterested = layout.findViewById(R.id.textview_interested);
        TextView textViewAddr = layout.findViewById(R.id.textView_addr);
        ImageView imageViewShare = layout.findViewById(R.id.imageView_share);
        textViewTitle.setText(newsList.get(position).getAnnouncement_name());
        if (newsList.get(position).getEnd_date() != null && !newsList.get(position).getEnd_date().isEmpty()) {
            String startDateTime = CommonUtils.getformattedDateFromString(ConstantLib.INPUT_DATE_TIME_FORMATE, ConstantLib.OUTPUT_DATE_TIME_FORMATE, newsList.get(position).getAnnouncement_date() + " " + newsList.get(position).getAnnouncement_time(), true,newsList.get(position).getTimezone());
            String endDateTime = CommonUtils.getformattedDateFromString(ConstantLib.INPUT_DATE_TIME_FORMATE, ConstantLib.OUTPUT_DATE_TIME_FORMATE, newsList.get(position).getEnd_date() + " " + newsList.get(position).getEnd_time(), true,newsList.get(position).getTimezone());
            textViewTime.setText(String.format("%s-%s", startDateTime, endDateTime));

        } else {
            textViewTime.setText(CommonUtils.getformattedDateFromString(ConstantLib.INPUT_DATE_TIME_FORMATE, ConstantLib.OUTPUT_DATE_TIME_FORMATE, newsList.get(position).getAnnouncement_date() + " " + newsList.get(position).getAnnouncement_time(), true,newsList.get(position).getTimezone()));
        }
        ImageLoadUtils.loadImage(newsList.get(position).getImage(), imageView);
       // TextView textViewDesc = layout.findViewById(R.id.textView_desc);
        textViewAddr.setText(newsList.get(position).getVenue());
        textViewInterested.setText(conversionData.getInterested());
        WebView webView = layout.findViewById(R.id.webview);
        webView.loadData(newsList.get(position).getAnnouncement_description(), "text/html; charset=utf-8", "utf-8");
       /* if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Spanned spanned = Html.fromHtml(newsList.get(position).getAnnouncement_description(), Html.FROM_HTML_MODE_LEGACY);
            textViewDesc.setText(spanned);
        } else {
            Spanned spanned = Html.fromHtml(newsList.get(position).getAnnouncement_description());
            textViewDesc.setText(spanned);
        }*/
        relativeLayout.setSelected(newsList.get(position).getUserIntrested() != null && newsList.get(position).getUserIntrested().equalsIgnoreCase("1"));
        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userIntrested = newsList.get(position).getUserIntrested();
                if (userIntrested != null && userIntrested.equalsIgnoreCase("1")) {
                    ((EventDetailsActivity) mContext).updateEventInterest(position, "0", newsList.get(position).getAnnouncement_id());
                } else {
                    ((EventDetailsActivity) mContext).updateEventInterest(position, "1", newsList.get(position).getAnnouncement_id());
                }
            }
        });

        imageViewShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((EventDetailsActivity) mContext).shareImageAndText(newsList.get(position).getImage(), Html.fromHtml(newsList.get(position).getAnnouncement_description()).toString());
            }
        });
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
