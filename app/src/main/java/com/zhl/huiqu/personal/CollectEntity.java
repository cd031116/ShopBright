package com.zhl.huiqu.personal;

import java.io.Serializable;

/**
 * Created by dw on 2017/8/14.
 */

public class CollectEntity implements Serializable{
    String imgUrl;
    String collectWhere;
    String collectMs;
    String collectAddress;
    String collectJb;
    String collectPf;
    String collectPrice;
    String collectTag;

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getCollectWhere() {
        return collectWhere;
    }

    public void setCollectWhere(String collectWhere) {
        this.collectWhere = collectWhere;
    }

    public String getCollectMs() {
        return collectMs;
    }

    public void setCollectMs(String collectMs) {
        this.collectMs = collectMs;
    }

    public String getCollectAddress() {
        return collectAddress;
    }

    public void setCollectAddress(String collectAddress) {
        this.collectAddress = collectAddress;
    }

    public String getCollectJb() {
        return collectJb;
    }

    public void setCollectJb(String collectJb) {
        this.collectJb = collectJb;
    }

    public String getCollectPf() {
        return collectPf;
    }

    public void setCollectPf(String collectPf) {
        this.collectPf = collectPf;
    }

    public String getCollectPrice() {
        return collectPrice;
    }

    public void setCollectPrice(String collectPrice) {
        this.collectPrice = collectPrice;
    }

    public String getCollectTag() {
        return collectTag;
    }

    public void setCollectTag(String collectTag) {
        this.collectTag = collectTag;
    }
}
