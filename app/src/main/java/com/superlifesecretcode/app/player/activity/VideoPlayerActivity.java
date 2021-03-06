package com.superlifesecretcode.app.player.activity;

import android.app.Activity;
import android.os.Bundle;

import com.devbrackets.android.exomedia.listener.VideoControlsSeekListener;
import com.devbrackets.android.exomedia.ui.widget.VideoView;
import com.google.android.exoplayer2.util.EventLogger;
import com.superlifesecretcode.app.R;
import com.superlifesecretcode.app.SuperLifeSecretCodeApp;
import com.superlifesecretcode.app.player.MediaItem;
//import com.superlifesecretcode.app.player.MyMediaItem;
import com.superlifesecretcode.app.player.MyMediaItem;
import com.superlifesecretcode.app.player.VideoApi;
import com.superlifesecretcode.app.player.manager.PlaylistManager;

import java.util.LinkedList;
import java.util.List;


public class VideoPlayerActivity extends Activity implements VideoControlsSeekListener {
    public static final String EXTRA_INDEX = "EXTRA_INDEX";
    public static final int PLAYLIST_ID = 2; //Arbitrary, for the example (different from audio)

    protected VideoApi videoApi;
    protected VideoView videoView;
    protected PlaylistManager playlistManager;

    protected int selectedIndex;
    private String videoUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video_player_activity);
        videoUrl=getIntent().getBundleExtra("bundle").getString("video");
        retrieveExtras();
        init();
    }

    @Override
    protected void onStop() {
        super.onStop();
        playlistManager.removeVideoApi(videoApi);
        playlistManager.invokeStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        playlistManager.invokeStop();
    }

    @Override
    public boolean onSeekStarted() {
        playlistManager.invokeSeekStarted();
        return true;
    }

    @Override
    public boolean onSeekEnded(long seekTime) {
        playlistManager.invokeSeekEnded(seekTime);
        return true;
    }

    /**
     * Retrieves the extra associated with the selected playlist index
     * so that we can start playing the correct item.
     */
    protected void retrieveExtras() {
        Bundle extras = getIntent().getExtras();
        selectedIndex = extras.getInt(EXTRA_INDEX, 0);
    }

    protected void init() {
        setupPlaylistManager();

        videoView = findViewById(R.id.video_play_activity_video_view);
        videoView.setHandleAudioFocus(false);
        videoView.getVideoControls().setSeekListener(this);
        videoView.setAnalyticsListener(new EventLogger(null));

        videoApi = new VideoApi(videoView);
        playlistManager.addVideoApi(videoApi);
        playlistManager.play(0, false);
    }

    /**
     * Retrieves the playlist instance and performs any generation
     * of content if it hasn't already been performed.
     */
    private void setupPlaylistManager() {
        playlistManager = ((SuperLifeSecretCodeApp) getApplicationContext()).getPlaylistManager();

        List<MediaItem> mediaItems = new LinkedList<>();
        mediaItems.add(new MediaItem(new MyMediaItem(videoUrl,"",""),false));
        playlistManager.setParameters(mediaItems, selectedIndex);
        playlistManager.setId(PLAYLIST_ID);
    }
}
