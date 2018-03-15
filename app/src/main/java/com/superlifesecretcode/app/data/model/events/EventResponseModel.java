package com.superlifesecretcode.app.data.model.events;

import com.superlifesecretcode.app.data.model.BaseResponseModel;

/**
 * Created by Divya on 15-03-2018.
 */

public class EventResponseModel extends BaseResponseModel {
    private int count;
    private int read;
    private int unread;
    private EventResponseData data;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getRead() {
        return read;
    }

    public void setRead(int read) {
        this.read = read;
    }

    public int getUnread() {
        return unread;
    }

    public void setUnread(int unread) {
        this.unread = unread;
    }

    public EventResponseData getData() {
        return data;
    }

    public void setData(EventResponseData data) {
        this.data = data;
    }
}
