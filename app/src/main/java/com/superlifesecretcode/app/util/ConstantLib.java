package com.superlifesecretcode.app.util;

/**
 * Created by Divya on 28-02-2018.
 */

public interface ConstantLib {
    String LANGUAGE_SIMPLIFIED = "1";
    String LANGUAGE_TRADITIONAL = "2";
    String LANGUAGE_ENGLISH = "3";
    String RESPONSE_SUCCESS = "true";
    String STRING_ENGLISH = "English";
    String STRING_SIMPLIFIED = "繁体中文";
    String STRING_TRADITIONAL = "繁體中文";
    String TC_URL = "https://www.richestlife.com/terms-of-service/";
    int TYPE_ANNOUNCEMENT = 5;
    int TYPE_SHARING = 6;
    String INPUT_DATE_ONLY_FORMATE = "yyyy-MM-dd";
    String OUTPUT_DATE_FORMATE = "dd MMM, yyyy";
    String INPUT_DATE_TIME_FORMATE = "yyyy-MM-dd HH:mm:ss";
    int TYPE_DAILY_ACTIVITIES = 7;
    int TYPE_COUNTRY_ACTIVITIES = 8;
    String OUTPUT_DATE_TIME_FORMATE = "dd MMM, yyyy hh:mm a";
    String STATUS_SUBMITED = "0";
    String STATUS_PUBLISHED = "1";
    String STATUS_REJECTED = "2";
    String TYPE_IMAGE = "2";
    String TYPE_VIDEO = "1";

    int TYPE_HOME = 0;
    int TYPE_NEWS = 1;
    int TYPE_EVENT = 2;
    int TYPE_LATEST = 3;
    int TYPE_SUBMIT = 4;
    int TYPE_PERSONAL_CALENDAR = 5;
    int TYPE_EVENT_CALENDAR = 6;
    int TYPE_STUDY_GROUP = 7;
    int TYPE_ONSITE = 8;
  /*  String SERVICE_PROVIDED_TRADTIONAL = "天圓文化是一所無任何宗教背景的身心靈諮詢教育機構，本機構服務範圍包括人生顧問、情緒治療、關係疏導、心靈輔導、靈性探索、催眠治療、教育推廣、興趣培養、團體培訓和社交訓練等。\n" +
            "創始人：太陽盛德導師(TED SUN)\n" +
            "《超級生命密碼》作者、身心靈諮詢導師、美國超級生命密碼協會精神導師、美國催眠師協會認證催眠諮詢師。\n" +
            "服務宗旨：\n" +
            "推廣身心健康的生活，宣揚樂觀、積極、正面思想及正能量人生\n" +
            "成立起源：\n" +
            "伴隨創始人太陽盛德導師《超級生命密碼》一書在美國和台灣出版問世，該書解開宇宙，自然，人生的各項謎團，教人拾起宇宙能量、改變生命地圖、承運光的祝福、開啟生命奇蹟，隨即得到亞洲至美洲讀者的熱烈迴響，因此前來諮詢個人問題的讀者絡繹不絕。2011年10月，天圓棧成立，在接待各年齡層的客戶提供一對一的咨詢服務的同時提供各種身心靈的課程，如：『靈性能量提升班』、『魅力女性研修坊』、『關係圓滿諧進班』，『能量減肥美容班』，『聰明寶寶靈性能量提升班』，『能量減壓解憂班』等，在身心靈健康的不同層面上幫助更多有需要的人解決他們的困擾，重拾個人自信與生活樂趣，邁向成功與圓滿的人生。\n" +
            "俱樂部誕生：\n" +
            "身為《超級生命密碼》作者、身心靈諮詢導師、美國超級生命密碼協會精神導師、美國催眠師協會認證催眠諮詢師，太陽盛德導師從眾多的 求助者看到很多人因負面情緒沒有求助的對象，也沒有正確的方法來求得指導或釋放，往往變成了負能量，影響到自己的生活以及身邊的家人、朋友、同事的關係， 甚至造成很多社區以及社會問題。為了幫助更多的人釋放負能量，故特開此“天圓棧身心靈健康俱樂部”，希望能幫助更多需解開心靈課題與身體難題的人們，讓大家每天生活更祥和，事事平順，步向成功、喜悅、圓滿的人生。";


    String SERVICE_PROVIDED_SIMPLY = "天圆文化是一所无任何宗教背景的身心灵咨询教育机构，本机构服务范围包括人生顾问，情绪治疗，关系疏导，心灵辅导，灵性探索，催眠治疗，教育推广，兴趣培养，团体培训和社交训练等。\n" +
            " \n" +
            "创始人：太阳盛德导师（TED SUN）\n" +
            "“超级生命密码”作者，身心灵咨询导师，美国超级生命密码协会精神导师，美国催眠师协会认证催眠咨询师。\n" +
            "服务宗旨：\n" +
            "推广身心健康的生活，宣扬乐观，积极，正面思想及正能量人生\n" +
            "成立起源：\n" +
            " \n" +
            "伴随创始人太阳盛德导师“超级生命密码”一书在美国和台湾出版问世，该书解开宇宙，自然，人生的各项谜团，教人拾起宇宙能量，改变生命地图，承运光的祝福，开启生命奇迹，随即得到亚洲至美洲读者的热烈回响，因此前来咨询个人问题的读者络绎不绝。2011年10月，天圆栈成立，在接待各年龄层的客户提供一对一的咨询服务的同时提供各种身心灵的课程，如：『灵性能量提升班』，『魅力女性研修坊』，『关系圆满谐进班』，『能量减肥美容班』，『聪明宝宝灵性能量提升班』， 『能量减压解忧班』等，在身心灵健康的不同层面上帮助更多有需要的人解决他们的困扰，重拾个人自信与生活乐趣，迈向成功与圆满的人生。\n" +
            "俱乐部诞生：\n" +
            "身为“超级生命密码”作者，身心灵咨询导师，美国超级生命密码协会精神导师，美国催眠师协会认证催眠咨询师，太阳盛德导师从众多的求助者看到很多人因负面情绪没有求助的对象，也没有正确的方法来求得指导或释放，往往变成了负能量，影响到自己的生活以及身边的家人，朋友，同事的关系，甚至造成很多社区以及社会问题。为了帮助更多的人释放负能量，故特开此“天圆栈身心灵健康俱乐部”，希望能帮助更多需解开心灵课题与身体难题的人们，让大家每天生活更祥和，事事平顺，步向成功，喜悦，圆满的人生。";*/
}
