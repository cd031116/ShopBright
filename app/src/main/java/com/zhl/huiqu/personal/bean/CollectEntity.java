package com.zhl.huiqu.personal.bean;

import java.io.Serializable;

/**
 * Created by dw on 2017/8/14.
 */

public class CollectEntity implements Serializable{

    /**
     * product_id : 1
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

    private int product_id;
    private String type;
    private String city;
    private String grade;
    private String theme;
    private String title;
    private String thumb;
    private String desc;
    private int add_time;
    private String shop_price;

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

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
}
