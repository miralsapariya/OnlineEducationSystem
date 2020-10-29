package com.onlineeducationsyestem.model;

import java.io.Serializable;

public class Country  implements Serializable {

    private String code;
    private String countryName;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }
}
