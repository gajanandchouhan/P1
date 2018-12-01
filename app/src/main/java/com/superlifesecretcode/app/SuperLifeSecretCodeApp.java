package com.superlifesecretcode.app;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.multidex.MultiDex;
import android.util.Log;

import com.devbrackets.android.exomedia.ExoMedia;
import com.google.android.exoplayer2.ext.okhttp.OkHttpDataSourceFactory;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.TransferListener;
import com.google.android.exoplayer2.upstream.cache.Cache;
import com.google.android.exoplayer2.upstream.cache.CacheDataSource;
import com.google.android.exoplayer2.upstream.cache.CacheDataSourceFactory;
import com.google.android.exoplayer2.upstream.cache.LeastRecentlyUsedCacheEvictor;
import com.google.android.exoplayer2.upstream.cache.SimpleCache;
import com.inscripts.interfaces.Callbacks;
import com.superlifesecretcode.app.data.model.language.LanguageResponseData;
import com.superlifesecretcode.app.data.persistance.SuperLifeSecretPreferences;
import com.superlifesecretcode.app.player.manager.PlaylistManager;
import com.twitter.sdk.android.core.Twitter;

import org.json.JSONObject;

import java.io.File;

import cometchat.inscripts.com.cometchatcore.coresdk.CometChat;
import okhttp3.OkHttpClient;

/**
 * Created by Divya on 21-02-2018.
 */

public class SuperLifeSecretCodeApp extends Application {
    static SuperLifeSecretCodeApp instance;
    private LanguageResponseData conversionData;
    private PlaylistManager playlistManager;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        Twitter.initialize(this);


        playlistManager = new PlaylistManager(this);
        configureExoMedia();
    }

    public PlaylistManager getPlaylistManager() {
        return playlistManager;
    }

    public LanguageResponseData getConversionData() {
        conversionData = SuperLifeSecretPreferences.getInstance().getConversionData();
        return conversionData;
    }

    public static SuperLifeSecretCodeApp getInstance() {
        return instance;
    }



    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    private void configureExoMedia() {
        // Registers the media sources to use the OkHttp client instead of the standard Apache one
        // Note: the OkHttpDataSourceFactory can be found in the ExoPlayer extension library `extension-okhttp`
        ExoMedia.setDataSourceFactoryProvider(new ExoMedia.DataSourceFactoryProvider() {
            @Nullable
            private CacheDataSourceFactory instance;

            @NonNull
            @Override
            public DataSource.Factory provide(@NonNull String userAgent, @Nullable TransferListener<? super DataSource> listener) {
                if (instance == null) {
                    // Updates the network data source to use the OKHttp implementation
                    DataSource.Factory upstreamFactory = new OkHttpDataSourceFactory(new OkHttpClient(), userAgent, listener);

                    // Adds a cache around the upstreamFactory
                    Cache cache = new SimpleCache(new File(getCacheDir(), "ExoMediaCache"), new LeastRecentlyUsedCacheEvictor(50 * 1024 * 1024));
                    instance = new CacheDataSourceFactory(cache, upstreamFactory, CacheDataSource.FLAG_IGNORE_CACHE_ON_ERROR);
                }

                return instance;
            }
        });
    }
}
