package com.superlifesecretcode.app.data.netcomm;


import com.superlifesecretcode.app.util.CommonUtils;

import io.reactivex.annotations.NonNull;
import io.reactivex.observers.DefaultObserver;

/**
 * Created by JAIN COMPUTERS on 11/18/2017.
 */

public class ResponseObserver<T> extends DefaultObserver<T> {
    private static final String TAG = "ResponseObserver";
    private final ResponseHandler handler;

    public ResponseObserver(ResponseHandler handler) {
        this.handler = handler;
    }

    @Override
    public void onNext(@NonNull T t) {
        if (t != null) {
            handler.onResponse(t);
        } else {
            handler.onError();
        }

    }

    @Override
    public void onError(@NonNull Throwable e) {
        e.printStackTrace();
        handler.onError();
    }

    @Override
    public void onComplete() {
        CommonUtils.showLog(TAG, "Completed");
    }
}
