package com.superlifesecretcode.app.ui.notification;

import com.superlifesecretcode.app.data.model.notifications.NotificationResponseData;
import com.superlifesecretcode.app.ui.base.BaseView;

import java.util.List;

/**
 * Created by Divya on 05-04-2018.
 */

public interface NotificationView extends BaseView {
    void setNotificationData(List<NotificationResponseData> data);
}
