package com.zhl.huiqu.main.team.bean;

import java.io.Serializable;

/**
 * Created by lyj on 2017/9/27.
 */

public class CityList implements Serializable {
    private String city_id;
    private String city;

    public String getCity_id() {
        return city_id;
    }

    public void setCity_id(String city_id) {
        this.city_id = city_id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
