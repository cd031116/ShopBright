package com.zhl.huiqu.personal;

import java.io.Serializable;

/**
 * Created by dw on 2017/8/18.
 */

public class UrLikeEntity implements Serializable {

    String imgUrl;
    String tag;
    String dpNum;
    String price;
    String address;
    String touristMs;

    public UrLikeEntity(String imgUrl, String tag, String dpNum, String price, String address, String touristMs) {
        this.imgUrl = imgUrl;
        this.tag = tag;
        this.dpNum = dpNum;
        this.price = price;
        this.address = address;
        this.touristMs = touristMs;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getDpNum() {
        return dpNum;
    }

    public void setDpNum(String dpNum) {
        this.dpNum = dpNum;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTouristMs() {
        return touristMs;
    }

    public void setTouristMs(String touristMs) {
        this.touristMs = touristMs;
    }
}
