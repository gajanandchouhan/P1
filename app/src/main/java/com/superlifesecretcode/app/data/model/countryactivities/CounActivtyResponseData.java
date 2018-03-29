package com.superlifesecretcode.app.data.model.countryactivities;

import java.util.List;

/**
 * Created by Divya on 29-03-2018.
 */

public class CounActivtyResponseData {

    List<CountryActivityInfoModel> today;
    List<CountryActivityInfoModel> upcoming;

    public List<CountryActivityInfoModel> getToday() {
        return today;
    }

    public void setToday(List<CountryActivityInfoModel> today) {
        this.today = today;
    }

    public List<CountryActivityInfoModel> getUpcoming() {
        return upcoming;
    }

    public void setUpcoming(List<CountryActivityInfoModel> upcoming) {
        this.upcoming = upcoming;
    }
}
