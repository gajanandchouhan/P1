package com.superlifesecretcode.app.ui.player;

import com.superlifesecretcode.app.data.model.BaseResponseModel;
import com.superlifesecretcode.app.ui.base.BaseView;

public interface PlayerView extends BaseView{
    void onSuccess(BaseResponseModel data);
}
