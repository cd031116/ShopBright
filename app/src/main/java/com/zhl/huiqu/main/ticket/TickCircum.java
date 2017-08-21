package com.zhl.huiqu.main.ticket;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/8/19.
 */

public class TickCircum implements Serializable{

    private String spot_team_id;
    private String city;
    private String title;
    private String thumb;
    private String desc;
    private String shop_price;
    private String theme;
    private String level;
    private String csr;


    public String getSpot_team_id() {
        return spot_team_id;
    }

    public void setSpot_team_id(String spot_team_id) {
        this.spot_team_id = spot_team_id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
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

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getCsr() {
        return csr;
    }

    public void setCsr(String csr) {
        this.csr = csr;
    }
}
