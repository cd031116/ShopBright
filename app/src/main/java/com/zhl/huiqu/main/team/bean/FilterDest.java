package com.zhl.huiqu.main.team.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/9/29.
 */

public class FilterDest implements Serializable{
    private String desCityName;
    private String desCityId;
    private  boolean select;

    public boolean isSelect() {
        return select;
    }

    public void setSelect(boolean select) {
        this.select = select;
    }

    public String getDesCityName() {
        return desCityName;
    }

    public void setDesCityName(String desCityName) {
        this.desCityName = desCityName;
    }

    public String getDesCityId() {
        return desCityId;
    }

    public void setDesCityId(String desCityId) {
        this.desCityId = desCityId;
    }
}
