package com.zhl.huiqu.main.team.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/10/21.
 */

public class GoalProInfo implements Serializable{
    private String desProvinceId;

    private String desProvinceName;

    private boolean ischeck;

    public boolean ischeck() {
        return ischeck;
    }

    public void setIscheck(boolean ischeck) {
        this.ischeck = ischeck;
    }

    public String getDesProvinceId() {
        return desProvinceId;
    }

    public void setDesProvinceId(String desProvinceId) {
        this.desProvinceId = desProvinceId;
    }

    public String getDesProvinceName() {
        return desProvinceName;
    }

    public void setDesProvinceName(String desProvinceName) {
        this.desProvinceName = desProvinceName;
    }
}
