package com.zhl.huiqu.main.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/8/15.
 */

public class HotelInfo implements Serializable{

    private String shop_hotel_type_id;
    private String type;

    public String getShop_hotel_type_id() {
        return shop_hotel_type_id;
    }

    public void setShop_hotel_type_id(String shop_hotel_type_id) {
        this.shop_hotel_type_id = shop_hotel_type_id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
