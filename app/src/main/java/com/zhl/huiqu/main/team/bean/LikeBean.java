package com.zhl.huiqu.main.team.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/9/28.
 */

public class LikeBean implements Serializable {

    /**
     * spot_team_id : 6
     * city : 张家界
     * out : 长沙
     * title : 张家界-黄龙洞-天门山-天门洞-玻璃栈道2日游
     * thumb : /upload/around/20170907/4d793f1f2a9851831501f6b7c7324eaf.jpg
     * shop_price : 256.00
     * day_time : 2
     * favor : 0
     * csr : 0%
     */

    private int spot_team_id;
    private String city;
    private String out;
    private String title;
    private String thumb;
    private String shop_price;
    private String day_time;
    private int favor;
    private String csr;

    public int getSpot_team_id() {
        return spot_team_id;
    }

    public void setSpot_team_id(int spot_team_id) {
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

    public String getShop_price() {
        return shop_price;
    }

    public void setShop_price(String shop_price) {
        this.shop_price = shop_price;
    }

    public String getDay_time() {
        return day_time;
    }

    public void setDay_time(String day_time) {
        this.day_time = day_time;
    }

    public int getFavor() {
        return favor;
    }

    public void setFavor(int favor) {
        this.favor = favor;
    }

    public String getCsr() {
        return csr;
    }

    public void setCsr(String csr) {
        this.csr = csr;
    }
}
