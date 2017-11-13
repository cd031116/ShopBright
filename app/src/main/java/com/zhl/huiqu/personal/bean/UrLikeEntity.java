package com.zhl.huiqu.personal.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by dw on 2017/8/18.
 */

public class UrLikeEntity implements Serializable {
    /**
     * shop_spot_id : 545
     * title : 鸣沙山月牙泉
     * thumb : http://pic4.40017.cn/scenery/destination/2016/09/12/16/lC9sM5_740x350_00.jpg
     * city : 酒泉
     * grade : 5A景区
     * theme : 河流湖泊
     * shop_price : 20.00
     * desc : 有这样一片土地，孤傲、冷漠、自由、热烈
     * sort : 100
     * csr : 暂无评价
     */
    private int shop_spot_id;
    private String title;
    private String thumb;
    private String city;
    private String grade;
    private String theme;
    private String shop_price;
    private String desc;
    private int sort;
    private String csr;
    /**
     * comment_num : 0
     */

    private int comment_num;

//    public UrLikeEntity(String imgUrl, String tag, String dpNum, String price, String address, String touristMs) {
//        this.imgUrl = imgUrl;
//        this.tag = tag;
//        this.dpNum = dpNum;
//        this.price = price;
//        this.address = address;
//        this.touristMs = touristMs;
//    }

    public int getShop_spot_id() {
        return shop_spot_id;
    }

    public void setShop_spot_id(int shop_spot_id) {
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

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public String getCsr() {
        return csr;
    }

    public void setCsr(String csr) {
        this.csr = csr;
    }

    public int getComment_num() {
        return comment_num;
    }

    public void setComment_num(int comment_num) {
        this.comment_num = comment_num;
    }
}
