package com.superlifesecretcode.app.ui.studygroup;

import android.content.Context;

import com.superlifesecretcode.app.R;
import com.superlifesecretcode.app.data.model.studygroups.StudyGroupResponseModel;
import com.superlifesecretcode.app.data.netcomm.ApiController;
import com.superlifesecretcode.app.data.netcomm.CheckNetworkState;
import com.superlifesecretcode.app.data.netcomm.RequestType;
import com.superlifesecretcode.app.data.netcomm.ResponseHandler;
import com.superlifesecretcode.app.ui.base.BasePresenter;
import com.superlifesecretcode.app.util.CommonUtils;
import com.superlifesecretcode.app.util.ConstantLib;

import java.util.Map;

public class StudyGroupPresenter extends BasePresenter<StudyGroupView> {
    private StudyGroupView view;

    public StudyGroupPresenter(Context mContext) {
        super(mContext);
    }

    @Override
    protected void setView(StudyGroupView studyGroupView) {
        this.view = studyGroupView;
    }


    public void getStudyGroup(Map<String, String> headers) {
        if (!CheckNetworkState.isOnline(mContext)) {
            CommonUtils.showSnakeBar(mContext, mContext.getString(R.string.no_internet));
            return;
        }
        view.showProgress();
        ApiController apiController = ApiController.getInstance();
        apiController.callGetWithHeader(mContext, RequestType.REQ_GET_GROUPS, new ResponseHandler<StudyGroupResponseModel>() {
            @Override
            public void onResponse(StudyGroupResponseModel annoucmenntResponseModel) {
                view.hideProgress();
                if (annoucmenntResponseModel != null) {
                    if (annoucmenntResponseModel.getStatus().equalsIgnoreCase(ConstantLib.RESPONSE_SUCCESS)) {
                     view.setStudyGroupList(annoucmenntResponseModel.getData());
                    } else {
                      CommonUtils.showSnakeBar(mContext,annoucmenntResponseModel.getMessage());
                    }
                } else {
                    CommonUtils.showSnakeBar(mContext, mContext.getString(R.string.server_error));
                }

            }

            @Override
            public void onError() {
                view.hideProgress();
                CommonUtils.showSnakeBar(mContext, mContext.getString(R.string.server_error));

            }
        }, headers, null, null);
    }

}
