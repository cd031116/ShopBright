package com.zhl.huiqu.main.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/11/10.
 */

public class MainTick implements Serializable{

    private String shop_spot_id;
    private String title;
    private String thumb;
    private String city;

    private String grade;
    private String theme;
    private String shop_price;
    private String desc;

    private String csr;
    private String today;

    public String getShop_spot_id() {
        return shop_spot_id;
    }

    public void setShop_spot_id(String shop_spot_id) {
        this.shop_spot_id = shop_spot_id;
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

    public String getShop_price() {
        return shop_price;
    }

    public void setShop_price(String shop_price) {
        this.shop_price = shop_price;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getCsr() {
        return csr;
    }

    public void setCsr(String csr) {
        this.csr = csr;
    }

    public String getToday() {
        return today;
    }

    public void setToday(String today) {
        this.today = today;
    }
}
