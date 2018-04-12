package com.superlifesecretcode.app.ui.ringtone;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.superlifesecretcode.app.R;
import com.superlifesecretcode.app.data.model.language.LanguageResponseData;
import com.superlifesecretcode.app.data.model.news.NewsResponseData;
import com.superlifesecretcode.app.data.model.userdetails.UserDetailResponseData;
import com.superlifesecretcode.app.data.persistance.SuperLifeSecretPreferences;
import com.superlifesecretcode.app.ui.base.BaseActivity;
import com.superlifesecretcode.app.ui.notification.NotificationAapter;
import com.superlifesecretcode.app.ui.notification.NotificationPresenter;
import com.superlifesecretcode.app.ui.notification.NotificationView;
import com.superlifesecretcode.app.util.CommonUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RingtoneActivity extends BaseActivity {

    private RecyclerView recyclerView;
    private RingtoneAapter newsAapter;
    private LanguageResponseData conversionData;
    private UserDetailResponseData userDetailResponseData;
    private MediaPlayer mediaPlayer;
    private int currentPlayPos = -2;

    @Override
    protected int getContentView() {
        return R.layout.activity_notification;
    }

    @Override
    protected void initializeView() {
        userDetailResponseData = SuperLifeSecretPreferences.getInstance().getUserData();
        conversionData = SuperLifeSecretPreferences.getInstance().getConversionData();
        setUpToolbar();
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        getRingtones();
        mediaPlayer = new MediaPlayer();

        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                mediaPlayer.release();
            }
        });
    }

    private void getRingtones() {
        new RingtoneTask().execute();
    }

    public void playPause(Uri uri, int playPosition) {
        if (mediaPlayer != null) {
            if (mediaPlayer.isPlaying()) {
                if (playPosition == currentPlayPos) {
                    mediaPlayer.stop();
                    mediaPlayer.release();
                } else {
                    mediaPlayer.stop();
                    mediaPlayer.release();
                    try {
                        mediaPlayer.setDataSource(this, uri);
                        mediaPlayer.prepare();
                        mediaPlayer.start();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                try {
                    mediaPlayer.setDataSource(this, uri);
                    mediaPlayer.prepare();
                    mediaPlayer.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            currentPlayPos = playPosition;
        }
    }

    private class RingtoneTask extends AsyncTask<Void, Void, Uri[]> {

        @Override
        protected Uri[] doInBackground(Void... voids) {
            return CommonUtils.getRingtoneUris(RingtoneActivity.this);
        }

        @Override
        protected void onPreExecute() {
            showProgress();
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Uri[] uris) {
            hideProgress();
            if (uris != null) {
                newsAapter = new RingtoneAapter(uris, RingtoneActivity.this);
                recyclerView.setAdapter(newsAapter);
            }
            super.onPostExecute(uris);
        }
    }


    @Override
    protected void initializePresenter() {
    }

    private void setUpToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        TextView textViewTitle = findViewById(R.id.textView_title);
        if (conversionData != null)
            textViewTitle.setText("Ringtones");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }


}
