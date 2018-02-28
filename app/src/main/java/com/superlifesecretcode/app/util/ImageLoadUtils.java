package com.superlifesecretcode.app.util;

import android.widget.ImageView;

import com.superlifesecretcode.app.GlideApp;
import com.superlifesecretcode.app.R;

/**
 * Created by Divya on 28-02-2018.
 */

public class ImageLoadUtils {

    public static void loadImage(String url, ImageView imageView) {
        GlideApp.with(imageView).load(url)
                .placeholder(R.drawable.default_placeholder).into(imageView);

    }

    public static void loadImage(String url, ImageView imageView, int placeHolder) {
        GlideApp.with(imageView).load(url)
                .placeholder(placeHolder).into(imageView);

    }
}
