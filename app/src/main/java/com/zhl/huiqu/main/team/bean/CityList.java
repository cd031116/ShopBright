package com.zhl.huiqu.main.team.bean;

import java.io.Serializable;

/**
 * Created by lyj on 2017/9/27.
 */

public class CityList implements Serializable {
    private String desCityName;
    private String desCityId;

    public String getDesCityId() {
        return desCityId;
    }

    public void setDesCityId(String desCityId) {
        this.desCityId = desCityId;
    }

    public String getDesCityName() {
        return desCityName;
    }

    public void setDesCityName(String desCityName) {
        this.desCityName = desCityName;
    }
}
