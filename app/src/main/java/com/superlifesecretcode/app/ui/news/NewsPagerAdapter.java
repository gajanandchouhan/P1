package com.superlifesecretcode.app.ui.news;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.superlifesecretcode.app.R;
import com.superlifesecretcode.app.data.model.language.LanguageResponseData;
import com.superlifesecretcode.app.data.model.news.NewsResponseData;
import com.superlifesecretcode.app.ui.events.EventDetailsActivity;
import com.superlifesecretcode.app.util.CommonUtils;
import com.superlifesecretcode.app.util.ConstantLib;
import com.superlifesecretcode.app.util.ImageLoadUtils;

import java.util.List;


/**
 * Created by hp on 17-08-2017.
 */

public class NewsPagerAdapter extends PagerAdapter {
    private final List<NewsResponseData> newsList;
    private final LanguageResponseData conversionData;
    private Context mContext;

    public NewsPagerAdapter(Context context, List newsList, LanguageResponseData conversionData) {
        mContext = context;
        this.newsList = newsList;
        this.conversionData=conversionData;
    }

    @Override
    public Object instantiateItem(ViewGroup collection, final int position) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        ViewGroup layout = (ViewGroup) inflater.inflate(R.layout.item_news_pager, collection, false);
        ScrollView scrollView=layout.findViewById(R.id.scrollView);
        ImageView imageView = layout.findViewById(R.id.imageView);
        TextView textViewTitle = layout.findViewById(R.id.textView_title);
        TextView textViewTime = layout.findViewById(R.id.textView_time);
        TextView textViewLikeCount = layout.findViewById(R.id.textView_like_count);
        ImageView imageViewLike = layout.findViewById(R.id.imageView_like);
        ImageView imageViewShare = layout.findViewById(R.id.imageView_share);
        imageViewLike.setSelected(newsList.get(position).getLiked_by_user().equalsIgnoreCase("1"));
        textViewTitle.setText(newsList.get(position).getAnnouncement_name());
        textViewTime.setText(CommonUtils.getformattedDateFromString(ConstantLib.INPUT_DATE_TIME_FORMATE, ConstantLib.OUTPUT_DATE_TIME_FORMATE, newsList.get(position).getCreated_at(),true,newsList.get(position).getTimezone()));
        textViewLikeCount.setText(String.format("%s "+conversionData.getLikes(), newsList.get(position).getLiked_by()));
        ImageLoadUtils.loadImage(newsList.get(position).getImage(), imageView);
        WebView webView = layout.findViewById(R.id.webview);
        webView.loadData(newsList.get(position).getAnnouncement_description(), "text/html; charset=utf-8", "utf-8");
        scrollView.fullScroll(ScrollView.FOCUS_UP);
        scrollView.smoothScrollBy(0,0);
        imageViewShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((NewsDetailsActivity) mContext).shareImageAndText(newsList.get(position).getImage(), Html.fromHtml(newsList.get(position).getAnnouncement_description()).toString());
            }
        });

        imageViewLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String liked_by_user = newsList.get(position).getLiked_by_user();
                if (liked_by_user != null && liked_by_user.equalsIgnoreCase("1")) {
                    ((NewsDetailsActivity) mContext).likeNews(position, "0", newsList.get(position).getAnnouncement_id());
                } else {
                    ((NewsDetailsActivity) mContext).likeNews(position, "1", newsList.get(position).getAnnouncement_id());
                }
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
