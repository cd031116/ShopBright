package com.zhl.huiqu.main.team.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/10/24.
 */

public class TeamOrderPriceBean implements Serializable {
    private String productId;
    private String productTitle;
    private String productTime;
    private int productAdultNum;
    private int productChildNum;
    private int productAdultPrice;
    private int productChildPrice;

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductTitle() {
        return productTitle;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }

    public String getProductTime() {
        return productTime;
    }

    public void setProductTime(String productTime) {
        this.productTime = productTime;
    }

    public int getProductAdultNum() {
        return productAdultNum;
    }

    public void setProductAdultNum(int productAdultNum) {
        this.productAdultNum = productAdultNum;
    }

    public int getProductChildNum() {
        return productChildNum;
    }

    public void setProductChildNum(int productChildNum) {
        this.productChildNum = productChildNum;
    }

    public int getProductAdultPrice() {
        return productAdultPrice;
    }

    public void setProductAdultPrice(int productAdultPrice) {
        this.productAdultPrice = productAdultPrice;
    }

    public int getProductChildPrice() {
        return productChildPrice;
    }

    public void setProductChildPrice(int productChildPrice) {
        this.productChildPrice = productChildPrice;
    }
}
