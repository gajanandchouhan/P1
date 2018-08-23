package com.superlifesecretcode.app.ui.player;

import android.content.Context;
import com.superlifesecretcode.app.R;
import com.superlifesecretcode.app.data.model.BaseResponseModel;
import com.superlifesecretcode.app.data.netcomm.ApiController;
import com.superlifesecretcode.app.data.netcomm.CheckNetworkState;
import com.superlifesecretcode.app.data.netcomm.RequestType;
import com.superlifesecretcode.app.data.netcomm.ResponseHandler;
import com.superlifesecretcode.app.data.persistance.SuperLifeSecretPreferences;
import com.superlifesecretcode.app.ui.base.BasePresenter;
import com.superlifesecretcode.app.util.CommonUtils;
import com.superlifesecretcode.app.util.ConstantLib;
import java.util.Map;


public class PlayerPresenter extends BasePresenter<PlayerView> {

    private PlayerView view;

    public PlayerPresenter(Context mContext) {
        super(mContext);
    }

    @Override
    protected void setView(PlayerView view) {
        this.view = view;
    }

    public void updateRemainder(Map<String, String> headers) {
        if (!CheckNetworkState.isOnline(mContext)) {
            CommonUtils.showSnakeBar(mContext, mContext.getString(R.string.no_internet));
            return;
        }

        view.showProgress();
        ApiController apiController = ApiController.getInstance();
        apiController.callGetWithHeader(mContext, RequestType.REQ_UPDATE_PROFILE_REMAINDER, new ResponseHandler<BaseResponseModel>() {
            @Override
            public void onResponse(BaseResponseModel shareListResponseModel) {

                view.hideProgress();
                if (shareListResponseModel != null) {
                    if (shareListResponseModel.getStatus().equalsIgnoreCase(ConstantLib.RESPONSE_SUCCESS)) {
                        view.onSuccess(shareListResponseModel);
                    } else
                        CommonUtils.showToast(mContext, SuperLifeSecretPreferences.getInstance().getConversionData().getNo_sharing());
                } else {
                    CommonUtils.showToast(mContext,mContext.getString(R.string.server_error));
                    //CommonUtils.showSnakeBar(mContext, mContext.getString(R.string.server_error));
                }
            }

            @Override
            public void onError() {
                view.hideProgress();
                CommonUtils.showToast(mContext,mContext.getString(R.string.server_error));
                //CommonUtils.showSnakeBar(mContext, mContext.getString(R.string.server_error));
            }
        }, headers,"","");
    }
}
