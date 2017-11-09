package com.zhl.huiqu.personal.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/11/9.
 */

public class ColletTeam implements Serializable{
    private String productId;
    private String productName;

    private String priceAdultMin;

    private String duration;

    private String departCitysName;
    private String desCityName;
    private String thumb;
    private String commentNum;
    private String csr;

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getPriceAdultMin() {
        return priceAdultMin;
    }

    public void setPriceAdultMin(String priceAdultMin) {
        this.priceAdultMin = priceAdultMin;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getDepartCitysName() {
        return departCitysName;
    }

    public void setDepartCitysName(String departCitysName) {
        this.departCitysName = departCitysName;
    }

    public String getDesCityName() {
        return desCityName;
    }

    public void setDesCityName(String desCityName) {
        this.desCityName = desCityName;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public String getCommentNum() {
        return commentNum;
    }

    public void setCommentNum(String commentNum) {
        this.commentNum = commentNum;
    }

    public String getCsr() {
        return csr;
    }

    public void setCsr(String csr) {
        this.csr = csr;
    }
}
