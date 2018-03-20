package com.superlifesecretcode.app.ui.sharing_submit;

import com.superlifesecretcode.app.data.model.shares.ShareListResponseData;
import com.superlifesecretcode.app.ui.base.BaseView;

import java.util.List;

/**
 * Created by Divya on 19-03-2018.
 */

public interface ShareListView extends BaseView {
    void setShareListData(List<ShareListResponseData> listData);

    void onLiked();
}
