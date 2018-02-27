package com.superlifesecretcode.app.ui.base;

import android.content.Context;

/**
 * Created by JAIN COMPUTERS on 11/18/2017.
 */

public abstract class BasePresenter<T> {
     protected Context mContext;

    protected abstract void setView(T t);

    public BasePresenter(Context mContext) {
        this.mContext=mContext;
    }
}
