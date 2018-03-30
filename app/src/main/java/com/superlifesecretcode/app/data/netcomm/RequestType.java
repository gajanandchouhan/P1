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
    byte REQ_INTERESTED_EVENT = 12;
    byte REQ_LIKE_NEWS = 13;
    byte REQ_ADD_SHARE = 14;
    byte REQ_GET_USER_SHARE = 15;
    byte REQ_GET_ALL_LATEST = 16;
    byte REQ_LIKE_SHARING = 17;
    byte REQ_MY_INTERESTED_EVENT = 18;
    byte REQ_GET_STANDARD_EVENT = 19;
    byte REQ_ADD_ACTIVITY = 20;
    byte REQ_GET_PERSONAL_EVENT = 21;
    byte REQ_UPDATE_EVENT_STATUS = 22;
    byte REQ_GET_COUNTRY_ACTIVITY = 23;
    byte REQ_MAKEINTERESTED_COUNTRY_ACTIVITY = 24;
}
