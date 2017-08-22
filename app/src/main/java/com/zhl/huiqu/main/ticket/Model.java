package com.zhl.huiqu.main.ticket;

import java.io.Serializable;

/**
 * Created by lyj on 2017/8/12.
 */

public class Model implements Serializable{
    private String shop_spot_attr_id;
    private String name;
    private String img;

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

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
