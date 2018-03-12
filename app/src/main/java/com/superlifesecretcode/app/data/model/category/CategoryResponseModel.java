package com.superlifesecretcode.app.data.model.category;

import com.superlifesecretcode.app.data.model.BaseResponseModel;

import java.util.List;

/**
 * Created by Divya on 12-03-2018.
 */

public class CategoryResponseModel extends BaseResponseModel {

    private List<CategoryResponseData> data;
    private List<BannerModel> banners;

    public List<CategoryResponseData> getData() {
        return data;
    }

    public void setData(List<CategoryResponseData> data) {
        this.data = data;
    }

    public List<BannerModel> getBanners() {
        return banners;
    }

    public void setBanners(List<BannerModel> banners) {
        this.banners = banners;
    }
}
