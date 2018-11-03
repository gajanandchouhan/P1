package com.superlifesecretcode.app.ui.studygroup.paymentproof;

import android.content.Context;

import com.superlifesecretcode.app.R;
import com.superlifesecretcode.app.data.model.BaseResponseModel;
import com.superlifesecretcode.app.data.netcomm.ApiController;
import com.superlifesecretcode.app.data.netcomm.CheckNetworkState;
import com.superlifesecretcode.app.data.netcomm.RequestType;
import com.superlifesecretcode.app.data.netcomm.ResponseHandler;
import com.superlifesecretcode.app.ui.base.BasePresenter;
import com.superlifesecretcode.app.ui.book.five.BankBean;
import com.superlifesecretcode.app.util.CommonUtils;
import com.superlifesecretcode.app.util.ConstantLib;

import java.util.Map;

import okhttp3.RequestBody;

public class PaymentProfPresenter extends BasePresenter<PaymentProofView> {

    PaymentProofView view;

    public PaymentProfPresenter(Context mContext) {
        super(mContext);
    }

    @Override
    protected void setView(PaymentProofView view) {
        this.view=view;
    }


    public void getBankList(Map<String, String> headers,String type) {
        if (!CheckNetworkState.isOnline(mContext)) {
            CommonUtils.showSnakeBar(mContext, mContext.getString(R.string.no_internet));
            return;
        }
        view.showProgress();
        ApiController apiController = ApiController.getInstance();
        apiController.callGetWithHeader(mContext, RequestType.REQ_GET_BANKLIST_BOOK_FIFTH, new ResponseHandler<BankBean>() {
            @Override
            public void onResponse(BankBean categoryResponseModel) {
                view.hideProgress();
                if (categoryResponseModel != null) {
                    if (categoryResponseModel.getStatus().equalsIgnoreCase(ConstantLib.RESPONSE_SUCCESS))
                        view.getBanks(categoryResponseModel);
                    else {
                        CommonUtils.showSnakeBar(mContext, categoryResponseModel.getMessage());
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
        },headers,type,"");
    }

    public void subscribePlan(RequestBody finalRequestBody, Map<String, String> headers) {
        if (!CheckNetworkState.isOnline(mContext)) {
            CommonUtils.showSnakeBar(mContext, mContext.getString(R.string.no_internet));
            return;
        }
        view.showProgress();
        ApiController apiController = ApiController.getInstance();
        apiController.callMultpleFileUpload(RequestType.REQ_SUBSCRIPTION_PLAN, new ResponseHandler<BaseResponseModel>() {
            @Override
            public void onResponse(BaseResponseModel baseResponseModel) {
                view.hideProgress();
                if (baseResponseModel != null) {
                    if (baseResponseModel.getStatus().equalsIgnoreCase(ConstantLib.RESPONSE_SUCCESS)) {
                        view.onSubscribed();
                    } else
                        CommonUtils.showToast(mContext, baseResponseModel.getMessage());
                } else {
                    CommonUtils.showSnakeBar(mContext, mContext.getString(R.string.server_error));
                }

            }

            @Override
            public void onError() {
                view.hideProgress();
                CommonUtils.showSnakeBar(mContext, mContext.getString(R.string.server_error));

            }
        }, finalRequestBody, headers);
    }
}
