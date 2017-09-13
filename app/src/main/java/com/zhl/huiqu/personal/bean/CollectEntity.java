package com.zhl.huiqu.personal.bean;

import java.io.Serializable;

/**
 * Created by dw on 2017/8/14.
 */

public class CollectEntity implements Serializable{

    /**
     * type : ZHL
     * city : 长沙
     * grade : 4A景区
     * theme : 户外探险
     * title : 石燕湖生态旅游景区
     * thumb : http://pic4.40017.cn/scenery/destination/2016/10/27/16/mV2dm9_740x350_00.jpg
     * desc : 城市绿肺，天然大氧吧。
     * add_time : 1501576677
     * shop_price : 90.00
     */

    private String type;
    private String city;
    private String grade;
    private String theme;
    private String title;
    private String thumb;
    private String desc;
    private int add_time;
    private String shop_price;
    /**
     * shop_spot_id : 1
     * csr : 1
     */

    private int shop_spot_id;
    private int csr;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
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

    public int getAdd_time() {
        return add_time;
    }

    public void setAdd_time(int add_time) {
        this.add_time = add_time;
    }

    public String getShop_price() {
        return shop_price;
    }

    public void setShop_price(String shop_price) {
        this.shop_price = shop_price;
    }

    public int getShop_spot_id() {
        return shop_spot_id;
    }

    public void setShop_spot_id(int shop_spot_id) {
        this.shop_spot_id = shop_spot_id;
    }

    public int getCsr() {
        return csr;
    }

    public void setCsr(int csr) {
        this.csr = csr;
    }
}
