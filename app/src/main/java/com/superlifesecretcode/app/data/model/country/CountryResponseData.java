package com.superlifesecretcode.app.data.model.country;

/**
 * Created by Divya on 01-03-2018.
 */

public class CountryResponseData {
    private String id;
    private String countrycode;
    private String name;
    private String phonecode;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCountrycode() {
        return countrycode;
    }

    public void setCountrycode(String countrycode) {
        this.countrycode = countrycode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhonecode() {
        return phonecode;
    }

    public void setPhonecode(String phonecode) {
        this.phonecode = phonecode;
    }
}
