package com.zhl.huiqu.main.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/8/22.
 */

public class GradeInfo implements Serializable{
    private String shop_spot_attr_id;
    private String name;
    private boolean isselect;

    public String getShop_spot_attr_id() {
        return shop_spot_attr_id;
    }

    public void setShop_spot_attr_id(String shop_spot_attr_id) {
        this.shop_spot_attr_id = shop_spot_attr_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isselect() {
        return isselect;
    }

    public void setIsselect(boolean isselect) {
        this.isselect = isselect;
    }
}
