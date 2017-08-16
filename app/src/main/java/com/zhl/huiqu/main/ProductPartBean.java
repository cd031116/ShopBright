package com.zhl.huiqu.main;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/8/11.
 */

public class ProductPartBean implements Serializable {
    private int shop_spot_id; //品类id

    private String title;


    private String content;

    private String   thumb;

    private int comment_num;

    private String shop_price;

    private String market_price;


    private String desc;

    private String csr;

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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getComment_num() {
        return comment_num;
    }

    public void setComment_num(int comment_num) {
        this.comment_num = comment_num;
    }

    public String getShop_price() {
        return shop_price;
    }

    public void setShop_price(String shop_price) {
        this.shop_price = shop_price;
    }

    public String getMarket_price() {
        return market_price;
    }

    public void setMarket_price(String market_price) {
        this.market_price = market_price;
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

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }
}
