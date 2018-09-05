package com.superlifesecretcode.app.ui.book.five;

import com.superlifesecretcode.app.data.model.BaseResponseModel;
import com.superlifesecretcode.app.ui.base.BaseView;
import com.superlifesecretcode.app.ui.book.forth.StoreBean;

public interface FifthBookView extends BaseView{

    void getBanks(BankBean bankBean);
    void onOrderSuccess(BaseResponseModel bankBean);
}
