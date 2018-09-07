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
    byte REQ_COUNTRY_ACTIVITY_DETAILS = 25;
    byte REQ_GET_ALL_MENU = 26;
    byte REQ_REMOVE_ACTIVITY = 27;
    byte REQ_ANNOUNCE_REMINDER = 28;
    byte REQ_COUNTRY_ACTIVITY_REMINDER = 29;
    byte REQ_GET_RESET_CODE = 30;
    byte REQ_RESET_PASS = 31;
    byte REQ_GET_ANNOUNCEMENT_COUNT = 32;
    byte REQ_GET_BANNERS = 33;
    byte REQ_GET_NOTIFICATIONS = 33;
    byte REQ_GET_DISCLOSURE = 34;
    byte REQ_GET_CITIES = 35;
    byte REQ_GET_EVENT_COUNTRIES = 36;
    byte GET_SHARE_COUNTRY = 37;
    byte REQ_CREATE_LEAD = 38;
    byte REQ_UPDATE_PROFILE_REMAINDER = 39;
    byte REQ_GET_BOOK_LIST = 40;
    byte REQ_GET_ADDRESSES_BOOK_FORTH = 41;
    byte REQ_GET_STORES_BOOK_FORTH = 42;
    byte REQ_GET_BANKLIST_BOOK_FIFTH = 43;
    byte REQ_BOOK_ORDER = 44;
    byte REQ_GET_ORDER_LIST_BOOK = 45;
    byte REQ_GET_ORDER_DETAIL_BOOK = 46;
    byte REQ_ADD_ANNOUNCMENT = 47;
    byte REQ_GET_MY_ANNOUCMENT = 48;
    byte REQ_DELETE_MY_ANNOUNCEMENT = 49;
    byte REQ_DELETE_MY_AANOUCNE_IMAGE = 50;
    byte REQ_SEND_EVENT_REQ = 51;
    byte REQ_GET_MY_COUNTRY_ACTIVITY = 52;
    byte REQ_ADD_COUNTRY_ACTIVITY = 53;
    byte REQ_DELETE_ACTIVITY_IMAGE=54;
    byte REQ_DELETE_ACTIVITY=55;
}
