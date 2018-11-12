package com.superlifesecretcode.app.data.model.collectiontypes;

import com.superlifesecretcode.app.data.model.BaseResponseModel;

import java.util.List;

public class CollectionTypeResponseModel extends BaseResponseModel {
    private List<CollectionTypeData> data;

    public List<CollectionTypeData> getData() {
        return data;
    }

    public void setData(List<CollectionTypeData> data) {
        this.data = data;
    }
}
