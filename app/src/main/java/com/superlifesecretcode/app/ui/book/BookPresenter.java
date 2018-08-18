package com.superlifesecretcode.app.ui.book;

import android.content.Context;

import com.superlifesecretcode.app.ui.base.BasePresenter;
import com.superlifesecretcode.app.ui.base.BaseView;

public class BookPresenter extends BasePresenter<BaseView> {

    BaseView baseView;

    public BookPresenter(Context mContext) {
        super(mContext);
    }

    @Override
    protected void setView(BaseView baseView) {
        this.baseView = baseView;
    }
}
