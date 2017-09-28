package com.zhl.huiqu.main.team.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/9/27.
 */

public class TeamListInfo implements Serializable{
    private String spot_team_id;
    private String city;
    private String out;
    private String title;
    private String thumb;
    private String desc;
    private String shop_price;
    private String theme;
    private String day_time;
    private String csr;
    private boolean isup;

    public boolean isup() {
        return isup;
    }

    public void setIsup(boolean isup) {
        this.isup = isup;
    }

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

    public String getOut() {
        return out;
    }

    public void setOut(String out) {
        this.out = out;
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

    public String getDay_time() {
        return day_time;
    }

    public void setDay_time(String day_time) {
        this.day_time = day_time;
    }

    public String getCsr() {
        return csr;
    }

    public void setCsr(String csr) {
        this.csr = csr;
    }
}
