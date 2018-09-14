package com.superlifesecretcode.app.player;

import com.devbrackets.android.playlistcore.annotation.SupportedMediaType;
import com.devbrackets.android.playlistcore.api.PlaylistItem;
import com.superlifesecretcode.app.player.manager.PlaylistManager;

/**
 * A custom {@link PlaylistItem}
 * to hold the information pertaining to the audio and video items
 */
public class MediaItem implements PlaylistItem {
//    private MyMediaItem sample;
    boolean isAudio;

//    public MediaItem(MyMediaItem sample, boolean isAudio) {
//        this.sample = sample;
//        this.isAudio = isAudio;
//    }

    @Override
    public long getId() {
        return 0;
    }

    @Override
    public boolean getDownloaded() {
        return false;
    }

    @Override
    @SupportedMediaType
    public int getMediaType() {
        return isAudio ? PlaylistManager.AUDIO : PlaylistManager.VIDEO;
    }

    @Override
    public String getMediaUrl() {
        return "";
//        return sample.getMediaUrl();
    }

    @Override
    public String getDownloadedMediaUri() {
        return null;
    }

    @Override
    public String getThumbnailUrl() {
        return "";
//        return sample.getImageUrl();
    }

    @Override
    public String getArtworkUrl() {
        return "";
//        return sample.getImageUrl();
    }

    @Override
    public String getTitle() {
        return "";
//        return sample.getTitle();
    }

    @Override
    public String getAlbum() {
        return "SuperLifeSecreteCode";
    }

    @Override
    public String getArtist() {
        return "";
    }
}