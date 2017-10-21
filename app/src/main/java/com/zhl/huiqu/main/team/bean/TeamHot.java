package com.zhl.huiqu.main.team.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/10/20.
 */

public class TeamHot implements Serializable{
    private String productId;
    private String productName;
    private String thumb;

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

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }
}
