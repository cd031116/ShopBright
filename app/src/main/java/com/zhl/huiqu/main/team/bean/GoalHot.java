package com.zhl.huiqu.main.team.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/10/21.
 */

public class GoalHot implements Serializable{
    private String desCityId;

    private String cityImg;

    private String desCityName;

    public String getDesCityId() {
        return desCityId;
    }

    public void setDesCityId(String desCityId) {
        this.desCityId = desCityId;
    }

    public String getCityImg() {
        return cityImg;
    }

    public void setCityImg(String cityImg) {
        this.cityImg = cityImg;
    }

    public String getDesCityName() {
        return desCityName;
    }

    public void setDesCityName(String desCityName) {
        this.desCityName = desCityName;
    }
}
