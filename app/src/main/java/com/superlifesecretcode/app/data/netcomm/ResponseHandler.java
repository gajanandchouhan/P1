package com.superlifesecretcode.app.data.netcomm;

/**
 * Created by JAIN COMPUTERS on 11/18/2017.
 */

public interface ResponseHandler<T> {

    void onResponse(T t);
    void onError();
}
