package com.superlifesecretcode.app.ui.news;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
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

public class NewsPagerAdapter extends PagerAdapter {
    private final List bannerList;
    private Context mContext;

    public NewsPagerAdapter(Context context, List bannerList) {
        mContext = context;
        this.bannerList = bannerList;
    }

    @Override
    public Object instantiateItem(ViewGroup collection, final int position) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        ViewGroup layout = (ViewGroup) inflater.inflate(R.layout.item_news_pager, collection, false);
        ImageView imageView = layout.findViewById(R.id.imageView);
        WebView webView = layout.findViewById(R.id.webview);
        webView.loadData("<!DOCTYPE html>\n" +
                "<html>\n" +
                "<body>\n" +
                "\n" +
                "<p>This is a paragraph.</p>\n" +
                "\t<body>\n" +
                "\t\t\t\t\t\t\t\t<h2>Spectacular Mountain</h2>\n" +
                "\t\t\t\t\t\t\t\t<img src=\"data:image/jpeg;base64,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\" alt=\"Mountain View\" style=\"width:304px;height:228px;\">\n" +
                "\t\t\t\t\t\t\t\t</body>\n" +
                "<p>This is a paragraph.</p>\n" +
                "<p>This is a paragraph.</p>\n" +
                "\n" +
                "</body>\n" +
                "</html>\n", "text/html", "utf-8");
     /*   ImageLoadUtils.loadImage(bannerList.get(position).getImage(), imageView);

        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("title", bannerList.get(position).getTitle());
                bundle.putString("url", bannerList.get(position).getLink());
                CommonUtils.startActivity((AppCompatActivity) mContext, WebViewActivity.class, bundle, false);
            }
        });*/
        collection.addView(layout);
        return layout;
    }

    @Override
    public void destroyItem(ViewGroup collection, int position, Object view) {
        collection.removeView((View) view);
    }

    @Override
    public int getCount() {
        return 5;
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
