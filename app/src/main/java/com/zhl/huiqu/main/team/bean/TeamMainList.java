package com.zhl.huiqu.main.team.bean;

import java.io.Serializable;

/**
 * Created by lyj on 2017/9/29.
 */

public class TeamMainList implements Serializable{
    private String productId ;
    private String productName ;

    private String priceAdultMin ;

    private String duration ;
    private String departCitysName ;

    private String thumb ;

    private String commentNum ;

    private String csr ;

    private boolean isup;

    public boolean isup() {
        return isup;
    }

    public void setIsup(boolean isup) {
        this.isup = isup;
    }

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
