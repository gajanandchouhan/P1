package com.superlifesecretcode.app.ui.studygroup.paymentproof;

import com.superlifesecretcode.app.ui.base.BaseView;
import com.superlifesecretcode.app.ui.book.five.BankBean;

interface PaymentProofView extends BaseView {
    void getBanks(BankBean bankBean);
}
