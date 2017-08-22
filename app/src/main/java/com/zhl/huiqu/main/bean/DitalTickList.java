package com.zhl.huiqu.main.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/8/15.
 */

public class DitalTickList implements Serializable{
    private String shop_ticket_id;
    private String spot_id;

    private String t_id;

    private String t_spot_id;


    private String title;

    private String desc;

    private String shop_price;

    private String market_price;

    public String getShop_ticket_id() {
        return shop_ticket_id;
    }

    public void setShop_ticket_id(String shop_ticket_id) {
        this.shop_ticket_id = shop_ticket_id;
    }

    public String getSpot_id() {
        return spot_id;
    }

    public void setSpot_id(String spot_id) {
        this.spot_id = spot_id;
    }

    public String getT_id() {
        return t_id;
    }

    public void setT_id(String t_id) {
        this.t_id = t_id;
    }

    public String getT_spot_id() {
        return t_spot_id;
    }

    public void setT_spot_id(String t_spot_id) {
        this.t_spot_id = t_spot_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getShop_price() {
        return shop_price;
    }

    public void setShop_price(String shop_price) {
        this.shop_price = shop_price;
    }

    public String getMarket_price() {
        return market_price;
    }

    public void setMarket_price(String market_price) {
        this.market_price = market_price;
    }
}
