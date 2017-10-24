package com.zhl.huiqu.main.team.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/10/23.
 */

public class TeamPriceBean implements Serializable{

    /**
     * departDate : 2017-10-24
     * retailAdultPrice : 2438
     * retailChildPrice : 636
     */

    private String departDate;
    private int retailAdultPrice;
    private int retailChildPrice;

    public String getDepartDate() {
        return departDate;
    }

    public void setDepartDate(String departDate) {
        this.departDate = departDate;
    }

    public int getRetailAdultPrice() {
        return retailAdultPrice;
    }

    public void setRetailAdultPrice(int retailAdultPrice) {
        this.retailAdultPrice = retailAdultPrice;
    }

    public int getRetailChildPrice() {
        return retailChildPrice;
    }

    public void setRetailChildPrice(int retailChildPrice) {
        this.retailChildPrice = retailChildPrice;
    }
}
