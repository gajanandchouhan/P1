package com.superlifesecretcode.app.data.model.news;

import com.superlifesecretcode.app.data.model.BaseResponseModel;

import java.util.List;

/**
 * Created by Divya on 14-03-2018.
 */

public class NewsResponseModel extends BaseResponseModel {
    private int count;
    private int read;
    private int unread;
    private List<NewsResponseData> data;

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

    public List<NewsResponseData> getData() {
        return data;
    }

    public void setData(List<NewsResponseData> data) {
        this.data = data;
    }
}
