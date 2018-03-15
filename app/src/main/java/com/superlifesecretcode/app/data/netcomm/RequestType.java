package com.superlifesecretcode.app.data.netcomm;

/**
 * Created by JAIN COMPUTERS on 11/18/2017.
 */

public interface RequestType {
    byte REQ_CONVERSION = 1;
    byte REQ_GET_COUNTRY = 2;
    byte REQ_GET_STATE = 3;
    byte REQ_REGISTER_USER = 4;
    byte REQ_LOGIN = 5;
    byte REQ_SOCIAL_LOGIN = 6;
    byte REQ_UPDATE_PROFILE = 7;
    byte REQ_GET_CATEGORY = 7;
    byte REQ_GET_SUB_CATEGORY = 8;
    byte REQ_GET_NEWS_UPDATES = 9;
    byte REQ_GET_EVENTS = 10;
    byte REQ_MARK_READ = 11;
    byte REQ_INTERESTED_EVENT=12;
}
