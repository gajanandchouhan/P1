package com.superlifesecretcode.app.data.model.banner;

import com.superlifesecretcode.app.data.model.BaseResponseModel;
import com.superlifesecretcode.app.data.model.category.BannerModel;

import java.util.List;

/**
 * Created by Divya on 07-04-2018.
 */

public class BannerResponseModel extends BaseResponseModel {
    List<BannerModel> banners;

    public List<BannerModel> getData() {
        return banners;
    }

    public void setData(List<BannerModel> data) {
        this.banners = data;
    }
}
