package com.superlifesecretcode.app.ui.book.forth;

import com.superlifesecretcode.app.data.model.country.CountryResponseData;
import com.superlifesecretcode.app.ui.base.BaseView;

import java.util.List;

public interface ForthBookView extends BaseView{


    void getStores(StoreBean categoryResponseModel);
    void getOldAddress(AddressBean categoryResponseModel);
    void setStateData(List<CountryResponseData> data);
    void setCities(List<CountryResponseData> data);

}
