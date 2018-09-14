package com.superlifesecretcode.app.player;

public class MyMediaItem {
    private String mediaUrl;
    private String imageUrl;
    private String title;

    public MyMediaItem(String mediaUrl, String imageUrl, String title) {
        this.mediaUrl = mediaUrl;
        this.imageUrl = imageUrl;
        this.title = title;
    }

    public String getMediaUrl() {
        return mediaUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }


    public String getTitle() {
        return title;
    }
}
