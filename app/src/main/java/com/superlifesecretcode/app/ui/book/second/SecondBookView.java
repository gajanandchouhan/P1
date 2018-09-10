package com.superlifesecretcode.app.ui.book.second;

import com.superlifesecretcode.app.ui.base.BaseView;
import com.superlifesecretcode.app.ui.book.first.BookList;

public interface SecondBookView extends BaseView {
    void getDeliveryCharges(Delivery categoryResponseModel);
}
